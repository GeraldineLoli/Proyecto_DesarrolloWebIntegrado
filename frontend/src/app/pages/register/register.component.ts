import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Usuario } from '../../models/usuario.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  usuario: Usuario = {
    nombre: '',
    apellido: '',
    email: '',
    dni: '',
    telefono: '',
    fechaNacimiento: '',
    password: '',
    rol: 'CLIENTE'
  };

  confirmPass = '';
  loading = false;
  error = '';
  success = false;
  showPass = false;
  showConfirm = false;
  step = 1;
  fechaMaxima: string;

  constructor(private authService: AuthService, private router: Router) {
    // Calcular fecha máxima (hace 13 años)
    const hoy = new Date();
    hoy.setFullYear(hoy.getFullYear() - 13);
    this.fechaMaxima = hoy.toISOString().split('T')[0];
  }

  nextStep(): void {
    if (!this.isStep1Valid()) return;
    this.error = '';
    this.step = 2;
  }

  prevStep(): void {
    this.step = 1;
    this.error = '';
    // Limpiar paso 2 para que no queden datos viejos si vuelven
    this.usuario.email = '';
    this.usuario.password = '';
    this.confirmPass = '';
  }

  isStep1Valid(): boolean {
    this.error = '';

    if (!this.usuario.nombre.trim()) {
      this.error = 'El nombre es obligatorio.'; return false;
    }
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(this.usuario.nombre.trim())) {
      this.error = 'El nombre solo puede contener letras.'; return false;
    }
    if (!this.usuario.apellido.trim()) {
      this.error = 'El apellido es obligatorio.'; return false;
    }
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(this.usuario.apellido.trim())) {
      this.error = 'El apellido solo puede contener letras.'; return false;
    }
    if (!this.usuario.dni.trim()) {
      this.error = 'El DNI es obligatorio.'; return false;
    }
    if (!/^\d{8}$/.test(this.usuario.dni)) {
      this.error = 'El DNI debe tener exactamente 8 dígitos numéricos.'; return false;
    }
    if (this.usuario.telefono && !/^[\d\s\+\-\(\)]{6,20}$/.test(this.usuario.telefono)) {
      this.error = 'El teléfono ingresado no es válido.'; return false;
    }
    if (!this.usuario.fechaNacimiento) {
      this.error = 'La fecha de nacimiento es obligatoria.'; return false;
    }
    const nacimiento = new Date(this.usuario.fechaNacimiento);
    const hoy = new Date();
    if (nacimiento >= hoy) {
      this.error = 'La fecha de nacimiento no puede ser hoy ni en el futuro.'; return false;
    }
    // Cálculo de edad preciso
    let edad = hoy.getFullYear() - nacimiento.getFullYear();
    const mDiff = hoy.getMonth() - nacimiento.getMonth();
    if (mDiff < 0 || (mDiff === 0 && hoy.getDate() < nacimiento.getDate())) {
      edad--;
    }
    if (edad < 13) {
      this.error = 'Debes tener al menos 13 años para registrarte.'; return false;
    }
    return true;
  }

  isStep2Valid(): boolean {
    this.error = '';
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!this.usuario.email || !emailRegex.test(this.usuario.email)) {
      this.error = 'Ingresa un correo electrónico válido.'; return false;
    }
    if (!this.usuario.password) {
      this.error = 'La contraseña es obligatoria.'; return false;
    }
    if (this.usuario.password.length < 8) {
      this.error = 'La contraseña debe tener al menos 8 caracteres.'; return false;
    }
    if (!/[A-Z]/.test(this.usuario.password)) {
      this.error = 'La contraseña debe contener al menos una letra mayúscula.'; return false;
    }
    if (!/[0-9]/.test(this.usuario.password)) {
      this.error = 'La contraseña debe contener al menos un número.'; return false;
    }
    if (this.usuario.password !== this.confirmPass) {
      this.error = 'Las contraseñas no coinciden.'; return false;
    }
    return true;
  }

  onSubmit(): void {
    if (!this.isStep2Valid()) return;

    this.loading = true;
    this.error = '';

    const payload = { ...this.usuario };
    if (!payload.telefono) delete payload.telefono;

    this.authService.register(payload).subscribe({
      next: () => {
        this.loading = false;
        this.success = true;
        setTimeout(() => this.router.navigate(['/login']), 2500);
      },
      error: (err) => {
        this.loading = false;
        if (err.status === 400) {
          const msg: string = (err.error ?? '').toString().toLowerCase();
          if (msg.includes('dni')) {
            // DNI duplicado: volver al paso 1 para que lo corrija
            this.error = 'El DNI ingresado ya está registrado. Corregilo en el paso anterior.';
            this.step = 1;
          } else if (msg.includes('email')) {
            this.error = 'El correo electrónico ya está registrado.';
          } else {
            this.error = 'Datos inválidos. Verificá que el email y DNI no estén ya registrados.';
          }
        } else {
          this.error = 'Error al conectar con el servidor. Intentalo más tarde.';
        }
      }
    });
  }
}
