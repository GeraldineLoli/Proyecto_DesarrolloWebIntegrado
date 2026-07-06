import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { AdminLayoutComponent } from './admin/admin-layout/admin-layout.component';

export const routes: Routes = [

  // Redirige la raíz al login
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Login (página pública — la creará el equipo)
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then(m => m.LoginComponent)
  },

  // Panel Admin — protegido por authGuard, usa AdminLayout como shell
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./admin/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'eventos',
        loadComponent: () =>
          import('./admin/eventos/eventos.component').then(m => m.EventosComponent)
      },
      {
        path: 'zonas',
        loadComponent: () =>
          import('./admin/zonas/zonas.component').then(m => m.ZonasComponent)
      },
      {
        path: 'promociones',
        loadComponent: () =>
          import('./admin/promociones/promociones.component').then(m => m.PromocionesComponent)
      },
      {
        path: 'pedidos',
        loadComponent: () =>
          import('./admin/pedidos/pedidos.component').then(m => m.PedidosComponent)
      },
    ]
  },

  // Cualquier ruta desconocida → login
  { path: '**', redirectTo: 'login' }
];

