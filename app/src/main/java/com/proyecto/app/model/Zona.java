package com.proyecto.app.model;

public class Zona {
    private int id;
    private int eventoId;
    private String nombre; // VIP, PLATEA, GENERAL, PALCO
    private double precio;
    private int capacidadTotal;
    private int entradasDisponibles;
    private String colorMapa;
    private boolean tieneNumeracion;
    
    public Zona() {}
    
    public Zona(int id, int eventoId, String nombre, double precio, int capacidadTotal, boolean tieneNumeracion) {
        this.id = id;
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.precio = precio;
        this.capacidadTotal = capacidadTotal;
        this.entradasDisponibles = capacidadTotal;
        this.tieneNumeracion = tieneNumeracion;
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
