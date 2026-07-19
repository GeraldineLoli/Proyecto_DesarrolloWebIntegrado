import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { AdminLayoutComponent } from './admin/admin-layout/admin-layout.component';

export const routes: Routes = [

  // Raíz → home
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  // ── Páginas públicas ───────────────────────────────────────
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then(m => m.LoginComponent),
    title: 'Iniciar sesión · TicketApp'
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./pages/register/register.component').then(m => m.RegisterComponent),
    title: 'Crear cuenta · TicketApp'
  },
  {
    path: 'home',
    loadComponent: () =>
      import('./pages/home/home.component').then(m => m.HomeComponent),
    title: 'Inicio · TicketApp'
  },

  // ── Páginas cliente — Fabiana ──────────────────────────────
  {
    path: 'eventos/:id',
    loadComponent: () =>
      import('./pages/evento-detalle/evento-detalle.component').then(m => m.EventoDetalleComponent),
    title: 'Evento · TicketApp'
  },
  {
    path: 'eventos/:id/resenas',
    loadComponent: () =>
      import('./pages/resenas/resenas.component').then(m => m.ResenasComponent),
    title: 'Reseñas · TicketApp'
  },
  {
    path: 'perfil',
    loadComponent: () =>
      import('./pages/perfil/perfil.component').then(m => m.PerfilComponent),
    title: 'Mi perfil · TicketApp',
    canActivate: [authGuard]
  },

  // ── Páginas cliente — Geraldine ────────────────────────────
  {
    path: 'eventos/:id/compra',
    loadComponent: () =>
      import('./pages/compra/compra.component').then(m => m.CompraComponent),
    title: 'Comprar Entradas · TicketApp',
    canActivate: [authGuard]
  },
  {
    path: 'mis-compras',
    loadComponent: () =>
      import('./pages/mis-compras/mis-compras.component').then(m => m.MisComprasComponent),
    title: 'Mis Compras · TicketApp',
    canActivate: [authGuard]
  },

  // ── Panel Admin — protegido por authGuard ──────────────────
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

  // Ruta comodín → home
  { path: '**', redirectTo: 'home' }
];
