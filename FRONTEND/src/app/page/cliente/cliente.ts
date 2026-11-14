import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';
import { ElectromaticApiService } from '../../services/electromatic-api.service';
import { timeout, finalize } from 'rxjs/operators';
import { forkJoin } from 'rxjs';
@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, CommonModule],
  templateUrl: './cliente.html',
  styleUrl: './cliente.css'
})
export class ClienteFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly changeDetector = inject(ChangeDetectorRef);
  private readonly clienteService = inject(ClienteService);
  private readonly api = inject(ElectromaticApiService);

  clientesServidor: any[] = [];
  ordenesServidor: any[] = [];
  tiposEquipoServidor: any[] = [];
  vistaActual = 'listado';
  modoEdicion = false;
  ordenActual: any = null;
  mensaje = '';
  mensajeTipos = '';
  guardando = false;
  exito = false;

  formulario = this.fb.nonNullable.group({
    nomCliente: ['', Validators.required],
    apeCliente: ['', Validators.required],
    dni: ['', Validators.required],
    // Acepta caracteres Unicode en la parte local del correo (por ejemplo, "más").
    correo: ['', [Validators.required, Validators.pattern(/^[^\s@]+@[^\s@]+\.[^\s@]+$/u)]],
    direcc: [''],
    numTel: ['', Validators.required],
    MARCA: ['', Validators.required],
    MODELO: ['', Validators.required],
    idTipoEquipo: ['', Validators.required],
    NUMERO_SERIE: [''],
    DESCRIPCION: ['', Validators.required]
  });

  ngOnInit(): void {
    this.cargarOrdenes();
    this.api.tiposEquipo().subscribe({
      next: rows => {
        this.tiposEquipoServidor = rows as any[];
        this.mensajeTipos = this.tiposEquipoServidor.length ? '' : 'No hay tipos de equipo registrados en la base de datos.';
        this.changeDetector.markForCheck();
      },
      error: error => {
        console.error('No se pudieron cargar los tipos de equipo.', error);
        this.mensajeTipos = `No se pudieron cargar los tipos de equipo (HTTP ${error.status}). Verifica que el backend esté activo.`;
        this.changeDetector.markForCheck();
      }
    });
  }

  private cargarOrdenes(): void {
    this.api.ordenes().subscribe({
      next: rows => {
        this.ordenesServidor = rows as any[];
        this.changeDetector.markForCheck();
      },
      error: error => { console.error('No se pudieron cargar las órdenes.', error); }
    });
  }

  mostrarListado(): void {
    this.vistaActual = 'listado';
    this.modoEdicion = false;
    this.mensaje = '';
    this.formulario.reset();
    this.cargarOrdenes();
  }

  mostrarFormulario(): void {
    this.vistaActual = 'formulario';
    this.modoEdicion = false;
    this.mensaje = '';
    this.exito = false;
    this.formulario.reset();
  }

  guardarOrden(): void {
    this.registrar();
  }


verDetalle(orden: any): void {
  this.mensaje = '';
  this.ordenActual = null;

  forkJoin({
    clientes: this.api.clientes(),
    equipos: this.api.equipos()
  }).subscribe({
    next: ({ clientes, equipos }) => {

      const listaClientes = clientes as any[];
      const listaEquipos = equipos as any[];

      // Buscar el cliente relacionado con la orden
      const cliente = listaClientes.find(
        c => Number(c.idCliente) === Number(orden.idCliente)
      );

      // Buscar el equipo relacionado con la orden
      const equipo = listaEquipos.find(
        e => Number(e.idEquipo) === Number(orden.idEquipo)
      );

      if (!cliente) {
        this.mensaje = 'No se encontraron los datos del cliente.';
        return;
      }

      if (!equipo) {
        this.mensaje = 'No se encontraron los datos del equipo.';
        return;
      }

      // Construir el detalle completo
      this.ordenActual = {
        ordenRegistrada: orden,

        // Datos del cliente
        idCliente: cliente.idCliente,
        nomCliente: cliente.nombreRazonSocial,
        apeCliente: '',
        numTel: cliente.telefono,
        dni: cliente.documento,
        correo: cliente.correo,
        direcc: cliente.direccion,

        // Datos del equipo
        idEquipo: equipo.idEquipo,
        MARCA: equipo.marca,
        MODELO: equipo.modelo,
        NUMERO_SERIE: equipo.numeroSerie,
        DESCRIPCION: equipo.descripcion,

        // Datos de la orden
        codigoSeguimiento: orden.codigoSeguimiento
      };

      this.vistaActual = 'detalle';
      this.changeDetector.markForCheck();

    },
    error: error => {
      console.error('Error al recuperar el detalle:', error);
      this.mensaje = 'No se pudieron recuperar los datos del cliente y del equipo.';
      this.changeDetector.markForCheck();
    }
  });
}


  registrar(): void {
    this.mensaje = '';
    this.exito = false;
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      const etiquetas: Record<string, string> = {
        nomCliente: 'nombres', apeCliente: 'apellidos', dni: 'documento', correo: 'correo válido',
        numTel: 'teléfono', MARCA: 'marca', MODELO: 'modelo', idTipoEquipo: 'tipo de equipo', DESCRIPCION: 'motivo'
      };
      const faltantes = Object.entries(this.formulario.controls)
        .filter(([, control]) => control.invalid)
        .map(([campo]) => etiquetas[campo] ?? campo);
      this.mensaje = `Revisa estos campos: ${faltantes.join(', ')}.`;
      return;
    }
    if (this.modoEdicion) { this.actualizarCliente(); return; }

    const value = this.formulario.getRawValue();
    this.guardando = true;
    let etapa: string = 'solicitud';
    this.api.registrarSolicitud({
      nomCliente: value.nomCliente, apeCliente: value.apeCliente, dni: value.dni, correo: value.correo,
      direcc: value.direcc, numTel: value.numTel, idTipoEquipo: Number(value.idTipoEquipo),
      MARCA: value.MARCA, MODELO: value.MODELO, NUMERO_SERIE: value.NUMERO_SERIE, DESCRIPCION: value.DESCRIPCION
    }).pipe(
      timeout({ first: 15000 }),
      finalize(() => {
        this.guardando = false;
        this.changeDetector.markForCheck();
      })
    ).subscribe({
      next: ({ cliente, equipo, orden }) => {
        this.exito = true;
        this.mensaje = `Cliente, equipo y orden registrados. Código de seguimiento: ${orden.codigoSeguimiento}`;
        this.ordenActual = { ...value, ...cliente, equipoRegistrado: equipo, ordenRegistrada: orden };
        this.vistaActual = 'detalle';
        this.formulario.reset();
        this.cargarOrdenes();
        this.changeDetector.markForCheck();
      },
      error: error => {
        const contexto = error.name === 'TimeoutError'
          ? 'El backend no respondió en 15 segundos.'
          : etapa === 'orden' ? 'Cliente y equipo guardados; falló la orden.'
          : etapa === 'equipo' ? 'Cliente guardado; falló el equipo.' : 'No se pudo registrar el cliente.';
        this.mostrarError(contexto, error);
      }
    });
  }

  private mostrarError(contexto: string, error: any): void {
    console.error(contexto, error);
    const detalle = error.error?.mensaje ?? error.error?.message ?? error.message ?? 'Error de conexión con el backend.';
    this.mensaje = `${contexto} HTTP ${error.status || 'sin conexión'}: ${detalle}`;
    this.exito = false;
    this.guardando = false;
    this.changeDetector.markForCheck();
  }

  editarOrden(): void {
    if (!this.ordenActual?.idCliente) return;
    this.vistaActual = 'formulario';
    this.modoEdicion = true;
    this.formulario.patchValue(this.ordenActual);
  }

  private actualizarCliente(): void {
    const value = this.formulario.getRawValue();
    this.clienteService.actualizarCliente(this.ordenActual.idCliente, value).subscribe({
      next: cliente => {
        this.ordenActual = { ...this.ordenActual, ...value, ...cliente };
        this.mensaje = 'Datos del cliente actualizados.';
        this.exito = true;
        this.vistaActual = 'detalle';
        this.modoEdicion = false;
        this.changeDetector.markForCheck();
      },
      error: error => this.mostrarError('No se pudo actualizar el cliente.', error)
    });
  }

  confirmarEliminacion(): void { this.vistaActual = 'eliminar'; }
  cancelarEliminacion(): void { this.vistaActual = 'detalle'; }

  eliminarOrden(): void {
    const id = this.ordenActual?.idCliente;
    if (!id) return;
    this.clienteService.desactivar(id).subscribe({
      next: () => {
        this.mensaje = 'Cliente desactivado; se conserva su historial.';
        this.vistaActual = 'listado';
        this.ordenActual = null;
        this.changeDetector.markForCheck();
      },
      error: error => this.mostrarError('No se pudo desactivar el cliente.', error)
    });
  }
}
