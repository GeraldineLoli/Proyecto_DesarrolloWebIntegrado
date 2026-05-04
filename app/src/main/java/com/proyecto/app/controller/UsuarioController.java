package com.proyecto.app.controller;

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

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final IUsuarioService usuarioService;
    
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
}
