package com.proyecto.app.repository;

import com.proyecto.app.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    Optional<Promocion> findByCodigo(String codigo);
    List<Promocion> findByEventoId(Long eventoId);
    List<Promocion> findByActivo(boolean activo);
}
