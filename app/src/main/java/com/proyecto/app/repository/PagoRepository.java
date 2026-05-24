package com.proyecto.app.repository;

import com.proyecto.app.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    // Métodos derivados de Spring Data JPA (ya existentes)
    List<Pago> findByUsuarioId(Long usuarioId);
    List<Pago> findByPedidoId(Long pedidoId);
    List<Pago> findByEstado(String estado);
    
    // ========== Consultas JPQL ==========
    
    // Buscar pagos por estado usando JPQL
    @Query("SELECT p FROM Pago p WHERE p.estado = ?1")
    List<Pago> buscarPorEstado(String estado);
    
    // Buscar pagos por usuario
    @Query("SELECT p FROM Pago p WHERE p.usuarioId = :usuarioId")
    List<Pago> buscarPorUsuario(@Param("usuarioId") Long usuarioId);
    
    // Buscar pagos por pedido
    @Query("SELECT p FROM Pago p WHERE p.pedidoId = :pedidoId")
    List<Pago> buscarPorPedido(@Param("pedidoId") Long pedidoId);
    
    // Buscar pagos por método de pago
    @Query("SELECT p FROM Pago p WHERE p.metodoPago = :metodo")
    List<Pago> buscarPorMetodoPago(@Param("metodo") String metodo);
    
    // Buscar pagos por código de transacción (búsqueda parcial)
    @Query("SELECT p FROM Pago p WHERE p.codigoTransaccion LIKE %:codigo%")
    List<Pago> buscarPorCodigoTransaccion(@Param("codigo") String codigo);
    
    // Buscar pagos realizados después de una fecha
    @Query("SELECT p FROM Pago p WHERE p.fechaPago > :fecha")
    List<Pago> buscarRealizadosDespuesDe(@Param("fecha") LocalDateTime fecha);
    
    // Buscar pagos entre dos fechas
    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<Pago> buscarEntreFechas(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                 @Param("fechaFin") LocalDateTime fechaFin);
    
    // Buscar pagos por monto mayor a un valor
    @Query("SELECT p FROM Pago p WHERE p.monto > :monto")
    List<Pago> buscarPorMontoMayor(@Param("monto") double monto);
    
    // Buscar pagos por rango de monto
    @Query("SELECT p FROM Pago p WHERE p.monto BETWEEN :montoMin AND :montoMax")
    List<Pago> buscarPorRangoMonto(@Param("montoMin") double montoMin, 
                                   @Param("montoMax") double montoMax);
    
    // Buscar pagos por estado y usuario
    @Query("SELECT p FROM Pago p WHERE p.estado = :estado AND p.usuarioId = :usuarioId")
    List<Pago> buscarPorEstadoYUsuario(@Param("estado") String estado, 
                                       @Param("usuarioId") Long usuarioId);
    
    // Buscar pagos por método y estado
    @Query("SELECT p FROM Pago p WHERE p.metodoPago = :metodo AND p.estado = :estado")
    List<Pago> buscarPorMetodoYEstado(@Param("metodo") String metodo, 
                                      @Param("estado") String estado);
    
    // Contar pagos por estado
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.estado = :estado")
    long contarPorEstado(@Param("estado") String estado);
    
    // Contar pagos por método de pago
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.metodoPago = :metodo")
    long contarPorMetodoPago(@Param("metodo") String metodo);
    
    // Contar pagos por usuario
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.usuarioId = :usuarioId")
    long contarPorUsuario(@Param("usuarioId") Long usuarioId);
    
    // Buscar pagos ordenados por fecha (más recientes primero)
    @Query("SELECT p FROM Pago p ORDER BY p.fechaPago DESC")
    List<Pago> buscarOrdenadosPorFecha();
    
    // Buscar pagos del día actual
    @Query("SELECT p FROM Pago p WHERE CAST(p.fechaPago AS LocalDate) = CURRENT_DATE")
    List<Pago> buscarPagosDeHoy();
    
    // Calcular total recaudado por usuario
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.usuarioId = :usuarioId AND p.estado = 'COMPLETADO'")
    Double calcularTotalRecaudadoPorUsuario(@Param("usuarioId") Long usuarioId);
    
    // Calcular total recaudado por método de pago
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.metodoPago = :metodo AND p.estado = 'COMPLETADO'")
    Double calcularTotalPorMetodoPago(@Param("metodo") String metodo);
    
    // Calcular total recaudado por estado
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = :estado")
    Double calcularTotalPorEstado(@Param("estado") String estado);
    
    // Buscar pagos completados ordenados por monto (mayor a menor)
    @Query("SELECT p FROM Pago p WHERE p.estado = 'COMPLETADO' ORDER BY p.monto DESC")
    List<Pago> buscarCompletadosOrdenadosPorMonto();
    
    // Calcular promedio de monto de pagos completados
    @Query("SELECT AVG(p.monto) FROM Pago p WHERE p.estado = 'COMPLETADO'")
    Double calcularPromedioMontoPagos();
}