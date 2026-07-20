package com.proyecto.app.service;

import com.proyecto.app.model.Entrada;
import com.proyecto.app.repository.EntradaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EntradaServiceImpl implements IEntradaService {

    private final EntradaRepository entradaRepository;
    private final IZonaService zonaService;

    public EntradaServiceImpl(EntradaRepository entradaRepository, IZonaService zonaService) {
        this.entradaRepository = entradaRepository;
        this.zonaService = zonaService;
    }

    @Override
    public Entrada crearEntrada(Entrada entrada) {
        if (entrada.getFechaCompra() == null) {
            entrada.setFechaCompra(LocalDateTime.now());
        }
        if (entrada.getEstado() == null || entrada.getEstado().isEmpty()) {
            entrada.setEstado("PAGADA");
        }
        if (entrada.getCodigoEntrada() == null || entrada.getCodigoEntrada().isEmpty()) {
            entrada.setCodigoEntrada(generarCodigoUnico());
        }
        
        // Actualizar las entradas disponibles en la zona
        if (!zonaService.reservarEntrada(entrada.getZonaId())) {
            throw new RuntimeException("No hay entradas disponibles en la zona seleccionada");
        }
        
        return entradaRepository.save(entrada);
    }

    @Override
    public Entrada obtenerEntradaPorId(Long id) {
        Optional<Entrada> optionalEntrada = entradaRepository.findById(id);
        return optionalEntrada.orElse(null);
    }

    @Override
    public List<Entrada> obtenerTodasLasEntradas() {
        return entradaRepository.findAll();
    }

    @Override
    public List<Entrada> obtenerEntradasPorUsuario(Long usuarioId) {
        return entradaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Entrada> obtenerEntradasPorEvento(Long eventoId) {
        return entradaRepository.findByEventoId(eventoId);
    }

    @Override
    public List<Entrada> obtenerEntradasActivas() {
        return entradaRepository.findByEstado("PAGADA");
    }

    @Override
    public Entrada actualizarEntrada(Entrada entrada) {
        Entrada entradaExistente = entradaRepository.findById(entrada.getId()).orElse(null);
        if (entradaExistente != null) {
            // Si el estado cambia de PAGADA a CANCELADA o REEMBOLSADA, liberar la entrada
            if (entradaExistente.getEstado().equals("PAGADA") && 
                (entrada.getEstado().equals("CANCELADA") || entrada.getEstado().equals("REEMBOLSADA"))) {
                zonaService.liberarEntrada(entradaExistente.getZonaId());
            }
            
            entradaExistente.setEventoId(entrada.getEventoId());
            entradaExistente.setZonaId(entrada.getZonaId());
            entradaExistente.setUsuarioId(entrada.getUsuarioId());
            entradaExistente.setNumeroAsiento(entrada.getNumeroAsiento());
            entradaExistente.setFila(entrada.getFila());
            entradaExistente.setPrecioPagado(entrada.getPrecioPagado());
            entradaExistente.setEstado(entrada.getEstado());
            entradaExistente.setMetodoPago(entrada.getMetodoPago());
            entradaExistente.setCodigoTransaccion(entrada.getCodigoTransaccion());
            return entradaRepository.save(entradaExistente);
        }
        return null;
    }

    @Override
    public void eliminarEntrada(Long id) {
        Entrada entrada = entradaRepository.findById(id).orElse(null);
        if (entrada != null) {
            // Liberar la entrada en la zona
            zonaService.liberarEntrada(entrada.getZonaId());
            entradaRepository.deleteById(id);
        }
    }

    private String generarCodigoUnico() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
