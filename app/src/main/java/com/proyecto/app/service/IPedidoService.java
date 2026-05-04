package com.proyecto.app.service;

import com.proyecto.app.model.Pedido;
import java.util.List;

public interface IPedidoService {
    Pedido crearPedido(Pedido pedido);
    Pedido obtenerPedidoPorId(Long id);
    List<Pedido> obtenerTodosLosPedidos();
    List<Pedido> obtenerPedidosPorUsuario(Long usuarioId);
    Pedido actualizarPedido(Pedido pedido);
    void eliminarPedido(Long id);
}
