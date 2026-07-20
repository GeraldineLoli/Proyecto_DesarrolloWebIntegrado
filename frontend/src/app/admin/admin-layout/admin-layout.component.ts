import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  route: string;
}

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent {

  sidebarOpen = true;

  navItems: NavItem[] = [
    { label: 'Dashboard',    icon: '📊', route: '/admin/dashboard'   },
    { label: 'Eventos',      icon: '🎭', route: '/admin/eventos'     },
    { label: 'Zonas',        icon: '🗺️', route: '/admin/zonas'       },
    { label: 'Promociones',  icon: '🎟️', route: '/admin/promociones' },
    { label: 'Pedidos',      icon: '📦', route: '/admin/pedidos'     },
  ];

  constructor(private authService: AuthService, private router: Router) {}

  get nombreAdmin(): string {
    return this.authService.getCurrentUser()?.nombre ?? 'Admin';
  }

  toggleSidebar(): void {
    this.sidebarOpen = !this.sidebarOpen;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
