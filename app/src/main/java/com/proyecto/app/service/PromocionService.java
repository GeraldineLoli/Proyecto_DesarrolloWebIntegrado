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
    private final IEventoService eventoService;
    
    public PromocionService(PromocionRepository promocionRepository, IEventoService eventoService) {
        this.promocionRepository = promocionRepository;
        this.eventoService = eventoService;
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
    
    private void validarFechasPromocion(Promocion promocion) {
        if (promocion.getFechaInicio() != null && promocion.getFechaFin() != null) {
            if (promocion.getFechaFin().isBefore(promocion.getFechaInicio())) {
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
            }
            if (promocion.getEventoId() != null) {
                com.proyecto.app.model.Evento evento = eventoService.obtenerEvento(promocion.getEventoId());
                if (evento != null && evento.getFechaHora() != null) {
                    if (promocion.getFechaFin().isAfter(evento.getFechaHora())) {
                        throw new IllegalArgumentException("La fecha de fin de la promoción no puede extenderse más allá de la fecha del evento.");
                    }
                }
            }
        }
    }

    public Promocion agregarPromocion(Promocion promocion) {
        validarFechasPromocion(promocion);
        return promocionRepository.save(promocion);
    }
    
    public Promocion actualizarPromocion(Long id, Promocion promocionActualizada) {
        validarFechasPromocion(promocionActualizada);
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
    
    // ========== Métodos adicionales con JPQL ==========
    
    public List<Promocion> buscarPromocionesActivas() {
        return promocionRepository.buscarPromocionesActivas();
    }
    
    public List<Promocion> buscarPorDescripcion(String texto) {
        return promocionRepository.buscarPorDescripcion(texto);
    }
    
    public List<Promocion> buscarPromocionesVigentes(LocalDateTime fecha) {
        return promocionRepository.buscarPromocionesVigentes(fecha);
    }
    
    public List<Promocion> buscarPorDescuentoMayor(double porcentaje) {
        return promocionRepository.buscarPorDescuentoMayor(porcentaje);
    }
    
    public List<Promocion> buscarPorRangoDescuento(double min, double max) {
        return promocionRepository.buscarPorRangoDescuento(min, max);
    }
    
    public List<Promocion> buscarConDisponibilidad() {
        return promocionRepository.buscarConDisponibilidad();
    }
    
    public List<Promocion> buscarQueExpiranProximo(LocalDateTime ahora, LocalDateTime fechaLimite) {
        return promocionRepository.buscarQueExpiranProximo(ahora, fechaLimite);
    }
    
    public List<Promocion> buscarPorEventoActivas(Long eventoId) {
        return promocionRepository.buscarPorEventoActivas(eventoId);
    }
    
    public long contarPorEvento(Long eventoId) {
        return promocionRepository.contarPorEvento(eventoId);
    }
    
    public long contarPromocionesActivas() {
        return promocionRepository.contarPromocionesActivas();
    }
    
    public List<Promocion> buscarOrdenadasPorDescuento() {
        return promocionRepository.buscarOrdenadasPorDescuento();
    }
    
    public List<Promocion> buscarMasUsadas() {
        return promocionRepository.buscarMasUsadas();
    }
    
    public Double calcularPromedioDescuento() {
        return promocionRepository.calcularPromedioDescuento();
    }
    
    public List<Promocion> buscarPorTexto(String texto) {
        return promocionRepository.buscarPorTexto(texto);
    }
}