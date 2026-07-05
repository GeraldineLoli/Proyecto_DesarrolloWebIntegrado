export interface Evento {
  id: number;
  nombre: string;
  descripcion: string;
  categoria: string;
  lugar: string;
  direccion?: string;
  fechaHora: string;
  duracionMinutos?: number;
  imagenUrl?: string;
  artistaPrincipal?: string;
  artistasInvitados?: string[];
  edadMinima?: number;
  activo: boolean;
}
