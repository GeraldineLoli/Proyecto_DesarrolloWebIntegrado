import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Zona {
  id?: number;
  nombre: string;
  capacidadTotal: number;
  entradasDisponibles: number;
  precio: number;
  tieneNumeracion: boolean;
  colorMapa?: string;
  eventoId: number;
}

@Injectable({ providedIn: 'root' })
export class ZonaService {

  private readonly API = 'http://localhost:8080/api/zonas';

  constructor(private http: HttpClient) {}

  getByEvento(eventoId: number): Observable<Zona[]> {
    return this.http.get<Zona[]>(`${this.API}/evento/${eventoId}`);
  }

  getById(id: number): Observable<Zona> {
    return this.http.get<Zona>(`${this.API}/${id}`);
  }

  create(zona: Zona): Observable<string> {
    return this.http.post(this.API, zona, { responseType: 'text' });
  }

  update(id: number, zona: Zona): Observable<string> {
    return this.http.put(`${this.API}/${id}`, zona, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.API}/${id}`, { responseType: 'text' });
  }
}
