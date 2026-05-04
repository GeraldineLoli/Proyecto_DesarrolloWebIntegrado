package com.proyecto.app.service;

import com.proyecto.app.model.Zona;
import com.proyecto.app.repository.ZonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaServiceImpl implements IZonaService {
    
    private final ZonaRepository zonaRepository;
    
    public ZonaServiceImpl(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }
    
    @Override
    public Zona crearZona(Zona zona) {
        if (zona.getEntradasDisponibles() == 0) {
            zona.setEntradasDisponibles(zona.getCapacidadTotal());
        }
        return zonaRepository.save(zona);
    }
    
    @Override
    public Zona obtenerZonaPorId(Long id) {
        Optional<Zona> optionalZona = zonaRepository.findById(id);
        return optionalZona.orElse(null);
    }
    
    @Override
    public List<Zona> obtenerTodasLasZonas() {
        return zonaRepository.findAll();
    }
    
    @Override
    public List<Zona> obtenerZonasPorEvento(Long eventoId) {
        return zonaRepository.findByEventoId(eventoId);
    }
    
    @Override
    public Zona actualizarZona(Zona zona) {
        Zona zonaExistente = zonaRepository.findById(zona.getId()).orElse(null);
        if (zonaExistente != null) {
            zonaExistente.setEventoId(zona.getEventoId());
            zonaExistente.setNombre(zona.getNombre());
            zonaExistente.setPrecio(zona.getPrecio());
            zonaExistente.setCapacidadTotal(zona.getCapacidadTotal());
            zonaExistente.setEntradasDisponibles(zona.getEntradasDisponibles());
            zonaExistente.setColorMapa(zona.getColorMapa());
            zonaExistente.setTieneNumeracion(zona.isTieneNumeracion());
            return zonaRepository.save(zonaExistente);
        }
        return null;
    }
    
    @Override
    public void eliminarZona(Long id) {
        zonaRepository.deleteById(id);
    }
    
    @Override
    public boolean reservarEntrada(Long zonaId) {
        Zona zona = obtenerZonaPorId(zonaId);
        if (zona != null && zona.getEntradasDisponibles() > 0) {
            zona.setEntradasDisponibles(zona.getEntradasDisponibles() - 1);
            zonaRepository.save(zona);
            return true;
        }
        return false;
    }
    
    @Override
    public void liberarEntrada(Long zonaId) {
        Zona zona = obtenerZonaPorId(zonaId);
        if (zona != null && zona.getEntradasDisponibles() < zona.getCapacidadTotal()) {
            zona.setEntradasDisponibles(zona.getEntradasDisponibles() + 1);
            zonaRepository.save(zona);
        }
    }
}
