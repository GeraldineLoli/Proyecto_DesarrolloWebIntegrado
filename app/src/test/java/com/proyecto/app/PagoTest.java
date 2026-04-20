package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.proyecto.app.controller.PagoController;
import com.proyecto.app.model.Pago;
import com.proyecto.app.model.Pedido;
import com.proyecto.app.service.EntradaService;
import com.proyecto.app.service.EventoService;
import com.proyecto.app.service.PagoService;
import com.proyecto.app.service.PedidoService;
import com.proyecto.app.service.UsuarioService;
import com.proyecto.app.service.ZonaService;

public class PagoTest {
    
    private PagoController pagoController;
    private PagoService pagoService;
    private PedidoService pedidoService;
    private UsuarioService usuarioService;
    private EntradaService entradaService;
    private EventoService eventoService;
    private ZonaService zonaService;
    
    @BeforeEach
    void setUp() {
        eventoService = new EventoService(true);
        zonaService = new ZonaService(true);
        usuarioService = new UsuarioService(true);
        entradaService = new EntradaService(zonaService, usuarioService, eventoService);
        pedidoService = new PedidoService(entradaService, usuarioService);
        pagoService = new PagoService(pedidoService, usuarioService, true);
        pagoController = new PagoController(pagoService);
    }
    
    // Tests para obtener todos los pagos
    @Test
    void whenObtenerTodos_shouldReturnAllPagos() {
        List<Pago> result = pagoController.obtenerTodos();
        
        assertNotNull(result);
        assertEquals(3, result.size());
    }
    
    // Tests para obtener pago por ID
    @Test
    void whenObtenerPorIdConId1_shouldReturnPagoConMonto850() {
        Pago result = pagoController.obtenerPorId(1);
        
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(850.00, result.getMonto());
        assertEquals("VISA", result.getMetodoPago());
        assertEquals("COMPLETADO", result.getEstado());
    }
    
    @Test
    void whenObtenerPorIdConId2_shouldReturnPagoConMonto450() {
        Pago result = pagoController.obtenerPorId(2);
        
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(450.00, result.getMonto());
        assertEquals("MASTERCARD", result.getMetodoPago());
    }
    
    @Test
    void whenObtenerPorIdConId3_shouldReturnPagoConMonto320() {
        Pago result = pagoController.obtenerPorId(3);
        
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals(320.00, result.getMonto());
        assertEquals("YAPE", result.getMetodoPago());
    }
    
    @Test
    void whenObtenerPorIdInvalido_shouldReturnNull() {
        Pago result = pagoController.obtenerPorId(999);
        
        assertNull(result);
    }
    
    // Tests para obtener pagos por usuario
    @Test
    void whenObtenerPorUsuarioConId1_shouldReturnPagosDeUsuario1() {
        List<Pago> result = pagoController.obtenerPorUsuario(1);
        
        assertEquals(1, result.size());
        assertEquals(850.00, result.get(0).getMonto());
        assertEquals(1, result.get(0).getUsuarioId());
    }
    
    @Test
    void whenObtenerPorUsuarioConId2_shouldReturnPagosDeUsuario2() {
        List<Pago> result = pagoController.obtenerPorUsuario(2);
        
        assertEquals(1, result.size());
        assertEquals(450.00, result.get(0).getMonto());
    }
    
    @Test
    void whenObtenerPorUsuarioConId3_shouldReturnPagosDeUsuario3() {
        List<Pago> result = pagoController.obtenerPorUsuario(3);
        
        assertEquals(1, result.size());
        assertEquals(320.00, result.get(0).getMonto());
    }
    
    @Test
    void whenObtenerPorUsuarioInexistente_shouldReturnEmptyList() {
        List<Pago> result = pagoController.obtenerPorUsuario(999);
        
        assertEquals(0, result.size());
    }
    
    // Tests para obtener pagos por pedido
    @Test
    void whenObtenerPorPedidoConId1_shouldReturnPagoDePedido1() {
        List<Pago> result = pagoController.obtenerPorPedido(1);
        
        assertEquals(1, result.size());
        assertEquals(850.00, result.get(0).getMonto());
        assertEquals(1, result.get(0).getPedidoId());
    }
    
    @Test
    void whenObtenerPorPedidoConId2_shouldReturnPagoDePedido2() {
        List<Pago> result = pagoController.obtenerPorPedido(2);
        
        assertEquals(1, result.size());
        assertEquals(450.00, result.get(0).getMonto());
    }
    
    @Test
    void whenObtenerPorPedidoInexistente_shouldReturnEmptyList() {
        List<Pago> result = pagoController.obtenerPorPedido(999);
        
        assertEquals(0, result.size());
    }
    
    // Tests para obtener pagos por estado
    @Test
    void whenObtenerPorEstadoCompletado_shouldReturnAllCompletedPagos() {
        List<Pago> result = pagoController.obtenerPorEstado("COMPLETADO");
        
        assertEquals(3, result.size());
    }
    
    @Test
    void whenObtenerPorEstadoPendiente_shouldReturnEmptyList() {
        List<Pago> result = pagoController.obtenerPorEstado("PENDIENTE");
        
        assertEquals(0, result.size());
    }
    
    @Test
    void whenObtenerPorEstadoFallido_shouldReturnEmptyList() {
        List<Pago> result = pagoController.obtenerPorEstado("FALLIDO");
        
        assertEquals(0, result.size());
    }
    
    // Tests para obtener total recaudado por usuario
    @Test
    void whenObtenerTotalRecaudadoConUsuario1_shouldReturn850() {
        double total = pagoController.obtenerTotalRecaudado(1);
        
        assertEquals(850.00, total);
    }
    
    @Test
    void whenObtenerTotalRecaudadoConUsuario2_shouldReturn450() {
        double total = pagoController.obtenerTotalRecaudado(2);
        
        assertEquals(450.00, total);
    }
    
    @Test
    void whenObtenerTotalRecaudadoConUsuario3_shouldReturn320() {
        double total = pagoController.obtenerTotalRecaudado(3);
        
        assertEquals(320.00, total);
    }
    
    @Test
    void whenObtenerTotalRecaudadoConUsuarioInexistente_shouldReturn0() {
        double total = pagoController.obtenerTotalRecaudado(999);
        
        assertEquals(0.0, total);
    }
    
    // Tests para procesar pago
    @Test
    void whenProcesarPago_shouldCreateNewPagoAndUpdatePedido() {
        // Crear un pedido pendiente
        var entrada = entradaService.comprarEntrada(1, 1, "VISA");
        
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuarioId(1);
        nuevoPedido.setEstado("PENDIENTE");
        nuevoPedido.getEntradaIds().add(entrada.getId());
        nuevoPedido.setTotal(entrada.getPrecioPagado());
        pedidoService.agregarPedido(nuevoPedido);
        
        int pedidoId = nuevoPedido.getId();
        int sizeBefore = pagoController.obtenerTodos().size();
        
        // Procesar pago
        Pago pago = pagoController.procesarPago(pedidoId, 1, "VISA", "4532015112893623");
        
        assertNotNull(pago);
        assertEquals(pedidoId, pago.getPedidoId());
        assertEquals(1, pago.getUsuarioId());
        assertEquals(entrada.getPrecioPagado(), pago.getMonto());
        assertEquals("VISA", pago.getMetodoPago());
        assertEquals("COMPLETADO", pago.getEstado());
        assertNotNull(pago.getCodigoTransaccion());
        assertTrue(pago.getCodigoTransaccion().startsWith("TXN-"));
        
        // Verificar que se agregó el pago
        assertEquals(sizeBefore + 1, pagoController.obtenerTodos().size());
        
        // Verificar que el pedido se actualizó a PAGADO
        Pedido pedidoActualizado = pedidoService.obtenerPedido(pedidoId);
        assertEquals("PAGADO", pedidoActualizado.getEstado());
    }
    
    @Test
    void whenProcesarPagoConUsuarioInexistente_shouldThrowException() {
        try {
            pagoController.procesarPago(1, 999, "VISA", "4532015112893623");
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Usuario no encontrado", e.getMessage());
        }
    }
    
    @Test
    void whenProcesarPagoConPedidoInexistente_shouldThrowException() {
        try {
            pagoController.procesarPago(999, 1, "VISA", "4532015112893623");
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Pedido no encontrado", e.getMessage());
        }
    }
    
    @Test
    void whenProcesarPagoDePedidoQueNoPerteneceAlUsuario_shouldThrowException() {
        // Pedido 1 pertenece al usuario 1, intentar pagar con usuario 2
        try {
            pagoController.procesarPago(1, 2, "VISA", "4532015112893623");
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("El pedido no pertenece al usuario", e.getMessage());
        }
    }
    
    @Test
    void whenProcesarPagoDePedidoYaPagado_shouldThrowException() {
        // Pedido 1 ya está pagado
        try {
            pagoController.procesarPago(1, 1, "VISA", "4532015112893623");
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("El pedido ya ha sido pagado", e.getMessage());
        }
    }
    
    @Test
    void whenProcesarPagoSinMetodoPago_shouldThrowException() {
        // Crear pedido pendiente primero
        var entrada = entradaService.comprarEntrada(1, 1, "VISA");
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuarioId(1);
        nuevoPedido.setEstado("PENDIENTE");
        nuevoPedido.getEntradaIds().add(entrada.getId());
        nuevoPedido.setTotal(entrada.getPrecioPagado());
        pedidoService.agregarPedido(nuevoPedido);
        
        try {
            pagoController.procesarPago(nuevoPedido.getId(), 1, "", "4532015112893623");
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Método de pago no especificado", e.getMessage());
        }
    }
    
    // Tests para reembolsar pago
    @Test
    void whenReembolsarPago_shouldUpdateEstadoToReembolsado() {
        // Crear pedido y pago primero
        var entrada = entradaService.comprarEntrada(1, 1, "VISA");
        
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuarioId(1);
        nuevoPedido.setEstado("PENDIENTE");
        nuevoPedido.getEntradaIds().add(entrada.getId());
        nuevoPedido.setTotal(entrada.getPrecioPagado());
        pedidoService.agregarPedido(nuevoPedido);
        
        Pago pago = pagoController.procesarPago(nuevoPedido.getId(), 1, "VISA", "4532015112893623");
        assertEquals("COMPLETADO", pago.getEstado());
        
        // Reembolsar
        Pago pagoReembolsado = pagoController.reembolsarPago(pago.getId());
        
        assertEquals("REEMBOLSADO", pagoReembolsado.getEstado());
        
        // Verificar que el pedido también se actualizó
        Pedido pedidoActualizado = pedidoService.obtenerPedido(nuevoPedido.getId());
        assertEquals("REEMBOLSADO", pedidoActualizado.getEstado());
    }
    
    @Test
    void whenReembolsarPagoInexistente_shouldThrowException() {
        try {
            pagoController.reembolsarPago(999);
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Pago no encontrado", e.getMessage());
        }
    }
    
    @Test
    void whenReembolsarPagoFallido_shouldThrowException() {
        // Crear pago fallido manualmente
        Pago pagoFallido = new Pago();
        pagoFallido.setPedidoId(1);
        pagoFallido.setUsuarioId(1);
        pagoFallido.setMonto(100.0);
        pagoFallido.setMetodoPago("VISA");
        pagoFallido.setEstado("FALLIDO");
        pagoService.agregarPago(pagoFallido);
        
        try {
            pagoController.reembolsarPago(pagoFallido.getId());
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Solo se pueden reembolsar pagos completados", e.getMessage());
        }
    }
    
    // Tests para crear pago manualmente
    @Test
    void whenCrearPago_shouldAddNewPago() {
        int sizeBefore = pagoController.obtenerTodos().size();
        
        Pago nuevoPago = new Pago();
        nuevoPago.setPedidoId(1);
        nuevoPago.setUsuarioId(1);
        nuevoPago.setMonto(850.00);
        nuevoPago.setMetodoPago("PLIN");
        
        pagoController.crearPago(nuevoPago);
        
        int sizeAfter = pagoController.obtenerTodos().size();
        assertEquals(sizeBefore + 1, sizeAfter);
        
        // El nuevo pago tendrá ID 4 (porque ya existían 3 pagos)
        Pago pagoGuardado = pagoController.obtenerPorId(4);
        assertNotNull(pagoGuardado);
        assertEquals("PLIN", pagoGuardado.getMetodoPago());
        assertEquals(850.00, pagoGuardado.getMonto());
        assertNotNull(pagoGuardado.getCodigoTransaccion());
        assertTrue(pagoGuardado.getCodigoTransaccion().startsWith("TXN-"));
    }
    
    @Test
    void whenCrearPagoConUsuarioInexistente_shouldThrowException() {
        Pago nuevoPago = new Pago();
        nuevoPago.setPedidoId(1);
        nuevoPago.setUsuarioId(999);
        nuevoPago.setMonto(100.0);
        nuevoPago.setMetodoPago("VISA");
        
        try {
            pagoController.crearPago(nuevoPago);
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Usuario no encontrado", e.getMessage());
        }
    }
    
    @Test
    void whenCrearPagoConPedidoInexistente_shouldThrowException() {
        Pago nuevoPago = new Pago();
        nuevoPago.setPedidoId(999);
        nuevoPago.setUsuarioId(1);
        nuevoPago.setMonto(100.0);
        nuevoPago.setMetodoPago("VISA");
        
        try {
            pagoController.crearPago(nuevoPago);
            assertFalse(true, "Debería haber lanzado excepción");
        } catch (RuntimeException e) {
            assertEquals("Pedido no encontrado", e.getMessage());
        }
    }
    
    // Tests para actualizar pago
    @Test
    void whenActualizarPago_shouldUpdateExistingPago() {
        Pago pagoActualizado = new Pago();
        pagoActualizado.setPedidoId(1);
        pagoActualizado.setUsuarioId(1);
        pagoActualizado.setMonto(900.00);
        pagoActualizado.setMetodoPago("MASTERCARD");
        pagoActualizado.setEstado("COMPLETADO");
        pagoActualizado.setNotas("Pago actualizado con nuevo método");
        
        pagoController.actualizarPago(1, pagoActualizado);
        
        Pago resultado = pagoController.obtenerPorId(1);
        assertEquals(900.00, resultado.getMonto());
        assertEquals("MASTERCARD", resultado.getMetodoPago());
        assertEquals("Pago actualizado con nuevo método", resultado.getNotas());
        assertEquals(1, resultado.getId());
    }
    
    // Tests para eliminar pago
    @Test
    void whenEliminarPago_shouldRemovePago() {
        assertNotNull(pagoController.obtenerPorId(1));
        
        pagoController.eliminarPago(1);
        
        assertNull(pagoController.obtenerPorId(1));
        assertEquals(2, pagoController.obtenerTodos().size());
    }
    
    @Test
    void whenEliminarPagoInexistente_shouldNotChangeList() {
        int sizeBefore = pagoController.obtenerTodos().size();
        
        pagoController.eliminarPago(999);
        
        int sizeAfter = pagoController.obtenerTodos().size();
        assertEquals(sizeBefore, sizeAfter);
    }
    
    // Tests para máscara de tarjeta
    @Test
    void whenProcesarPagoConTarjeta_shouldMaskCardNumber() {
        // Crear pedido pendiente
        var entrada = entradaService.comprarEntrada(1, 1, "VISA");
        
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuarioId(1);
        nuevoPedido.setEstado("PENDIENTE");
        nuevoPedido.getEntradaIds().add(entrada.getId());
        nuevoPedido.setTotal(entrada.getPrecioPagado());
        pedidoService.agregarPedido(nuevoPedido);
        
        // Procesar pago con tarjeta
        Pago pago = pagoController.procesarPago(nuevoPedido.getId(), 1, "VISA", "4532015112893623");
        
        assertNotNull(pago.getNumeroTarjeta());
        assertEquals("****3623", pago.getNumeroTarjeta());
    }
    
    @Test
    void whenProcesarPagoConYape_shouldNotMaskCardNumber() {
        // Crear pedido pendiente
        var entrada = entradaService.comprarEntrada(1, 1, "VISA");
        
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuarioId(1);
        nuevoPedido.setEstado("PENDIENTE");
        nuevoPedido.getEntradaIds().add(entrada.getId());
        nuevoPedido.setTotal(entrada.getPrecioPagado());
        pedidoService.agregarPedido(nuevoPedido);
        
        // Procesar pago con YAPE (sin tarjeta)
        Pago pago = pagoController.procesarPago(nuevoPedido.getId(), 1, "YAPE", null);
        
        assertNull(pago.getNumeroTarjeta());
    }
}