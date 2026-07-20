package com.proyecto.app.service;

import com.proyecto.app.model.Zona;
import com.proyecto.app.repository.ZonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public synchronized boolean reservarEntrada(Long zonaId) {
        // Forzar recarga desde base de datos para evitar problemas de caché
        zonaRepository.flush();
        Zona zona = zonaRepository.findById(zonaId).orElse(null);
        
        if (zona != null && zona.getEntradasDisponibles() > 0) {
            int nuevasDisponibles = zona.getEntradasDisponibles() - 1;
            zona.setEntradasDisponibles(nuevasDisponibles);
            zonaRepository.save(zona);
            zonaRepository.flush(); // Forzar escritura inmediata a la BD
            System.out.println("✅ ZONA ACTUALIZADA - ID: " + zonaId + " - Entradas disponibles: " + nuevasDisponibles);
            return true;
        }
        System.out.println("❌ NO SE PUDO RESERVAR - Zona ID: " + zonaId + " - Disponibles: " + (zona != null ? zona.getEntradasDisponibles() : "zona null"));
        return false;
    }
    
    @Override
    @Transactional
    public synchronized void liberarEntrada(Long zonaId) {
        // Forzar recarga desde base de datos para evitar problemas de caché
        zonaRepository.flush();
        Zona zona = zonaRepository.findById(zonaId).orElse(null);
        
        if (zona != null && zona.getEntradasDisponibles() < zona.getCapacidadTotal()) {
            int nuevasDisponibles = zona.getEntradasDisponibles() + 1;
            zona.setEntradasDisponibles(nuevasDisponibles);
            zonaRepository.save(zona);
            zonaRepository.flush(); // Forzar escritura inmediata a la BD
            System.out.println("✅ ZONA LIBERADA - ID: " + zonaId + " - Entradas disponibles: " + nuevasDisponibles);
        }
    }
}
