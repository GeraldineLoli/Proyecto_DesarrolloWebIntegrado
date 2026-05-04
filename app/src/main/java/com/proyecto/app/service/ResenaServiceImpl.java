package com.proyecto.app.service;

import com.proyecto.app.model.Resena;
import com.proyecto.app.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResenaServiceImpl implements IResenaService {

    private final ResenaRepository resenaRepository;

    @Autowired
    public ResenaServiceImpl(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    @Override
    public Resena agregarResena(Resena resena) {
        if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new RuntimeException("La calificacion debe estar entre 1 y 5");
        }
        if (resena.getFecha() == null) {
            resena.setFecha(LocalDate.now());
        }
        return resenaRepository.save(resena);
    }

    @Override
    public Resena obtenerResena(Long id) {
        return resenaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Resena> todas() {
        return resenaRepository.findAll();
    }

    @Override
    public List<Resena> obtenerPorEvento(Long eventoId) {
        return resenaRepository.findByEventoId(eventoId);
    }

    @Override
    public List<Resena> obtenerPorUsuario(Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public double obtenerPromedioEvento(Long eventoId) {
        List<Resena> resenasEvento = obtenerPorEvento(eventoId);
        if (resenasEvento.isEmpty()) {
            return 0.0;
        }
        return resenasEvento.stream()
                .mapToInt(Resena::getCalificacion)
                .average()
                .orElse(0.0);
    }

    @Override
    public Resena actualizarResena(Long id, Resena actualizada) {
        if (actualizada.getCalificacion() < 1 || actualizada.getCalificacion() > 5) {
            throw new RuntimeException("La calificacion debe estar entre 1 y 5");
        }
        Resena existente = resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con id: " + id));
        existente.setCalificacion(actualizada.getCalificacion());
        existente.setComentario(actualizada.getComentario());
        existente.setFecha(actualizada.getFecha() != null ? actualizada.getFecha() : existente.getFecha());
        return resenaRepository.save(existente);
    }

    @Override
    public void eliminarResena(Long id) {
        resenaRepository.deleteById(id);
    }
}
