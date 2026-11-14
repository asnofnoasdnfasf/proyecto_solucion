import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { inject } from '@angular/core';
import { ElectromaticApiService } from '../../services/electromatic-api.service';

@Component({
  selector: 'app-recepcion',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './recepcion.html',
  styleUrl: '../cliente/cliente.css'
})
export class Recepcion implements OnInit {
  private api = inject(ElectromaticApiService);
  ordenesServidor: any[] = [];
  get ordenesEnProceso(): number { return this.ordenesServidor.filter(o => o.idEstadoOrden < 7).length; }
  get ordenesEntregadas(): number { return this.ordenesServidor.filter(o => o.idEstadoOrden === 7).length; }
  vistaActual: string = 'listado';
  modoEdicion: boolean = false;
  ordenActual: any = null;
  historialServidor: any[] = [];

  recepcionForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.recepcionForm = this.fb.group({
      num_solicitud: [''],
      COD_SEGUIMIENTO: [''],
      fecha_hora_recepcion: [''],
      estado_visual: ['', Validators.required],
      danos_visibles: ['Sin daños visibles', Validators.required],
      accesorios_entregados: ['', Validators.required],
      IDEMPREC: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      idEquipo: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    });
  }

  ngOnInit(): void { this.api.ordenes().subscribe({ next: rows => this.ordenesServidor = rows, error: e => console.error('No se cargaron órdenes:', e) }); }

  mostrarListado() {
    this.vistaActual = 'listado';
    this.modoEdicion = false;
    this.recepcionForm.reset();
  }

  mostrarFormulario() {
    this.vistaActual = 'formulario';
    this.modoEdicion = false;
    this.recepcionForm.reset();
  }

  guardarOrden() {
    if (this.recepcionForm.invalid) { this.recepcionForm.markAllAsTouched(); alert('Completa estado visual, daños, accesorios, ID de cliente e ID de equipo.'); return; }
    const f = this.recepcionForm.getRawValue();
    this.api.crearOrden({
      idCliente: Number(f.IDEMPREC), idEquipo: Number(f.idEquipo),
      condicionFisica: f.estado_visual, danosVisibles: f.danos_visibles, accesorios: f.accesorios_entregados
    }).subscribe({
      next: orden => { this.ordenActual = { ...f, ...orden, COD_SEGUIMIENTO: orden.codigoSeguimiento, fecha_hora_recepcion: orden.fechaRecepcion }; this.vistaActual = 'detalle'; this.modoEdicion = false; this.ngOnInit(); },
      error: e => alert(e.error?.mensaje ?? 'No se pudo registrar la orden.')
    });
  }

  editarOrden() {
    this.vistaActual = 'formulario';
    this.modoEdicion = true;

    if (this.ordenActual) {
      this.recepcionForm.patchValue(this.ordenActual);
    }
  }

  verDetalle(orden?: any) {
    if (orden) this.ordenActual = { ...orden, COD_SEGUIMIENTO: orden.codigoSeguimiento, num_solicitud: orden.idOrdenServicio, fecha_hora_recepcion: orden.fechaRecepcion };
    this.vistaActual = 'detalle';
  }

  cargarHistorialOrden(): void {
    const id = this.ordenActual?.idOrdenServicio;
    if (!id) return;
    this.api.historialOrden(id).subscribe({
      next: rows => this.historialServidor = rows,
      error: error => alert(error.error?.mensaje ?? 'No se pudo cargar el historial de la orden.')
    });
  }
}
