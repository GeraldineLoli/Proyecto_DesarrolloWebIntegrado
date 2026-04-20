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

import com.proyecto.app.model.Promocion;
import com.proyecto.app.service.PromocionService;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {
    private final PromocionService promocionService;
    
    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }
    
    @GetMapping
    public List<Promocion> obtenerTodas() {
        return promocionService.todas();
    }
    
    @GetMapping("/{id}")
    public Promocion obtenerPorId(@PathVariable int id) {
        return promocionService.obtenerPromocion(id);
    }
    
    @GetMapping("/codigo/{codigo}")
    public Promocion obtenerPorCodigo(@PathVariable String codigo) {
        return promocionService.obtenerPorCodigo(codigo);
    }
    
    @GetMapping("/evento/{eventoId}")
    public List<Promocion> obtenerPorEvento(@PathVariable int eventoId) {
        return promocionService.obtenerPorEvento(eventoId);
    }
    
    @GetMapping("/activas")
    public List<Promocion> obtenerActivas() {
        return promocionService.obtenerActivas();
    }
    
    @PostMapping
    public void crearPromocion(@RequestBody Promocion promocion) {
        promocionService.agregarPromocion(promocion);
    }
    
    @PutMapping("/{id}")
    public void actualizarPromocion(@PathVariable int id, @RequestBody Promocion promocion) {
        promocionService.actualizarPromocion(id, promocion);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarPromocion(@PathVariable int id) {
        promocionService.eliminarPromocion(id);
    }
    
    @PostMapping("/{id}/usar")
    public boolean usarPromocion(@PathVariable int id) {
        return promocionService.usarPromocion(id);
    }
}
