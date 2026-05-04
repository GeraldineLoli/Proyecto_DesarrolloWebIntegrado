package com.proyecto.app.service;

import com.proyecto.app.model.Usuario;
import com.proyecto.app.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.orElse(null);
    }
    
    @Override
    public Usuario obtenerPorEmail(String email) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        return optionalUsuario.orElse(null);
    }
    
    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setContraseña(usuario.getContraseña());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setDni(usuario.getDni());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioExistente.setRol(usuario.getRol());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }
    
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    @Override
    public boolean validarCredenciales(String email, String password) {
        Usuario usuario = obtenerPorEmail(email);
        return usuario != null && usuario.getContraseña() != null && usuario.getContraseña().equals(password);
    }
}
