package com.proyecto.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.app.model.Pago;
import com.proyecto.app.model.Pedido;

@Service
public class PagoService {
    private List<Pago> pagos;
    private int nextId;
    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    
    // ✅ CONSTRUCTOR POR DEFECTO (obligatorio para Spring)
    public PagoService() {
        this.pagos = new ArrayList<>();
        this.nextId = 1;
        this.pedidoService = null;
        this.usuarioService = null;
        initData();
    }
    
    // ✅ Constructor con @Autowired para inyección de dependencias
    @Autowired
    public PagoService(PedidoService pedidoService, UsuarioService usuarioService) {
        this.pagos = new ArrayList<>();
        this.nextId = 1;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        initData();
    }
    
    // Constructor para testing
    public PagoService(PedidoService pedidoService, UsuarioService usuarioService, boolean inicializarDatos) {
        this.pagos = new ArrayList<>();
        this.nextId = 1;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        if (inicializarDatos) {
            initData();
        }
    }
    
    private void initData() {
        pagos.clear();
        
        Pago p1 = new Pago(1, 1, 1, 850.00, "VISA", "TXN-VISA-001");
        p1.setFechaPago(LocalDateTime.of(2026, 4, 1, 10, 35));
        p1.setNumeroTarjeta("****3623");
        
        Pago p2 = new Pago(2, 2, 2, 450.00, "MASTERCARD", "TXN-MC-002");
        p2.setFechaPago(LocalDateTime.of(2026, 4, 2, 18, 15));
        p2.setNumeroTarjeta("****9903");
        
        Pago p3 = new Pago(3, 3, 3, 320.00, "YAPE", "TXN-YAPE-003");
        p3.setFechaPago(LocalDateTime.of(2026, 4, 10, 12, 5));
        
        pagos.add(p1);
        pagos.add(p2);
        pagos.add(p3);
        nextId = 4;
    }
    
    public List<Pago> todos() {
        return new ArrayList<>(pagos);
    }
    
    public Pago obtenerPago(int id) {
        return pagos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public List<Pago> obtenerPorUsuario(int usuarioId) {
        return pagos.stream()
                .filter(p -> p.getUsuarioId() == usuarioId)
                .collect(Collectors.toList());
    }
    
    public List<Pago> obtenerPorPedido(int pedidoId) {
        return pagos.stream()
                .filter(p -> p.getPedidoId() == pedidoId)
                .collect(Collectors.toList());
    }
    
    public List<Pago> obtenerPorEstado(String estado) {
        return pagos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
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
    
    public Pago procesarPago(int pedidoId, int usuarioId, String metodoPago, String numeroTarjeta) {
        // Verificar que los servicios no sean null
        if (pedidoService == null || usuarioService == null) {
            throw new RuntimeException("Servicios no inicializados correctamente");
        }
        
        if (usuarioService.obtenerUsuario(usuarioId) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Pedido pedido = pedidoService.obtenerPedido(pedidoId);
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
            nuevoPago.setId(nextId++);
            pagos.add(nuevoPago);
            pedido.setEstado("PAGADO");
            return nuevoPago;
        } else {
            nuevoPago.setEstado("FALLIDO");
            throw new RuntimeException("El pago fue rechazado por el proveedor");
        }
    }
    
    public Pago reembolsarPago(int pagoId) {
        if (pedidoService == null) {
            throw new RuntimeException("Servicio de pedidos no inicializado");
        }
        
        Pago pago = obtenerPago(pagoId);
        if (pago == null) {
            throw new RuntimeException("Pago no encontrado");
        }
        
        if (!pago.getEstado().equals("COMPLETADO")) {
            throw new RuntimeException("Solo se pueden reembolsar pagos completados");
        }
        
        pago.setEstado("REEMBOLSADO");
        
        Pedido pedido = pedidoService.obtenerPedido(pago.getPedidoId());
        if (pedido != null) {
            pedido.setEstado("REEMBOLSADO");
        }
        
        return pago;
    }
    
    public void agregarPago(Pago pago) {
        if (usuarioService == null || pedidoService == null) {
            throw new RuntimeException("Servicios no inicializados correctamente");
        }
        
        if (usuarioService.obtenerUsuario(pago.getUsuarioId()) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Pedido pedido = pedidoService.obtenerPedido(pago.getPedidoId());
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
        
        pago.setId(nextId++);
        pagos.add(pago);
    }
    
    public void actualizarPago(int id, Pago pagoActualizado) {
        for (int i = 0; i < pagos.size(); i++) {
            if (pagos.get(i).getId() == id) {
                pagoActualizado.setId(id);
                pagos.set(i, pagoActualizado);
                return;
            }
        }
    }
    
    public void eliminarPago(int id) {
        pagos.removeIf(p -> p.getId() == id);
    }
    
    public double obtenerTotalRecaudadoPorUsuario(int usuarioId) {
        return obtenerPorUsuario(usuarioId).stream()
                .filter(p -> p.getEstado().equals("COMPLETADO"))
                .mapToDouble(Pago::getMonto)
                .sum();
    }
}