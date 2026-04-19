package com.proyecto.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.app.model.Entrada;
import com.proyecto.app.model.Pedido;

@Service
public class PedidoService {
    private List<Pedido> pedidos;
    private int nextId;
    private final EntradaService entradaService;
    private final UsuarioService usuarioService;

    public PedidoService(EntradaService entradaService, UsuarioService usuarioService) {
        this.pedidos = new ArrayList<>();
        this.nextId = 1;
        this.entradaService = entradaService;
        this.usuarioService = usuarioService;
        initData();
    }

    private void initData() {
        pedidos.clear();

        Pedido p1 = new Pedido(1, 1, LocalDateTime.of(2026, 4, 1, 10, 30), 850.00, "PAGADO", "ORD-2026-0001");
        p1.getEntradaIds().add(1);

        Pedido p2 = new Pedido(2, 2, LocalDateTime.of(2026, 4, 2, 18, 0), 450.00, "PAGADO", "ORD-2026-0002");
        p2.getEntradaIds().add(2);

        Pedido p3 = new Pedido(3, 3, LocalDateTime.of(2026, 4, 10, 12, 0), 320.00, "PENDIENTE", "ORD-2026-0003");
        p3.getEntradaIds().add(3);

        pedidos.add(p1);
        pedidos.add(p2);
        pedidos.add(p3);
        nextId = 4;
    }

    public List<Pedido> todos() {
        return new ArrayList<>(pedidos);
    }

    public Pedido obtenerPedido(int id) {
        return pedidos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public List<Pedido> obtenerPorUsuario(int usuarioId) {
        return pedidos.stream().filter(p -> p.getUsuarioId() == usuarioId).collect(Collectors.toList());
    }

    public void agregarPedido(Pedido pedido) {
        if (usuarioService.obtenerUsuario(pedido.getUsuarioId()) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (pedido.getEntradaIds() == null) {
            pedido.setEntradaIds(new ArrayList<>());
        }
        validarEntradasDelPedido(pedido);
        if (!pedido.getEntradaIds().isEmpty()) {
            double total = 0;
            for (Integer entradaId : pedido.getEntradaIds()) {
                Entrada e = entradaService.obtenerEntrada(entradaId);
                total += e.getPrecioPagado();
            }
            pedido.setTotal(total);
        }
        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(LocalDateTime.now());
        }
        if (pedido.getCodigoPedido() == null || pedido.getCodigoPedido().isEmpty()) {
            pedido.setCodigoPedido(generarCodigoPedido());
        }
        pedido.setId(nextId++);
        pedidos.add(pedido);
    }

    private void validarEntradasDelPedido(Pedido pedido) {
        for (Integer entradaId : pedido.getEntradaIds()) {
            Entrada entrada = entradaService.obtenerEntrada(entradaId);
            if (entrada == null) {
                throw new RuntimeException("Entrada no encontrada: " + entradaId);
            }
            if (entrada.getUsuarioId() != pedido.getUsuarioId()) {
                throw new RuntimeException("La entrada no pertenece al usuario del pedido");
            }
        }
    }

    private String generarCodigoPedido() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void actualizarPedido(int id, Pedido actualizado) {
        if (usuarioService.obtenerUsuario(actualizado.getUsuarioId()) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (actualizado.getEntradaIds() == null) {
            actualizado.setEntradaIds(new ArrayList<>());
        }
        validarEntradasDelPedido(actualizado);
        if (!actualizado.getEntradaIds().isEmpty()) {
            double total = 0;
            for (Integer entradaId : actualizado.getEntradaIds()) {
                Entrada e = entradaService.obtenerEntrada(entradaId);
                total += e.getPrecioPagado();
            }
            actualizado.setTotal(total);
        }
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId() == id) {
                actualizado.setId(id);
                pedidos.set(i, actualizado);
                return;
            }
        }
    }

    public void eliminarPedido(int id) {
        pedidos.removeIf(p -> p.getId() == id);
    }
}
