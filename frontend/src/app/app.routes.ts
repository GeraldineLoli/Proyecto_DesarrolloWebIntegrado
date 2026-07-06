import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  // Ruta raíz redirige al home
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  // Login — carga diferida
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then(m => m.LoginComponent),
    title: 'Iniciar sesión · TicketApp'
  },
  // Registro
  {
    path: 'register',
    loadComponent: () =>
      import('./pages/register/register.component').then(m => m.RegisterComponent),
    title: 'Crear cuenta · TicketApp'
  },
  // Home (público, pero con contexto de usuario si está logueado)
  {
    path: 'home',
    loadComponent: () =>
      import('./pages/home/home.component').then(m => m.HomeComponent),
    title: 'Inicio · TicketApp'
  },
  // Detalle del evento
  {
    path: 'eventos/:id',
    loadComponent: () =>
      import('./pages/evento-detalle/evento-detalle.component').then(m => m.EventoDetalleComponent),
    title: 'Evento · TicketApp'
  },
  // Reseñas del evento
  {
    path: 'eventos/:id/resenas',
    loadComponent: () =>
      import('./pages/resenas/resenas.component').then(m => m.ResenasComponent),
    title: 'Reseñas · TicketApp'
  },
  // Perfil del usuario (protegido)
  {
    path: 'perfil',
    loadComponent: () =>
      import('./pages/perfil/perfil.component').then(m => m.PerfilComponent),
    title: 'Mi perfil · TicketApp',
    canActivate: [authGuard]
  },
  // Compra de entradas (protegido)
  {
    path: 'eventos/:id/compra',
    loadComponent: () =>
      import('./pages/compra/compra.component').then(m => m.CompraComponent),
    title: 'Comprar Entradas · TicketApp',
    canActivate: [authGuard]
  },
  // Mis compras (protegido)
  {
    path: 'mis-compras',
    loadComponent: () =>
      import('./pages/mis-compras/mis-compras.component').then(m => m.MisComprasComponent),
    title: 'Mis Compras · TicketApp',
    canActivate: [authGuard]
  },
  // Ruta comodín
  {
    path: '**',
    redirectTo: 'home'
  }
];
