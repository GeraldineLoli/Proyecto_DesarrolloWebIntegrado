package com.proyecto.app.model;

import java.time.LocalDateTime;

public class Pago {
    private int id;
    private int pedidoId;
    private int usuarioId;
    private double monto;
    private String metodoPago; // VISA, MASTERCARD, YAPE, PLIN, EFECTIVO
    private String estado; // PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO
    private String codigoTransaccion;
    private String numeroTarjeta; // Últimos 4 dígitos solamente
    private LocalDateTime fechaPago;
    private String comprobanteUrl;
    private String notas;
    
    public Pago() {
        this.estado = "PENDIENTE";
    }
    
    public Pago(int id, int pedidoId, int usuarioId, double monto, String metodoPago, String codigoTransaccion) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.codigoTransaccion = codigoTransaccion;
        this.estado = "COMPLETADO";
        this.fechaPago = LocalDateTime.now();
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
    
    public int getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(int usuarioId) {
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