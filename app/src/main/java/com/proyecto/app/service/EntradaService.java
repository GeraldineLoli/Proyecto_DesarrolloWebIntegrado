package com.proyecto.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.proyecto.app.model.Entrada;

@Service
public class EntradaService {
    private List<Entrada> entradas;
    private int nextId;
    private final ZonaService zonaService;
    private final UsuarioService usuarioService;
    private final EventoService eventoService;
    
    public EntradaService(ZonaService zonaService, UsuarioService usuarioService, EventoService eventoService) {
        this.entradas = new ArrayList<>();
        this.nextId = 4;
        this.zonaService = zonaService;
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
        initData();
    }
    
    private void initData() {
        entradas.add(new Entrada(1, 1, 1, 1, generarCodigoUnico(), 850.00, "VISA"));
        entradas.add(new Entrada(2, 1, 2, 2, generarCodigoUnico(), 450.00, "MASTERCARD"));
        entradas.add(new Entrada(3, 2, 4, 3, generarCodigoUnico(), 320.00, "YAPE"));
    }
    
    private String generarCodigoUnico() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public List<Entrada> todas() {
        return new ArrayList<>(entradas);
    }
    
    public Entrada obtenerEntrada(int id) {
        return entradas.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
    
    public List<Entrada> obtenerPorUsuario(int usuarioId) {
        return entradas.stream().filter(e -> e.getUsuarioId() == usuarioId).collect(Collectors.toList());
    }
    
    public List<Entrada> obtenerPorEvento(int eventoId) {
        return entradas.stream().filter(e -> e.getEventoId() == eventoId).collect(Collectors.toList());
    }
    
    public Entrada comprarEntrada(int usuarioId, int zonaId, String metodoPago) {
        // Validar usuario existe
        if (usuarioService.obtenerUsuario(usuarioId) == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        // Validar zona existe y tiene disponibilidad
        if (!zonaService.reservarEntrada(zonaId)) {
            throw new RuntimeException("No hay entradas disponibles en esta zona");
        }
        
        // Obtener zona para saber el precio y evento
        var zona = zonaService.obtenerZona(zonaId);
        
        // Crear entrada
        Entrada nuevaEntrada = new Entrada(0, zona.getEventoId(), zonaId, usuarioId, 
                                          generarCodigoUnico(), zona.getPrecio(), metodoPago);
        nuevaEntrada.setId(nextId++);
        entradas.add(nuevaEntrada);
        
        return nuevaEntrada;
    }
    
    public void cancelarEntrada(int id) {
        Entrada entrada = obtenerEntrada(id);
        if (entrada != null && !entrada.getEstado().equals("USADA")) {
            entrada.setEstado("CANCELADA");
            // Liberar la entrada en la zona
            zonaService.liberarEntrada(entrada.getZonaId());
        }
    }
    
    public void usarEntrada(int id) {
        Entrada entrada = obtenerEntrada(id);
        if (entrada != null && entrada.getEstado().equals("PAGADA")) {
            entrada.setEstado("USADA");
        }
    }
    
    public List<Entrada> obtenerEntradasActivas() {
        return entradas.stream()
                .filter(e -> e.getEstado().equals("PAGADA"))
                .collect(Collectors.toList());
    }
}