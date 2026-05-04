package com.proyecto.app.service;

import com.proyecto.app.model.Evento;
import com.proyecto.app.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoServiceImpl implements IEventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoServiceImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento agregarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public Evento obtenerEvento(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Evento> todos() {
        return eventoRepository.findAll();
    }

    @Override
    public List<Evento> obtenerPorCategoria(String categoria) {
        return eventoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Evento> obtenerEventosProximos() {
        return eventoRepository.findByFechaHoraAfter(LocalDateTime.now());
    }

    @Override
    public Evento actualizarEvento(Long id, Evento eventoActualizado) {
        Evento existente = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con id: " + id));
        existente.setNombre(eventoActualizado.getNombre());
        existente.setDescripcion(eventoActualizado.getDescripcion());
        existente.setCategoria(eventoActualizado.getCategoria());
        existente.setLugar(eventoActualizado.getLugar());
        existente.setDireccion(eventoActualizado.getDireccion());
        existente.setFechaHora(eventoActualizado.getFechaHora());
        existente.setDuracionMinutos(eventoActualizado.getDuracionMinutos());
        existente.setImagenUrl(eventoActualizado.getImagenUrl());
        existente.setArtistaPrincipal(eventoActualizado.getArtistaPrincipal());
        existente.setArtistasInvitados(eventoActualizado.getArtistasInvitados());
        existente.setEdadMinima(eventoActualizado.getEdadMinima());
        existente.setActivo(eventoActualizado.isActivo());
        return eventoRepository.save(existente);
    }

    @Override
    public void eliminarEvento(Long id) {
        eventoRepository.deleteById(id);
    }
}
