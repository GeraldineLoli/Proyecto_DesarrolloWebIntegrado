package com.proyecto.app.service;

import com.proyecto.app.model.Zona;
import java.util.List;

public interface IZonaService {
    Zona crearZona(Zona zona);
    Zona obtenerZonaPorId(Long id);
    List<Zona> obtenerTodasLasZonas();
    List<Zona> obtenerZonasPorEvento(Long eventoId);
    Zona actualizarZona(Zona zona);
    void eliminarZona(Long id);
    boolean reservarEntrada(Long zonaId);
    void liberarEntrada(Long zonaId);
}
