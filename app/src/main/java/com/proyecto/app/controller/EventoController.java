package com.proyecto.app.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.model.Evento;
import com.proyecto.app.service.IEventoService;
import com.proyecto.app.service.EventoServiceImpl;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    private final IEventoService eventoService;
    private final EventoServiceImpl eventoServiceImpl;

    public EventoController(IEventoService eventoService, EventoServiceImpl eventoServiceImpl) {
        this.eventoService = eventoService;
        this.eventoServiceImpl = eventoServiceImpl;
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
    
    // ========== Endpoints con consultas JPQL ==========
    
    // GET /api/eventos/buscar/nombre?q=texto
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Evento>> buscarPorNombre(@RequestParam String q) {
        List<Evento> eventos = eventoServiceImpl.buscarPorNombre(q);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/artista/{artista}
    @GetMapping("/buscar/artista/{artista}")
    public ResponseEntity<List<Evento>> buscarPorArtistaPrincipal(@PathVariable String artista) {
        List<Evento> eventos = eventoServiceImpl.buscarPorArtistaPrincipal(artista);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/lugar/{lugar}
    @GetMapping("/buscar/lugar/{lugar}")
    public ResponseEntity<List<Evento>> buscarPorLugar(@PathVariable String lugar) {
        List<Evento> eventos = eventoServiceImpl.buscarPorLugar(lugar);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/entre-fechas?inicio=2024-01-01T00:00:00&fin=2024-12-31T23:59:59
    @GetMapping("/buscar/entre-fechas")
    public ResponseEntity<List<Evento>> buscarEventosEntreFechas(
            @RequestParam String inicio, 
            @RequestParam String fin) {
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        List<Evento> eventos = eventoServiceImpl.buscarEventosEntreFechas(fechaInicio, fechaFin);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/para-edad/{edad}
    @GetMapping("/buscar/para-edad/{edad}")
    public ResponseEntity<List<Evento>> buscarEventosParaEdad(@PathVariable int edad) {
        List<Evento> eventos = eventoServiceImpl.buscarEventosParaEdad(edad);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/texto?q=texto
    @GetMapping("/buscar/texto")
    public ResponseEntity<List<Evento>> buscarPorTexto(@RequestParam String q) {
        List<Evento> eventos = eventoServiceImpl.buscarPorTexto(q);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/categoria-activos/{categoria}
    @GetMapping("/buscar/categoria-activos/{categoria}")
    public ResponseEntity<List<Evento>> buscarPorCategoriaActivos(@PathVariable String categoria) {
        List<Evento> eventos = eventoServiceImpl.buscarPorCategoriaActivos(categoria);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/duracion-minima/{duracion}
    @GetMapping("/buscar/duracion-minima/{duracion}")
    public ResponseEntity<List<Evento>> buscarEventosPorDuracionMinima(@PathVariable int duracion) {
        List<Evento> eventos = eventoServiceImpl.buscarEventosPorDuracionMinima(duracion);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/contar-categoria/{categoria}
    @GetMapping("/buscar/contar-categoria/{categoria}")
    public ResponseEntity<Long> contarPorCategoria(@PathVariable String categoria) {
        long count = eventoServiceImpl.contarPorCategoria(categoria);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/activos-ordenados
    @GetMapping("/buscar/activos-ordenados")
    public ResponseEntity<List<Evento>> buscarEventosActivosOrdenados() {
        List<Evento> eventos = eventoServiceImpl.buscarEventosActivosOrdenados();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/categoria-lugar?categoria=CONCIERTO&lugar=Estadio Nacional
    @GetMapping("/buscar/categoria-lugar")
    public ResponseEntity<List<Evento>> buscarPorCategoriaYLugar(
            @RequestParam String categoria, 
            @RequestParam String lugar) {
        List<Evento> eventos = eventoServiceImpl.buscarPorCategoriaYLugar(categoria, lugar);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
    
    // GET /api/eventos/buscar/hoy
    @GetMapping("/buscar/hoy")
    public ResponseEntity<List<Evento>> buscarEventosDeHoy() {
        List<Evento> eventos = eventoServiceImpl.buscarEventosDeHoy();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
}
