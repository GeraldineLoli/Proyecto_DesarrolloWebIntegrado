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

import com.proyecto.app.model.Zona;
import com.proyecto.app.service.ZonaService;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {
    private final ZonaService zonaService;
    
    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }
    
    @GetMapping
    public List<Zona> obtenerTodas() {
        return zonaService.todas();
    }
    
    @GetMapping("/{id}")
    public Zona obtenerPorId(@PathVariable int id) {
        return zonaService.obtenerZona(id);
    }
    
    @GetMapping("/evento/{eventoId}")
    public List<Zona> obtenerPorEvento(@PathVariable int eventoId) {
        return zonaService.obtenerZonasPorEvento(eventoId);
    }
    
    @PostMapping
    public String crearZona(@RequestBody Zona zona) {
        zonaService.agregarZona(zona);
        return "Zona creada exitosamente";
    }
    
    @PutMapping("/{id}")
    public String actualizarZona(@PathVariable int id, @RequestBody Zona zona) {
        zonaService.actualizarZona(id, zona);
        return "Zona actualizada exitosamente";
    }
    
    @DeleteMapping("/{id}")
    public String eliminarZona(@PathVariable int id) {
        zonaService.eliminarZona(id);
        return "Zona eliminada exitosamente";
    }
    
    @PostMapping("/{id}/reservar")
    public boolean reservarEntrada(@PathVariable int id) {
        return zonaService.reservarEntrada(id);
    }
}   
