package com.proyecto.app.repository;

import com.proyecto.app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método derivado de Spring Data JPA (ya existente)
    Optional<Usuario> findByEmail(String email);
    
    // Buscar usuarios por nombre usando JPQL
    @Query("SELECT u FROM Usuario u WHERE u.nombre = ?1")
    List<Usuario> buscarPorNombre(String nombre);
    
    // Buscar usuarios por apellido usando JPQL con parámetro nombrado
    @Query("SELECT u FROM Usuario u WHERE u.apellido = :apellido")
    List<Usuario> buscarPorApellido(@Param("apellido") String apellido);
    
    // Buscar usuarios por nombre o apellido
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:texto% OR u.apellido LIKE %:texto%")
    List<Usuario> buscarPorNombreOApellido(@Param("texto") String texto);
    
    // Buscar usuarios por rol
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> buscarPorRol(@Param("rol") String rol);
    
    // Buscar usuarios registrados después de una fecha
    @Query("SELECT u FROM Usuario u WHERE u.fechaRegistro > :fecha")
    List<Usuario> buscarRegistradosDespuesDe(@Param("fecha") LocalDate fecha);
    
    // Buscar usuarios por DNI
    @Query("SELECT u FROM Usuario u WHERE u.dni = :dni")
    Optional<Usuario> buscarPorDni(@Param("dni") String dni);
    
    // Contar usuarios por rol
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
    long contarPorRol(@Param("rol") String rol);
    
    // Buscar usuarios con email que contenga un dominio específico
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:dominio%")
    List<Usuario> buscarPorDominioEmail(@Param("dominio") String dominio);
}
