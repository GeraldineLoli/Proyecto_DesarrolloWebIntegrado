export interface Zona {
  id: number;
  eventoId: number;
  nombre: string; // VIP, PLATEA, GENERAL, PALCO
  precio: number;
  capacidadTotal: number;
  entradasDisponibles: number;
  colorMapa?: string;
  tieneNumeracion: boolean;
}
