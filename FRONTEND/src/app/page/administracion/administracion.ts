import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ElectromaticApiService } from '../../services/electromatic-api.service';

@Component({
  selector: 'app-administracion',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './administracion.html',
  styleUrls: ['./administracion.css', '../cliente/cliente.css']
})
export class Administracion implements OnInit {
  private api = inject(ElectromaticApiService);
  proveedoresServidor: any[] = [];
  solicitudesCompraServidor: any[] = [];
  pagosServidor: any[] = [];
  entregasServidor: any[] = [];
  vistaActual: string = 'listado'; // Estados: 'listado', 'formularioCompra', 'formularioComprobante', 'formularioGarantia', 'detalle'
  subTabActiva: string = 'compras'; 
  modoEdicion: boolean = false;
  itemActual: any = null;

  compraForm: FormGroup;
  comprobanteForm: FormGroup;
  entregaForm: FormGroup;
  garantiaForm: FormGroup; // Formulario para garantías

  constructor(private fb: FormBuilder) {
    this.compraForm = this.fb.group({
      idRequerimiento: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      justificacion: ['', Validators.required]
    });

    this.comprobanteForm = this.fb.group({
      idOrdenServicio: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      idIntento: [''], tipoComprobante: ['BOLETA', Validators.required],
      identificador: ['', Validators.required], importe: ['', [Validators.required, Validators.min(0)]]
    });

    // Inicialización del formulario de garantía basado en la interfaz
    this.garantiaForm = this.fb.group({
      idOrdenServicio: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      fechaInicio: ['', Validators.required], fechaFin: ['', Validators.required],
      cobertura: ['', Validators.required], condiciones: ['']
    });
    this.entregaForm = this.fb.group({
      idOrdenServicio: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      personaRecibe: ['', Validators.required], documentoReceptor: ['', Validators.required], motivoExcepcion: ['']
    });
  }

  ngOnInit(): void {
    this.api.proveedores().subscribe({ next: rows => this.proveedoresServidor = rows as any[], error: e => console.error('No se cargaron proveedores:', e) });
    this.api.solicitudesCompra().subscribe({ next: rows => this.solicitudesCompraServidor = rows as any[], error: e => console.error('No se cargaron solicitudes de compra:', e) });
    this.api.pagos().subscribe({ next: rows => this.pagosServidor = rows as any[], error: e => console.error('No se cargaron pagos:', e) });
    this.api.entregas().subscribe({ next: rows => this.entregasServidor = rows as any[], error: e => console.error('No se cargaron entregas:', e) });
  }

  cambiarSubTab(tab: string) {
    this.subTabActiva = tab;
    this.vistaActual = 'listado';
  }

  mostrarListado() {
    this.vistaActual = 'listado';
    this.modoEdicion = false;
    this.compraForm.reset();
    this.comprobanteForm.reset();
    this.garantiaForm.reset();
    this.entregaForm.reset();
  }

  mostrarFormularioCompra() {
    this.vistaActual = 'formularioCompra';
    this.modoEdicion = false;
    this.compraForm.reset();
  }

  mostrarFormularioComprobante() {
    this.vistaActual = 'formularioComprobante';
    this.modoEdicion = false;
    this.comprobanteForm.reset({ tipoComprobante: 'BOLETA' });
  }

  mostrarFormularioGarantia() {
    this.vistaActual = 'formularioGarantia';
    this.modoEdicion = false;
    this.garantiaForm.reset();
  }

  mostrarFormularioEntrega() { this.vistaActual = 'formularioEntrega'; this.modoEdicion = false; this.entregaForm.reset(); }

  guardarEntrega() {
    if (this.entregaForm.invalid) { this.entregaForm.markAllAsTouched(); alert('Completa orden, persona que recibe y documento del receptor.'); return; }
    const v = this.entregaForm.getRawValue();
    this.api.entregarEquipo({ ...v, idOrdenServicio: Number(v.idOrdenServicio) })
      .subscribe({ next: row => { this.itemActual = row; this.vistaActual = 'detalle'; this.ngOnInit(); }, error: e => alert(e.error?.mensaje ?? 'No se pudo entregar. Verifica calidad y pago confirmado.') });
  }

  guardarCompra() {
    if (this.compraForm.invalid) { this.compraForm.markAllAsTouched(); alert('Completa el ID del requerimiento y la justificación de compra.'); return; }
    const v = this.compraForm.getRawValue();
    this.api.crearSolicitudCompra({ idRequerimiento: Number(v.idRequerimiento), justificacion: v.justificacion })
      .subscribe({ next: row => { this.itemActual = row; this.vistaActual = 'detalle'; this.modoEdicion = false; this.ngOnInit(); }, error: e => alert(e.error?.mensaje ?? 'No se pudo registrar la solicitud de compra.') });
  }

  guardarComprobante() {
    if (this.comprobanteForm.invalid) { this.comprobanteForm.markAllAsTouched(); alert('Completa orden, identificador fiscal e importe.'); return; }
    const v = this.comprobanteForm.getRawValue();
    this.api.emitirComprobante({ idOrdenServicio: Number(v.idOrdenServicio), idIntento: v.idIntento ? Number(v.idIntento) : null, tipoComprobante: v.tipoComprobante, identificador: v.identificador, importe: Number(v.importe) })
      .subscribe({ next: row => { this.itemActual = row; this.vistaActual = 'detalle'; this.modoEdicion = false; }, error: e => alert(e.error?.mensaje ?? 'No se pudo emitir el comprobante.') });
  }

  guardarGarantia() {
    if (this.garantiaForm.invalid) { this.garantiaForm.markAllAsTouched(); alert('Completa orden, fechas de vigencia y cobertura de la garantía.'); return; }
    const v = this.garantiaForm.getRawValue();
    this.api.crearGarantia({ ...v, idOrdenServicio: Number(v.idOrdenServicio) })
      .subscribe({ next: row => { this.itemActual = row; this.vistaActual = 'detalle'; this.modoEdicion = false; }, error: e => alert(e.error?.mensaje ?? 'No se pudo registrar la garantía.') });
  }

  editarItem() {
    if (this.subTabActiva === 'compras') this.vistaActual = 'formularioCompra';
    else if (this.subTabActiva === 'comprobantes') this.vistaActual = 'formularioComprobante';
    else if (this.subTabActiva === 'garantias') this.vistaActual = 'formularioGarantia';

    this.modoEdicion = true;
    if (this.itemActual) {
      if (this.subTabActiva === 'compras') this.compraForm.patchValue(this.itemActual);
      else if (this.subTabActiva === 'comprobantes') this.comprobanteForm.patchValue(this.itemActual);
      else if (this.subTabActiva === 'garantias') this.garantiaForm.patchValue(this.itemActual);
    }
  }

  verDetalle() {
    this.vistaActual = 'detalle';
  }
}
