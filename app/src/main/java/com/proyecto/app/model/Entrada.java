package com.proyecto.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entradas")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventoId;

    @Column(nullable = false)
    private Long zonaId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(unique = true)
    private String codigoEntrada;

    private int numeroAsiento;
    private String fila;
    private double precioPagado;
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private String estado; // PAGADA, CANCELADA, USADA, REEMBOLSADA

    private String metodoPago;
    private String codigoTransaccion;

    public Entrada() {
        this.fechaCompra = LocalDateTime.now();
        this.estado = "PAGADA";
    }

    public Entrada(Long eventoId, Long zonaId, Long usuarioId, String codigoEntrada,
                   double precioPagado, String metodoPago) {
        this.eventoId = eventoId;
        this.zonaId = zonaId;
        this.usuarioId = usuarioId;
        this.codigoEntrada = codigoEntrada;
        this.precioPagado = precioPagado;
        this.metodoPago = metodoPago;
        this.fechaCompra = LocalDateTime.now();
        this.estado = "PAGADA";
        this.numeroAsiento = -1;
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

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCodigoEntrada() {
        return codigoEntrada;
    }

    public void setCodigoEntrada(String codigoEntrada) {
        this.codigoEntrada = codigoEntrada;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public double getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(double precioPagado) {
        this.precioPagado = precioPagado;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }
}
