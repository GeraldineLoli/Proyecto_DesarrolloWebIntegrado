package com.proyecto.app.repository;

import com.proyecto.app.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
    // Métodos derivados de Spring Data JPA (ya existentes)
    Optional<Promocion> findByCodigo(String codigo);
    List<Promocion> findByEventoId(Long eventoId);
    List<Promocion> findByActivo(boolean activo);
    
    // ========== Consultas JPQL ==========
    
    // Buscar promociones por código usando JPQL
    @Query("SELECT p FROM Promocion p WHERE p.codigo = ?1")
    Optional<Promocion> buscarPorCodigo(String codigo);
    
    // Buscar promociones por evento
    @Query("SELECT p FROM Promocion p WHERE p.eventoId = :eventoId")
    List<Promocion> buscarPorEvento(@Param("eventoId") Long eventoId);
    
    // Buscar promociones activas
    @Query("SELECT p FROM Promocion p WHERE p.activo = true")
    List<Promocion> buscarPromocionesActivas();
    
    // Buscar promociones por descripción (búsqueda parcial)
    @Query("SELECT p FROM Promocion p WHERE p.descripcion LIKE %:texto%")
    List<Promocion> buscarPorDescripcion(@Param("texto") String texto);
    
    // Buscar promociones vigentes (entre fechas de inicio y fin)
    @Query("SELECT p FROM Promocion p WHERE :fecha BETWEEN p.fechaInicio AND p.fechaFin AND p.activo = true")
    List<Promocion> buscarPromocionesVigentes(@Param("fecha") LocalDateTime fecha);
    
    // Buscar promociones por porcentaje de descuento mayor a un valor
    @Query("SELECT p FROM Promocion p WHERE p.porcentajeDescuento > :porcentaje")
    List<Promocion> buscarPorDescuentoMayor(@Param("porcentaje") double porcentaje);
    
    // Buscar promociones por rango de descuento
    @Query("SELECT p FROM Promocion p WHERE p.porcentajeDescuento BETWEEN :min AND :max")
    List<Promocion> buscarPorRangoDescuento(@Param("min") double min, @Param("max") double max);
    
    // Buscar promociones con disponibilidad
    @Query("SELECT p FROM Promocion p WHERE p.cantidadUsada < p.cantidadDisponible AND p.activo = true")
    List<Promocion> buscarConDisponibilidad();
    
    // Buscar promociones que expiran pronto (en los próximos días)
    @Query("SELECT p FROM Promocion p WHERE p.fechaFin BETWEEN :ahora AND :fechaLimite AND p.activo = true")
    List<Promocion> buscarQueExpiranProximo(@Param("ahora") LocalDateTime ahora, 
                                            @Param("fechaLimite") LocalDateTime fechaLimite);
    
    // Buscar promociones por evento y que estén activas
    @Query("SELECT p FROM Promocion p WHERE p.eventoId = :eventoId AND p.activo = true")
    List<Promocion> buscarPorEventoActivas(@Param("eventoId") Long eventoId);
    
    // Contar promociones por evento
    @Query("SELECT COUNT(p) FROM Promocion p WHERE p.eventoId = :eventoId")
    long contarPorEvento(@Param("eventoId") Long eventoId);
    
    // Contar promociones activas
    @Query("SELECT COUNT(p) FROM Promocion p WHERE p.activo = true")
    long contarPromocionesActivas();
    
    // Buscar promociones ordenadas por descuento (mayor a menor)
    @Query("SELECT p FROM Promocion p WHERE p.activo = true ORDER BY p.porcentajeDescuento DESC")
    List<Promocion> buscarOrdenadasPorDescuento();
    
    // Buscar promociones más usadas
    @Query("SELECT p FROM Promocion p ORDER BY p.cantidadUsada DESC")
    List<Promocion> buscarMasUsadas();
    
    // Calcular promedio de descuento de promociones activas
    @Query("SELECT AVG(p.porcentajeDescuento) FROM Promocion p WHERE p.activo = true")
    Double calcularPromedioDescuento();
    
    // Buscar promociones por código o descripción (búsqueda de texto)
    @Query("SELECT p FROM Promocion p WHERE p.codigo LIKE %:texto% OR p.descripcion LIKE %:texto%")
    List<Promocion> buscarPorTexto(@Param("texto") String texto);
}
