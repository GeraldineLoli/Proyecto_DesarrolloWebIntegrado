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

import com.proyecto.app.model.Entrada;
import com.proyecto.app.service.IEntradaService;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    private final IEntradaService entradaService;

    public EntradaController(IEntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @PostMapping
    public ResponseEntity<?> crearEntrada(@RequestBody Entrada entrada) {
        try {
            Entrada entradaCreada = entradaService.crearEntrada(entrada);
            return new ResponseEntity<>(entradaCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear entrada: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrada> obtenerPorId(@PathVariable("id") Long id) {
        Entrada entrada = entradaService.obtenerEntradaPorId(id);
        return new ResponseEntity<>(entrada, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Entrada>> obtenerTodas() {
        List<Entrada> entradas = entradaService.obtenerTodasLasEntradas();
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Entrada>> obtenerPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        List<Entrada> entradas = entradaService.obtenerEntradasPorUsuario(usuarioId);
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Entrada>> obtenerPorEvento(@PathVariable("eventoId") Long eventoId) {
        List<Entrada> entradas = entradaService.obtenerEntradasPorEvento(eventoId);
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Entrada>> obtenerActivas() {
        List<Entrada> entradas = entradaService.obtenerEntradasActivas();
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEntrada(@PathVariable("id") Long id, @RequestBody Entrada entrada) {
        entrada.setId(id);
        entradaService.actualizarEntrada(entrada);
        return new ResponseEntity<>("Entrada actualizada exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEntrada(@PathVariable("id") Long id) {
        entradaService.eliminarEntrada(id);
        return new ResponseEntity<>("Entrada eliminada exitosamente", HttpStatus.OK);
    }
}
