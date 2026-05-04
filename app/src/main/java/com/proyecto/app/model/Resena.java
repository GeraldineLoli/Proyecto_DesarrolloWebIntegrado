package com.proyecto.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventoId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long entradaId;

    @Column(nullable = false)
    private int calificacion; // 1 a 5 estrellas

    @Column(length = 1000)
    private String comentario;

    private LocalDate fecha;

    public Resena() {
    }

    public Resena(Long eventoId, Long usuarioId, Long entradaId, int calificacion, String comentario) {
        this.eventoId = eventoId;
        this.usuarioId = usuarioId;
        this.entradaId = entradaId;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(Long entradaId) {
        this.entradaId = entradaId;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
