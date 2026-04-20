package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proyecto.app.controller.ResenaController;
import com.proyecto.app.model.Resena;
import com.proyecto.app.service.EntradaService;
import com.proyecto.app.service.EventoService;
import com.proyecto.app.service.ResenaService;
import com.proyecto.app.service.UsuarioService;
import com.proyecto.app.service.ZonaService;

class ResenaTest {

    private ResenaController resenaController;
    private ResenaService resenaService;
    private EntradaService entradaService;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        // Crear servicios con datos iniciales siguiendo el mismo patrón del proyecto
        EventoService eventoService = new EventoService(true);
        ZonaService zonaService = new ZonaService(true);
        usuarioService = new UsuarioService(true);
        entradaService = new EntradaService(zonaService, usuarioService, eventoService);
        resenaService = new ResenaService(entradaService, usuarioService, true);
        resenaController = new ResenaController(resenaService);
    }

    @Test
    void whenObtenerTodas_shouldReturnAllResenas() {
        List<Resena> result = resenaController.obtenerTodas();

        assertNotNull(result);
        assertEquals(2, result.size(), "Deberían haber 2 reseñas iniciales");
    }

    @Test
    void whenObtenerPorIdConId1_shouldReturnResena() {
        Resena result = resenaController.obtenerPorId(1);

        assertNotNull(result);
        assertEquals(5, result.getCalificacion());
        assertEquals(2, result.getEventoId());
    }

    @Test
    void whenObtenerPorIdInexistente_shouldReturnNull() {
        Resena result = resenaController.obtenerPorId(999);

        assertNull(result);
    }

    @Test
    void whenObtenerPorEvento_shouldReturnFilteredResenas() {
        List<Resena> result = resenaController.obtenerPorEvento(2);

        assertNotNull(result);
        assertEquals(1, result.size(), "El evento 2 debería tener 1 reseña");
        assertEquals(5, result.get(0).getCalificacion());
    }

    @Test
    void whenObtenerPorUsuario_shouldReturnFilteredResenas() {
        List<Resena> result = resenaController.obtenerPorUsuario(3);

        assertNotNull(result);
        assertEquals(1, result.size(), "El usuario 3 debería tener 1 reseña");
    }

    @Test
    void whenObtenerPromedio_shouldCalculateCorrectly() {
        // El evento 2 tiene 1 reseña con calificacion 5
        double promedio = resenaController.obtenerPromedioEvento(2);

        assertEquals(5.0, promedio, "El promedio del evento 2 debería ser 5.0");
    }

    @Test
    void whenObtenerPromedioSinResenas_shouldReturnCero() {
        // El evento 3 no tiene reseñas
        double promedio = resenaController.obtenerPromedioEvento(3);

        assertEquals(0.0, promedio, "Un evento sin reseñas debe retornar promedio 0.0");
    }

    @Test
    void whenAgregarResena_shouldAddNewResena() {
        // Primero marcar la entrada 1 como USADA para poder reseñarla
        entradaService.usarEntrada(1);

        int sizeBefore = resenaController.obtenerTodas().size();

        Resena nuevaResena = new Resena(0, 1, 1, 1, 5, "Coldplay fue un show increíble!");
        resenaController.agregarResena(nuevaResena);

        int sizeAfter = resenaController.obtenerTodas().size();
        assertEquals(sizeBefore + 1, sizeAfter, "Debería haber una reseña más");

        Resena guardada = resenaController.obtenerPorId(3);
        assertNotNull(guardada);
        assertEquals("Coldplay fue un show increíble!", guardada.getComentario());
    }

    @Test
    void whenAgregarResenaConEntradaNoUsada_shouldThrowException() {
        // La entrada 2 está en estado PAGADA, no USADA
        Resena resena = new Resena(0, 1, 2, 2, 4, "Intento de reseña inválida");

        assertThrows(RuntimeException.class, () -> {
            resenaController.agregarResena(resena);
        }, "Debería lanzar excepción si la entrada no fue USADA");
    }

    @Test
    void whenAgregarResenaConCalificacionInvalida_shouldThrowException() {
        entradaService.usarEntrada(1);

        Resena resena = new Resena(0, 1, 1, 1, 6, "Calificacion fuera de rango");

        assertThrows(RuntimeException.class, () -> {
            resenaController.agregarResena(resena);
        }, "Debería lanzar excepción si calificacion > 5");
    }

    @Test
    void whenActualizarResena_shouldUpdateExistingResena() {
        Resena actualizada = new Resena(0, 2, 3, 3, 3, "Comentario actualizado");
        resenaController.actualizarResena(1, actualizada);

        Resena resultado = resenaController.obtenerPorId(1);
        assertNotNull(resultado);
        assertEquals(3, resultado.getCalificacion());
        assertEquals("Comentario actualizado", resultado.getComentario());
    }

    @Test
    void whenEliminarResena_shouldRemoveResena() {
        assertNotNull(resenaController.obtenerPorId(1));

        resenaController.eliminarResena(1);

        assertNull(resenaController.obtenerPorId(1));
        assertEquals(1, resenaController.obtenerTodas().size());
    }
}
