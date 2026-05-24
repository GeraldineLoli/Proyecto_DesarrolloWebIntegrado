package com.proyecto.app.service;

import com.proyecto.app.model.Usuario;
import com.proyecto.app.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        // Cifrar la contraseña antes de persistir
        if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
            usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        }
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

            // Solo cifrar si llega una contraseña nueva (no nula y no vacía)
            if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
                usuarioExistente.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
            }

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
        // Usa BCrypt matches() para comparar contraseña plana con el hash almacenado
        return usuario != null && usuario.getContraseña() != null
                && passwordEncoder.matches(password, usuario.getContraseña());
    }
    
    // ========== Métodos adicionales con JPQL ==========
    
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.buscarPorNombre(nombre);
    }
    
    public List<Usuario> buscarPorApellido(String apellido) {
        return usuarioRepository.buscarPorApellido(apellido);
    }
    
    public List<Usuario> buscarPorNombreOApellido(String texto) {
        return usuarioRepository.buscarPorNombreOApellido(texto);
    }
    
    public List<Usuario> buscarPorRol(String rol) {
        return usuarioRepository.buscarPorRol(rol);
    }
    
    public List<Usuario> buscarRegistradosDespuesDe(java.time.LocalDate fecha) {
        return usuarioRepository.buscarRegistradosDespuesDe(fecha);
    }
    
    public Usuario buscarPorDni(String dni) {
        return usuarioRepository.buscarPorDni(dni).orElse(null);
    }
    
    public long contarPorRol(String rol) {
        return usuarioRepository.contarPorRol(rol);
    }
    
    public List<Usuario> buscarPorDominioEmail(String dominio) {
        return usuarioRepository.buscarPorDominioEmail(dominio);
    }
}

