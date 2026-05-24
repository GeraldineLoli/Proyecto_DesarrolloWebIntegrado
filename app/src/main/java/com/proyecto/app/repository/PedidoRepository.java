package com.proyecto.app.repository;

import com.proyecto.app.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Métodos derivados de Spring Data JPA (ya existentes)
    List<Pedido> findByUsuarioId(Long usuarioId);
    Optional<Pedido> findByCodigoPedido(String codigoPedido);
    
    // ========== Consultas JPQL ==========
    
    // Buscar pedidos por estado usando JPQL
    @Query("SELECT p FROM Pedido p WHERE p.estado = ?1")
    List<Pedido> buscarPorEstado(String estado);
    
    // Buscar pedidos por usuario
    @Query("SELECT p FROM Pedido p WHERE p.usuarioId = :usuarioId")
    List<Pedido> buscarPorUsuario(@Param("usuarioId") Long usuarioId);
    
    // Buscar pedidos por código de pedido (búsqueda parcial)
    @Query("SELECT p FROM Pedido p WHERE p.codigoPedido LIKE %:codigo%")
    List<Pedido> buscarPorCodigoPedidoParcial(@Param("codigo") String codigo);
    
    // Buscar pedidos creados después de una fecha
    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion > :fecha")
    List<Pedido> buscarCreadosDespuesDe(@Param("fecha") LocalDateTime fecha);
    
    // Buscar pedidos entre dos fechas
    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> buscarEntreFechas(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                   @Param("fechaFin") LocalDateTime fechaFin);
    
    // Buscar pedidos por estado y usuario
    @Query("SELECT p FROM Pedido p WHERE p.estado = :estado AND p.usuarioId = :usuarioId")
    List<Pedido> buscarPorEstadoYUsuario(@Param("estado") String estado, 
                                         @Param("usuarioId") Long usuarioId);
    
    // Buscar pedidos con total mayor a un monto
    @Query("SELECT p FROM Pedido p WHERE p.total > :monto")
    List<Pedido> buscarPorMontoMayor(@Param("monto") double monto);
    
    // Buscar pedidos con total entre dos montos
    @Query("SELECT p FROM Pedido p WHERE p.total BETWEEN :montoMin AND :montoMax")
    List<Pedido> buscarPorRangoMonto(@Param("montoMin") double montoMin, 
                                     @Param("montoMax") double montoMax);
    
    // Contar pedidos por estado
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.estado = :estado")
    long contarPorEstado(@Param("estado") String estado);
    
    // Contar pedidos por usuario
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.usuarioId = :usuarioId")
    long contarPorUsuario(@Param("usuarioId") Long usuarioId);
    
    // Buscar pedidos ordenados por fecha (más recientes primero)
    @Query("SELECT p FROM Pedido p ORDER BY p.fechaCreacion DESC")
    List<Pedido> buscarOrdenadosPorFecha();
    
    // Buscar pedidos del día actual
    @Query("SELECT p FROM Pedido p WHERE CAST(p.fechaCreacion AS LocalDate) = CURRENT_DATE")
    List<Pedido> buscarPedidosDeHoy();
    
    // Calcular total de ventas por usuario
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.usuarioId = :usuarioId AND p.estado = 'PAGADO'")
    Double calcularTotalVentasPorUsuario(@Param("usuarioId") Long usuarioId);
    
    // Calcular total de ventas por estado
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.estado = :estado")
    Double calcularTotalVentasPorEstado(@Param("estado") String estado);
    
    // Buscar pedidos pagados ordenados por total (mayor a menor)
    @Query("SELECT p FROM Pedido p WHERE p.estado = 'PAGADO' ORDER BY p.total DESC")
    List<Pedido> buscarPedidosPagadosOrdenadosPorTotal();
}
