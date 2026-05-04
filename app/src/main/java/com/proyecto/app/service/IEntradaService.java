package com.proyecto.app.service;

import com.proyecto.app.model.Entrada;
import java.util.List;

public interface IEntradaService {
    Entrada crearEntrada(Entrada entrada);
    Entrada obtenerEntradaPorId(Long id);
    List<Entrada> obtenerTodasLasEntradas();
    List<Entrada> obtenerEntradasPorUsuario(Long usuarioId);
    List<Entrada> obtenerEntradasPorEvento(Long eventoId);
    List<Entrada> obtenerEntradasActivas();
    Entrada actualizarEntrada(Entrada entrada);
    void eliminarEntrada(Long id);
}
