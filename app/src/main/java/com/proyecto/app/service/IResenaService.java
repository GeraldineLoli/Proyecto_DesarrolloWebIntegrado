package com.proyecto.app.service;

import com.proyecto.app.model.Resena;
import java.util.List;

public interface IResenaService {
    Resena agregarResena(Resena resena);
    Resena obtenerResena(Long id);
    List<Resena> todas();
    List<Resena> obtenerPorEvento(Long eventoId);
    List<Resena> obtenerPorUsuario(Long usuarioId);
    double obtenerPromedioEvento(Long eventoId);
    Resena actualizarResena(Long id, Resena actualizada);
    void eliminarResena(Long id);
}
