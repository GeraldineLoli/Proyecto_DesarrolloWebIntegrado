import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Zona } from '../models/zona.model';

@Injectable({ providedIn: 'root' })
export class ZonaService {
  private readonly API = 'http://localhost:8080/api/zonas';

  constructor(private http: HttpClient) {}

  getZonasPorEvento(eventoId: number): Observable<Zona[]> {
    return this.http.get<Zona[]>(`${this.API}/evento/${eventoId}`);
  }

  getZona(id: number): Observable<Zona> {
    return this.http.get<Zona>(`${this.API}/${id}`);
  }
}
