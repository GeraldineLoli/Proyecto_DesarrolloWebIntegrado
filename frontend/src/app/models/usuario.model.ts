export interface Usuario {
  id?: number;
  email: string;
  // Se usa 'password' en el frontend; el servicio lo mapea a 'contraseña' al enviar
  password?: string;
  nombre: string;
  apellido: string;
  dni: string;
  telefono?: string;
  fechaNacimiento?: string;
  rol?: string;
  fechaRegistro?: string;
}

// Payload que se envía al backend — usa bracket notation para la clave 'contraseña'
export interface UsuarioPayload {
  email: string;
  nombre: string;
  apellido: string;
  dni: string;
  telefono?: string;
  fechaNacimiento?: string;
  rol?: string;
  [key: string]: string | undefined; // permite asignar payload['contraseña']
}

export interface AuthRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  id?: number;
  token: string;
  email: string;
  nombre: string;
  apellido: string;
  rol: string;
}
