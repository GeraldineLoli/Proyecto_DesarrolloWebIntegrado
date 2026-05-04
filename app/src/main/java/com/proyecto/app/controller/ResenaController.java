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
import com.proyecto.app.service.IResenaService;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    private final IResenaService resenaService;

    public ResenaController(IResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // Endpoint para obtener todas las reseñas
    @GetMapping
    public List<Resena> obtenerTodas() {
        return resenaService.todas();
    }

    // Endpoint para obtener una reseña por su ID
    @GetMapping("/{id}")
    public Resena obtenerPorId(@PathVariable Long id) {
        return resenaService.obtenerResena(id);
    }

    // Endpoint para obtener reseñas de un evento
    @GetMapping("/evento/{eventoId}")
    public List<Resena> obtenerPorEvento(@PathVariable Long eventoId) {
        return resenaService.obtenerPorEvento(eventoId);
    }

    // Endpoint para obtener reseñas de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Resena> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return resenaService.obtenerPorUsuario(usuarioId);
    }

    // Endpoint para obtener el promedio de calificaciones de un evento
    @GetMapping("/evento/{eventoId}/promedio")
    public double obtenerPromedioEvento(@PathVariable Long eventoId) {
        return resenaService.obtenerPromedioEvento(eventoId);
    }

    // Endpoint para agregar una nueva reseña
    @PostMapping
    public String agregarResena(@RequestBody Resena resena) {
        resenaService.agregarResena(resena);
        return "Reseña creada exitosamente";
    }

    // Endpoint para actualizar una reseña existente
    @PutMapping("/{id}")
    public String actualizarResena(@PathVariable Long id, @RequestBody Resena resena) {
        resenaService.actualizarResena(id, resena);
        return "Reseña actualizada exitosamente";
    }

    // Endpoint para eliminar una reseña
    @DeleteMapping("/{id}")
    public String eliminarResena(@PathVariable Long id) {
        resenaService.eliminarResena(id);
        return "Reseña eliminada exitosamente";
    }
}
