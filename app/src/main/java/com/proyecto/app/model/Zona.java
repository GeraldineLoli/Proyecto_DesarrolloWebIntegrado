package com.proyecto.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "zonas")
public class Zona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long eventoId;
    
    @Column(nullable = false)
    private String nombre; // VIP, PLATEA, GENERAL, PALCO
    
    @Column(nullable = false)
    private double precio;
    
    @Column(nullable = false)
    private int capacidadTotal;
    
    @Column(nullable = false)
    private int entradasDisponibles;
    
    private String colorMapa;
    
    @Column(nullable = false)
    private boolean tieneNumeracion;
    
    public Zona() {}
    
    public Zona(Long eventoId, String nombre, double precio, int capacidadTotal, boolean tieneNumeracion) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.precio = precio;
        this.capacidadTotal = capacidadTotal;
        this.entradasDisponibles = capacidadTotal;
        this.tieneNumeracion = tieneNumeracion;
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

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public double getPrecio() { 
        return precio; 
    }

    public void setPrecio(double precio) { 
        this.precio = precio; 
    }

    public int getCapacidadTotal() { 
        return capacidadTotal; 
    }

    public void setCapacidadTotal(int capacidadTotal) { 
        this.capacidadTotal = capacidadTotal; 
    }
    
    public int getEntradasDisponibles() { 
        return entradasDisponibles; 
    }

    public void setEntradasDisponibles(int entradasDisponibles) { 
        this.entradasDisponibles = entradasDisponibles; 
    }

    public String getColorMapa() { 
        return colorMapa; 
    }
    
    public void setColorMapa(String colorMapa) { 
        this.colorMapa = colorMapa; 
    }

    public boolean isTieneNumeracion() { 
        return tieneNumeracion; 
    }

    public void setTieneNumeracion(boolean tieneNumeracion) { 
        this.tieneNumeracion = tieneNumeracion; 
    }
}
