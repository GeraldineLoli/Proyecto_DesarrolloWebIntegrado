package com.proyecto.app.repository;

import com.proyecto.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByCategoria(String categoria);
    List<Evento> findByFechaHoraAfter(LocalDateTime fecha);
    List<Evento> findByActivoTrue();
}
