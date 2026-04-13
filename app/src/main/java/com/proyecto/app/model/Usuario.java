package com.proyecto.app.model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String rol;
    private LocalDate fechaRegistro;
    
    public Usuario() {

    }
    
    public Usuario(int id, String email, String nombre, String apellido, String dni) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaRegistro = LocalDate.now();
    }
    
    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getContraseña() { 
        return contraseña; 
    }
    
    public void setContraseña(String contraseña) { 
        this.contraseña = contraseña; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getApellido() { 
        return apellido; 
    }
    
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }
    
    public String getDni() { 
        return dni; 
    }
    
    public void setDni(String dni) { 
        this.dni = dni; 
    }
    
    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    
    public LocalDate getFechaNacimiento() { 
        return fechaNacimiento; 
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) { 
        this.fechaNacimiento = fechaNacimiento; 
    }
    
    public String getRol() { 
        return rol; 
    }
    
    public void setRol(String rol) { 
        this.rol = rol; 
    }
    
    public LocalDate getFechaRegistro() { 
        return fechaRegistro; 
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
}
