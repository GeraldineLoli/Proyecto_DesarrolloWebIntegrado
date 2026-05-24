package com.proyecto.app.repository;

import com.proyecto.app.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Métodos derivados de Spring Data JPA
    List<Resena> findByEventoId(Long eventoId);
    List<Resena> findByUsuarioId(Long usuarioId);

    // ========== Consultas JPQL ==========

    // Buscar reseñas por evento usando JPQL
    @Query("SELECT r FROM Resena r WHERE r.eventoId = :eventoId")
    List<Resena> buscarPorEvento(@Param("eventoId") Long eventoId);

    // Buscar reseñas por usuario
    @Query("SELECT r FROM Resena r WHERE r.usuarioId = :usuarioId")
    List<Resena> buscarPorUsuario(@Param("usuarioId") Long usuarioId);

    // Buscar reseñas por calificación
    @Query("SELECT r FROM Resena r WHERE r.calificacion = :calificacion")
    List<Resena> buscarPorCalificacion(@Param("calificacion") int calificacion);

    // Buscar reseñas por calificación mayor o igual a un valor
    @Query("SELECT r FROM Resena r WHERE r.calificacion >= :calificacion")
    List<Resena> buscarPorCalificacionMinima(@Param("calificacion") int calificacion);

    // Calcular promedio de calificación por evento
    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.eventoId = :eventoId")
    Double calcularPromedioCalificacion(@Param("eventoId") Long eventoId);

    // Contar reseñas por evento
    @Query("SELECT COUNT(r) FROM Resena r WHERE r.eventoId = :eventoId")
    long contarPorEvento(@Param("eventoId") Long eventoId);
}
