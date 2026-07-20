import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { EventoService } from '../../services/evento.service';
import { Evento } from '../../models/evento.model';
import { AuthResponse } from '../../models/usuario.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  user: AuthResponse | null = null;
  eventos: Evento[] = [];
  eventosFiltrados: Evento[] = [];
  loading = true;
  error = '';
  busqueda = '';
  categoriaActiva = 'Todos';
  menuAbierto = false;

  readonly categorias = ['Todos', 'Concierto', 'Teatro', 'Deporte', 'Festival', 'Conferencia', 'Otro'];

  constructor(
    private authService: AuthService,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.cargarEventos();
  }

  cargarEventos(): void {
    this.loading = true;
    this.error = '';

    this.eventoService.getEventos().subscribe({
      next: (data) => {
        const ahora = new Date();
        // Filtrar: solo activos Y con fecha futura
        this.eventos = data.filter(e => e.activo && new Date(e.fechaHora) > ahora);
        this.eventosFiltrados = [...this.eventos];
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los eventos. Verifica que el servidor esté en funcionamiento.';
        this.loading = false;
      }
    });
  }

  filtrarPorCategoria(categoria: string): void {
    this.categoriaActiva = categoria;
    this.aplicarFiltros();
  }

  aplicarFiltros(): void {
    let resultado = [...this.eventos];

    if (this.categoriaActiva !== 'Todos') {
      resultado = resultado.filter(e =>
        e.categoria.toLowerCase() === this.categoriaActiva.toLowerCase()
      );
    }

    if (this.busqueda.trim()) {
      const q = this.busqueda.toLowerCase();
      resultado = resultado.filter(e =>
        e.nombre.toLowerCase().includes(q) ||
        e.lugar.toLowerCase().includes(q) ||
        (e.artistaPrincipal?.toLowerCase().includes(q))
      );
    }

    this.eventosFiltrados = resultado;
  }

  onBusqueda(): void {
    this.aplicarFiltros();
  }

  limpiarBusqueda(): void {
    this.busqueda = '';
    this.aplicarFiltros();
  }

  logout(): void {
    this.authService.logout();
    // La navegación al login la maneja el guard implícitamente
    window.location.href = '/login';
  }

  scrollToEventos(): void {
    const el = document.getElementById('eventos');
    if (el) el.scrollIntoView({ behavior: 'smooth' });
  }

  toggleMenu(): void {
    this.menuAbierto = !this.menuAbierto;
  }

  formatFecha(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleDateString('es-PE', {
      weekday: 'short',
      day: 'numeric',
      month: 'short',
      year: 'numeric'
    });
  }

  formatHora(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' });
  }

  getCategoriaIcon(categoria: string): string {
    const icons: Record<string, string> = {
      'concierto':    '🎵',
      'teatro':       '🎭',
      'deporte':      '🏟️',
      'festival':     '🎉',
      'conferencia':  '🎤',
      'otro':         '🎪'
    };
    return icons[categoria.toLowerCase()] || '📅';
  }

  getPlaceholderImage(categoria: string): string {
    const colors: Record<string, string> = {
      'concierto':   '7c3aed',
      'teatro':      'dc2626',
      'deporte':     '16a34a',
      'festival':    'ea580c',
      'conferencia': '0891b2',
      'otro':        '6b7280'
    };
    const color = colors[categoria.toLowerCase()] || '7c3aed';
    return `https://placehold.co/400x220/${color}/ffffff?text=${encodeURIComponent(categoria)}`;
  }
}
