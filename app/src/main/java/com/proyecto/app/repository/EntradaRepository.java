package com.proyecto.app.repository;

import com.proyecto.app.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findByUsuarioId(Long usuarioId);
    List<Entrada> findByEventoId(Long eventoId);
    List<Entrada> findByEstado(String estado);
    Optional<Entrada> findByCodigoEntrada(String codigoEntrada);
}
