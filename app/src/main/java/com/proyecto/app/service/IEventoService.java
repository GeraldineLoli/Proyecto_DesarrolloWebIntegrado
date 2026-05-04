package com.proyecto.app.service;

import com.proyecto.app.model.Evento;
import java.util.List;

public interface IEventoService {
    Evento agregarEvento(Evento evento);
    Evento obtenerEvento(Long id);
    List<Evento> todos();
    List<Evento> obtenerPorCategoria(String categoria);
    List<Evento> obtenerEventosProximos();
    Evento actualizarEvento(Long id, Evento eventoActualizado);
    void eliminarEvento(Long id);
}
