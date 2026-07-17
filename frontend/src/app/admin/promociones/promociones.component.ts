import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventoService, Evento } from '../../core/services/evento.service';
import { PromocionService, Promocion } from '../../core/services/promocion.service';

@Component({
  selector: 'app-promociones',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './promociones.component.html',
  styleUrl: './promociones.component.css'
})
export class PromocionesComponent implements OnInit {

  promociones: Promocion[] = [];
  promocionesFiltradas: Promocion[] = [];
  eventos: Evento[] = [];
  textoBusqueda = '';

  // Estados UI
  cargando = true;
  error    = false;
  mensajeExito = '';

  // Modal crear / editar
  modalAbierto = false;
  modoEdicion  = false;
  guardando    = false;

  // Modal eliminar
  modalEliminarAbierto = false;
  promocionAEliminar: Promocion | null = null;
  eliminando = false;

  promoForm: Promocion = this.promoVacia();

  constructor(
    private promocionService: PromocionService,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.cargando = true;

    // Cargo promociones y eventos en paralelo
    this.eventoService.getAll().subscribe({
      next: eventos => { this.eventos = eventos; }
    });

    this.promocionService.getAll().subscribe({
      next: data => {
        this.promociones = data;
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
    this.promocionesFiltradas = q
      ? this.promociones.filter(p =>
          p.codigo.toLowerCase().includes(q) ||
          (p.descripcion?.toLowerCase().includes(q) ?? false)
        )
      : [...this.promociones];
  }

  // ── Helpers de vista ──────────────────────────────────────
  nombreEvento(eventoId: number): string {
    return this.eventos.find(e => e.id === eventoId)?.nombre ?? `Evento #${eventoId}`;
  }

  formatFecha(fecha: string): string {
    if (!fecha) return '—';
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: '2-digit', month: 'short', year: 'numeric'
    });
  }

  vigenciaClass(promo: Promocion): string {
    const hoy  = new Date();
    const fin  = new Date(promo.fechaFin);
    const ini  = new Date(promo.fechaInicio);
    if (!promo.activo)   return 'inactiva';
    if (fin < hoy)       return 'vencida';
    if (ini > hoy)       return 'proxima';
    return 'vigente';
  }

  vigenciaLabel(promo: Promocion): string {
    const mapa: Record<string, string> = {
      inactiva: 'Inactiva',
      vencida:  'Vencida',
      proxima:  'Próxima',
      vigente:  'Vigente'
    };
    return mapa[this.vigenciaClass(promo)];
  }

  get totalActivas(): number {
    return this.promociones.filter(p => this.vigenciaClass(p) === 'vigente').length;
  }

  // ── Modal crear / editar ──────────────────────────────────
  abrirCrear(): void {
    this.promoForm = this.promoVacia();
    this.modoEdicion = false;
    this.modalAbierto = true;
  }

  abrirEditar(promo: Promocion): void {
    this.promoForm = {
      ...promo,
      fechaInicio: promo.fechaInicio?.substring(0, 10) ?? '',
      fechaFin:    promo.fechaFin?.substring(0, 10)    ?? ''
    };
    this.modoEdicion = true;
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
  }

  guardar(): void {
    this.guardando = true;
    
    const promoToSend: Promocion = {
      ...this.promoForm,
      fechaInicio: this.promoForm.fechaInicio.length === 10 ? `${this.promoForm.fechaInicio}T00:00:00` : this.promoForm.fechaInicio,
      fechaFin: this.promoForm.fechaFin.length === 10 ? `${this.promoForm.fechaFin}T23:59:59` : this.promoForm.fechaFin
    };

    const accion = this.modoEdicion
      ? this.promocionService.update(promoToSend.id!, promoToSend)
      : this.promocionService.create(promoToSend);

    accion.subscribe({
      next: () => {
        this.mensajeExito = this.modoEdicion
          ? '✅ Promoción actualizada correctamente.'
          : '✅ Promoción creada correctamente.';
        this.guardando = false;
        this.cerrarModal();
        this.cargarDatos();
        setTimeout(() => this.mensajeExito = '', 4000);
      },
      error: () => { this.guardando = false; }
    });
  }

  // ── Eliminar ──────────────────────────────────────────────
  confirmarEliminar(promo: Promocion): void {
    this.promocionAEliminar = promo;
    this.modalEliminarAbierto = true;
  }

  cancelarEliminar(): void {
    this.promocionAEliminar = null;
    this.modalEliminarAbierto = false;
  }

  eliminar(): void {
    if (!this.promocionAEliminar?.id) return;
    this.eliminando = true;
    this.promocionService.delete(this.promocionAEliminar.id).subscribe({
      next: () => {
        this.eliminando = false;
        this.modalEliminarAbierto = false;
        this.promocionAEliminar = null;
        this.cargarDatos();
      },
      error: () => { this.eliminando = false; }
    });
  }

  private promoVacia(): Promocion {
    const hoy    = new Date().toISOString().substring(0, 10);
    const unMes  = new Date(Date.now() + 30 * 24 * 60 * 60 * 1000)
                     .toISOString().substring(0, 10);
    return {
      codigo: '', descripcion: '', porcentajeDescuento: 10,
      fechaInicio: hoy, fechaFin: unMes,
      cantidadDisponible: 100, cantidadUsada: 0,
      activo: true, eventoId: 0
    };
  }
}
