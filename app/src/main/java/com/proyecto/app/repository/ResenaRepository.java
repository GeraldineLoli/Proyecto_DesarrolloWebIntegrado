package com.proyecto.app.repository;

import com.proyecto.app.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByEventoId(Long eventoId);
    List<Resena> findByUsuarioId(Long usuarioId);
}
