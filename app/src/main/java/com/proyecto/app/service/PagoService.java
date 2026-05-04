package com.proyecto.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.proyecto.app.model.Pago;
import com.proyecto.app.model.Pedido;
import com.proyecto.app.repository.PagoRepository;

@Service
public class PagoService {
    
    private final PagoRepository pagoRepository;
    private final IPedidoService pedidoService;
    private final IUsuarioService usuarioService;
    
    public PagoService(PagoRepository pagoRepository, IPedidoService pedidoService, IUsuarioService usuarioService) {
        this.pagoRepository = pagoRepository;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
    }
    
    public List<Pago> todos() {
        return pagoRepository.findAll();
    }
    
    public Pago obtenerPago(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }
    
    public List<Pago> obtenerPorUsuario(Long usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId);
    }
    
    public List<Pago> obtenerPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }
    
    public List<Pago> obtenerPorEstado(String estado) {
        return pagoRepository.findByEstado(estado);
    }
    
    private String generarCodigoTransaccion() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private boolean procesarPagoConProveedor(double monto, String metodoPago) {
        if (monto <= 0) {
            return false;
        }
        if (metodoPago == null || metodoPago.isEmpty()) {
            return false;
        }
        return monto <= 10000;
    }
    
    public Pago procesarPago(Long pedidoId, Long usuarioId, String metodoPago, String numeroTarjeta) {
        if (usuarioService.obtenerUsuarioPorId(usuarioId) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Pedido pedido = pedidoService.obtenerPedidoPorId(pedidoId);
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado");
        }
        
        if (pedido.getUsuarioId() != usuarioId) {
            throw new RuntimeException("El pedido no pertenece al usuario");
        }
        
        if (pedido.getEstado().equals("PAGADO")) {
            throw new RuntimeException("El pedido ya ha sido pagado");
        }
        
        if (metodoPago == null || metodoPago.isEmpty()) {
            throw new RuntimeException("Método de pago no especificado");
        }
        
        double monto = pedido.getTotal();
        boolean pagoExitoso = procesarPagoConProveedor(monto, metodoPago);
        
        Pago nuevoPago = new Pago();
        nuevoPago.setPedidoId(pedidoId);
        nuevoPago.setUsuarioId(usuarioId);
        nuevoPago.setMonto(monto);
        nuevoPago.setMetodoPago(metodoPago);
        nuevoPago.setCodigoTransaccion(generarCodigoTransaccion());
        nuevoPago.setFechaPago(LocalDateTime.now());
        
        if (numeroTarjeta != null && (metodoPago.equals("VISA") || metodoPago.equals("MASTERCARD"))) {
            nuevoPago.setNumeroTarjeta(numeroTarjeta);
        }
        
        if (pagoExitoso) {
            nuevoPago.setEstado("COMPLETADO");
            Pago pagoGuardado = pagoRepository.save(nuevoPago);
            pedido.setEstado("PAGADO");
            return pagoGuardado;
        } else {
            nuevoPago.setEstado("FALLIDO");
            throw new RuntimeException("El pago fue rechazado por el proveedor");
        }
    }
    
    public Pago reembolsarPago(Long pagoId) {
        Pago pago = obtenerPago(pagoId);
        if (pago == null) {
            throw new RuntimeException("Pago no encontrado");
        }
        
        if (!pago.getEstado().equals("COMPLETADO")) {
            throw new RuntimeException("Solo se pueden reembolsar pagos completados");
        }
        
        pago.setEstado("REEMBOLSADO");
        
        Pedido pedido = pedidoService.obtenerPedidoPorId(pago.getPedidoId());
        if (pedido != null) {
            pedido.setEstado("REEMBOLSADO");
        }
        
        return pagoRepository.save(pago);
    }
    
    public Pago agregarPago(Pago pago) {
        if (usuarioService.obtenerUsuarioPorId(pago.getUsuarioId()) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Pedido pedido = pedidoService.obtenerPedidoPorId(pago.getPedidoId());
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado");
        }
        
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
        }
        
        if (pago.getCodigoTransaccion() == null || pago.getCodigoTransaccion().isEmpty()) {
            pago.setCodigoTransaccion(generarCodigoTransaccion());
        }
        
        if (pago.getEstado() == null) {
            pago.setEstado("COMPLETADO");
        }
        
        return pagoRepository.save(pago);
    }
    
    public Pago actualizarPago(Long id, Pago pagoActualizado) {
        Pago pagoExistente = pagoRepository.findById(id).orElse(null);
        if (pagoExistente != null) {
            pagoExistente.setPedidoId(pagoActualizado.getPedidoId());
            pagoExistente.setUsuarioId(pagoActualizado.getUsuarioId());
            pagoExistente.setMonto(pagoActualizado.getMonto());
            pagoExistente.setMetodoPago(pagoActualizado.getMetodoPago());
            pagoExistente.setEstado(pagoActualizado.getEstado());
            pagoExistente.setCodigoTransaccion(pagoActualizado.getCodigoTransaccion());
            pagoExistente.setNumeroTarjeta(pagoActualizado.getNumeroTarjeta());
            pagoExistente.setFechaPago(pagoActualizado.getFechaPago());
            pagoExistente.setComprobanteUrl(pagoActualizado.getComprobanteUrl());
            pagoExistente.setNotas(pagoActualizado.getNotas());
            return pagoRepository.save(pagoExistente);
        }
        return null;
    }
    
    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }
    
    public double obtenerTotalRecaudadoPorUsuario(Long usuarioId) {
        return obtenerPorUsuario(usuarioId).stream()
                .filter(p -> p.getEstado().equals("COMPLETADO"))
                .mapToDouble(Pago::getMonto)
                .sum();
    }
}