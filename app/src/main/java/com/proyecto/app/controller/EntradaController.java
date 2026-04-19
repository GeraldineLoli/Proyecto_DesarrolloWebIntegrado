package com.proyecto.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.proyecto.app.model.Entrada;
import com.proyecto.app.service.EntradaService;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {
    private final EntradaService entradaService;
    
    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }
    
    @GetMapping
    public List<Entrada> obtenerTodas() {
        return entradaService.todas();
    }
    
    @GetMapping("/{id}")
    public Entrada obtenerPorId(@PathVariable int id) {
        return entradaService.obtenerEntrada(id);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Entrada> obtenerPorUsuario(@PathVariable int usuarioId) {
        return entradaService.obtenerPorUsuario(usuarioId);
    }
    
    @GetMapping("/evento/{eventoId}")
    public List<Entrada> obtenerPorEvento(@PathVariable int eventoId) {
        return entradaService.obtenerPorEvento(eventoId);
    }
    
    @GetMapping("/activas")
    public List<Entrada> obtenerActivas() {
        return entradaService.obtenerEntradasActivas();
    }
    
    @PostMapping("/comprar")
    public Entrada comprarEntrada(@RequestParam int usuarioId, @RequestParam int zonaId, @RequestParam String metodoPago) {
        return entradaService.comprarEntrada(usuarioId, zonaId, metodoPago);
    }
    
    @PutMapping("/{id}/cancelar")
    public void cancelarEntrada(@PathVariable int id) {
        entradaService.cancelarEntrada(id);
    }
    
    @PutMapping("/{id}/usar")
    public void usarEntrada(@PathVariable int id) {
        entradaService.usarEntrada(id);
    }
}