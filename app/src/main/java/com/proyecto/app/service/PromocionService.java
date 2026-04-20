package com.proyecto.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.app.model.Promocion;

@Service
public class PromocionService {
    private List<Promocion> promociones;
    private int nextId;
    
    // Constructor para producción
    public PromocionService() {
        this.promociones = new ArrayList<>();
        this.nextId = 1;
        initData();
    }
    
    // Constructor para testing
    public PromocionService(boolean inicializarDatos) {
        this.promociones = new ArrayList<>();
        this.nextId = 1;
        if (inicializarDatos) {
            initData();
        }
    }
    
    private void initData() {
        promociones.clear();
        
        // Promociones de ejemplo
        Promocion promo1 = new Promocion(1, "COLDPLAY20", "20% de descuento en Coldplay", 
                20.0, 1, LocalDateTime.of(2026, 3, 1, 0, 0), 
                LocalDateTime.of(2026, 5, 15, 23, 59), 100);
        
        Promocion promo2 = new Promocion(2, "EARLYBIRD10", "10% descuento early bird", 
                10.0, 2, LocalDateTime.of(2026, 2, 15, 0, 0), 
                LocalDateTime.of(2026, 6, 30, 23, 59), 50);
        
        Promocion promo3 = new Promocion(3, "ESTUDIANTE15", "15% descuento estudiantes", 
                15.0, 3, LocalDateTime.of(2026, 4, 1, 0, 0), 
                LocalDateTime.of(2026, 8, 31, 23, 59), 75);
        
        promociones.add(promo1);
        promociones.add(promo2);
        promociones.add(promo3);
        nextId = 4;
    }
    
    public List<Promocion> todas() {
        return new ArrayList<>(promociones);
    }
    
    public Promocion obtenerPromocion(int id) {
        return promociones.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public Promocion obtenerPorCodigo(String codigo) {
        return promociones.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }
    
    public List<Promocion> obtenerPorEvento(int eventoId) {
        return promociones.stream()
                .filter(p -> p.getEventoId() == eventoId)
                .collect(Collectors.toList());
    }
    
    public List<Promocion> obtenerActivas() {
        LocalDateTime ahora = LocalDateTime.now();
        return promociones.stream()
                .filter(p -> p.isActivo() && 
                        p.getFechaInicio().isBefore(ahora) && 
                        p.getFechaFin().isAfter(ahora) &&
                        p.getCantidadUsada() < p.getCantidadDisponible())
                .collect(Collectors.toList());
    }
    
    public void agregarPromocion(Promocion promocion) {
        promocion.setId(nextId++);
        promociones.add(promocion);
    }
    
    public void actualizarPromocion(int id, Promocion promocionActualizada) {
        Promocion promocion = obtenerPromocion(id);
        if (promocion != null) {
            promocion.setCodigo(promocionActualizada.getCodigo());
            promocion.setDescripcion(promocionActualizada.getDescripcion());
            promocion.setPorcentajeDescuento(promocionActualizada.getPorcentajeDescuento());
            promocion.setEventoId(promocionActualizada.getEventoId());
            promocion.setFechaInicio(promocionActualizada.getFechaInicio());
            promocion.setFechaFin(promocionActualizada.getFechaFin());
            promocion.setCantidadDisponible(promocionActualizada.getCantidadDisponible());
            promocion.setActivo(promocionActualizada.isActivo());
        }
    }
    
    public void eliminarPromocion(int id) {
        promociones.removeIf(p -> p.getId() == id);
    }
    
    public boolean usarPromocion(int id) {
        Promocion promocion = obtenerPromocion(id);
        if (promocion != null && promocion.getCantidadUsada() < promocion.getCantidadDisponible()) {
            promocion.setCantidadUsada(promocion.getCantidadUsada() + 1);
            return true;
        }
        return false;
    }
}
