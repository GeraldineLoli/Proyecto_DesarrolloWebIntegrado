package com.proyecto.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.proyecto.app.controller.UsuarioController;
import com.proyecto.app.model.Usuario;
import com.proyecto.app.service.UsuarioService;

public class UsuarioTest {
    
    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    
    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(true);
        usuarioController = new UsuarioController(usuarioService);
    }
    
    // Tests para obtener usuarios
    @Test
    void whenObtenerTodos_shouldReturnAllUsers() {
        List<Usuario> result = usuarioController.obtenerTodos();
        
        assertNotNull(result);
        assertEquals(3, result.size());
    }
    
    @Test
    void whenObtenerPorIdConId1_shouldReturnJuan() {
        Usuario result = usuarioController.obtenerPorId(1);
        
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals("Perez", result.getApellido());
        assertEquals("juan@email.com", result.getEmail());
    }
    
    @Test
    void whenObtenerPorIdConId2_shouldReturnMaria() {
        Usuario result = usuarioController.obtenerPorId(2);
        
        assertNotNull(result);
        assertEquals("Maria", result.getNombre());
        assertEquals("Gomez", result.getApellido());
        assertEquals("maria@email.com", result.getEmail());
    }
    
    @Test
    void whenObtenerPorIdConId3_shouldReturnCarlos() {
        Usuario result = usuarioController.obtenerPorId(3);
        
        assertNotNull(result);
        assertEquals("Carlos", result.getNombre());
        assertEquals("Lopez", result.getApellido());
        assertEquals("carlos@email.com", result.getEmail());
    }
    
    @Test
    void whenObtenerPorIdInvalido_shouldReturnNull() {
        Usuario result = usuarioController.obtenerPorId(999);
        
        assertNull(result);
    }
    
    // Tests para obtener por email
    @Test
    void whenObtenerPorEmailExistente_shouldReturnUser() {
        Usuario result = usuarioController.obtenerPorEmail("juan@email.com");
        
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }
    
    @Test
    void whenObtenerPorEmailInexistente_shouldReturnNull() {
        Usuario result = usuarioController.obtenerPorEmail("noexiste@email.com");
        
        assertNull(result);
    }
    
    @Test
    void whenObtenerPorEmailCaseInsensitive_shouldReturnUser() {
        Usuario result = usuarioController.obtenerPorEmail("JUAN@EMAIL.COM");
        
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }
    
    // Tests para crear usuario
    @Test
    void whenCrearUsuario_shouldAddNewUser() {
        int sizeBefore = usuarioController.obtenerTodos().size();
        assertEquals(3, sizeBefore); 
        
        Usuario nuevoUsuario = new Usuario(0, "nuevo@email.com", "Nuevo", "Usuario", "99999999");
        usuarioController.crearUsuario(nuevoUsuario);
        
        int sizeAfter = usuarioController.obtenerTodos().size();
        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals(4, sizeAfter); 

        Usuario usuarioGuardado = usuarioController.obtenerPorId(4);
        assertNotNull(usuarioGuardado, "El usuario guardado no debería ser null");
        assertEquals("nuevo@email.com", usuarioGuardado.getEmail());
        assertEquals("Nuevo", usuarioGuardado.getNombre());
    }
    
    @Test
    void whenCrearMultiplesUsuarios_shouldAssignIncrementalIds() {
        Usuario user1 = new Usuario(0, "user1@email.com", "User1", "Last1", "11111111");
        Usuario user2 = new Usuario(0, "user2@email.com", "User2", "Last2", "22222222");
        
        usuarioController.crearUsuario(user1);
        usuarioController.crearUsuario(user2);
        
        Usuario result1 = usuarioController.obtenerPorId(4);
        Usuario result2 = usuarioController.obtenerPorId(5);
        
        assertEquals("user1@email.com", result1.getEmail());
        assertEquals("user2@email.com", result2.getEmail());
    }
    
    // Tests para actualizar usuario
    @Test
    void whenActualizarUsuario_shouldUpdateExistingUser() {
        Usuario usuarioActualizado = new Usuario(0, "juan_updated@email.com", "Juan Actualizado", 
                "Perez Actualizado", "12345678");
        usuarioController.actualizarUsuario(1, usuarioActualizado);
        
        Usuario resultado = usuarioController.obtenerPorId(1);
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("Perez Actualizado", resultado.getApellido());
        assertEquals("juan_updated@email.com", resultado.getEmail());
    }
    
    @Test
    void whenActualizarUsuarioInexistente_shouldDoNothing() {
        Usuario usuarioActualizado = new Usuario(0, "ghost@email.com", "Ghost", "User", "00000000");
        
        usuarioController.actualizarUsuario(999, usuarioActualizado);
        
        assertNull(usuarioController.obtenerPorId(999));
    }
    
    // Tests para eliminar usuario
    @Test
    void whenEliminarUsuario_shouldRemoveUser() {
        assertNotNull(usuarioController.obtenerPorId(1));
        
        usuarioController.eliminarUsuario(1);
        
        assertNull(usuarioController.obtenerPorId(1));
        assertEquals(2, usuarioController.obtenerTodos().size());
    }
    
    @Test
    void whenEliminarUsuarioInexistente_shouldNotChangeList() {
        int sizeBefore = usuarioController.obtenerTodos().size();
        
        usuarioController.eliminarUsuario(999);
        
        int sizeAfter = usuarioController.obtenerTodos().size();
        assertEquals(sizeBefore, sizeAfter);
    }
    
    // Tests para login
    @Test
    void whenLoginWithValidCredentials_shouldReturnTrue() {
        // Primero establecer password
        Usuario usuario = usuarioController.obtenerPorId(1);
        usuario.setContraseña("secret123");
        usuarioController.actualizarUsuario(1, usuario);
        
        boolean result = usuarioController.login("juan@email.com", "secret123");
        
        assertTrue(result);
    }
    
    @Test
    void whenLoginWithInvalidPassword_shouldReturnFalse() {
        Usuario usuario = usuarioController.obtenerPorId(1);
        usuario.setContraseña("secret123");
        usuarioController.actualizarUsuario(1, usuario);
        
        boolean result = usuarioController.login("juan@email.com", "wrongpassword");
        
        assertFalse(result);
    }
    
    @Test
    void whenLoginWithNonExistentEmail_shouldReturnFalse() {
        boolean result = usuarioController.login("noexiste@email.com", "anything");
        
        assertFalse(result);
    }
}