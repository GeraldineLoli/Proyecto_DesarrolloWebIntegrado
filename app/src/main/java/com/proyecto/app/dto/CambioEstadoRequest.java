package com.proyecto.app.dto;

import java.util.List;

/**
 * DTO para recibir el cambio de estado de un pedido.
 * Estados válidos: PENDIENTE, APROBADO, PAGADO, CANCELADO
 */
public class CambioEstadoRequest {

    private static final List<String> ESTADOS_VALIDOS =
        List.of("PENDIENTE", "APROBADO", "PAGADO", "CANCELADO");

    private String estado;

    public CambioEstadoRequest() {}

    public CambioEstadoRequest(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Valida que el estado sea uno de los valores permitidos.
     */
    public boolean esValido() {
        return estado != null && ESTADOS_VALIDOS.contains(estado.toUpperCase());
    }

    public static List<String> getEstadosValidos() {
        return ESTADOS_VALIDOS;
    }
}
