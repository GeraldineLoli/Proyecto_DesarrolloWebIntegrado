import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { PedidoService } from '../../services/pedido.service';
import { PagoService } from '../../services/pago.service';
import { EventoService } from '../../services/evento.service';
import { EntradaService } from '../../services/entrada.service';
import { Pedido } from '../../models/pedido.model';
import { Pago } from '../../models/pago.model';
import { Evento } from '../../models/evento.model';
import { Entrada } from '../../models/entrada.model';
import { AuthResponse } from '../../models/usuario.model';

interface PedidoDetalle {
  pedido: Pedido;
  pago: Pago | null;
  evento: Evento | null;
  entradas: Entrada[];
}

@Component({
  selector: 'app-mis-compras',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './mis-compras.component.html',
  styleUrl: './mis-compras.component.css'
})
export class MisComprasComponent implements OnInit {
  user: AuthResponse | null = null;
  pedidosDetalle: PedidoDetalle[] = [];
  loading = true;
  error = '';
  menuAbierto = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private pedidoService: PedidoService,
    private pagoService: PagoService,
    private eventoService: EventoService,
    private entradaService: EntradaService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    
    // Verificar que el usuario tenga ID
    if (!this.user || !this.user.id) {
      console.error('Usuario sin ID, redirigiendo al login');
      this.authService.logout();
      this.router.navigate(['/login']);
      return;
    }
    
    if (this.user.rol !== 'CLIENTE') {
      this.router.navigate(['/home']);
      return;
    }

    this.cargarCompras();
  }

  cargarCompras(): void {
    this.loading = true;
    this.error = '';
    
    this.pedidoService.obtenerPorUsuario(this.user!.id!).subscribe({
      next: (pedidos) => {
        if (pedidos.length === 0) {
          this.loading = false;
          return;
        }

        // Ordenar pedidos por fecha (más recientes primero)
        pedidos.sort((a, b) => {
          const fechaA = a.fechaCreacion ? new Date(a.fechaCreacion).getTime() : 0;
          const fechaB = b.fechaCreacion ? new Date(b.fechaCreacion).getTime() : 0;
          return fechaB - fechaA;
        });

        // Cargar detalles de cada pedido
        let pedidosProcesados = 0;
        const totalPedidos = pedidos.length;
        const detallesTemp: PedidoDetalle[] = [];

        pedidos.forEach(pedido => {
          this.cargarDetallePedido(pedido).then(detalle => {
            detallesTemp.push(detalle);
            pedidosProcesados++;
            
            if (pedidosProcesados === totalPedidos) {
              // Ordenar detalles por fecha
              this.pedidosDetalle = detallesTemp.sort((a, b) => {
                const fechaA = a.pedido.fechaCreacion ? new Date(a.pedido.fechaCreacion).getTime() : 0;
                const fechaB = b.pedido.fechaCreacion ? new Date(b.pedido.fechaCreacion).getTime() : 0;
                return fechaB - fechaA;
              });
              this.loading = false;
            }
          }).catch(err => {
            console.error('Error al cargar detalle del pedido:', err);
            pedidosProcesados++;
            if (pedidosProcesados === totalPedidos) {
              this.pedidosDetalle = detallesTemp;
              this.loading = false;
            }
          });
        });
      },
      error: (err) => {
        console.error('Error al cargar compras:', err);
        this.error = 'Error al cargar las compras. Por favor, intenta nuevamente.';
        this.loading = false;
      }
    });
  }

  cargarDetallePedido(pedido: Pedido): Promise<PedidoDetalle> {
    return new Promise((resolve, reject) => {
      const detalle: PedidoDetalle = {
        pedido: pedido,
        pago: null,
        evento: null,
        entradas: []
      };

      // Cargar pago del pedido
      this.pagoService.obtenerPorPedido(pedido.id!).subscribe({
        next: (pagos) => {
          detalle.pago = pagos.length > 0 ? pagos[0] : null;
          
          // Cargar entradas del usuario (filtradas por fecha cercana al pedido)
          this.entradaService.obtenerPorUsuario(this.user!.id!).subscribe({
            next: (todasEntradas) => {
              // Filtrar entradas cercanas a la fecha del pedido (5 minutos)
              if (pedido.fechaCreacion) {
                const fechaPedido = new Date(pedido.fechaCreacion);
                detalle.entradas = todasEntradas.filter(entrada => {
                  const fechaEntrada = new Date(entrada.fechaCompra);
                  const diff = Math.abs(fechaEntrada.getTime() - fechaPedido.getTime());
                  return diff < 300000; // 5 minutos de diferencia
                }).sort((a, b) => 
                  new Date(b.fechaCompra).getTime() - new Date(a.fechaCompra).getTime()
                );
              } else {
                detalle.entradas = [];
              }

              // Cargar evento: priorizar entradas con entradaIds, luego por fecha
              let eventoIdBuscar: number | null = null;
              
              if (pedido.entradaIds && pedido.entradaIds.length > 0) {
                // Buscar la primera entrada que coincida con los IDs del pedido
                const entradaConId = detalle.entradas.find(e => 
                  e.id && pedido.entradaIds!.includes(e.id)
                );
                if (entradaConId) {
                  eventoIdBuscar = entradaConId.eventoId;
                }
              }
              
              // Si no se encontró por IDs, usar la primera entrada por fecha
              if (!eventoIdBuscar && detalle.entradas.length > 0) {
                eventoIdBuscar = detalle.entradas[0].eventoId;
              }

              if (eventoIdBuscar) {
                this.eventoService.getEvento(eventoIdBuscar).subscribe({
                  next: (evento) => {
                    detalle.evento = evento;
                    resolve(detalle);
                  },
                  error: (err) => {
                    console.error('Error al cargar evento:', err);
                    resolve(detalle);
                  }
                });
              } else {
                resolve(detalle);
              }
            },
            error: (err) => {
              console.error('Error al cargar entradas:', err);
              resolve(detalle);
            }
          });
        },
        error: (err) => {
          console.error('Error al cargar pago:', err);
          resolve(detalle);
        }
      });
    });
  }

  getEstadoClass(estado: string): string {
    const clases: Record<string, string> = {
      'PENDIENTE': 'estado-pendiente',
      'PAGADO': 'estado-pagado',
      'COMPLETADO': 'estado-completado',
      'CANCELADO': 'estado-cancelado',
      'REEMBOLSADO': 'estado-reembolsado',
      'FALLIDO': 'estado-fallido'
    };
    return clases[estado] || 'estado-pendiente';
  }

  formatFecha(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleDateString('es-PE', {
      day: 'numeric',
      month: 'short',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  formatFechaEvento(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleDateString('es-PE', {
      weekday: 'short',
      day: 'numeric',
      month: 'short',
      year: 'numeric'
    });
  }

  logout(): void {
    this.authService.logout();
    window.location.href = '/login';
  }

  toggleMenu(): void {
    this.menuAbierto = !this.menuAbierto;
  }
}
