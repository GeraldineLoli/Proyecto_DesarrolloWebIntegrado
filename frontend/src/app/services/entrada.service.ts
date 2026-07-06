import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Entrada } from '../models/entrada.model';

@Injectable({ providedIn: 'root' })
export class EntradaService {
  private readonly API = 'http://localhost:8080/api/entradas';

  constructor(private http: HttpClient) {}

  getEntradasPorUsuario(usuarioId: number): Observable<Entrada[]> {
    return this.http.get<Entrada[]>(`${this.API}/usuario/${usuarioId}`);
  }

  obtenerPorUsuario(usuarioId: number): Observable<Entrada[]> {
    return this.http.get<Entrada[]>(`${this.API}/usuario/${usuarioId}`);
  }

  getEntradasPorEvento(eventoId: number): Observable<Entrada[]> {
    return this.http.get<Entrada[]>(`${this.API}/evento/${eventoId}`);
  }

  getEntrada(id: number): Observable<Entrada> {
    return this.http.get<Entrada>(`${this.API}/${id}`);
  }

  obtenerPorId(id: number): Observable<Entrada> {
    return this.http.get<Entrada>(`${this.API}/${id}`);
  }

  crearEntrada(entrada: Entrada): Observable<Entrada> {
    return this.http.post<Entrada>(this.API, entrada);
  }
}
