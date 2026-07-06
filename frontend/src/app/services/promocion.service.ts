import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Promocion } from '../models/promocion.model';

@Injectable({ providedIn: 'root' })
export class PromocionService {
  private readonly API = 'http://localhost:8080/api/promociones';

  constructor(private http: HttpClient) {}

  obtenerTodas(): Observable<Promocion[]> {
    return this.http.get<Promocion[]>(this.API);
  }

  obtenerPorId(id: number): Observable<Promocion> {
    return this.http.get<Promocion>(`${this.API}/${id}`);
  }

  obtenerPorCodigo(codigo: string): Observable<Promocion> {
    return this.http.get<Promocion>(`${this.API}/codigo/${codigo}`);
  }

  obtenerPorEvento(eventoId: number): Observable<Promocion[]> {
    return this.http.get<Promocion[]>(`${this.API}/evento/${eventoId}`);
  }

  obtenerActivas(): Observable<Promocion[]> {
    return this.http.get<Promocion[]>(`${this.API}/activas`);
  }

  usarPromocion(id: number): Observable<boolean> {
    return this.http.post<boolean>(`${this.API}/${id}/usar`, null);
  }
}
