package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proyecto.app.controller.EventoController;
import com.proyecto.app.model.Evento;
import com.proyecto.app.service.EventoService;

class EventoTest {
    
    private EventoController eventoController;
    private EventoService eventoService;
    
    @BeforeEach
    void setUp() {
        // Crear servicio con datos iniciales
        eventoService = new EventoService(true);
        eventoController = new EventoController(eventoService);
        
        // Debug: imprimir eventos iniciales
        System.out.println("=== Setup: Verificando eventos iniciales ===");
        eventoService.printAllEvents();
    }
    
    @Test
    void whenObtenerTodos_shouldReturnAllEvents() {
        List<Evento> result = eventoController.obtenerTodos();
        
        assertNotNull(result);
        assertEquals(4, result.size(), "Deberían haber 4 eventos iniciales");
    }
    
    @Test
    void whenObtenerPorIdConId1_shouldReturnColdplay() {
        Evento result = eventoController.obtenerPorId(1);
        
        assertNotNull(result);
        assertEquals("Coldplay - Music of the Spheres", result.getNombre());
    }
    
    @Test
    void whenCrearEvento_shouldAddNewEvent() {
        // Verificar estado inicial
        int sizeBefore = eventoController.obtenerTodos().size();
        assertEquals(4, sizeBefore, "Inicialmente deberían haber 4 eventos");
        
        // Crear nuevo evento
        Evento nuevoEvento = new Evento(
            0, "Test Event", "Test Description", "CONCIERTO",
            "Test Venue", "Test Address", LocalDateTime.now().plusDays(30),
            120, "Test Artist", 12
        );
        
        eventoController.crearEvento(nuevoEvento);
        
        // Verificar después de crear
        int sizeAfter = eventoController.obtenerTodos().size();
        assertEquals(sizeBefore + 1, sizeAfter, "Debería haber un evento más");
        
        // El nuevo evento debería tener ID = 5
        Evento eventoGuardado = eventoController.obtenerPorId(5);
        assertNotNull(eventoGuardado, "El evento con ID 5 no debería ser null");
        assertEquals("Test Event", eventoGuardado.getNombre());
        
        // Debug
        eventoService.printAllEvents();
    }
    
    @Test
    void whenCrearMultiplesEventos_shouldAssignIncrementalIds() {
        // Verificar estado inicial
        int eventosIniciales = eventoController.obtenerTodos().size();
        assertEquals(4, eventosIniciales, "Inicialmente deberían haber 4 eventos");
        
        // Crear primer evento
        Evento evento1 = new Evento(
            0, "Evento 1", "Desc 1", "CONCIERTO",
            "Lugar 1", "Dir 1", LocalDateTime.now().plusDays(10), 60, "Artist 1", 0
        );
        eventoController.crearEvento(evento1);
        
        // Crear segundo evento
        Evento evento2 = new Evento(
            0, "Evento 2", "Desc 2", "TEATRO",
            "Lugar 2", "Dir 2", LocalDateTime.now().plusDays(20), 90, "Artist 2", 0
        );
        eventoController.crearEvento(evento2);
        
        // Verificar IDs
        int primerIdEsperado = eventosIniciales + 1; // 5
        int segundoIdEsperado = eventosIniciales + 2; // 6
        
        Evento result1 = eventoController.obtenerPorId(primerIdEsperado);
        Evento result2 = eventoController.obtenerPorId(segundoIdEsperado);
        
        assertNotNull(result1, "El evento con ID " + primerIdEsperado + " no debería ser null");
        assertNotNull(result2, "El evento con ID " + segundoIdEsperado + " no debería ser null");
        
        assertEquals("Evento 1", result1.getNombre());
        assertEquals("Evento 2", result2.getNombre());
        
        // Verificar que los IDs son correctos
        assertEquals(primerIdEsperado, result1.getId());
        assertEquals(segundoIdEsperado, result2.getId());
    }
    
    @Test
    void whenObtenerProximos_shouldReturnFutureEvents() {
        List<Evento> result = eventoController.obtenerProximos();
        
        assertNotNull(result);
        // Todos los eventos tienen fecha en 2026 (futuro)
        assertEquals(4, result.size(), "Deberían haber 4 eventos próximos");
    }
    
    @Test
    void whenCrearEvento_elIdDebeSerIncremental() {
        // Verificar último ID antes de crear
        int sizeBefore = eventoController.obtenerTodos().size();
        int ultimoIdAntes = sizeBefore; // Como los IDs son 1,2,3,4, el último es 4
        
        // Crear nuevo evento
        Evento nuevoEvento = new Evento(
            0, "Evento Incremental", "Desc", "CONCIERTO",
            "Lugar", "Dir", LocalDateTime.now().plusDays(5), 60, "Artist", 0
        );
        
        eventoController.crearEvento(nuevoEvento);
        
        // El nuevo evento debería tener ID = ultimoIdAntes + 1 = 5
        int idEsperado = ultimoIdAntes + 1;
        Evento eventoGuardado = eventoController.obtenerPorId(idEsperado);
        
        assertNotNull(eventoGuardado, "El evento con ID " + idEsperado + " no debería ser null");
        assertEquals("Evento Incremental", eventoGuardado.getNombre());
        assertEquals(idEsperado, eventoGuardado.getId());
    }
    
    @Test
    void whenObtenerPorCategoria_shouldReturnFilteredEvents() {
        List<Evento> conciertos = eventoController.obtenerPorCategoria("CONCIERTO");
        assertEquals(2, conciertos.size());
        
        List<Evento> teatro = eventoController.obtenerPorCategoria("TEATRO");
        assertEquals(1, teatro.size());
        
        List<Evento> deportes = eventoController.obtenerPorCategoria("DEPORTES");
        assertEquals(1, deportes.size());
    }
    
    @Test
    void whenActualizarEvento_shouldUpdateExistingEvent() {
        Evento eventoActualizado = new Evento(
            0, "Coldplay - Actualizado", "Descripción actualizada",
            "CONCIERTO", "Nuevo Lugar", "Nueva Dirección",
            LocalDateTime.of(2026, 10, 15, 20, 0), 180, "Coldplay", 18
        );
        
        eventoController.actualizarEvento(1, eventoActualizado);
        
        Evento resultado = eventoController.obtenerPorId(1);
        assertNotNull(resultado);
        assertEquals("Coldplay - Actualizado", resultado.getNombre());
        assertEquals("Nuevo Lugar", resultado.getLugar());
    }
    
    @Test
    void whenEliminarEvento_shouldRemoveEvent() {
        // Verificar que existe
        assertNotNull(eventoController.obtenerPorId(1));
        
        eventoController.eliminarEvento(1);
        
        // Verificar que ya no existe
        assertNull(eventoController.obtenerPorId(1));
        assertEquals(3, eventoController.obtenerTodos().size());
    }
}