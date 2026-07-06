import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { AuthRequest, AuthResponse, Usuario, UsuarioPayload } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = 'http://localhost:8080/api';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY  = 'auth_user';

  private userSubject = new BehaviorSubject<AuthResponse | null>(this.loadUser());
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {}

  // ── Login ──────────────────────────────────────────────────────────────────
  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/auth/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
        localStorage.setItem(this.USER_KEY, JSON.stringify(response));
        this.userSubject.next(response);
      })
    );
  }

  // ── Registro — mapea password → 'contraseña' para el backend ──────────────
  register(usuario: Usuario): Observable<string> {
    const payload: UsuarioPayload = {
      email:    usuario.email,
      nombre:   usuario.nombre,
      apellido: usuario.apellido,
      dni:      usuario.dni,
      rol:      usuario.rol ?? 'CLIENTE'
    };
    // La clave lleva ñ — usamos bracket notation para evitar error de compilación
    payload['contraseña'] = usuario.password!;
    if (usuario.telefono)        payload['telefono']        = usuario.telefono;
    if (usuario.fechaNacimiento) payload['fechaNacimiento'] = usuario.fechaNacimiento;

    return this.http.post(`${this.API}/auth/register`, payload, { responseType: 'text' });
  }

  // ── Logout ─────────────────────────────────────────────────────────────────
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.userSubject.next(null);
  }

  // ── Helpers ────────────────────────────────────────────────────────────────
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): AuthResponse | null {
    return this.userSubject.value;
  }

  isAdmin(): boolean {
    return this.getCurrentUser()?.rol === 'ADMIN';
  }

  private loadUser(): AuthResponse | null {
    try {
      const raw = localStorage.getItem(this.USER_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }
}
