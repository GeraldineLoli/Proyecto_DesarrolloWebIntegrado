import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pedido } from '../models/pedido.model';

@Injectable({ providedIn: 'root' })
export class PedidoService {
  private readonly API = 'http://localhost:8080/api/pedidos';

  constructor(private http: HttpClient) {}

  crearPedido(pedido: Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(this.API, pedido);
  }

  obtenerPorId(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.API}/${id}`);
  }

  obtenerTodos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(this.API);
  }

  obtenerPorUsuario(usuarioId: number): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.API}/usuario/${usuarioId}`);
  }

  actualizarPedido(id: number, pedido: Pedido): Observable<Pedido> {
    return this.http.put<Pedido>(`${this.API}/${id}`, pedido);
  }

  eliminarPedido(id: number): Observable<string> {
    return this.http.delete(`${this.API}/${id}`, { responseType: 'text' });
  }
}
