import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Evento } from '../models/evento.model';

@Injectable({ providedIn: 'root' })
export class EventoService {
  private readonly API = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) {}

  getEventos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(this.API);
  }

  getEventosProximos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.API}/proximos`);
  }

  getEventosPorCategoria(categoria: string): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.API}/categoria/${categoria}`);
  }

  getEvento(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${this.API}/${id}`);
  }
}
