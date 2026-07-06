import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';

import { EventoService } from '../../core/services/evento.service';
import { PedidoService, Pedido, ESTADOS_PEDIDO } from '../../core/services/pedido.service';
import { UsuarioService } from '../../core/services/usuario.service';

interface StatCard {
  label: string;
  value: string | number;
  icon: string;
  color: string;
  sublabel?: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  cards: StatCard[] = [];
  pedidosRecientes: Pedido[] = [];
  cargando = true;
  error = false;

  constructor(
    private eventoService: EventoService,
    private pedidoService: PedidoService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    forkJoin({
      eventos:  this.eventoService.getAll(),
      pedidos:  this.pedidoService.getAll(),
      usuarios: this.usuarioService.getAll()
    }).subscribe({
      next: ({ eventos, pedidos, usuarios }) => {
        // Cálculos
        const totalIngresos = pedidos
          .filter(p => p.estado === 'PAGADO')
          .reduce((sum, p) => sum + p.total, 0);

        const pendientes = pedidos.filter(p => p.estado === 'PENDIENTE').length;

        // Tarjetas de resumen
        this.cards = [
          {
            label: 'Eventos activos',
            value: eventos.length,
            icon: '🎭',
            color: 'blue',
            sublabel: 'en el sistema'
          },
          {
            label: 'Usuarios registrados',
            value: usuarios.length,
            icon: '👥',
            color: 'green',
            sublabel: 'en total'
          },
          {
            label: 'Pedidos totales',
            value: pedidos.length,
            icon: '📦',
            color: 'purple',
            sublabel: `${pendientes} pendiente(s)`
          },
          {
            label: 'Ingresos (pagados)',
            value: `S/ ${totalIngresos.toFixed(2)}`,
            icon: '💰',
            color: 'gold',
            sublabel: 'pedidos pagados'
          }
        ];

        // Últimos 10 pedidos ordenados por id desc
        this.pedidosRecientes = [...pedidos]
          .sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
          .slice(0, 10);

        this.cargando = false;
      },
      error: () => {
        this.error = true;
        this.cargando = false;
      }
    });
  }

  estadoClass(estado: string): string {
    const mapa: Record<string, string> = {
      PENDIENTE: 'badge-pendiente',
      APROBADO:  'badge-aprobado',
      PAGADO:    'badge-pagado',
      CANCELADO: 'badge-cancelado'
    };
    return mapa[estado] ?? 'badge-default';
  }

  formatFecha(fecha?: string): string {
    if (!fecha) return '—';
    return new Date(fecha).toLocaleDateString('es-PE', {
      day: '2-digit', month: 'short', year: 'numeric'
    });
  }
}
