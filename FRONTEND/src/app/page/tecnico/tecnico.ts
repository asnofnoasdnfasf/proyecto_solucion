import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ElectromaticApiService } from '../../services/electromatic-api.service';

interface RequerimientoTecnico {
  codigo: string;
  ordenServicio: string | null;
  repuestoRequerido: string | null;
  cantidadSolicitada: number | string | null;
  motivoEspecificaciones: string | null;
  solicitadoEn: string;
}

@Component({
  selector: 'app-tecnico',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './tecnico.html',
  styleUrls: ['./tecnico.css', '../cliente/cliente.css']
})
export class Tecnico implements OnInit {
  private api = inject(ElectromaticApiService);
  diagnosticosServidor: any[] = [];
  requerimientosServidor: any[] = [];
  ordenesServidor: any[] = [];
  vistaActual: string = 'listado'; 
  subTabActiva: string = 'ordenes';
  modoEdicion: boolean = false;
  
  // Variables para almacenar los datos en memoria local
  itemActual: any = null;
  requerimientoActual: RequerimientoTecnico | null = null;

  tecnicoForm: FormGroup;
  requerimientoForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.tecnicoForm = this.fb.group({
      idOrdenServicio: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      sintomas: ['', Validators.required], causaProbable: [''], observaciones: [''],
      recomendaciones: [''], clasificacionMantenimiento: ['', Validators.required], alcance: ['']
    });

    this.requerimientoForm = this.fb.group({
      ordenServicio: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      repuestoRequerido: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      prioridad: ['MEDIA', Validators.required],
      cantidadSolicitada: ['', [Validators.required, Validators.min(1)]],
      motivoEspecificaciones: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.api.diagnosticos().subscribe({ next: rows => this.diagnosticosServidor = rows as any[], error: e => console.error('No se cargaron diagnósticos:', e) });
    this.api.requerimientos().subscribe({ next: rows => this.requerimientosServidor = rows as any[], error: e => console.error('No se cargaron requerimientos:', e) });
    this.api.ordenes().subscribe({ next: rows => this.ordenesServidor = rows as any[], error: e => console.error('No se cargaron ordenes:', e) });
  }

  cambiarSubTab(tab: string) {
    this.subTabActiva = tab;
    this.vistaActual = 'listado';
  }

  mostrarListado() {
    this.vistaActual = 'listado';
    this.modoEdicion = false;
    this.tecnicoForm.reset();
    this.requerimientoForm.reset();
  }

  mostrarFormularioRequerimiento(orden?: any) {
    this.vistaActual = 'formRequerimiento';
    this.modoEdicion = false;
    this.requerimientoForm.reset();
    if (orden?.idOrdenServicio) this.requerimientoForm.patchValue({ ordenServicio: orden.idOrdenServicio });
  }

  mostrarFormularioTecnico(orden?: any) { this.vistaActual = 'formularioTecnico'; this.tecnicoForm.reset(); if (orden?.idOrdenServicio) this.tecnicoForm.patchValue({ idOrdenServicio: orden.idOrdenServicio }); }

  guardarTecnico() {
    if (this.tecnicoForm.invalid) { this.tecnicoForm.markAllAsTouched(); alert('Completa el ID de orden, los síntomas y la clasificación del mantenimiento.'); return; }
    const v = this.tecnicoForm.getRawValue();
    this.api.crearDiagnostico({ ...v, idOrdenServicio: Number(v.idOrdenServicio) })
      .subscribe({ next: row => { this.itemActual = row; this.vistaActual = 'detalle'; this.modoEdicion = false; this.ngOnInit(); }, error: e => alert(e.error?.mensaje ?? 'No se pudo registrar el diagnóstico.') });
  }

  guardarRequerimiento() {
    if (this.requerimientoForm.invalid) {
      this.requerimientoForm.markAllAsTouched();
      alert('Completa orden, material, cantidad, prioridad y justificación técnica.');
      return;
    }

    const v = this.requerimientoForm.getRawValue();
    const body = { idOrdenServicio: Number(v.ordenServicio), idMaterial: Number(v.repuestoRequerido), cantidad: Number(v.cantidadSolicitada), prioridad: v.prioridad, justificacionTecnica: v.motivoEspecificaciones };
    this.api.crearRequerimiento(body).subscribe({ next: (row: any) => { this.requerimientoActual = { ...v, codigo: row.codigoRequerimiento, solicitadoEn: row.fechaSolicitud }; this.vistaActual = 'detalleRequerimiento'; this.modoEdicion = false; this.api.requerimientos().subscribe({ next: rows => this.requerimientosServidor = rows as any[] }); }, error: e => alert(e.error?.mensaje ?? 'No se pudo crear el requerimiento.') });
  }

  private generarCodigoRequerimiento(): string {
    const fecha = Date.now().toString(36).toUpperCase();
    const sufijo = Math.random().toString(36).slice(2, 5).toUpperCase();
    return `REQ-${fecha}-${sufijo}`;
  }

  private formatearFecha(fecha: Date): string {
    const fechaFormato = fecha.toLocaleDateString('en-GB', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
    const horaFormato = fecha.toLocaleTimeString('en-US', {
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: true
    }).replace(' AM', ' a. m.').replace(' PM', ' p. m.');

    return `${fechaFormato}, ${horaFormato}`;
  }

  editarRequerimiento() {
    this.vistaActual = 'formRequerimiento';
    this.modoEdicion = true;
    if (this.requerimientoActual) {
      this.requerimientoForm.patchValue(this.requerimientoActual);
    }
  }

  verDetalle(orden?: any) {
    if (orden) this.itemActual = orden;
    this.vistaActual = 'detalle';
  }
}
