package com.proyecto.app.controller;

import java.util.List;

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
import com.proyecto.app.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.todos();
    }
    
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable int id) {
        return usuarioService.obtenerUsuario(id);
    }
    
    @GetMapping("/email/{email}")
    public Usuario obtenerPorEmail(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email);
    }
    
    @PostMapping
    public void crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.agregarUsuario(usuario);
    }
    
    @PutMapping("/{id}")
    public void actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(id, usuario);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
    }
    
    @PostMapping("/login")
    public boolean login(@RequestParam String email, @RequestParam String contraseña) {
        return usuarioService.validarCredenciales(email, contraseña);
    }
}