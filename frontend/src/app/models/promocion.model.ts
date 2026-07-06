export interface Promocion {
  id: number;
  codigo: string;
  descripcion: string;
  porcentajeDescuento: number;
  eventoId: number;
  fechaInicio: string;
  fechaFin: string;
  cantidadDisponible: number;
  cantidadUsada: number;
  activo: boolean;
}
