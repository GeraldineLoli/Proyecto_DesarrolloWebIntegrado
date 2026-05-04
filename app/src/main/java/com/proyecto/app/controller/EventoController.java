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

import com.proyecto.app.model.Evento;
import com.proyecto.app.service.IEventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    private final IEventoService eventoService;

    public EventoController(IEventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> obtenerTodos() {
        return eventoService.todos();
    }

    @GetMapping("/{id}")
    public Evento obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerEvento(id);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Evento> obtenerPorCategoria(@PathVariable String categoria) {
        return eventoService.obtenerPorCategoria(categoria);
    }

    @GetMapping("/proximos")
    public List<Evento> obtenerProximos() {
        return eventoService.obtenerEventosProximos();
    }

    @PostMapping
    public String crearEvento(@RequestBody Evento evento) {
        eventoService.agregarEvento(evento);
        return "Evento creado exitosamente";
    }

    @PutMapping("/{id}")
    public String actualizarEvento(@PathVariable Long id, @RequestBody Evento evento) {
        eventoService.actualizarEvento(id, evento);
        return "Evento actualizado exitosamente";
    }

    @DeleteMapping("/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return "Evento eliminado exitosamente";
    }
}