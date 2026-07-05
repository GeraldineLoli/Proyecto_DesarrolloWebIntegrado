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
    nombre:    '',
    apellido:  '',
    email:     '',
    dni:       '',
    telefono:  '',
    fechaNacimiento: '',
    password: '',
    rol: 'CLIENTE'
  };

  confirmPass = '';
  loading     = false;
  error       = '';
  success     = false;
  showPass    = false;
  showConfirm = false;

  // Paso del formulario (stepper)
  step = 1;

  constructor(private authService: AuthService, private router: Router) {}

  nextStep(): void {
    if (this.step === 1 && !this.isStep1Valid()) return;
    this.step++;
  }

  prevStep(): void {
    this.step--;
    this.error = '';
  }

  isStep1Valid(): boolean {
    this.error = '';
    if (!this.usuario.nombre.trim())   { this.error = 'El nombre es obligatorio.'; return false; }
    if (!this.usuario.apellido.trim())  { this.error = 'El apellido es obligatorio.'; return false; }
    if (!this.usuario.dni.trim())       { this.error = 'El DNI es obligatorio.'; return false; }
    if (!/^\d{8}$/.test(this.usuario.dni)) { this.error = 'El DNI debe tener 8 dígitos.'; return false; }
    return true;
  }

  isStep2Valid(): boolean {
    this.error = '';
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!this.usuario.email || !emailRegex.test(this.usuario.email)) {
      this.error = 'Ingresa un correo electrónico válido.'; return false;
    }
    if (!this.usuario.password || this.usuario.password.length < 6) {
      this.error = 'La contraseña debe tener al menos 6 caracteres.'; return false;
    }
    if (this.usuario.password !== this.confirmPass) {
      this.error = 'Las contraseñas no coinciden.'; return false;
    }
    return true;
  }

  onSubmit(): void {
    if (!this.isStep2Valid()) return;

    this.loading = true;
    this.error   = '';

    const payload = { ...this.usuario };
    if (!payload.telefono) delete payload.telefono;
    if (!payload.fechaNacimiento) delete payload.fechaNacimiento;

    this.authService.register(payload).subscribe({
      next: () => {
        this.loading = false;
        this.success = true;
        setTimeout(() => this.router.navigate(['/login']), 2500);
      },
      error: (err) => {
        this.loading = false;
        if (err.status === 400) {
          this.error = err.error || 'Datos inválidos. Verifica que el email y DNI no estén registrados.';
        } else {
          this.error = 'Error al conectar con el servidor. Inténtalo más tarde.';
        }
      }
    });
  }
}
