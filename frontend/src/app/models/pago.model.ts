export interface Pago {
  id?: number;
  pedidoId: number;
  usuarioId: number;
  monto: number;
  metodoPago: string; // VISA, MASTERCARD, YAPE, PLIN, EFECTIVO
  estado: string; // PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO
  codigoTransaccion?: string;
  numeroTarjeta?: string;
  fechaPago?: string;
  comprobanteUrl?: string;
  notas?: string;
}
