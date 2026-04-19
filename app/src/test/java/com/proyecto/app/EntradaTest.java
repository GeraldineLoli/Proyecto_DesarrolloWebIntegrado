package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proyecto.app.controller.EntradaController;
import com.proyecto.app.controller.ZonaController;
import com.proyecto.app.model.Entrada;
import com.proyecto.app.model.Zona;
import com.proyecto.app.service.EntradaService;
import com.proyecto.app.service.EventoService;
import com.proyecto.app.service.UsuarioService;
import com.proyecto.app.service.ZonaService;

public class EntradaTest {
    
    private EntradaController entradaController;
    private EntradaService entradaService;
    private ZonaService zonaService;
    private UsuarioService usuarioService;
    private EventoService eventoService;
    
    @BeforeEach
    void setUp() {
        zonaService = new ZonaService(true);
        usuarioService = new UsuarioService(true);
        eventoService = new EventoService(true);
        entradaService = new EntradaService(zonaService, usuarioService, eventoService);
        entradaController = new EntradaController(entradaService);
    }
    
    // Tests para obtener entradas
    @Test
    void whenObtenerTodas_shouldReturnAllEntradas() {
        List<Entrada> result = entradaController.obtenerTodas();
        
        assertNotNull(result);
        assertEquals(3, result.size());
    }
    
    @Test
    void whenObtenerPorIdConId1_shouldReturnEntrada() {
        Entrada result = entradaController.obtenerPorId(1);
        
        assertNotNull(result);
        assertEquals(1, result.getEventoId());
        assertEquals(1, result.getZonaId());
        assertEquals(1, result.getUsuarioId());
        assertEquals(850.00, result.getPrecioPagado());
        assertEquals("PAGADA", result.getEstado());
    }
    
    @Test
    void whenObtenerPorIdConId2_shouldReturnEntrada() {
        Entrada result = entradaController.obtenerPorId(2);
        
        assertNotNull(result);
        assertEquals(1, result.getEventoId());
        assertEquals(2, result.getZonaId());
        assertEquals(2, result.getUsuarioId());
        assertEquals(450.00, result.getPrecioPagado());
    }
    
    @Test
    void whenObtenerPorIdConId3_shouldReturnEntrada() {
        Entrada result = entradaController.obtenerPorId(3);
        
        assertNotNull(result);
        assertEquals(2, result.getEventoId());
        assertEquals(4, result.getZonaId());
        assertEquals(3, result.getUsuarioId());
        assertEquals(320.00, result.getPrecioPagado());
    }
    
    @Test
    void whenObtenerPorIdInvalido_shouldReturnNull() {
        Entrada result = entradaController.obtenerPorId(999);
        
        assertNull(result);
    }
    
    // Tests para obtener entradas por usuario
    @Test
    void whenObtenerPorUsuarioId1_shouldReturn1Entrada() {
        List<Entrada> result = entradaController.obtenerPorUsuario(1);
        
        assertEquals(1, result.size());
        assertEquals(850.00, result.get(0).getPrecioPagado());
    }
    
    @Test
    void whenObtenerPorUsuarioId2_shouldReturn1Entrada() {
        List<Entrada> result = entradaController.obtenerPorUsuario(2);
        
        assertEquals(1, result.size());
        assertEquals(450.00, result.get(0).getPrecioPagado());
    }
    
    @Test
    void whenObtenerPorUsuarioId3_shouldReturn1Entrada() {
        List<Entrada> result = entradaController.obtenerPorUsuario(3);
        
        assertEquals(1, result.size());
        assertEquals(320.00, result.get(0).getPrecioPagado());
    }
    
    @Test
    void whenObtenerPorUsuarioInexistente_shouldReturnEmptyList() {
        List<Entrada> result = entradaController.obtenerPorUsuario(999);
        
        assertEquals(0, result.size());
    }
    
    // Tests para obtener entradas por evento
    @Test
    void whenObtenerPorEventoId1_shouldReturn2Entradas() {
        List<Entrada> result = entradaController.obtenerPorEvento(1);
        
        assertEquals(2, result.size());
    }
    
    @Test
    void whenObtenerPorEventoId2_shouldReturn1Entrada() {
        List<Entrada> result = entradaController.obtenerPorEvento(2);
        
        assertEquals(1, result.size());
    }
    
    @Test
    void whenObtenerPorEventoId3_shouldReturn0Entradas() {
        List<Entrada> result = entradaController.obtenerPorEvento(3);
        
        assertEquals(0, result.size());
    }
    
    // Tests para obtener entradas activas
    @Test
    void whenObtenerActivas_shouldReturnOnlyPagadas() {
        List<Entrada> result = entradaController.obtenerActivas();
        
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(e -> e.getEstado().equals("PAGADA")));
    }
    
    @Test
    void whenCancelarEntrada_shouldNotBeInActivas() {
        entradaController.cancelarEntrada(1);
        
        List<Entrada> activas = entradaController.obtenerActivas();
        assertEquals(2, activas.size());
        assertTrue(activas.stream().noneMatch(e -> e.getId() == 1));
    }
    
    // Tests para comprar entrada
    @Test
    void whenComprarEntrada_shouldCreateNewEntrada() {
        int sizeBefore = entradaController.obtenerTodas().size();
        
        Entrada nuevaEntrada = entradaController.comprarEntrada(1, 3, "VISA");
        
        assertNotNull(nuevaEntrada);
        assertEquals(4, nuevaEntrada.getId());
        assertEquals(1, nuevaEntrada.getEventoId());
        assertEquals(3, nuevaEntrada.getZonaId());
        assertEquals(1, nuevaEntrada.getUsuarioId());
        assertEquals(250.00, nuevaEntrada.getPrecioPagado());
        assertEquals("PAGADA", nuevaEntrada.getEstado());
        
        int sizeAfter = entradaController.obtenerTodas().size();
        assertEquals(sizeBefore + 1, sizeAfter);
        
        // Verificar que la disponibilidad de la zona disminuyó
        Zona zona = new ZonaController(zonaService).obtenerPorId(3);
        assertEquals(4999, zona.getEntradasDisponibles()); // De 5000 a 4999
    }
    
    @Test
    void whenComprarEntradaConUsuarioInexistente_shouldThrowException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            entradaController.comprarEntrada(999, 1, "VISA");
        });
        
        assertEquals("Usuario no encontrado", exception.getMessage());
    }
    
    @Test
    void whenComprarEntradaEnZonaSinDisponibilidad_shouldThrowException() {
        // Primero agotar todas las entradas de la zona 4 (VIP del evento 2, capacidad 50)
        ZonaController zonaController = new ZonaController(zonaService);
        for (int i = 0; i < 50; i++) {
            zonaController.reservarEntrada(4);
        }
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            entradaController.comprarEntrada(1, 4, "VISA");
        });
        
        assertEquals("No hay entradas disponibles en esta zona", exception.getMessage());
    }
    
    // Tests para cancelar entrada
    @Test
    void whenCancelarEntrada_shouldChangeEstadoToCancelada() {
        Entrada entradaBefore = entradaController.obtenerPorId(1);
        assertEquals("PAGADA", entradaBefore.getEstado());
        
        entradaController.cancelarEntrada(1);
        
        Entrada entradaAfter = entradaController.obtenerPorId(1);
        assertEquals("CANCELADA", entradaAfter.getEstado());
        
        // Verificar que se liberó la entrada en la zona
        Zona zona = new ZonaController(zonaService).obtenerPorId(1);
        assertEquals(500, zona.getEntradasDisponibles()); // Se liberó una entrada VIP
    }
    
    @Test
    void whenCancelarEntradaYaUsada_shouldNotChange() {
        // Primero marcar como usada
        entradaController.usarEntrada(1);
        
        entradaController.cancelarEntrada(1);
        
        Entrada entrada = entradaController.obtenerPorId(1);
        assertEquals("USADA", entrada.getEstado()); // Sigue siendo USADA
    }
    
    @Test
    void whenCancelarEntradaInexistente_shouldDoNothing() {
        // No debe lanzar excepción
        entradaController.cancelarEntrada(999);
        
        assertEquals(3, entradaController.obtenerTodas().size());
    }
    
    // Tests para usar entrada
    @Test
    void whenUsarEntrada_shouldChangeEstadoToUsada() {
        Entrada entradaBefore = entradaController.obtenerPorId(1);
        assertEquals("PAGADA", entradaBefore.getEstado());
        
        entradaController.usarEntrada(1);
        
        Entrada entradaAfter = entradaController.obtenerPorId(1);
        assertEquals("USADA", entradaAfter.getEstado());
    }
    
    @Test
    void whenUsarEntradaCancelada_shouldNotChange() {
        entradaController.cancelarEntrada(1);
        
        entradaController.usarEntrada(1);
        
        Entrada entrada = entradaController.obtenerPorId(1);
        assertEquals("CANCELADA", entrada.getEstado()); // Sigue siendo CANCELADA
    }
    
    @Test
    void whenUsarEntradaInexistente_shouldDoNothing() {
        entradaController.usarEntrada(999);
        
        assertEquals(3, entradaController.obtenerTodas().size());
    }
    
    // Tests para código único de entrada
    @Test
    void whenComprarEntrada_shouldGenerateUniqueCode() {
        Entrada entrada1 = entradaController.comprarEntrada(1, 3, "VISA");
        Entrada entrada2 = entradaController.comprarEntrada(2, 5, "MASTERCARD");
        
        assertNotNull(entrada1.getCodigoEntrada());
        assertNotNull(entrada2.getCodigoEntrada());
        assertNotEquals(entrada1.getCodigoEntrada(), entrada2.getCodigoEntrada());
        assertTrue(entrada1.getCodigoEntrada().startsWith("TKT-"));
        assertTrue(entrada2.getCodigoEntrada().startsWith("TKT-"));
    }
    
    // Tests para compra múltiple
    @Test
    void whenComprarMultiplesEntradas_shouldCreateAll() {
        int sizeBefore = entradaController.obtenerTodas().size();
        
        Entrada entrada1 = entradaController.comprarEntrada(1, 2, "VISA");
        Entrada entrada2 = entradaController.comprarEntrada(1, 3, "MASTERCARD");
        Entrada entrada3 = entradaController.comprarEntrada(2, 5, "YAPE");
        
        assertEquals(sizeBefore + 3, entradaController.obtenerTodas().size());
        
        assertNotNull(entrada1);
        assertNotNull(entrada2);
        assertNotNull(entrada3);
    }
}