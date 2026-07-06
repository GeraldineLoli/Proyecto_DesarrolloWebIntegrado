import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventoService } from '../../services/evento.service';
import { ZonaService } from '../../services/zona.service';
import { PromocionService } from '../../services/promocion.service';
import { PedidoService } from '../../services/pedido.service';
import { PagoService } from '../../services/pago.service';
import { EntradaService } from '../../services/entrada.service';
import { Evento } from '../../models/evento.model';
import { Zona } from '../../models/zona.model';
import { Promocion } from '../../models/promocion.model';
import { Pedido } from '../../models/pedido.model';
import { Entrada } from '../../models/entrada.model';
import { AuthResponse } from '../../models/usuario.model';

interface ZonaCompra {
  zona: Zona;
  cantidad: number;
  subtotal: number;
}

@Component({
  selector: 'app-compra',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './compra.component.html',
  styleUrl: './compra.component.css'
})
export class CompraComponent implements OnInit {
  user: AuthResponse | null = null;
  evento: Evento | null = null;
  zonas: Zona[] = [];
  zonasCompra: ZonaCompra[] = [];
  
  // Promoción
  codigoPromocion = '';
  promocionAplicada: Promocion | null = null;
  errorPromocion = '';
  
  // Cálculos
  subtotal = 0;
  descuento = 0;
  total = 0;
  
  // Pago
  metodoPago = '';
  numeroTarjeta = '';
  nombreTarjeta = '';
  fechaExpiracion = '';
  cvv = '';
  
  // Validaciones
  errorMetodoPago = '';
  errorNumeroTarjeta = '';
  errorNombreTarjeta = '';
  errorFechaExpiracion = '';
  errorCvv = '';
  
  // Estados
  loading = true;
  procesandoPago = false;
  error = '';
  paso = 1; // 1: selección, 2: pago, 3: confirmación

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private eventoService: EventoService,
    private zonaService: ZonaService,
    private promocionService: PromocionService,
    private pedidoService: PedidoService,
    private pagoService: PagoService,
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

    const eventoId = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarEvento(eventoId);
  }

  cargarEvento(id: number): void {
    this.loading = true;
    this.eventoService.getEvento(id).subscribe({
      next: (evento) => {
        this.evento = evento;
        this.cargarZonas(id);
      },
      error: () => {
        this.error = 'No se pudo cargar el evento';
        this.loading = false;
      }
    });
  }

  cargarZonas(eventoId: number): void {
    this.zonaService.getZonasPorEvento(eventoId).subscribe({
      next: (zonas) => {
        this.zonas = zonas;
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar las zonas';
        this.loading = false;
      }
    });
  }

  agregarZona(zona: Zona): void {
    const existente = this.zonasCompra.find(zc => zc.zona.id === zona.id);
    if (existente) {
      if (existente.cantidad < zona.entradasDisponibles) {
        existente.cantidad++;
        existente.subtotal = existente.cantidad * zona.precio;
      }
    } else {
      this.zonasCompra.push({
        zona: zona,
        cantidad: 1,
        subtotal: zona.precio
      });
    }
    this.calcularTotales();
  }

  eliminarZona(zonaId: number): void {
    this.zonasCompra = this.zonasCompra.filter(zc => zc.zona.id !== zonaId);
    this.calcularTotales();
  }

  modificarCantidad(zonaId: number, incremento: number): void {
    const zonaCompra = this.zonasCompra.find(zc => zc.zona.id === zonaId);
    if (zonaCompra) {
      const nuevaCantidad = zonaCompra.cantidad + incremento;
      if (nuevaCantidad > 0 && nuevaCantidad <= zonaCompra.zona.entradasDisponibles) {
        zonaCompra.cantidad = nuevaCantidad;
        zonaCompra.subtotal = zonaCompra.cantidad * zonaCompra.zona.precio;
        this.calcularTotales();
      }
    }
  }

  aplicarPromocion(): void {
    if (!this.codigoPromocion.trim()) {
      this.errorPromocion = 'Ingresa un código de promoción';
      return;
    }

    this.promocionService.obtenerPorCodigo(this.codigoPromocion.toUpperCase()).subscribe({
      next: (promo) => {
        if (!promo) {
          this.errorPromocion = 'Código inválido';
          return;
        }
        if (promo.eventoId !== this.evento?.id) {
          this.errorPromocion = 'Esta promoción no es válida para este evento';
          return;
        }
        if (!promo.activo) {
          this.errorPromocion = 'Promoción no activa';
          return;
        }
        if (promo.cantidadUsada >= promo.cantidadDisponible) {
          this.errorPromocion = 'Promoción agotada';
          return;
        }
        
        const ahora = new Date();
        const inicio = new Date(promo.fechaInicio);
        const fin = new Date(promo.fechaFin);
        
        if (ahora < inicio || ahora > fin) {
          this.errorPromocion = 'Promoción no vigente';
          return;
        }

        this.promocionAplicada = promo;
        this.errorPromocion = '';
        this.calcularTotales();
      },
      error: () => {
        this.errorPromocion = 'Error al validar el código';
      }
    });
  }

  quitarPromocion(): void {
    this.promocionAplicada = null;
    this.codigoPromocion = '';
    this.errorPromocion = '';
    this.calcularTotales();
  }

  calcularTotales(): void {
    this.subtotal = this.zonasCompra.reduce((sum, zc) => sum + zc.subtotal, 0);
    this.descuento = this.promocionAplicada 
      ? this.subtotal * (this.promocionAplicada.porcentajeDescuento / 100) 
      : 0;
    this.total = this.subtotal - this.descuento;
  }

  irAPago(): void {
    if (this.zonasCompra.length === 0) {
      this.error = 'Debes seleccionar al menos una entrada';
      return;
    }
    this.paso = 2;
    this.error = '';
  }

  volverASeleccion(): void {
    this.paso = 1;
  }

  finalizarCompra(): void {
    // Limpiar errores previos
    this.errorMetodoPago = '';
    this.errorNumeroTarjeta = '';
    this.errorNombreTarjeta = '';
    this.errorFechaExpiracion = '';
    this.errorCvv = '';
    this.error = '';

    let hayErrores = false;

    // Validar método de pago
    if (!this.metodoPago) {
      this.errorMetodoPago = 'Selecciona un método de pago';
      hayErrores = true;
    }

    // Validar datos de tarjeta si es necesario
    if (this.metodoPago === 'VISA' || this.metodoPago === 'MASTERCARD') {
      if (!this.numeroTarjeta || this.numeroTarjeta.trim().length < 13) {
        this.errorNumeroTarjeta = 'Ingresa un número de tarjeta válido (mínimo 13 dígitos)';
        hayErrores = true;
      }
      
      if (!this.nombreTarjeta || this.nombreTarjeta.trim().length < 3) {
        this.errorNombreTarjeta = 'Ingresa el nombre como aparece en la tarjeta';
        hayErrores = true;
      }
      
      if (!this.fechaExpiracion || this.fechaExpiracion.trim().length < 4) {
        this.errorFechaExpiracion = 'Ingresa la fecha de expiración (MM/AA)';
        hayErrores = true;
      }
      
      if (!this.cvv || this.cvv.trim().length < 3) {
        this.errorCvv = 'Ingresa el CVV (3 dígitos)';
        hayErrores = true;
      }
    }

    if (hayErrores) {
      return;
    }

    this.procesandoPago = true;

    // Crear pedido
    const pedido: Pedido = {
      usuarioId: this.user!.id!,
      estado: 'PENDIENTE',
      total: this.total,
      entradaIds: []
    };

    this.pedidoService.crearPedido(pedido).subscribe({
      next: (pedidoCreado) => {
        if (pedidoCreado && pedidoCreado.id) {
          this.procesarPago(pedidoCreado);
        } else {
          this.error = 'No se pudo obtener el ID del pedido creado';
          this.procesandoPago = false;
        }
      },
      error: (err) => {
        console.error('Error al crear pedido:', err);
        this.error = 'Error al crear el pedido: ' + (err.error || 'Error desconocido');
        this.procesandoPago = false;
      }
    });
  }

  procesarPago(pedido: Pedido): void {
    const numeroTarjetaParam = this.numeroTarjeta ? this.numeroTarjeta : undefined;
    
    this.pagoService.procesarPago(
      pedido.id!, 
      this.user!.id!, 
      this.metodoPago, 
      numeroTarjetaParam
    ).subscribe({
      next: () => {
        // Crear entradas
        this.crearEntradas(pedido);
      },
      error: (err) => {
        console.error('Error al procesar pago:', err);
        this.error = 'Error al procesar el pago: ' + (err.error || 'Error desconocido');
        this.procesandoPago = false;
      }
    });
  }

  crearEntradas(pedido: Pedido): void {
    const entradas: Entrada[] = [];
    
    // Preparar todas las entradas
    this.zonasCompra.forEach(zc => {
      for (let i = 0; i < zc.cantidad; i++) {
        const entrada: Entrada = {
          eventoId: this.evento!.id,
          zonaId: zc.zona.id,
          usuarioId: this.user!.id!,
          codigoEntrada: '',
          precioPagado: zc.zona.precio,
          fechaCompra: new Date().toISOString(),
          estado: 'PAGADA',
          metodoPago: this.metodoPago
        } as Entrada;
        entradas.push(entrada);
      }
    });

    // Crear todas las entradas
    let entradasCreadas = 0;
    const totalEntradas = entradas.length;
    const idsEntradas: number[] = [];

    if (totalEntradas === 0) {
      this.error = 'No hay entradas para crear';
      this.procesandoPago = false;
      return;
    }

    entradas.forEach(entrada => {
      this.entradaService.crearEntrada(entrada).subscribe({
        next: (entradaCreada) => {
          entradasCreadas++;
          if (entradaCreada && entradaCreada.id) {
            idsEntradas.push(entradaCreada.id);
          }
          
          // Cuando todas las entradas estén creadas
          if (entradasCreadas === totalEntradas) {
            // Usar promoción si existe
            if (this.promocionAplicada) {
              this.promocionService.usarPromocion(this.promocionAplicada.id).subscribe();
            }
            
            // Actualizar estado del pedido
            pedido.estado = 'PAGADO';
            pedido.entradaIds = idsEntradas;
            
            this.pedidoService.actualizarPedido(pedido.id!, pedido).subscribe({
              next: () => {
                this.procesandoPago = false;
                this.paso = 3;
                
                // Redirigir a mis compras después de 3 segundos
                setTimeout(() => {
                  this.router.navigate(['/mis-compras']);
                }, 3000);
              },
              error: (err) => {
                console.error('Error al actualizar pedido:', err);
                // Aunque falle la actualización, la compra se realizó
                this.procesandoPago = false;
                this.paso = 3;
                
                setTimeout(() => {
                  this.router.navigate(['/mis-compras']);
                }, 3000);
              }
            });
          }
        },
        error: (err) => {
          console.error('Error al crear entrada:', err);
          this.error = 'Error al crear las entradas: ' + (err.error || 'Error desconocido');
          this.procesandoPago = false;
        }
      });
    });
  }

  getZonaColor(nombre: string): string {
    const mapa: Record<string, string> = {
      VIP: '#7c3aed',
      PLATEA: '#0891b2',
      GENERAL: '#16a34a',
      PALCO: '#ea580c'
    };
    return mapa[nombre.toUpperCase()] ?? '#6b7280';
  }

  formatFecha(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleDateString('es-PE', {
      day: 'numeric',
      month: 'short',
      year: 'numeric'
    });
  }

  formatHora(fechaHora: string): string {
    const fecha = new Date(fechaHora);
    return fecha.toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' });
  }
}
