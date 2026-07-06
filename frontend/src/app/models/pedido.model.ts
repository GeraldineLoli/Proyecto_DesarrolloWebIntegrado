export interface Pedido {
  id?: number;
  usuarioId: number;
  fechaCreacion?: string;
  estado: string; // PENDIENTE, PAGADO, CANCELADO, REEMBOLSADO
  total: number;
  codigoPedido?: string;
  entradaIds?: number[];
}
