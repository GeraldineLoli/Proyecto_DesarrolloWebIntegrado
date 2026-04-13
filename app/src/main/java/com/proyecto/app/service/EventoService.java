package com.proyecto.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.app.model.Evento;

@Service
public class EventoService {
    private List<Evento> eventos;
    private int nextId;
    
    // Constructor para producción
    public EventoService() {
        this.eventos = new ArrayList<>();
        this.nextId = 1;
        initData();
    }
    
    // Constructor para testing
    public EventoService(boolean inicializarDatos) {
        this.eventos = new ArrayList<>();
        this.nextId = 1;
        if (inicializarDatos) {
            initData();
        }
    }
    
    private void initData() {
        // Limpiar lista antes de agregar datos
        eventos.clear();
        
        // Agregar eventos con IDs explícitos
        Evento evento1 = new Evento(1, "Coldplay - Music of the Spheres", "Concierto internacional", 
                "CONCIERTO", "Estadio Nacional", "Av. Javier Prado Este", 
                LocalDateTime.of(2026, 10, 15, 20, 0), 180, "Coldplay", 8);
        
        Evento evento2 = new Evento(2, "El Rey León", "El musical más exitoso", 
                "TEATRO", "Teatro Segura", "Jr. Huancavelica 265", 
                LocalDateTime.of(2026, 5, 20, 19, 30), 150, "Elenco El Rey León", 5);
        
        Evento evento3 = new Evento(3, "Perú vs Argentina", "Eliminatorias Mundial 2026", 
                "DEPORTES", "Estadio Nacional", "Av. Javier Prado Este", 
                LocalDateTime.of(2026, 9, 5, 21, 0), 120, "Selección Peruana", 0);
        
        Evento evento4 = new Evento(4, "Bad Bunny", "World's Hottest Tour", 
                "CONCIERTO", "Estadio Monumental", "Av. Javier Prado Este", 
                LocalDateTime.of(2026, 11, 20, 21, 0), 150, "Bad Bunny", 14);
        
        eventos.add(evento1);
        eventos.add(evento2);
        eventos.add(evento3);
        eventos.add(evento4);
        
        // El próximo ID debe ser 5 porque ya tenemos IDs 1,2,3,4
        nextId = 5;
    }
    
    public List<Evento> todos() {
        return new ArrayList<>(eventos);
    }
    
    public Evento obtenerEvento(int id) {
        return eventos.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public List<Evento> obtenerPorCategoria(String categoria) {
        return eventos.stream()
                .filter(e -> e.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
    
    public List<Evento> obtenerEventosProximos() {
        LocalDateTime now = LocalDateTime.now();
        return eventos.stream()
                .filter(e -> e.getFechaHora().isAfter(now))
                .collect(Collectors.toList());
    }
    
    public void agregarEvento(Evento evento) {
        evento.setId(nextId);
        eventos.add(evento);
        nextId++;
    }
    
    public void actualizarEvento(int id, Evento eventoActualizado) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId() == id) {
                eventoActualizado.setId(id);
                eventos.set(i, eventoActualizado);
                return;
            }
        }
    }
    
    public void eliminarEvento(int id) {
        eventos.removeIf(e -> e.getId() == id);
    }
    
    // Método útil para debugging
    public void printAllEvents() {
        System.out.println("=== Eventos actuales ===");
        for (Evento e : eventos) {
            System.out.println("ID: " + e.getId() + ", Nombre: " + e.getNombre());
        }
        System.out.println("Próximo ID: " + nextId);
    }
}