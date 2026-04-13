package com.proyecto.app.model;

import java.time.LocalDateTime;

public class Entrada {
    private int id;
    private int eventoId;
    private int zonaId;
    private int usuarioId;
    private String codigoEntrada;
    private int numeroAsiento;
    private String fila;
    private double precioPagado;
    private LocalDateTime fechaCompra;
    private String estado; // PAGADA, CANCELADA, USADA, REEMBOLSADA
    private String metodoPago;
    private String codigoTransaccion;
    
    public Entrada() {
        
    }
    
    public Entrada(int id, int eventoId, int zonaId, int usuarioId, String codigoEntrada, double precioPagado, String metodoPago) {
        this.id = id;
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
    
    public int getZonaId() { 
        return zonaId; 
    }
    
    public void setZonaId(int zonaId) { 
        this.zonaId = zonaId; 
    }
    
    public int getUsuarioId() { 
        return usuarioId; 
    }
    
    public void setUsuarioId(int usuarioId) { 
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
