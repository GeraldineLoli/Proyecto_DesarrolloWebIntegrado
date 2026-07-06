export interface Entrada {
  id: number;
  eventoId: number;
  zonaId: number;
  usuarioId: number;
  codigoEntrada: string;
  numeroAsiento?: number;
  fila?: string;
  precioPagado: number;
  fechaCompra: string;
  estado: 'PAGADA' | 'CANCELADA' | 'USADA' | 'REEMBOLSADA';
  metodoPago?: string;
  codigoTransaccion?: string;
}
