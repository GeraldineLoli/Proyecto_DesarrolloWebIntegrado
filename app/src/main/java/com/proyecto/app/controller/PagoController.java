package com.proyecto.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.model.Pago;
import com.proyecto.app.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final PagoService pagoService;
    
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }
    
    @GetMapping
    public List<Pago> obtenerTodos() {
        return pagoService.todos();
    }
    
    @GetMapping("/{id}")
    public Pago obtenerPorId(@PathVariable int id) {
        return pagoService.obtenerPago(id);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Pago> obtenerPorUsuario(@PathVariable int usuarioId) {
        return pagoService.obtenerPorUsuario(usuarioId);
    }
    
    @GetMapping("/pedido/{pedidoId}")
    public List<Pago> obtenerPorPedido(@PathVariable int pedidoId) {
        return pagoService.obtenerPorPedido(pedidoId);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Pago> obtenerPorEstado(@PathVariable String estado) {
        return pagoService.obtenerPorEstado(estado);
    }
    
    @GetMapping("/usuario/{usuarioId}/total-recaudado")
    public double obtenerTotalRecaudado(@PathVariable int usuarioId) {
        return pagoService.obtenerTotalRecaudadoPorUsuario(usuarioId);
    }
    
    @PostMapping("/procesar")
    public Pago procesarPago(@RequestParam int pedidoId, @RequestParam int usuarioId, @RequestParam String metodoPago,@RequestParam(required = false) String numeroTarjeta) {
        return pagoService.procesarPago(pedidoId, usuarioId, metodoPago, numeroTarjeta);
    }
    
    @PostMapping("/{id}/reembolsar")
    public Pago reembolsarPago(@PathVariable int id) {
        return pagoService.reembolsarPago(id);
    }
    
    @PostMapping
    public void crearPago(@RequestBody Pago pago) {
        pagoService.agregarPago(pago);
    }
    
    @PutMapping("/{id}")
    public void actualizarPago(@PathVariable int id, @RequestBody Pago pago) {
        pagoService.actualizarPago(id, pago);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable int id) {
        pagoService.eliminarPago(id);
    }
}