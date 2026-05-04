package com.proyecto.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.app.model.Promocion;
import com.proyecto.app.repository.PromocionRepository;

@Service
public class PromocionService {
    
    private final PromocionRepository promocionRepository;
    
    public PromocionService(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }
    
    public List<Promocion> todas() {
        return promocionRepository.findAll();
    }
    
    public Promocion obtenerPromocion(Long id) {
        return promocionRepository.findById(id).orElse(null);
    }
    
    public Promocion obtenerPorCodigo(String codigo) {
        return promocionRepository.findByCodigo(codigo).orElse(null);
    }
    
    public List<Promocion> obtenerPorEvento(Long eventoId) {
        return promocionRepository.findByEventoId(eventoId);
    }
    
    public List<Promocion> obtenerActivas() {
        LocalDateTime ahora = LocalDateTime.now();
        return promocionRepository.findByActivo(true).stream()
                .filter(p -> p.getFechaInicio().isBefore(ahora) && 
                        p.getFechaFin().isAfter(ahora) &&
                        p.getCantidadUsada() < p.getCantidadDisponible())
                .collect(Collectors.toList());
    }
    
    public Promocion agregarPromocion(Promocion promocion) {
        return promocionRepository.save(promocion);
    }
    
    public Promocion actualizarPromocion(Long id, Promocion promocionActualizada) {
        Promocion promocionExistente = promocionRepository.findById(id).orElse(null);
        if (promocionExistente != null) {
            promocionExistente.setCodigo(promocionActualizada.getCodigo());
            promocionExistente.setDescripcion(promocionActualizada.getDescripcion());
            promocionExistente.setPorcentajeDescuento(promocionActualizada.getPorcentajeDescuento());
            promocionExistente.setEventoId(promocionActualizada.getEventoId());
            promocionExistente.setFechaInicio(promocionActualizada.getFechaInicio());
            promocionExistente.setFechaFin(promocionActualizada.getFechaFin());
            promocionExistente.setCantidadDisponible(promocionActualizada.getCantidadDisponible());
            promocionExistente.setCantidadUsada(promocionActualizada.getCantidadUsada());
            promocionExistente.setActivo(promocionActualizada.isActivo());
            return promocionRepository.save(promocionExistente);
        }
        return null;
    }
    
    public void eliminarPromocion(Long id) {
        promocionRepository.deleteById(id);
    }
    
    public boolean usarPromocion(Long id) {
        Promocion promocion = obtenerPromocion(id);
        if (promocion != null && promocion.getCantidadUsada() < promocion.getCantidadDisponible()) {
            promocion.setCantidadUsada(promocion.getCantidadUsada() + 1);
            promocionRepository.save(promocion);
            return true;
        }
        return false;
    }
}