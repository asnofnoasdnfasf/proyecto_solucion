import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { ClienteFormComponent } from './cliente';
import { ClienteService } from '../../services/cliente.service';
import { ElectromaticApiService } from '../../services/electromatic-api.service';

describe('ClienteFormComponent', () => {
  let component: ClienteFormComponent;
  let fixture: ComponentFixture<ClienteFormComponent>;
  const clienteService = {
    crearCliente: () => of({ idCliente: 12 }),
    actualizarCliente: () => of({ idCliente: 12 }),
    desactivar: () => of({ idCliente: 12 })
  };
  const api = {
    ordenes: () => of([]),
    tiposEquipo: () => of([]),
    crearEquipo: () => of({ idEquipo: 34 }),
    crearOrden: () => of({ codigoSeguimiento: 'OS-ABC123' })
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteFormComponent],
      providers: [
        provideRouter([]),
        { provide: ClienteService, useValue: clienteService },
        { provide: ElectromaticApiService, useValue: api }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ClienteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('shows success and clears the saving state after registering the order', async () => {
    component.formulario.setValue({
      nomCliente: 'Ana',
      apeCliente: 'Pérez',
      dni: '12345678',
      correo: 'ana@example.com',
      direcc: '',
      numTel: '5551234',
      MARCA: 'Siemens',
      MODELO: '1LE1',
      idTipoEquipo: '1',
      NUMERO_SERIE: '',
      DESCRIPCION: 'Revisión general'
    });

    component.guardarOrden();
    await fixture.whenStable();

    expect(component.guardando).toBe(false);
    expect(component.exito).toBe(true);
    expect(component.mensaje).toContain('OS-ABC123');
    expect(component.vistaActual).toBe('detalle');
    expect(fixture.nativeElement.textContent).toContain('Cliente, equipo y orden registrados.');
  });
});
