package com.proyecto.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private String metodoPago; // VISA, MASTERCARD, YAPE, PLIN, EFECTIVO

    @Column(nullable = false)
    private String estado; // PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO

    private String codigoTransaccion;

    private String numeroTarjeta; // Últimos 4 dígitos solamente

    private LocalDateTime fechaPago;

    private String comprobanteUrl;

    private String notas;

    public Pago() {
        this.estado = "PENDIENTE";
    }

    public Pago(Long pedidoId, Long usuarioId, double monto, String metodoPago, String codigoTransaccion) {
        this.pedidoId = pedidoId;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.codigoTransaccion = codigoTransaccion;
        this.estado = "COMPLETADO";
        this.fechaPago = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        if (numeroTarjeta != null && numeroTarjeta.length() > 4) {
            this.numeroTarjeta = "****" + numeroTarjeta.substring(numeroTarjeta.length() - 4);
        } else {
            this.numeroTarjeta = numeroTarjeta;
        }
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getComprobanteUrl() {
        return comprobanteUrl;
    }

    public void setComprobanteUrl(String comprobanteUrl) {
        this.comprobanteUrl = comprobanteUrl;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
