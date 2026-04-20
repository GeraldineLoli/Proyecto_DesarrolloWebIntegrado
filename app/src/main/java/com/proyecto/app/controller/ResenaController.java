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

import com.proyecto.app.model.Resena;
import com.proyecto.app.service.ResenaService;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // Endpoint para obtener todas las reseñas
    @GetMapping
    public List<Resena> obtenerTodas() {
        return resenaService.todas();
    }

    // Endpoint para obtener una reseña por su ID
    @GetMapping("/{id}")
    public Resena obtenerPorId(@PathVariable int id) {
        return resenaService.obtenerResena(id);
    }

    // Endpoint para obtener reseñas de un evento
    @GetMapping("/evento/{eventoId}")
    public List<Resena> obtenerPorEvento(@PathVariable int eventoId) {
        return resenaService.obtenerPorEvento(eventoId);
    }

    // Endpoint para obtener reseñas de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Resena> obtenerPorUsuario(@PathVariable int usuarioId) {
        return resenaService.obtenerPorUsuario(usuarioId);
    }

    // Endpoint para obtener el promedio de calificaciones de un evento
    @GetMapping("/evento/{eventoId}/promedio")
    public double obtenerPromedioEvento(@PathVariable int eventoId) {
        return resenaService.obtenerPromedioEvento(eventoId);
    }

    // Endpoint para agregar una nueva reseña
    @PostMapping
    public void agregarResena(@RequestBody Resena resena) {
        resenaService.agregarResena(resena);
    }

    // Endpoint para actualizar una reseña existente
    @PutMapping("/{id}")
    public void actualizarResena(@PathVariable int id, @RequestBody Resena resena) {
        resenaService.actualizarResena(id, resena);
    }

    // Endpoint para eliminar una reseña
    @DeleteMapping("/{id}")
    public void eliminarResena(@PathVariable int id) {
        resenaService.eliminarResena(id);
    }
}
