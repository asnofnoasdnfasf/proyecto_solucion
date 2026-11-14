import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ElectromaticApiService } from '../../services/electromatic-api.service';

@Component({
  selector: 'app-calidad',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, RouterLink],
  templateUrl: './calidad.html',
  styleUrls: ['./calidad.css', '../cliente/cliente.css']
})
export class Calidad implements OnInit {
  private api = inject(ElectromaticApiService);
  checklistsServidor: any[] = [];
  evaluacionesServidor: any[] = [];
  criteriosServidor: any[] = [];
  vistaActual: string = 'listado'; // 'listado', 'detalle', 'formulario'
  modoEdicion: boolean = false;
  ordenActual: any = null;

  calidadForm: FormGroup;

  listaOrdenesCalidad: any[] = [];

  constructor(private fb: FormBuilder) {
    // Inicialización del formulario con los campos exactos de la interfaz de edición
    this.calidadForm = this.fb.group({
      idOrdenServicio: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      idChecklist: ['', Validators.required],
      resultado: ['APROBADA', Validators.required], motivoRechazo: [''], observaciones: ['']
    });
  }

  ngOnInit(): void {
    this.api.checklists().subscribe({ next: rows => this.checklistsServidor = rows as any[], error: e => console.error('No se cargaron checklists:', e) });
    this.api.evaluaciones().subscribe({ next: rows => this.evaluacionesServidor = rows as any[], error: e => console.error('No se cargaron evaluaciones:', e) });
    this.api.ordenes().subscribe({ next: rows => this.listaOrdenesCalidad = rows.filter(x => x.idEstadoOrden === 5).map(x => ({ ...x, codigo: x.codigoSeguimiento, seguimiento: x.codigoSeguimiento, estado: 'En calidad' })), error: e => console.error('No se cargaron órdenes para calidad:', e) });
  }

  mostrarListado() {
    this.vistaActual = 'listado';
    this.ordenActual = null;
    this.calidadForm.reset();
  }

  verDetalle(orden: any) {
    this.ordenActual = orden;
    this.vistaActual = 'detalle';
  }

  editarEvaluacion() {
    this.vistaActual = 'formulario';
    this.modoEdicion = true;

    if (this.ordenActual) {
      this.calidadForm.patchValue({ idOrdenServicio: this.ordenActual.idOrdenServicio });
    }
  }

  prepararEvaluacion(resultado: 'APROBADA' | 'RECHAZADA'): void {
    if (!this.ordenActual?.idOrdenServicio) return;
    this.vistaActual = 'formulario';
    this.modoEdicion = false;
    this.criteriosServidor = [];
    this.calidadForm.reset({ idOrdenServicio: this.ordenActual.idOrdenServicio, idChecklist: '', resultado, motivoRechazo: '', observaciones: '' });
  }

  cargarCriteriosPorChecklist(): void {
    const id = Number(this.calidadForm.controls['idChecklist'].value);
    if (!id) return;
    this.api.criteriosChecklist(id).subscribe({
      next: (rows: any) => this.criteriosServidor = rows,
      error: e => { this.criteriosServidor = []; alert(e.error?.mensaje ?? 'No se pudieron cargar los criterios.'); }
    });
  }

  guardarOrden() {
    if (this.calidadForm.invalid || !this.criteriosServidor.length || this.criteriosServidor.some(c => !c.resultadoSeleccionado)) { this.calidadForm.markAllAsTouched(); alert('Completa todos los criterios del checklist.'); return; }
    const v = this.calidadForm.getRawValue();
    const criterios = this.criteriosServidor.map(c => ({ idCriterio: c.idCriterio, resultado: c.resultadoSeleccionado, observaciones: c.observacionesSeleccionadas }));
    this.api.evaluarCalidad({ idOrdenServicio: Number(v.idOrdenServicio), idChecklist: Number(v.idChecklist), resultado: v.resultado as 'APROBADA'|'RECHAZADA', motivoRechazo: v.motivoRechazo ?? undefined, observaciones: v.observaciones ?? undefined, criterios: criterios as any })
      .subscribe({ next: row => { this.ordenActual = row; this.vistaActual = 'detalle'; this.ngOnInit(); }, error: e => alert(e.error?.mensaje ?? 'No se pudo guardar la evaluación.') });
  }
}
