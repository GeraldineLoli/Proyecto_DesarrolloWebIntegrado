import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Evento {
  id?: number;
  nombre: string;
  descripcion: string;
  fechaHora: string;
  lugar: string;
  direccion: string;
  categoria: string;
  artistaPrincipal: string;
  duracionMinutos: number;
  edadMinima: number;
  imagenUrl?: string;
  activo: boolean;
}

@Injectable({ providedIn: 'root' })
export class EventoService {

  private readonly API = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Evento[]> {
    return this.http.get<Evento[]>(this.API);
  }

  getById(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${this.API}/${id}`);
  }

  create(evento: Evento): Observable<string> {
    return this.http.post(this.API, evento, { responseType: 'text' });
  }

  update(id: number, evento: Evento): Observable<string> {
    return this.http.put(`${this.API}/${id}`, evento, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.API}/${id}`, { responseType: 'text' });
  }

  buscarPorTexto(q: string): Observable<Evento[]> {
    const params = new HttpParams().set('q', q);
    return this.http.get<Evento[]>(`${this.API}/buscar/texto`, { params });
  }

  buscarActivosOrdenados(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.API}/buscar/activos-ordenados`);
  }
}
