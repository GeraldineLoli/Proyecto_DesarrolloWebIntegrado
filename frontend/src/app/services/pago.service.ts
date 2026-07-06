import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago } from '../models/pago.model';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private readonly API = 'http://localhost:8080/api/pagos';

  constructor(private http: HttpClient) {}

  obtenerTodos(): Observable<Pago[]> {
    return this.http.get<Pago[]>(this.API);
  }

  obtenerPorId(id: number): Observable<Pago> {
    return this.http.get<Pago>(`${this.API}/${id}`);
  }

  obtenerPorUsuario(usuarioId: number): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.API}/usuario/${usuarioId}`);
  }

  obtenerPorPedido(pedidoId: number): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.API}/pedido/${pedidoId}`);
  }

  procesarPago(pedidoId: number, usuarioId: number, metodoPago: string, numeroTarjeta?: string): Observable<Pago> {
    let params = new HttpParams()
      .set('pedidoId', pedidoId.toString())
      .set('usuarioId', usuarioId.toString())
      .set('metodoPago', metodoPago);
    
    if (numeroTarjeta) {
      params = params.set('numeroTarjeta', numeroTarjeta);
    }

    return this.http.post<Pago>(`${this.API}/procesar`, null, { params });
  }
}
