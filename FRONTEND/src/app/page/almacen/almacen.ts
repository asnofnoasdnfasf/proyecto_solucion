import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ElectromaticApiService } from '../../services/electromatic-api.service';

@Component({
  selector: 'app-almacen',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './almacen.html',
  styleUrls: ['./almacen.css', '../cliente/cliente.css']
})
export class Almacen implements OnInit {
  private api = inject(ElectromaticApiService);
  materialesServidor: any[] = [];
  movimientosServidor: any[] = [];
  requerimientosServidor: any[] = [];
  atencionesServidor: any[] = [];
  stockPorMaterial: Record<number, number> = {};
  guardando = false;
  vistaActual: string = 'listado'; // 'listado', 'formulario', 'detalle'
  subTabActiva: string = 'requerimientos'; // 'requerimientos', 'inventario', 'ingresos'
  modoEdicion: boolean = false;
  itemActual: any = null;

  almacenForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.almacenForm = this.fb.group({
      idRequerimiento: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      observaciones: ['']
    });
  }

  ngOnInit(): void {
    this.api.materiales().subscribe({ next: rows => { this.materialesServidor = rows as any[]; this.materialesServidor.forEach(m => this.api.stockMaterial(m.idMaterial).subscribe({ next: (s: any) => this.stockPorMaterial[m.idMaterial] = Number(s.stockActual ?? 0) })); }, error: e => console.error('No se cargaron materiales:', e) });
    this.api.movimientos().subscribe({ next: rows => this.movimientosServidor = rows as any[], error: e => console.error('No se cargaron movimientos:', e) });
    this.api.requerimientosAlmacen().subscribe({ next: rows => this.requerimientosServidor = rows as any[], error: e => console.error('No se cargaron requerimientos:', e) });
    this.api.atencionesAlmacen().subscribe({ next: rows => this.atencionesServidor = rows as any[], error: e => console.error('No se cargaron atenciones:', e) });
  }

  cambiarSubTab(tab: string) {
    this.subTabActiva = tab;
    this.vistaActual = 'listado';
  }

  mostrarListado() {
    this.vistaActual = 'listado';
    this.modoEdicion = false;
    this.almacenForm.reset();
  }

  mostrarFormulario() {
    this.vistaActual = 'formulario';
    this.modoEdicion = false;
    this.almacenForm.reset();
  }

  guardarItem() {
    if (this.almacenForm.invalid) { this.almacenForm.markAllAsTouched(); alert('Indica el ID numérico del requerimiento que se atenderá.'); return; }
    const v = this.almacenForm.getRawValue();
    this.guardando = true;
    this.api.atenderRequerimiento(Number(v.idRequerimiento), v.observaciones)
      .subscribe({ next: row => { this.itemActual = row; this.vistaActual = 'detalle'; this.modoEdicion = false; this.guardando = false; this.ngOnInit(); }, error: e => { this.guardando = false; alert(e.error?.mensaje ?? 'No se pudo atender el requerimiento.'); } });
  }

  atenderDesdeFila(requerimiento: any): void {
    this.almacenForm.reset({ idRequerimiento: requerimiento.idRequerimiento, observaciones: '' });
    this.vistaActual = 'formulario';
    this.modoEdicion = false;
  }

  actualizarStock(idMaterial: number): void {
    this.api.stockMaterial(idMaterial).subscribe({ next: (s: any) => this.stockPorMaterial[idMaterial] = Number(s.stockActual ?? 0), error: e => alert(e.error?.mensaje ?? 'No se pudo consultar el stock.') });
  }

  editarItem() {
    this.vistaActual = 'formulario';
    this.modoEdicion = true;
    if (this.itemActual) {
      this.almacenForm.patchValue(this.itemActual);
    }
  }

  verDetalle() {
    this.vistaActual = 'detalle';
  }
}
