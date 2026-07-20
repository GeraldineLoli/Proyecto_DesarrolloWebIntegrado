import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Zona } from '../models/zona.model';

@Injectable({ providedIn: 'root' })
export class ZonaService {
  private readonly API = 'http://localhost:8080/api/zonas';

  constructor(private http: HttpClient) {}

  getZonasPorEvento(eventoId: number): Observable<Zona[]> {
    // Agregar headers para evitar caché del navegador
    const headers = new HttpHeaders({
      'Cache-Control': 'no-cache, no-store, must-revalidate',
      'Pragma': 'no-cache',
      'Expires': '0'
    });
    return this.http.get<Zona[]>(`${this.API}/evento/${eventoId}`, { headers });
  }

  getZona(id: number): Observable<Zona> {
    return this.http.get<Zona>(`${this.API}/${id}`);
  }
}
