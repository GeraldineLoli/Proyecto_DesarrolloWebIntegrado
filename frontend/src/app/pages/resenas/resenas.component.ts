import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventoService } from '../../services/evento.service';
import { ResenaService } from '../../services/resena.service';
import { EntradaService } from '../../services/entrada.service';
import { UsuarioService } from '../../services/usuario.service';
import { Evento } from '../../models/evento.model';
import { Resena } from '../../models/resena.model';
import { Entrada } from '../../models/entrada.model';
import { AuthResponse } from '../../models/usuario.model';

@Component({
  selector: 'app-resenas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './resenas.component.html',
  styleUrl: './resenas.component.css'
})
export class ResenasComponent implements OnInit {
  eventoId = 0;
  evento: Evento | null = null;
  resenas: Resena[] = [];
  promedio = 0;
  user: AuthResponse | null = null;
  usuarioId: number | null = null;

  // Entrada del usuario para este evento (necesaria para postear resena)
  entradaUsuario: Entrada | null = null;
  yaReseno = false;

  // Estado formulario
  mostrarFormulario = false;
  nuevaResena: Partial<Resena> = { calificacion: 5, comentario: '' };
  enviando = false;
  errorForm = '';
  successMsg = '';

  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private eventoService: EventoService,
    private resenaService: ResenaService,
    private entradaService: EntradaService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.eventoId = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarDatos();

    if (this.user) {
      this.cargarUsuarioId();
    }
  }

  cargarDatos(): void {
    this.loading = true;

    this.eventoService.getEvento(this.eventoId).subscribe({
      next: (ev) => {
        this.evento = ev;
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el evento.';
        this.loading = false;
      }
    });

    this.resenaService.getResenasPorEvento(this.eventoId).subscribe({
      next: (r) => (this.resenas = r),
      error: () => (this.resenas = [])
    });

    this.resenaService.getPromedioEvento(this.eventoId).subscribe({
      next: (p) => (this.promedio = p),
      error: () => (this.promedio = 0)
    });
  }

  cargarUsuarioId(): void {
    const email = this.user!.email;
    this.usuarioService.getUsuarioPorEmail(email).subscribe({
      next: (u) => {
        this.usuarioId = u.id ?? null;
        if (this.usuarioId) {
          this.verificarEntrada();
          this.verificarResenaExistente();
        }
      },
      error: () => (this.usuarioId = null)
    });
  }

  verificarEntrada(): void {
    this.entradaService.getEntradasPorUsuario(this.usuarioId!).subscribe({
      next: (entradas) => {
        const entrada = entradas.find(e => e.eventoId === this.eventoId && e.estado !== 'CANCELADA');
        this.entradaUsuario = entrada ?? null;
      },
      error: () => (this.entradaUsuario = null)
    });
  }

  verificarResenaExistente(): void {
    this.resenaService.getResenasPorUsuario(this.usuarioId!).subscribe({
      next: (resenas) => {
        this.yaReseno = resenas.some(r => r.eventoId === this.eventoId);
      },
      error: () => (this.yaReseno = false)
    });
  }

  toggleFormulario(): void {
    this.mostrarFormulario = !this.mostrarFormulario;
    this.errorForm = '';
    this.successMsg = '';
  }

  setEstrellas(n: number): void {
    this.nuevaResena.calificacion = n;
  }

  enviarResena(): void {
    if (!this.nuevaResena.calificacion) {
      this.errorForm = 'Debes seleccionar una calificación.';
      return;
    }
    if (!this.entradaUsuario) {
      this.errorForm = 'Necesitas haber comprado una entrada para dejar una reseña.';
      return;
    }

    this.enviando = true;
    this.errorForm = '';

    const resena: Resena = {
      eventoId: this.eventoId,
      usuarioId: this.usuarioId!,
      entradaId: this.entradaUsuario.id!,
      calificacion: this.nuevaResena.calificacion!,
      comentario: this.nuevaResena.comentario
    };

    this.resenaService.crearResena(resena).subscribe({
      next: () => {
        this.successMsg = '¡Resena publicada exitosamente!';
        this.mostrarFormulario = false;
        this.yaReseno = true;
        this.enviando = false;
        this.nuevaResena = { calificacion: 5, comentario: '' };
        // Recargar resenas y promedio
        this.resenaService.getResenasPorEvento(this.eventoId).subscribe(r => (this.resenas = r));
        this.resenaService.getPromedioEvento(this.eventoId).subscribe(p => (this.promedio = p));
      },
      error: () => {
        this.errorForm = 'No se pudo publicar la resena. Intentalo de nuevo.';
        this.enviando = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    window.location.href = '/login';
  }

  getEstrellas(n: number): string[] {
    return Array(5).fill('').map((_, i) => (i < n ? '★' : '☆'));
  }

  formatFecha(fecha: string): string {
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: 'numeric', month: 'short', year: 'numeric'
    });
  }

  promedioRedondeado(): string {
    return this.promedio.toFixed(1);
  }

  distribucion(estrella: number): number {
    if (this.resenas.length === 0) return 0;
    const count = this.resenas.filter(r => r.calificacion === estrella).length;
    return Math.round((count / this.resenas.length) * 100);
  }
}
