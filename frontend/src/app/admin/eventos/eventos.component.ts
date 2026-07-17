import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventoService, Evento } from '../../core/services/evento.service';

@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './eventos.component.html',
  styleUrl: './eventos.component.css'
})
export class EventosComponent implements OnInit {

  eventos: Evento[] = [];
  eventosFiltrados: Evento[] = [];
  textoBusqueda = '';

  // Modal
  modalAbierto = false;
  modoEdicion = false;
  guardando = false;

  // Modal eliminar
  modalEliminarAbierto = false;
  eventoAEliminar: Evento | null = null;
  eliminando = false;

  // Estados
  cargando = true;
  error = false;
  mensajeExito = '';
  errorServidor = '';

  eventoForm: Evento = this.eventoVacio();

  readonly CATEGORIAS = [
    'CONCIERTO', 'TEATRO', 'DEPORTES', 'FESTIVAL',
    'CONFERENCIA', 'STAND_UP', 'OTRO'
  ];

  constructor(private eventoService: EventoService) {}

  ngOnInit(): void {
    this.cargarEventos();
  }

  cargarEventos(): void {
    this.cargando = true;
    this.eventoService.getAll().subscribe({
      next: data => {
        this.eventos = data;
        this.aplicarFiltro();
        this.cargando = false;
      },
      error: () => {
        this.error = true;
        this.cargando = false;
      }
    });
  }

  aplicarFiltro(): void {
    const q = this.textoBusqueda.toLowerCase().trim();
    if (!q) {
      this.eventosFiltrados = [...this.eventos];
      return;
    }
    this.eventosFiltrados = this.eventos.filter(e =>
      e.nombre.toLowerCase().includes(q) ||
      e.categoria.toLowerCase().includes(q) ||
      e.lugar.toLowerCase().includes(q) ||
      (e.artistaPrincipal?.toLowerCase().includes(q) ?? false)
    );
  }

  // ── Helpers de vista ──────────────────────────────────────
  formatFecha(fecha: string): string {
    if (!fecha) return '—';
    return new Date(fecha).toLocaleString('es-PE', {
      day: '2-digit', month: 'short', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

  // ── Modal crear / editar ──────────────────────────────────
  abrirCrear(): void {
    this.eventoForm = this.eventoVacio();
    this.modoEdicion = false;
    this.errorServidor = '';
    this.modalAbierto = true;
  }

  abrirEditar(evento: Evento): void {
    this.eventoForm = { 
      ...evento,
      fechaHora: evento.fechaHora ? evento.fechaHora.substring(0, 16) : ''
    };
    this.modoEdicion = true;
    this.errorServidor = '';
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.errorServidor = '';
  }

  guardar(): void {
    this.guardando = true;
    this.errorServidor = '';
    const accion = this.modoEdicion
      ? this.eventoService.update(this.eventoForm.id!, this.eventoForm)
      : this.eventoService.create(this.eventoForm);

    accion.subscribe({
      next: () => {
        this.mensajeExito = this.modoEdicion
          ? '✅ Evento actualizado correctamente.'
          : '✅ Evento creado correctamente.';
        this.guardando = false;
        this.cerrarModal();
        this.cargarEventos();
        setTimeout(() => this.mensajeExito = '', 4000);
      },
      error: (err) => { 
        this.guardando = false; 
        if (err.error && typeof err.error === 'string') {
          this.errorServidor = err.error;
        } else {
          this.errorServidor = 'Ocurrió un error inesperado al guardar.';
        }
      }
    });
  }

  // ── Eliminar ──────────────────────────────────────────────
  confirmarEliminar(evento: Evento): void {
    this.eventoAEliminar = evento;
    this.modalEliminarAbierto = true;
  }

  cancelarEliminar(): void {
    this.eventoAEliminar = null;
    this.modalEliminarAbierto = false;
  }

  eliminar(): void {
    if (!this.eventoAEliminar?.id) return;
    this.eliminando = true;
    this.eventoService.delete(this.eventoAEliminar.id).subscribe({
      next: () => {
        this.eliminando = false;
        this.modalEliminarAbierto = false;
        this.eventoAEliminar = null;
        this.cargarEventos();
      },
      error: () => {
        this.eliminando = false;
      }
    });
  }

  // ── Helpers ───────────────────────────────────────────────

  private eventoVacio(): Evento {
    return {
      nombre: '', descripcion: '', fechaHora: '',
      lugar: '', direccion: '', categoria: 'CONCIERTO',
      artistaPrincipal: '', duracionMinutos: 90,
      edadMinima: 0, imagenUrl: '', activo: true
    };
  }
}
