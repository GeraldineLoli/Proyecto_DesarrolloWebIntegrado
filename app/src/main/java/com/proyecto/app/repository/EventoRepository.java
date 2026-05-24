package com.proyecto.app.repository;

import com.proyecto.app.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Métodos derivados de Spring Data JPA (ya existentes)
    List<Evento> findByCategoria(String categoria);
    List<Evento> findByFechaHoraAfter(LocalDateTime fecha);
    List<Evento> findByActivoTrue();
    
    // Buscar eventos por categoría usando JPQL
    @Query("SELECT e FROM Evento e WHERE e.categoria = ?1")
    List<Evento> buscarPorCategoria(String categoria);
    
    // Buscar eventos por nombre (búsqueda parcial)
    @Query("SELECT e FROM Evento e WHERE e.nombre LIKE %:nombre%")
    List<Evento> buscarPorNombre(@Param("nombre") String nombre);
    
    // Buscar eventos por artista principal
    @Query("SELECT e FROM Evento e WHERE e.artistaPrincipal = :artista")
    List<Evento> buscarPorArtistaPrincipal(@Param("artista") String artista);
    
    // Buscar eventos por lugar
    @Query("SELECT e FROM Evento e WHERE e.lugar = :lugar")
    List<Evento> buscarPorLugar(@Param("lugar") String lugar);
    
    // Buscar eventos próximos (después de una fecha)
    @Query("SELECT e FROM Evento e WHERE e.fechaHora > :fecha AND e.activo = true")
    List<Evento> buscarEventosProximos(@Param("fecha") LocalDateTime fecha);
    
    // Buscar eventos entre dos fechas
    @Query("SELECT e FROM Evento e WHERE e.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    List<Evento> buscarEventosEntreFechas(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                           @Param("fechaFin") LocalDateTime fechaFin);
    
    // Buscar eventos por categoría y que estén activos
    @Query("SELECT e FROM Evento e WHERE e.categoria = :categoria AND e.activo = true")
    List<Evento> buscarPorCategoriaActivos(@Param("categoria") String categoria);
    
    // Buscar eventos con edad mínima menor o igual a un valor
    @Query("SELECT e FROM Evento e WHERE e.edadMinima <= :edad")
    List<Evento> buscarEventosParaEdad(@Param("edad") int edad);
    
    // Buscar eventos por duración mínima
    @Query("SELECT e FROM Evento e WHERE e.duracionMinutos >= :duracion")
    List<Evento> buscarEventosPorDuracionMinima(@Param("duracion") int duracion);
    
    // Contar eventos por categoría
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.categoria = :categoria")
    long contarPorCategoria(@Param("categoria") String categoria);
    
    // Buscar eventos activos ordenados por fecha
    @Query("SELECT e FROM Evento e WHERE e.activo = true ORDER BY e.fechaHora ASC")
    List<Evento> buscarEventosActivosOrdenados();
    
    // Buscar eventos por categoría y lugar
    @Query("SELECT e FROM Evento e WHERE e.categoria = :categoria AND e.lugar = :lugar")
    List<Evento> buscarPorCategoriaYLugar(@Param("categoria") String categoria, 
                                           @Param("lugar") String lugar);
    
    // Buscar eventos del día actual (usando CAST para compatibilidad)
    @Query("SELECT e FROM Evento e WHERE CAST(e.fechaHora AS LocalDate) = CURRENT_DATE")
    List<Evento> buscarEventosDeHoy();
    
    // Buscar eventos por nombre o descripción (búsqueda de texto)
    @Query("SELECT e FROM Evento e WHERE e.nombre LIKE %:texto% OR e.descripcion LIKE %:texto%")
    List<Evento> buscarPorTexto(@Param("texto") String texto);
}
