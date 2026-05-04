package com.proyecto.app.service;

import com.proyecto.app.model.Usuario;
import java.util.List;

public interface IUsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerPorEmail(String email);
    List<Usuario> obtenerTodosLosUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    boolean validarCredenciales(String email, String password);
}
