package com.proyecto.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.model.Usuario;
import com.proyecto.app.service.IUsuarioService;
import com.proyecto.app.service.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final IUsuarioService usuarioService;
    private final UsuarioServiceImpl usuarioServiceImpl;
    
    public UsuarioController(IUsuarioService usuarioService, UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioService = usuarioService;
        this.usuarioServiceImpl = usuarioServiceImpl;
    }
    
    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.obtenerPorEmail(email);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioService.actualizarUsuario(usuario);
        return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String contraseña) {
        boolean isValid = usuarioService.validarCredenciales(email, contraseña);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
    
    // ========== Endpoints con consultas JPQL ==========
    
    // GET /api/usuarios/buscar/nombre/{nombre}
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioServiceImpl.buscarPorNombre(nombre);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/apellido/{apellido}
    @GetMapping("/buscar/apellido/{apellido}")
    public ResponseEntity<List<Usuario>> buscarPorApellido(@PathVariable String apellido) {
        List<Usuario> usuarios = usuarioServiceImpl.buscarPorApellido(apellido);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/texto?q=texto
    @GetMapping("/buscar/texto")
    public ResponseEntity<List<Usuario>> buscarPorNombreOApellido(@RequestParam String q) {
        List<Usuario> usuarios = usuarioServiceImpl.buscarPorNombreOApellido(q);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/rol/{rol}
    @GetMapping("/buscar/rol/{rol}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioServiceImpl.buscarPorRol(rol);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/registrados-despues?fecha=2024-01-01
    @GetMapping("/buscar/registrados-despues")
    public ResponseEntity<List<Usuario>> buscarRegistradosDespuesDe(@RequestParam String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        List<Usuario> usuarios = usuarioServiceImpl.buscarRegistradosDespuesDe(localDate);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/dni/{dni}
    @GetMapping("/buscar/dni/{dni}")
    public ResponseEntity<Usuario> buscarPorDni(@PathVariable String dni) {
        Usuario usuario = usuarioServiceImpl.buscarPorDni(dni);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/contar-rol/{rol}
    @GetMapping("/buscar/contar-rol/{rol}")
    public ResponseEntity<Long> contarPorRol(@PathVariable String rol) {
        long count = usuarioServiceImpl.contarPorRol(rol);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    // GET /api/usuarios/buscar/dominio?dominio=gmail.com
    @GetMapping("/buscar/dominio")
    public ResponseEntity<List<Usuario>> buscarPorDominioEmail(@RequestParam String dominio) {
        List<Usuario> usuarios = usuarioServiceImpl.buscarPorDominioEmail(dominio);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
