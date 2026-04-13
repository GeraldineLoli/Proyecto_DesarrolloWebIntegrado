package com.proyecto.app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.proyecto.app.model.Usuario;

@Service
public class UsuarioService {
    private List<Usuario> usuarios;
    private int nextId;
    
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.nextId = 4;
        initData();
    }
    
    public UsuarioService(boolean inicializarDatos) {
        this.usuarios = new ArrayList<>();
        this.nextId = 1;
        if (inicializarDatos) {
            initData();
        }
    }
    
    private void initData() {
        usuarios.add(new Usuario(1, "juan@email.com", "Juan", "Perez", "12345678"));
        usuarios.add(new Usuario(2, "maria@email.com", "Maria", "Gomez", "87654321"));
        usuarios.add(new Usuario(3, "carlos@email.com", "Carlos", "Lopez", "45678912"));
    }
    
    public List<Usuario> todos() {
        return new ArrayList<>(usuarios);
    }
    
    public Usuario obtenerUsuario(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }
    
    public Usuario obtenerPorEmail(String email) {
        return usuarios.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }
    
    public void agregarUsuario(Usuario usuario) {
        usuario.setId(nextId++);
        usuarios.add(usuario);
    }
    
    public void actualizarUsuario(int id, Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                usuarioActualizado.setId(id);
                usuarios.set(i, usuarioActualizado);
                return;
            }
        }
    }
    
    public void eliminarUsuario(int id) {
        usuarios.removeIf(u -> u.getId() == id);
    }
    
    public boolean validarCredenciales(String email, String contraseña) {
        Usuario usuario = obtenerPorEmail(email);
        return usuario != null && usuario.getContraseña() != null && usuario.getContraseña().equals(contraseña);
    }
}