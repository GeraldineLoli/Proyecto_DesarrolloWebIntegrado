import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Promocion {
  id?: number;
  codigo: string;
  descripcion?: string;
  porcentajeDescuento: number;
  fechaInicio: string;
  fechaFin: string;
  cantidadDisponible: number;
  cantidadUsada: number;
  activo: boolean;
  eventoId: number;
}

@Injectable({ providedIn: 'root' })
export class PromocionService {

  private readonly API = 'http://localhost:8080/api/promociones';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Promocion[]> {
    return this.http.get<Promocion[]>(this.API);
  }

  getById(id: number): Observable<Promocion> {
    return this.http.get<Promocion>(`${this.API}/${id}`);
  }

  getByCodigo(codigo: string): Observable<Promocion> {
    return this.http.get<Promocion>(`${this.API}/codigo/${codigo}`);
  }

  create(promocion: Promocion): Observable<string> {
    return this.http.post<string>(this.API, promocion);
  }

  update(id: number, promocion: Promocion): Observable<string> {
    return this.http.put<string>(`${this.API}/${id}`, promocion);
  }

  delete(id: number): Observable<string> {
    return this.http.delete<string>(`${this.API}/${id}`);
  }
}
