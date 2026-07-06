package com.proyecto.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.dto.CambioEstadoRequest;
import com.proyecto.app.model.Pedido;
import com.proyecto.app.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    public PedidoController(IPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<String> crearPedido(@RequestBody Pedido pedido) {
        pedidoService.crearPedido(pedido);
        return new ResponseEntity<>("Pedido creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable("id") Long id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> obtenerPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuarioId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPedido(@PathVariable("id") Long id, @RequestBody Pedido pedido) {
        pedido.setId(id);
        pedidoService.actualizarPedido(pedido);
        return new ResponseEntity<>("Pedido actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPedido(@PathVariable("id") Long id) {
        pedidoService.eliminarPedido(id);
        return new ResponseEntity<>("Pedido eliminado exitosamente", HttpStatus.OK);
    }

    /**
     * PATCH /api/pedidos/{id}/estado
     * Cambia únicamente el estado del pedido. Solo accesible por ADMIN.
     * Body: { "estado": "APROBADO" }
     * Estados válidos: PENDIENTE, APROBADO, PAGADO, CANCELADO
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable("id") Long id,
            @RequestBody CambioEstadoRequest request) {

        // Validar que el estado sea uno de los permitidos
        if (!request.esValido()) {
            return ResponseEntity
                .badRequest()
                .body("Estado inválido. Valores permitidos: " + CambioEstadoRequest.getEstadosValidos());
        }

        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        pedido.setEstado(request.getEstado().toUpperCase());
        Pedido actualizado = pedidoService.actualizarPedido(pedido);

        // Devuelve el pedido actualizado para que el frontend no necesite un segundo GET
        return ResponseEntity.ok(actualizado);
    }
}
