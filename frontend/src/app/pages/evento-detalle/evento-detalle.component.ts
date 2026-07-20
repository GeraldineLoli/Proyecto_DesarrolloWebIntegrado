import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventoService } from '../../services/evento.service';
import { ZonaService } from '../../services/zona.service';
import { Evento } from '../../models/evento.model';
import { Zona } from '../../models/zona.model';
import { AuthResponse } from '../../models/usuario.model';

@Component({
  selector: 'app-evento-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './evento-detalle.component.html',
  styleUrl: './evento-detalle.component.css'
})
export class EventoDetalleComponent implements OnInit, OnDestroy {
  evento: Evento | null = null;
  zonas: Zona[] = [];
  user: AuthResponse | null = null;
  loading = true;
  errorEvento = '';
  menuAbierto = false;
  private intervalId: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private eventoService: EventoService,
    private zonaService: ZonaService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarEvento(id);
    
    // Recargar zonas cada 5 segundos para mantener la información actualizada
    this.intervalId = setInterval(() => {
      if (this.evento) {
        this.cargarZonas(this.evento.id);
      }
    }, 5000);
    
    // También recargar cuando la ventana recupera el foco (volver de otra pestaña)
    window.addEventListener('focus', this.onWindowFocus);
  }

  ngOnDestroy(): void {
    // Limpiar el intervalo cuando se destruye el componente
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    window.removeEventListener('focus', this.onWindowFocus);
  }

  private onWindowFocus = (): void => {
    if (this.evento) {
      this.cargarZonas(this.evento.id);
    }
  };

  cargarEvento(id: number): void {
    this.loading = true;
    this.eventoService.getEvento(id).subscribe({
      next: (ev) => {
        this.evento = ev;
        this.loading = false;
        this.cargarZonas(id);
      },
      error: () => {
        this.errorEvento = 'No se pudo cargar el evento.';
        this.loading = false;
      }
    });
  }

  cargarZonas(eventoId: number): void {
    this.zonaService.getZonasPorEvento(eventoId).subscribe({
      next: (z) => (this.zonas = z),
      error: () => (this.zonas = [])
    });
  }

  verResenas(): void {
    this.router.navigate(['/eventos', this.evento?.id, 'resenas']);
  }

  logout(): void {
    this.authService.logout();
    window.location.href = '/login';
  }

  toggleMenu(): void {
    this.menuAbierto = !this.menuAbierto;
  }

  formatFecha(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleDateString('es-PE', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  }

  formatHora(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' });
  }

  getZonaColor(nombre: string): string {
    const mapa: Record<string, string> = {
      VIP: '#7c3aed',
      PLATEA: '#0891b2',
      GENERAL: '#16a34a',
      PALCO: '#ea580c'
    };
    return mapa[nombre.toUpperCase()] ?? '#6b7280';
  }

  getPlaceholderImage(): string {
    const cat = this.evento?.categoria ?? 'Evento';
    const colors: Record<string, string> = {
      concierto: '7c3aed',
      teatro: 'dc2626',
      deporte: '16a34a',
      festival: 'ea580c',
      conferencia: '0891b2',
      otro: '6b7280'
    };
    const color = colors[cat.toLowerCase()] ?? '7c3aed';
    return `https://placehold.co/800x400/${color}/ffffff?text=${encodeURIComponent(cat)}`;
  }

  getCategoriaIcon(categoria: string): string {
    const icons: Record<string, string> = {
      concierto: '🎵',
      teatro: '🎭',
      deporte: '🏟️',
      festival: '🎉',
      conferencia: '🎤',
      otro: '🎪'
    };
    return icons[categoria.toLowerCase()] ?? '📅';
  }
}
