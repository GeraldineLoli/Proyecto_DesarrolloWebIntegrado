package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proyecto.app.controller.ZonaController;
import com.proyecto.app.model.Zona;
import com.proyecto.app.service.ZonaService;

class ZonaTest {
    
    private ZonaController zonaController;
    private ZonaService zonaService;
    
    @BeforeEach
    void setUp() {
        zonaService = new ZonaService(true);
        zonaController = new ZonaController(zonaService);
    }
    
    // Tests para obtener zonas
    @Test
    void whenObtenerTodas_shouldReturnAllZonas() {
        List<Zona> result = zonaController.obtenerTodas();
        
        assertNotNull(result);
        assertEquals(12, result.size());
    }
    
    @Test
    void whenObtenerPorIdConId1_shouldReturnZonaVIP() {
        Zona result = zonaController.obtenerPorId(1);
        
        assertNotNull(result);
        assertEquals("VIP", result.getNombre());
        assertEquals(850.00, result.getPrecio());
        assertEquals(1, result.getEventoId());
    }
    
    @Test
    void whenObtenerPorIdConId2_shouldReturnZonaPlatea() {
        Zona result = zonaController.obtenerPorId(2);
        
        assertNotNull(result);
        assertEquals("PLATEA", result.getNombre());
        assertEquals(450.00, result.getPrecio());
    }
    
    @Test
    void whenObtenerPorIdConId3_shouldReturnZonaGeneral() {
        Zona result = zonaController.obtenerPorId(3);
        
        assertNotNull(result);
        assertEquals("GENERAL", result.getNombre());
        assertEquals(250.00, result.getPrecio());
        assertFalse(result.isTieneNumeracion());
    }
    
    @Test
    void whenObtenerPorIdInvalido_shouldReturnNull() {
        Zona result = zonaController.obtenerPorId(999);
        
        assertNull(result);
    }
    
    // Tests para obtener zonas por evento
    @Test
    void whenObtenerPorEventoConId1_shouldReturn3Zonas() {
        List<Zona> result = zonaController.obtenerPorEvento(1);
        
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(z -> z.getEventoId() == 1));
    }
    
    @Test
    void whenObtenerPorEventoConId2_shouldReturn3Zonas() {
        List<Zona> result = zonaController.obtenerPorEvento(2);
        
        assertEquals(3, result.size());
        assertEquals("VIP", result.get(0).getNombre());
        assertEquals("PLATEA", result.get(1).getNombre());
        assertEquals("GENERAL", result.get(2).getNombre());
    }
    
    @Test
    void whenObtenerPorEventoConId3_shouldReturn4Zonas() {
        List<Zona> result = zonaController.obtenerPorEvento(3);
        
        assertEquals(4, result.size());
        assertTrue(result.stream().anyMatch(z -> z.getNombre().equals("ORIENTE")));
        assertTrue(result.stream().anyMatch(z -> z.getNombre().equals("OCCIDENTE")));
    }
    
    @Test
    void whenObtenerPorEventoConId4_shouldReturn2Zonas() {
        List<Zona> result = zonaController.obtenerPorEvento(4);
        
        assertEquals(2, result.size());
        assertEquals("PISTA VIP", result.get(0).getNombre());
        assertEquals("PLATEA", result.get(1).getNombre());
    }
    
    @Test
    void whenObtenerPorEventoInexistente_shouldReturnEmptyList() {
        List<Zona> result = zonaController.obtenerPorEvento(999);
        
        assertEquals(0, result.size());
    }
    
    // Tests para crear zona
    @Test
    void whenCrearZona_shouldAddNewZona() {
        int sizeBefore = zonaController.obtenerTodas().size();
        
        Zona nuevaZona = new Zona(0, 1, "NUEVA_ZONA_TEST", 300.0, 100, true);
        zonaController.crearZona(nuevaZona);
        
        int sizeAfter = zonaController.obtenerTodas().size();
        assertEquals(sizeBefore + 1, sizeAfter);
        
        Zona zonaGuardada = zonaController.obtenerPorId(13);
        assertNotNull(zonaGuardada, "La zona con ID 13 debería existir");
        assertEquals("NUEVA_ZONA_TEST", zonaGuardada.getNombre());
        assertEquals(300.0, zonaGuardada.getPrecio());
    }
    
    // Tests para reservar entrada
    @Test
    void whenReservarEntrada_shouldDecreaseAvailability() {
        Zona zonaBefore = zonaController.obtenerPorId(11);
        int disponibilidadBefore = zonaBefore.getEntradasDisponibles();
        
        boolean result = zonaController.reservarEntrada(11);
        
        assertTrue(result);
        Zona zonaAfter = zonaController.obtenerPorId(11);
        assertEquals(disponibilidadBefore - 1, zonaAfter.getEntradasDisponibles());
    }
    
    @Test
    void whenReservarEntradaHastaAgotar_shouldReturnFalseAlFinal() {
        Zona zona = zonaController.obtenerPorId(11);
        int capacidad = zona.getEntradasDisponibles();
        
        // Reservar todas las entradas
        for (int i = 0; i < capacidad; i++) {
            assertTrue(zonaController.reservarEntrada(11));
        }
        
        // Intentar reservar una más
        boolean result = zonaController.reservarEntrada(11);
        assertFalse(result);
        
        Zona zonaFinal = zonaController.obtenerPorId(11);
        assertEquals(0, zonaFinal.getEntradasDisponibles());
    }
    
    @Test
    void whenReservarEntradaEnZonaInexistente_shouldReturnFalse() {
        boolean result = zonaController.reservarEntrada(999);
        
        assertFalse(result);
    }
    
    // Tests para actualizar zona
    @Test
    void whenActualizarZona_shouldUpdateExistingZona() {
        Zona zonaActualizada = new Zona(0, 2, "VIP ACTUALIZADA", 500.0, 200, false);
        zonaController.actualizarZona(4, zonaActualizada);
        
        Zona resultado = zonaController.obtenerPorId(4);
        assertEquals("VIP ACTUALIZADA", resultado.getNombre());
        assertEquals(500.0, resultado.getPrecio());
        assertEquals(2, resultado.getEventoId());
    }
    
    // Tests para eliminar zona
    @Test
    void whenEliminarZona_shouldRemoveZona() {
        assertNotNull(zonaController.obtenerPorId(1));
        
        zonaController.eliminarZona(1);
        
        assertNull(zonaController.obtenerPorId(1));
        assertEquals(11, zonaController.obtenerTodas().size());
    }
}