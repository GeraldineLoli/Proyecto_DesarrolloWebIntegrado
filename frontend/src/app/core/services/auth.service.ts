import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

export interface AuthRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  email: string;
  nombre: string;
  apellido: string;
  rol: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly API = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('rol',   response.rol);
        localStorage.setItem('email', response.email);
        localStorage.setItem('nombre', response.nombre);
      })
    );
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRol(): string | null {
    return localStorage.getItem('rol');
  }

  getNombre(): string | null {
    return localStorage.getItem('nombre');
  }

  getEmail(): string | null {
    return localStorage.getItem('email');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRol() === 'ADMIN';
  }
}
