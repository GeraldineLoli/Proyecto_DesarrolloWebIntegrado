export interface Resena {
  id?: number;
  eventoId: number;
  usuarioId: number;
  entradaId: number;
  calificacion: number; // 1 a 5
  comentario?: string;
  fecha?: string;
}
