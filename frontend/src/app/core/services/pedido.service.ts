import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Pedido {
  id?: number;
  codigoPedido?: string;
  estado: string;
  total: number;
  fechaCreacion?: string;
  usuarioId: number;
}

// Estados válidos — deben coincidir con el backend
export const ESTADOS_PEDIDO = ['PENDIENTE', 'APROBADO', 'PAGADO', 'CANCELADO'] as const;
export type EstadoPedido = typeof ESTADOS_PEDIDO[number];

@Injectable({ providedIn: 'root' })
export class PedidoService {

  private readonly API = 'http://localhost:8080/api/pedidos';

  constructor(private http: HttpClient) {}

  // Solo ADMIN puede ver todos los pedidos
  getAll(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(this.API);
  }

  getById(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.API}/${id}`);
  }

  getByUsuario(usuarioId: number): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.API}/usuario/${usuarioId}`);
  }

  // Solo ADMIN — devuelve el pedido actualizado directamente
  cambiarEstado(id: number, estado: EstadoPedido): Observable<Pedido> {
    // El backend espera { "estado": "APROBADO" } como CambioEstadoRequest
    return this.http.patch<Pedido>(`${this.API}/${id}/estado`, { estado });
  }
}
