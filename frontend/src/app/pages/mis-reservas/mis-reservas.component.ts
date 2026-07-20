import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EntradaService } from '../../services/entrada.service';
import { EventoService } from '../../services/evento.service';
import { ResenaService } from '../../services/resena.service';
import { UsuarioService } from '../../services/usuario.service';
import { Entrada } from '../../models/entrada.model';
import { Evento } from '../../models/evento.model';
import { AuthResponse } from '../../models/usuario.model';

export interface ReservaConEvento {
  entrada: Entrada;
  evento: Evento;
  eventoYaPaso: boolean;
  yaReseno: boolean;
}

@Component({
  selector: 'app-mis-reservas',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './mis-reservas.component.html',
  styleUrl: './mis-reservas.component.css'
})
export class MisReservasComponent implements OnInit {
  user: AuthResponse | null = null;
  usuarioId: number | null = null;
  reservas: ReservaConEvento[] = [];
  loading = true;
  error = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private entradaService: EntradaService,
    private eventoService: EventoService,
    private resenaService: ResenaService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }
    this.cargarUsuarioId();
  }

  cargarUsuarioId(): void {
    this.usuarioService.getUsuarioPorEmail(this.user!.email).subscribe({
      next: (u) => {
        this.usuarioId = u.id ?? null;
        if (this.usuarioId) this.cargarReservas();
        else { this.error = 'No se pudo cargar el usuario.'; this.loading = false; }
      },
      error: () => { this.error = 'No se pudo cargar el usuario.'; this.loading = false; }
    });
  }

  cargarReservas(): void {
    this.loading = true;
    this.entradaService.getEntradasPorUsuario(this.usuarioId!).subscribe({
      next: (entradas) => {
        // Solo entradas activas (no canceladas ni reembolsadas)
        const entradasActivas = entradas.filter(
          e => e.estado === 'PAGADA' || e.estado === 'USADA'
        );

        if (entradasActivas.length === 0) {
          this.loading = false;
          return;
        }

        // IDs únicos de eventos
        const eventoIds = [...new Set(entradasActivas.map(e => e.eventoId))];
        let cargados = 0;

        // Obtener reseñas del usuario para saber cuáles ya hizo
        this.resenaService.getResenasPorUsuario(this.usuarioId!).subscribe({
          next: (resenas) => {
            const eventoIdsResenados = new Set(resenas.map(r => r.eventoId));

            eventoIds.forEach(eventoId => {
              this.eventoService.getEvento(eventoId).subscribe({
                next: (evento) => {
                  // Buscar entradas para este evento
                  const entradasDelEvento = entradasActivas.filter(e => e.eventoId === eventoId);
                  const ahora = new Date();
                  const fechaEvento = new Date(evento.fechaHora);
                  const eventoYaPaso = fechaEvento < ahora;

                  // Una reserva por evento (tomar la primera entrada)
                  entradasDelEvento.forEach(entrada => {
                    this.reservas.push({
                      entrada,
                      evento,
                      eventoYaPaso,
                      yaReseno: eventoIdsResenados.has(eventoId)
                    });
                  });

                  cargados++;
                  if (cargados === eventoIds.length) {
                    // Ordenar: primero los que ya pasaron, luego los próximos
                    this.reservas.sort((a, b) => {
                      if (a.eventoYaPaso && !b.eventoYaPaso) return -1;
                      if (!a.eventoYaPaso && b.eventoYaPaso) return 1;
                      return new Date(b.evento.fechaHora).getTime() - new Date(a.evento.fechaHora).getTime();
                    });
                    this.loading = false;
                  }
                },
                error: () => {
                  cargados++;
                  if (cargados === eventoIds.length) this.loading = false;
                }
              });
            });
          },
          error: () => {
            // Si falla obtener reseñas, continuar sin esa info
            eventoIds.forEach(eventoId => {
              this.eventoService.getEvento(eventoId).subscribe({
                next: (evento) => {
                  const entradasDelEvento = entradasActivas.filter(e => e.eventoId === eventoId);
                  const eventoYaPaso = new Date(evento.fechaHora) < new Date();
                  entradasDelEvento.forEach(entrada => {
                    this.reservas.push({ entrada, evento, eventoYaPaso, yaReseno: false });
                  });
                  cargados++;
                  if (cargados === eventoIds.length) this.loading = false;
                },
                error: () => { cargados++; if (cargados === eventoIds.length) this.loading = false; }
              });
            });
          }
        });
      },
      error: () => { this.error = 'No se pudieron cargar tus reservas.'; this.loading = false; }
    });
  }

  irAResenas(eventoId: number): void {
    this.router.navigate(['/eventos', eventoId, 'resenas']);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  formatFecha(fechaHora: string): string {
    return new Date(fechaHora).toLocaleDateString('es-PE', {
      weekday: 'short', day: 'numeric', month: 'short', year: 'numeric'
    });
  }

  formatHora(fechaHora: string): string {
    return new Date(fechaHora).toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' });
  }

  formatFechaCompra(fecha: string): string {
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: 'numeric', month: 'short', year: 'numeric'
    });
  }

  diasRestantes(fechaHora: string): number {
    const diff = new Date(fechaHora).getTime() - new Date().getTime();
    return Math.ceil(diff / (1000 * 60 * 60 * 24));
  }

  getCategoriaIcon(categoria: string): string {
    const icons: Record<string, string> = {
      concierto: '🎵', teatro: '🎭', deporte: '🏟️',
      festival: '🎉', conferencia: '🎤', otro: '🎪'
    };
    return icons[categoria.toLowerCase()] ?? '📅';
  }

  getPlaceholderImage(categoria: string): string {
    const colors: Record<string, string> = {
      concierto: '7c3aed', teatro: 'dc2626', deporte: '16a34a',
      festival: 'ea580c', conferencia: '0891b2', otro: '6b7280'
    };
    const color = colors[categoria.toLowerCase()] ?? '7c3aed';
    return `https://placehold.co/400x200/${color}/ffffff?text=${encodeURIComponent(categoria)}`;
  }
}
