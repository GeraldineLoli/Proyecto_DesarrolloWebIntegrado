package com.proyecto.app.model;

import java.time.LocalDateTime;

public class Promocion {
    private int id;
    private String codigo;
    private String descripcion;
    private double porcentajeDescuento;
    private int eventoId; // Relación con Evento
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int cantidadDisponible;
    private int cantidadUsada;
    private boolean activo;
    
    public Promocion() {}
    
    public Promocion(int id, String codigo, String descripcion, double porcentajeDescuento, 
                     int eventoId, LocalDateTime fechaInicio, LocalDateTime fechaFin, 
                     int cantidadDisponible) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.eventoId = eventoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadUsada = 0;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
    
    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    public int getEventoId() {
        return eventoId;
    }
    
    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
    public int getCantidadUsada() {
        return cantidadUsada;
    }
    
    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
