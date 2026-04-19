package com.proyecto.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private int usuarioId;
    private LocalDateTime fechaCreacion;
    private String estado;
    private double total;
    private String codigoPedido;
    private List<Integer> entradaIds;

    public Pedido() {
        this.entradaIds = new ArrayList<>();
    }

    public Pedido(int id, int usuarioId, LocalDateTime fechaCreacion, double total, String estado, String codigoPedido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaCreacion = fechaCreacion;
        this.total = total;
        this.estado = estado;
        this.codigoPedido = codigoPedido;
        this.entradaIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public List<Integer> getEntradaIds() {
        return entradaIds;
    }

    public void setEntradaIds(List<Integer> entradaIds) {
        this.entradaIds = entradaIds != null ? entradaIds : new ArrayList<>();
    }
}
