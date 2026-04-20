package com.proyecto.app.model;

import java.time.LocalDate;

public class Resena {
    private int id;
    private int eventoId;
    private int usuarioId;
    private int entradaId;
    private int calificacion; // 1 a 5 estrellas
    private String comentario;
    private LocalDate fecha;

    public Resena() {
    }

    public Resena(int id, int eventoId, int usuarioId, int entradaId, int calificacion, String comentario) {
        this.id = id;
        this.eventoId = eventoId;
        this.usuarioId = usuarioId;
        this.entradaId = entradaId;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(int entradaId) {
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
