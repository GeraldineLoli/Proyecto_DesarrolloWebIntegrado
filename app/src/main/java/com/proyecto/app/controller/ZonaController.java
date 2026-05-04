package com.proyecto.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.model.Zona;
import com.proyecto.app.service.IZonaService;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {
    
    private final IZonaService zonaService;
    
    public ZonaController(IZonaService zonaService) {
        this.zonaService = zonaService;
    }
    
    @PostMapping
    public ResponseEntity<String> crearZona(@RequestBody Zona zona) {
        zonaService.crearZona(zona);
        return new ResponseEntity<>("Zona creada exitosamente", HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Zona> obtenerPorId(@PathVariable("id") Long id) {
        Zona zona = zonaService.obtenerZonaPorId(id);
        return new ResponseEntity<>(zona, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Zona>> obtenerTodas() {
        List<Zona> zonas = zonaService.obtenerTodasLasZonas();
        return new ResponseEntity<>(zonas, HttpStatus.OK);
    }
    
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Zona>> obtenerPorEvento(@PathVariable("eventoId") Long eventoId) {
        List<Zona> zonas = zonaService.obtenerZonasPorEvento(eventoId);
        return new ResponseEntity<>(zonas, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarZona(@PathVariable("id") Long id, @RequestBody Zona zona) {
        zona.setId(id);
        zonaService.actualizarZona(zona);
        return new ResponseEntity<>("Zona actualizada exitosamente", HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarZona(@PathVariable("id") Long id) {
        zonaService.eliminarZona(id);
        return new ResponseEntity<>("Zona eliminada exitosamente", HttpStatus.OK);
    }
    
    @PostMapping("/{id}/reservar")
    public ResponseEntity<Boolean> reservarEntrada(@PathVariable("id") Long id) {
        boolean reservado = zonaService.reservarEntrada(id);
        return new ResponseEntity<>(reservado, HttpStatus.OK);
    }
}
