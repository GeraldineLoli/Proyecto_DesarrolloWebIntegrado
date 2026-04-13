package com.proyecto.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.app.model.Zona;

@Service
public class ZonaService {
    private List<Zona> zonas;
    private int nextId;
    
    public ZonaService() {
        this.zonas = new ArrayList<>();
        this.nextId = 13;
        initData();
    }
    
    public ZonaService(boolean inicializarDatos) {
        this.zonas = new ArrayList<>();
        this.nextId = 1;
        if (inicializarDatos) {
            initData();
        }
    }
    
    private void initData() {
        zonas.add(new Zona(1, 1, "VIP", 850.00, 500, true));
        zonas.add(new Zona(2, 1, "PLATEA", 450.00, 2000, true));
        zonas.add(new Zona(3, 1, "GENERAL", 250.00, 5000, false));
        zonas.add(new Zona(4, 2, "VIP", 320.00, 50, true));
        zonas.add(new Zona(5, 2, "PLATEA", 180.00, 200, true));
        zonas.add(new Zona(6, 2, "GENERAL", 80.00, 300, false));
        zonas.add(new Zona(7, 3, "ORIENTE", 350.00, 8000, true));
        zonas.add(new Zona(8, 3, "OCCIDENTE", 350.00, 8000, true));
        zonas.add(new Zona(9, 3, "NORTE", 120.00, 10000, false));
        zonas.add(new Zona(10, 3, "SUR", 120.00, 10000, false));
        zonas.add(new Zona(11, 4, "PISTA VIP", 950.00, 1000, false));
        zonas.add(new Zona(12, 4, "PLATEA", 550.00, 3000, true));
    }
    
    public List<Zona> todas() {
        return new ArrayList<>(zonas);
    }
    
    public Zona obtenerZona(int id) {
        return zonas.stream().filter(z -> z.getId() == id).findFirst().orElse(null);
    }
    
    public List<Zona> obtenerZonasPorEvento(int eventoId) {
        return zonas.stream().filter(z -> z.getEventoId() == eventoId).collect(Collectors.toList());
    }
    
    public void agregarZona(Zona zona) {
        zona.setId(nextId++);
        zonas.add(zona);
    }
    
    public boolean reservarEntrada(int zonaId) {
        Zona zona = obtenerZona(zonaId);
        if (zona != null && zona.getEntradasDisponibles() > 0) {
            zona.setEntradasDisponibles(zona.getEntradasDisponibles() - 1);
            return true;
        }
        return false;
    }
    
    public void liberarEntrada(int zonaId) {
        Zona zona = obtenerZona(zonaId);
        if (zona != null && zona.getEntradasDisponibles() < zona.getCapacidadTotal()) {
            zona.setEntradasDisponibles(zona.getEntradasDisponibles() + 1);
        }
    }
    
    public void actualizarZona(int id, Zona nuevaZona) {
        for (int i = 0; i < zonas.size(); i++) {
            if (zonas.get(i).getId() == id) {
                nuevaZona.setId(id);
                zonas.set(i, nuevaZona);
                return;
            }
        }
    }
    
    public void eliminarZona(int id) {
        zonas.removeIf(z -> z.getId() == id);
    }
}