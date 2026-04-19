package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proyecto.app.controller.PedidoController;
import com.proyecto.app.model.Pedido;
import com.proyecto.app.service.EntradaService;
import com.proyecto.app.service.EventoService;
import com.proyecto.app.service.PedidoService;
import com.proyecto.app.service.UsuarioService;
import com.proyecto.app.service.ZonaService;

class PedidoTest {

    private PedidoController pedidoController;
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        EventoService eventoService = new EventoService(true);
        ZonaService zonaService = new ZonaService(true);
        UsuarioService usuarioService = new UsuarioService(true);
        EntradaService entradaService = new EntradaService(zonaService, usuarioService, eventoService);
        pedidoService = new PedidoService(entradaService, usuarioService);
        pedidoController = new PedidoController(pedidoService);
    }

    @Test
    void whenObtenerTodos_shouldReturnInitialPedidos() {
        List<Pedido> result = pedidoController.obtenerTodos();
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void whenObtenerPorId_shouldReturnPedido() {
        Pedido p = pedidoController.obtenerPorId(1);
        assertNotNull(p);
        assertEquals("ORD-2026-0001", p.getCodigoPedido());
        assertEquals(1, p.getEntradaIds().size());
    }

    @Test
    void whenObtenerPorUsuario_shouldFilter() {
        List<Pedido> deUsuario1 = pedidoController.obtenerPorUsuario(1);
        assertEquals(1, deUsuario1.size());
    }

    @Test
    void whenCrearPedido_shouldAssignIdAndCodigo() {
        Pedido nuevo = new Pedido();
        nuevo.setUsuarioId(1);
        nuevo.setTotal(99.90);
        nuevo.setEstado("PENDIENTE");

        pedidoController.crearPedido(nuevo);

        Pedido guardado = pedidoController.obtenerPorId(4);
        assertNotNull(guardado);
        assertEquals(4, guardado.getId());
        assertNotNull(guardado.getCodigoPedido());
        assertNotNull(guardado.getFechaCreacion());
        assertEquals(99.90, guardado.getTotal(), 0.001);
    }

    @Test
    void whenActualizarPedido_shouldReplace() {
        Pedido actualizado = new Pedido(0, 1, LocalDateTime.now(), 1300.0, "CANCELADO", "ORD-2026-0001");
        actualizado.getEntradaIds().add(1);

        pedidoController.actualizarPedido(1, actualizado);

        assertEquals("CANCELADO", pedidoController.obtenerPorId(1).getEstado());
        assertEquals(850.0, pedidoController.obtenerPorId(1).getTotal(), 0.001);
    }

    @Test
    void whenEliminarPedido_shouldRemove() {
        assertNotNull(pedidoController.obtenerPorId(3));
        pedidoController.eliminarPedido(3);
        assertNull(pedidoController.obtenerPorId(3));
        assertEquals(2, pedidoController.obtenerTodos().size());
    }
}
