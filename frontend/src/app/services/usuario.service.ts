import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private readonly API = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  getUsuarioPorEmail(email: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.API}/email/${email}`);
  }

  getUsuarioPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.API}/${id}`);
  }

  actualizarUsuario(id: number, usuario: Partial<Usuario>): Observable<string> {
    return this.http.put(`${this.API}/${id}`, usuario, { responseType: 'text' });
  }
}
