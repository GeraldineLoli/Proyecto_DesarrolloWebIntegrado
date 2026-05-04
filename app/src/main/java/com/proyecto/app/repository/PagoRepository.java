package com.proyecto.app.repository;

import com.proyecto.app.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByUsuarioId(Long usuarioId);
    List<Pago> findByPedidoId(Long pedidoId);
    List<Pago> findByEstado(String estado);
}