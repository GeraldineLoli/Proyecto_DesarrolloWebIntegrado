package com.proyecto.app.repository;

import com.proyecto.app.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    // Métodos derivados de Spring Data JPA
    List<Entrada> findByUsuarioId(Long usuarioId);
    List<Entrada> findByEventoId(Long eventoId);
    List<Entrada> findByEstado(String estado);
    Optional<Entrada> findByCodigoEntrada(String codigoEntrada);

    // ========== Consultas JPQL ==========

    // Buscar entradas por usuario usando JPQL
    @Query("SELECT e FROM Entrada e WHERE e.usuarioId = :usuarioId")
    List<Entrada> buscarPorUsuario(@Param("usuarioId") Long usuarioId);

    // Buscar entradas por evento
    @Query("SELECT e FROM Entrada e WHERE e.eventoId = :eventoId")
    List<Entrada> buscarPorEvento(@Param("eventoId") Long eventoId);

    // Buscar entradas por estado
    @Query("SELECT e FROM Entrada e WHERE e.estado = :estado")
    List<Entrada> buscarPorEstado(@Param("estado") String estado);

    // Buscar entradas por zona
    @Query("SELECT e FROM Entrada e WHERE e.zonaId = :zonaId")
    List<Entrada> buscarPorZona(@Param("zonaId") Long zonaId);

    // Buscar entradas por método de pago
    @Query("SELECT e FROM Entrada e WHERE e.metodoPago = :metodoPago")
    List<Entrada> buscarPorMetodoPago(@Param("metodoPago") String metodoPago);

    // Contar entradas por evento y estado
    @Query("SELECT COUNT(e) FROM Entrada e WHERE e.eventoId = :eventoId AND e.estado = :estado")
    long contarPorEventoYEstado(@Param("eventoId") Long eventoId, @Param("estado") String estado);

    // Calcular total recaudado por evento
    @Query("SELECT SUM(e.precioPagado) FROM Entrada e WHERE e.eventoId = :eventoId AND e.estado = 'PAGADA'")
    Double calcularTotalRecaudadoPorEvento(@Param("eventoId") Long eventoId);
}
