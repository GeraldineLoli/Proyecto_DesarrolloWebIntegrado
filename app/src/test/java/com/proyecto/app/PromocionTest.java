package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proyecto.app.controller.PromocionController;
import com.proyecto.app.model.Promocion;
import com.proyecto.app.service.PromocionService;

class PromocionTest {
    
    private PromocionController promocionController;
    private PromocionService promocionService;
    
    @BeforeEach
    void setUp() {
        promocionService = new PromocionService(true);
        promocionController = new PromocionController(promocionService);
    }
    
    @Test
    void whenObtenerTodas_shouldReturnAllPromotions() {
        List<Promocion> result = promocionController.obtenerTodas();
        
        assertNotNull(result);
        assertEquals(3, result.size(), "Deberían haber 3 promociones iniciales");
    }
    
    @Test
    void whenObtenerPorId_shouldReturnCorrectPromotion() {
        Promocion result = promocionController.obtenerPorId(1);
        
        assertNotNull(result);
        assertEquals("COLDPLAY20", result.getCodigo());
        assertEquals(20.0, result.getPorcentajeDescuento());
    }
    
    @Test
    void whenObtenerPorCodigo_shouldReturnPromocionByCodigo() {
        Promocion result = promocionController.obtenerPorCodigo("ESTUDIANTE15");
        
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals(15.0, result.getPorcentajeDescuento());
    }
    
    @Test
    void whenObtenerPorEvento_shouldReturnPromocionesForEvent() {
        List<Promocion> result = promocionController.obtenerPorEvento(1);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("COLDPLAY20", result.get(0).getCodigo());
    }
    
    @Test
    void whenUsarPromocion_shouldIncrementCantidadUsada() {
        boolean result = promocionController.usarPromocion(1);
        
        assertTrue(result);
        Promocion promocion = promocionController.obtenerPorId(1);
        assertEquals(1, promocion.getCantidadUsada());
    }
    
    @Test
    void whenUsarPromocionAgotada_shouldReturnFalse() {
        Promocion promo = promocionController.obtenerPorId(1);
        promo.setCantidadDisponible(1);
        promo.setCantidadUsada(1);
        
        boolean result = promocionController.usarPromocion(1);
        
        assertFalse(result, "No se debe poder usar una promoción agotada");
    }
    
    @Test
    void whenCrearPromocion_shouldAddNewPromotion() {
        int tamañoInicial = promocionController.obtenerTodas().size();
        
        Promocion nuevaPromo = new Promocion(0, "NEWCODE50", "50% descuento especial", 
                50.0, 2, LocalDateTime.now(), LocalDateTime.now().plusDays(30), 25);
        
        promocionController.crearPromocion(nuevaPromo);
        
        List<Promocion> resultado = promocionController.obtenerTodas();
        assertEquals(tamañoInicial + 1, resultado.size());
    }
    
    @Test
    void whenActualizarPromocion_shouldUpdateFields() {
        Promocion promoActualizada = new Promocion(1, "COLDPLAY25", "Actualizada 25%", 
                25.0, 1, LocalDateTime.of(2026, 9, 1, 0, 0), 
                LocalDateTime.of(2026, 10, 15, 23, 59), 150);
        
        promocionController.actualizarPromocion(1, promoActualizada);
        
        Promocion resultado = promocionController.obtenerPorId(1);
        assertEquals("COLDPLAY25", resultado.getCodigo());
        assertEquals(25.0, resultado.getPorcentajeDescuento());
        assertEquals(150, resultado.getCantidadDisponible());
    }
    
    @Test
    void whenEliminarPromocion_shouldRemovePromotion() {
        int tamañoInicial = promocionController.obtenerTodas().size();
        
        promocionController.eliminarPromocion(1);
        
        List<Promocion> resultado = promocionController.obtenerTodas();
        assertEquals(tamañoInicial - 1, resultado.size());
        assertNull(promocionController.obtenerPorId(1));
    }
}
