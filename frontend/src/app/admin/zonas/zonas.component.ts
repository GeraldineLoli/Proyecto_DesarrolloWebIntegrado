import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventoService, Evento } from '../../core/services/evento.service';
import { ZonaService, Zona } from '../../core/services/zona.service';

@Component({
  selector: 'app-zonas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './zonas.component.html',
  styleUrl: './zonas.component.css'
})
export class ZonasComponent implements OnInit {

  // Eventos para el selector
  eventos: Evento[] = [];
  eventoSeleccionadoId: number | null = null;
  eventoSeleccionado: Evento | null = null;

  // Zonas del evento seleccionado
  zonas: Zona[] = [];

  // Estados UI
  cargandoEventos = true;
  cargandoZonas   = false;
  errorEventos     = false;
  errorZonas       = false;

  // Modal crear / editar
  modalAbierto = false;
  modoEdicion  = false;
  guardando    = false;

  // Modal eliminar
  modalEliminarAbierto = false;
  zonaAEliminar: Zona | null = null;
  eliminando = false;

  mensajeExito = '';

  zonaForm: Zona = this.zonaVacia();

  constructor(
    private eventoService: EventoService,
    private zonaService: ZonaService
  ) {}

  ngOnInit(): void {
    this.eventoService.getAll().subscribe({
      next: data => {
        this.eventos = data;
        this.cargandoEventos = false;
      },
      error: () => {
        this.errorEventos = true;
        this.cargandoEventos = false;
      }
    });
  }

  // ── Selección de evento ───────────────────────────────────
  onEventoChange(): void {
    if (!this.eventoSeleccionadoId) {
      this.zonas = [];
      this.eventoSeleccionado = null;
      return;
    }
    this.eventoSeleccionado = this.eventos.find(
      e => e.id === Number(this.eventoSeleccionadoId)
    ) ?? null;
    this.cargarZonas();
  }

  cargarZonas(): void {
    if (!this.eventoSeleccionadoId) return;
    this.cargandoZonas = true;
    this.errorZonas = false;
    this.zonaService.getByEvento(Number(this.eventoSeleccionadoId)).subscribe({
      next: data => {
        this.zonas = data;
        this.cargandoZonas = false;
      },
      error: () => {
        this.errorZonas = true;
        this.cargandoZonas = false;
      }
    });
  }

  // ── Modal crear / editar ──────────────────────────────────
  abrirCrear(): void {
    this.zonaForm = this.zonaVacia();
    this.zonaForm.eventoId = Number(this.eventoSeleccionadoId);
    this.modoEdicion = false;
    this.modalAbierto = true;
  }

  abrirEditar(zona: Zona): void {
    this.zonaForm = { ...zona };
    this.modoEdicion = true;
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
  }

  guardar(): void {
    this.guardando = true;
    const accion = this.modoEdicion
      ? this.zonaService.update(this.zonaForm.id!, this.zonaForm)
      : this.zonaService.create(this.zonaForm);

    accion.subscribe({
      next: () => {
        this.mensajeExito = this.modoEdicion
          ? '✅ Zona actualizada correctamente.'
          : '✅ Zona creada correctamente.';
        this.guardando = false;
        this.cerrarModal();
        this.cargarZonas();
        setTimeout(() => this.mensajeExito = '', 4000);
      },
      error: () => { this.guardando = false; }
    });
  }

  // ── Eliminar ──────────────────────────────────────────────
  confirmarEliminar(zona: Zona): void {
    this.zonaAEliminar = zona;
    this.modalEliminarAbierto = true;
  }

  cancelarEliminar(): void {
    this.zonaAEliminar = null;
    this.modalEliminarAbierto = false;
  }

  eliminar(): void {
    if (!this.zonaAEliminar?.id) return;
    this.eliminando = true;
    this.zonaService.delete(this.zonaAEliminar.id).subscribe({
      next: () => {
        this.eliminando = false;
        this.modalEliminarAbierto = false;
        this.zonaAEliminar = null;
        this.cargarZonas();
      },
      error: () => { this.eliminando = false; }
    });
  }

  // ── Helpers ───────────────────────────────────────────────
  get capacidadTotal(): number {
    return this.zonas.reduce((s, z) => s + z.capacidadTotal, 0);
  }

  get disponibleTotal(): number {
    return this.zonas.reduce((s, z) => s + z.entradasDisponibles, 0);
  }

  private zonaVacia(): Zona {
    return {
      nombre: '', capacidadTotal: 100, entradasDisponibles: 100,
      precio: 0, tieneNumeracion: false, colorMapa: '#4f6ef7', eventoId: 0
    };
  }
}
