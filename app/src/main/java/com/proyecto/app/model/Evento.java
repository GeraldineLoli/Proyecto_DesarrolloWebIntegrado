package com.proyecto.app.model;

import java.time.LocalDateTime;
import java.util.List;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String lugar;
    private String direccion;
    private LocalDateTime fechaHora;
    private int duracionMinutos;
    private String imagenUrl;
    private String artistaPrincipal;
    private List<String> artistasInvitados;
    private int edadMinima;
    private boolean activo;
    
    public Evento() {}
    
    public Evento(int id, String nombre, String descripcion, String categoria, 
                  String lugar, String direccion, LocalDateTime fechaHora, 
                  int duracionMinutos, String artistaPrincipal, int edadMinima) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugar = lugar;
        this.direccion = direccion;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.artistaPrincipal = artistaPrincipal;
        this.edadMinima = edadMinima;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public String getArtistaPrincipal() { return artistaPrincipal; }
    public void setArtistaPrincipal(String artistaPrincipal) { this.artistaPrincipal = artistaPrincipal; }
    public List<String> getArtistasInvitados() { return artistasInvitados; }
    public void setArtistasInvitados(List<String> artistasInvitados) { this.artistasInvitados = artistasInvitados; }
    public int getEdadMinima() { return edadMinima; }
    public void setEdadMinima(int edadMinima) { this.edadMinima = edadMinima; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
