package com.proyecto.app.service;

import com.proyecto.app.model.Pedido;
import com.proyecto.app.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido crearPedido(Pedido pedido) {
        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(LocalDateTime.now());
        }
        if (pedido.getCodigoPedido() == null || pedido.getCodigoPedido().isEmpty()) {
            pedido.setCodigoPedido(generarCodigoPedido());
        }
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido obtenerPedidoPorId(Long id) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        return optionalPedido.orElse(null);
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Pedido actualizarPedido(Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(pedido.getId()).orElse(null);
        if (pedidoExistente != null) {
            pedidoExistente.setUsuarioId(pedido.getUsuarioId());
            pedidoExistente.setEstado(pedido.getEstado());
            pedidoExistente.setTotal(pedido.getTotal());
            pedidoExistente.setCodigoPedido(pedido.getCodigoPedido());
            pedidoExistente.setEntradaIds(pedido.getEntradaIds());
            return pedidoRepository.save(pedidoExistente);
        }
        return null;
    }

    @Override
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    private String generarCodigoPedido() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
