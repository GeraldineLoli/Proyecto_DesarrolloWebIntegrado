package com.proyecto.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.app.model.Resena;

@Service
public class ResenaService {
    private List<Resena> resenas;
    private int nextId;
    private final EntradaService entradaService;
    private final UsuarioService usuarioService;

    @Autowired
    public ResenaService(EntradaService entradaService, UsuarioService usuarioService) {
        this.resenas = new ArrayList<>();
        this.nextId = 1;
        this.entradaService = entradaService;
        this.usuarioService = usuarioService;
        initData();
    }

    // Constructor para testing
    public ResenaService(EntradaService entradaService, UsuarioService usuarioService, boolean inicializarDatos) {
        this.resenas = new ArrayList<>();
        this.nextId = 1;
        this.entradaService = entradaService;
        this.usuarioService = usuarioService;
        if (inicializarDatos) {
            initData();
        }
    }

    private void initData() {
        // Reseña del usuario 1 sobre el evento 1 (entrada 1 - estado PAGADA, no USADA)
        // Solo se agregan reseñas de entradas que ya fueron USADAS
        Resena r1 = new Resena(1, 2, 3, 3, 5, "El Rey León fue increíble, el elenco fue espectacular.");
        r1.setFecha(LocalDate.of(2026, 4, 11));

        Resena r2 = new Resena(2, 1, 2, 2, 4, "Coldplay superó todas mis expectativas. Altamente recomendado.");
        r2.setFecha(LocalDate.of(2026, 4, 3));

        resenas.add(r1);
        resenas.add(r2);
        nextId = 3;
    }

    public List<Resena> todas() {
        return new ArrayList<>(resenas);
    }

    public Resena obtenerResena(int id) {
        return resenas.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Resena> obtenerPorEvento(int eventoId) {
        return resenas.stream()
                .filter(r -> r.getEventoId() == eventoId)
                .collect(Collectors.toList());
    }

    public List<Resena> obtenerPorUsuario(int usuarioId) {
        return resenas.stream()
                .filter(r -> r.getUsuarioId() == usuarioId)
                .collect(Collectors.toList());
    }

    public double obtenerPromedioEvento(int eventoId) {
        List<Resena> resenasEvento = obtenerPorEvento(eventoId);
        if (resenasEvento.isEmpty()) {
            return 0.0;
        }
        return resenasEvento.stream()
                .mapToInt(Resena::getCalificacion)
                .average()
                .orElse(0.0);
    }

    public void agregarResena(Resena resena) {
        // Validar que el usuario existe
        if (usuarioService.obtenerUsuario(resena.getUsuarioId()) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        // Validar que la entrada existe y fue usada
        var entrada = entradaService.obtenerEntrada(resena.getEntradaId());
        if (entrada == null) {
            throw new RuntimeException("Entrada no encontrada");
        }
        if (!entrada.getEstado().equals("USADA")) {
            throw new RuntimeException("Solo se puede reseñar eventos a los que asististe");
        }
        // Validar calificacion entre 1 y 5
        if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new RuntimeException("La calificacion debe estar entre 1 y 5");
        }
        if (resena.getFecha() == null) {
            resena.setFecha(LocalDate.now());
        }
        resena.setId(nextId++);
        resenas.add(resena);
    }

    public void actualizarResena(int id, Resena actualizada) {
        if (actualizada.getCalificacion() < 1 || actualizada.getCalificacion() > 5) {
            throw new RuntimeException("La calificacion debe estar entre 1 y 5");
        }
        for (int i = 0; i < resenas.size(); i++) {
            if (resenas.get(i).getId() == id) {
                actualizada.setId(id);
                resenas.set(i, actualizada);
                return;
            }
        }
    }

    public void eliminarResena(int id) {
        resenas.removeIf(r -> r.getId() == id);
    }
}
