import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PedidoService, Pedido, ESTADOS_PEDIDO, EstadoPedido } from '../../core/services/pedido.service';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css'
})
export class PedidosComponent implements OnInit {

  pedidos: Pedido[] = [];
  pedidosFiltrados: Pedido[] = [];

  textoBusqueda  = '';
  filtroEstado   = 'TODOS';
  readonly ESTADOS = ['TODOS', ...ESTADOS_PEDIDO];

  // Estados UI
  cargando = true;
  error    = false;

  // Modal cambio de estado
  modalEstadoAbierto   = false;
  pedidoSeleccionado: Pedido | null = null;
  nuevoEstado: EstadoPedido        = 'PENDIENTE';
  guardandoEstado = false;
  mensajeExito    = '';

  readonly ESTADOS_OPCIONES = ESTADOS_PEDIDO;

  constructor(private pedidoService: PedidoService) {}

  ngOnInit(): void {
    this.cargarPedidos();
  }

  cargarPedidos(): void {
    this.cargando = true;
    this.pedidoService.getAll().subscribe({
      next: data => {
        // Ordena por id desc (más recientes primero)
        this.pedidos = [...data].sort((a, b) => (b.id ?? 0) - (a.id ?? 0));
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
    let resultado = [...this.pedidos];

    if (this.filtroEstado !== 'TODOS') {
      resultado = resultado.filter(p => p.estado === this.filtroEstado);
    }

    const q = this.textoBusqueda.toLowerCase().trim();
    if (q) {
      resultado = resultado.filter(p =>
        (p.codigoPedido?.toLowerCase().includes(q)) ||
        String(p.usuarioId).includes(q)
      );
    }

    this.pedidosFiltrados = resultado;
  }

  // ── Conteos por estado para las tarjetas ──────────────────
  contarPorEstado(estado: string): number {
    return this.pedidos.filter(p => p.estado === estado).length;
  }

  totalIngresos(): number {
    return this.pedidos
      .filter(p => p.estado === 'PAGADO')
      .reduce((s, p) => s + p.total, 0);
  }

  // ── Modal cambio de estado ────────────────────────────────
  abrirCambioEstado(pedido: Pedido): void {
    this.pedidoSeleccionado = pedido;
    this.nuevoEstado = pedido.estado as EstadoPedido;
    this.mensajeExito = '';
    this.modalEstadoAbierto = true;
  }

  cerrarModal(): void {
    this.modalEstadoAbierto = false;
    this.pedidoSeleccionado = null;
  }

  confirmarCambioEstado(): void {
    if (!this.pedidoSeleccionado?.id) return;
    this.guardandoEstado = true;

    this.pedidoService.cambiarEstado(this.pedidoSeleccionado.id, this.nuevoEstado)
      .subscribe({
        next: pedidoActualizado => {
          // Actualiza el pedido en la lista local — sin re-fetch
          const idx = this.pedidos.findIndex(p => p.id === pedidoActualizado.id);
          if (idx !== -1) this.pedidos[idx] = pedidoActualizado;
          this.aplicarFiltro();

          this.mensajeExito = `✅ Estado actualizado a ${this.nuevoEstado}`;
          this.guardandoEstado = false;
          this.cerrarModal();
          setTimeout(() => this.mensajeExito = '', 4000);
        },
        error: () => { this.guardandoEstado = false; }
      });
  }

  // ── Helpers de vista ──────────────────────────────────────
  estadoClass(estado: string): string {
    const mapa: Record<string, string> = {
      PENDIENTE: 'pendiente',
      APROBADO:  'aprobado',
      PAGADO:    'pagado',
      CANCELADO: 'cancelado'
    };
    return mapa[estado] ?? '';
  }

  formatFecha(fecha?: string): string {
    if (!fecha) return '—';
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: '2-digit', month: 'short', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

  // Transiciones de estado válidas (lógica de negocio)
  estadosSiguientes(estadoActual: string): EstadoPedido[] {
    const flujo: Record<string, EstadoPedido[]> = {
      PENDIENTE: ['APROBADO', 'CANCELADO'],
      APROBADO:  ['PAGADO',   'CANCELADO'],
      PAGADO:    [],          // estado final
      CANCELADO: []           // estado final
    };
    return flujo[estadoActual] ?? [];
  }

  esFinalizado(estado: string): boolean {
    return estado === 'PAGADO' || estado === 'CANCELADO';
  }
}
