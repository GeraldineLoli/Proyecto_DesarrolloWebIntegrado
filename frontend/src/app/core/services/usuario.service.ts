import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Usuario {
  id?: number;
  nombre: string;
  apellido: string;
  email: string;
  rol: string;
  dni?: string;
  fechaRegistro?: string;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {

  private readonly API = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.API);
  }

  getById(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.API}/${id}`);
  }

  contarPorRol(rol: string): Observable<number> {
    return this.http.get<number>(`${this.API}/buscar/contar-rol/${rol}`);
  }
}
