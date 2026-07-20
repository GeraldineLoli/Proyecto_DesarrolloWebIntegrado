import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UsuarioService } from '../../services/usuario.service';
import { EntradaService } from '../../services/entrada.service';
import { ResenaService } from '../../services/resena.service';
import { EventoService } from '../../services/evento.service';
import { Usuario } from '../../models/usuario.model';
import { Entrada } from '../../models/entrada.model';
import { Resena } from '../../models/resena.model';
import { Evento } from '../../models/evento.model';
import { AuthResponse } from '../../models/usuario.model';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {
  user: AuthResponse | null = null;
  usuario: Usuario | null = null;
  entradas: Entrada[] = [];
  resenas: Resena[] = [];
  eventosMap: Map<number, Evento> = new Map();

  tabActivo: 'datos' | 'entradas' | 'resenas' = 'datos';

  // Edición
  editando = false;
  usuarioEdit: Partial<Usuario> = {};
  guardando = false;
  mensajeExito = '';
  mensajeError = '';

  loading = true;
  error = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private entradaService: EntradaService,
    private resenaService: ResenaService,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }
    this.cargarUsuario();
  }

  cargarUsuario(): void {
    this.loading = true;
    this.usuarioService.getUsuarioPorEmail(this.user!.email).subscribe({
      next: (u) => {
        this.usuario = u;
        this.loading = false;
        this.cargarEntradas(u.id!);
        this.cargarResenas(u.id!);
      },
      error: () => {
        this.error = 'No se pudo cargar el perfil.';
        this.loading = false;
      }
    });
  }

  cargarEntradas(usuarioId: number): void {
    this.entradaService.getEntradasPorUsuario(usuarioId).subscribe({
      next: (e) => {
        this.entradas = e;
        // Cargar eventos relacionados para mostrar nombres
        const eventoIds = [...new Set(e.map(en => en.eventoId))];
        eventoIds.forEach(id => {
          this.eventoService.getEvento(id).subscribe({
            next: (ev) => this.eventosMap.set(id, ev),
            error: () => {}
          });
        });
      },
      error: () => (this.entradas = [])
    });
  }

  cargarResenas(usuarioId: number): void {
    this.resenaService.getResenasPorUsuario(usuarioId).subscribe({
      next: (r) => {
        this.resenas = r;
        const eventoIds = [...new Set(r.map(res => res.eventoId))];
        eventoIds.forEach(id => {
          if (!this.eventosMap.has(id)) {
            this.eventoService.getEvento(id).subscribe({
              next: (ev) => this.eventosMap.set(id, ev),
              error: () => {}
            });
          }
        });
      },
      error: () => (this.resenas = [])
    });
  }

  setTab(tab: 'datos' | 'entradas' | 'resenas'): void {
    this.tabActivo = tab;
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  iniciarEdicion(): void {
    // Solo se permite editar email y telefono
    this.usuarioEdit = {
      email: this.usuario?.email,
      telefono: this.usuario?.telefono
    };
    this.editando = true;
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  cancelarEdicion(): void {
    this.editando = false;
    this.mensajeError = '';
  }

  guardarCambios(): void {
    if (!this.usuario?.id) return;

    // Validar email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!this.usuarioEdit.email || !emailRegex.test(this.usuarioEdit.email)) {
      this.mensajeError = 'Ingresa un correo electrónico válido.';
      return;
    }

    // Validar telefono si se ingresó
    if (this.usuarioEdit.telefono && !/^[\d\s\+\-\(\)]{6,20}$/.test(this.usuarioEdit.telefono)) {
      this.mensajeError = 'El teléfono ingresado no es válido.';
      return;
    }

    this.guardando = true;
    this.mensajeError = '';

    // Solo se envían los campos editables al backend
    const payload = {
      ...this.usuario,
      email: this.usuarioEdit.email,
      telefono: this.usuarioEdit.telefono
    };

    this.usuarioService.actualizarUsuario(this.usuario.id, payload).subscribe({
      next: () => {
        this.mensajeExito = 'Perfil actualizado correctamente.';
        this.editando = false;
        this.guardando = false;
        Object.assign(this.usuario!, { email: this.usuarioEdit.email, telefono: this.usuarioEdit.telefono });
      },
      error: () => {
        this.mensajeError = 'Error al guardar los cambios. El email puede estar en uso.';
        this.guardando = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getNombreEvento(eventoId: number): string {
    return this.eventosMap.get(eventoId)?.nombre ?? `Evento #${eventoId}`;
  }

  getEstadoClass(estado: string): string {
    const map: Record<string, string> = {
      PAGADA: 'estado-pagada',
      USADA: 'estado-usada',
      CANCELADA: 'estado-cancelada',
      REEMBOLSADA: 'estado-reembolsada'
    };
    return map[estado] ?? '';
  }

  getEstrellas(n: number): string[] {
    return Array(5).fill('').map((_, i) => (i < n ? '★' : '☆'));
  }

  formatFecha(fecha: string | undefined): string {
    if (!fecha) return '—';
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: 'numeric', month: 'short', year: 'numeric'
    });
  }

  formatFechaHora(fecha: string): string {
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: 'numeric', month: 'short', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

  getIniciales(): string {
    const n = this.usuario?.nombre?.[0] ?? '';
    const a = this.usuario?.apellido?.[0] ?? '';
    return (n + a).toUpperCase();
  }
}
