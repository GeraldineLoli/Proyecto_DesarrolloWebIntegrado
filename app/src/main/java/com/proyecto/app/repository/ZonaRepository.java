package com.proyecto.app.repository;

import com.proyecto.app.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

    // Métodos derivados de Spring Data JPA
    List<Zona> findByEventoId(Long eventoId);

    // ========== Consultas JPQL ==========

    // Buscar zonas por evento usando JPQL
    @Query("SELECT z FROM Zona z WHERE z.eventoId = :eventoId")
    List<Zona> buscarPorEvento(@Param("eventoId") Long eventoId);

    // Buscar zonas por nombre
    @Query("SELECT z FROM Zona z WHERE z.nombre = :nombre")
    List<Zona> buscarPorNombre(@Param("nombre") String nombre);

    // Buscar zonas con entradas disponibles
    @Query("SELECT z FROM Zona z WHERE z.entradasDisponibles > 0")
    List<Zona> buscarConDisponibilidad();

    // Buscar zonas por precio menor o igual a un valor
    @Query("SELECT z FROM Zona z WHERE z.precio <= :precio")
    List<Zona> buscarPorPrecioMaximo(@Param("precio") double precio);

    // Contar zonas por evento
    @Query("SELECT COUNT(z) FROM Zona z WHERE z.eventoId = :eventoId")
    long contarPorEvento(@Param("eventoId") Long eventoId);
}
