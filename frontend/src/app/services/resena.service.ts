import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Resena } from '../models/resena.model';

@Injectable({ providedIn: 'root' })
export class ResenaService {
  private readonly API = 'http://localhost:8080/api/resenas';

  constructor(private http: HttpClient) {}

  getResenasPorEvento(eventoId: number): Observable<Resena[]> {
    return this.http.get<Resena[]>(`${this.API}/evento/${eventoId}`);
  }

  getResenasPorUsuario(usuarioId: number): Observable<Resena[]> {
    return this.http.get<Resena[]>(`${this.API}/usuario/${usuarioId}`);
  }

  getPromedioEvento(eventoId: number): Observable<number> {
    return this.http.get<number>(`${this.API}/evento/${eventoId}/promedio`);
  }

  crearResena(resena: Resena): Observable<string> {
    return this.http.post(`${this.API}`, resena, { responseType: 'text' });
  }

  actualizarResena(id: number, resena: Resena): Observable<string> {
    return this.http.put(`${this.API}/${id}`, resena, { responseType: 'text' });
  }

  eliminarResena(id: number): Observable<string> {
    return this.http.delete(`${this.API}/${id}`, { responseType: 'text' });
  }
}
