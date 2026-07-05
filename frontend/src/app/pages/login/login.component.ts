import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email     = '';
  password  = '';
  loading   = false;
  error     = '';
  showPass  = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (!this.email || !this.password) {
      this.error = 'Por favor, completa todos los campos.';
      return;
    }

    this.loading = true;
    this.error   = '';

    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.loading = false;
        if (err.status === 401) {
          this.error = 'Email o contraseña incorrectos.';
        } else {
          this.error = 'Error al conectar con el servidor. Inténtalo más tarde.';
        }
      }
    });
  }

  togglePassword(): void {
    this.showPass = !this.showPass;
  }
}
