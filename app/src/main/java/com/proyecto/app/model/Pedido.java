package com.proyecto.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private String estado;

    private double total;

    @Column(unique = true)
    private String codigoPedido;

    @ElementCollection
    @CollectionTable(name = "pedido_entradas", joinColumns = @JoinColumn(name = "pedido_id"))
    @Column(name = "entrada_id")
    private List<Long> entradaIds;

    public Pedido() {
        this.entradaIds = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
    }

    public Pedido(Long usuarioId, double total, String estado, String codigoPedido) {
        this.usuarioId = usuarioId;
        this.total = total;
        this.estado = estado;
        this.codigoPedido = codigoPedido;
        this.entradaIds = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
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

    public List<Long> getEntradaIds() {
        return entradaIds;
    }

    public void setEntradaIds(List<Long> entradaIds) {
        this.entradaIds = entradaIds != null ? entradaIds : new ArrayList<>();
    }
}
