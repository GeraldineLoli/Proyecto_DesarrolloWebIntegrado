package com.proyecto.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.model.Pedido;
import com.proyecto.app.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> obtenerTodos() {
        return pedidoService.todos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPorId(@PathVariable int id) {
        return pedidoService.obtenerPedido(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> obtenerPorUsuario(@PathVariable int usuarioId) {
        return pedidoService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public String crearPedido(@RequestBody Pedido pedido) {
        pedidoService.agregarPedido(pedido);
        return "Pedido creado exitosamente";
    }

    @PutMapping("/{id}")
    public String actualizarPedido(@PathVariable int id, @RequestBody Pedido pedido) {
        pedidoService.actualizarPedido(id, pedido);
        return "Pedido actualizado exitosamente";
    }

    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable int id) {
        pedidoService.eliminarPedido(id);
        return "Pedido eliminado exitosamente";
    }
}
