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
  // Ruta comodín
  {
    path: '**',
    redirectTo: 'home'
  }
];
