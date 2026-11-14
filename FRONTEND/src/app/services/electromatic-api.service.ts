import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface OrdenServicioApi { idOrdenServicio?: number; idCliente: number; idEquipo: number; condicionFisica?: string; danosVisibles?: string; accesorios?: string; codigoSeguimiento?: string; idEstadoOrden?: number; fechaRecepcion?: string; }
export interface MovimientoInventarioApi { idMaterial: number; tipoMovimiento: 'INGRESO'|'SALIDA'|'AJUSTE'; cantidad: number; justificacion: string; idDetalleAtencion?: number; idDetalleCompra?: number; }
export interface EvaluacionCalidadApi { idOrdenServicio: number; idChecklist: number; resultado: 'APROBADA'|'RECHAZADA'; motivoRechazo?: string; observaciones?: string; criterios: { idCriterio: number; resultado: 'CUMPLE'|'NO_CUMPLE'|'NO_APLICA'; observaciones?: string }[]; }

@Injectable({ providedIn: 'root' })
export class ElectromaticApiService {
  private readonly http = inject(HttpClient);
  private readonly api = environment.apiUrl;

  indicadores() { return this.http.get(`${this.api}/dashboard/indicadores`); }

  clientes() { return this.http.get(`${this.api}/clientes`); }
  registrarSolicitud(datos: unknown) { return this.http.post<{ cliente: any; equipo: any; orden: OrdenServicioApi }>(`${this.api}/clientes/solicitudes`, datos); }
  crearOrden(datos: OrdenServicioApi) { return this.http.post<OrdenServicioApi>(`${this.api}/recepcion/ordenes`, datos); }
  ordenes() { return this.http.get<OrdenServicioApi[]>(`${this.api}/recepcion/ordenes`); }
  historialOrden(id: number) { return this.http.get<any[]>(`${this.api}/recepcion/ordenes/${id}/historial`); }
  tiposEquipo() { return this.http.get(`${this.api}/recepcion/tipos-equipo`); }
  equipos() { return this.http.get(`${this.api}/recepcion/equipos`); }
  crearEquipo(datos: unknown) { return this.http.post(`${this.api}/recepcion/equipos`, datos); }
  seguimiento(codigo: string, documento: string) { return this.http.get(`${this.api}/recepcion/seguimiento/${encodeURIComponent(codigo)}`, { params: { documento } }); }
  garantias() { return this.http.get(`${this.api}/recepcion/garantias`); }
  crearGarantia(datos: unknown) { return this.http.post(`${this.api}/recepcion/garantias`, datos); }

  diagnosticos() { return this.http.get(`${this.api}/tecnico/diagnosticos`); }
  crearDiagnostico(datos: unknown) { return this.http.post(`${this.api}/tecnico/diagnosticos`, datos); }
  iniciarMantenimiento(idOrdenServicio: number) { return this.http.post(`${this.api}/tecnico/mantenimientos`, { idOrdenServicio }); }
  pruebaInicial(idMantenimiento: number, resultado: 'APROBADA'|'FALLIDA', observaciones?: string) { return this.http.post(`${this.api}/tecnico/mantenimientos/${idMantenimiento}/prueba-inicial`, { resultado, observaciones }); }
  crearRequerimiento(datos: unknown) { return this.http.post(`${this.api}/tecnico/requerimientos`, datos); }
  requerimientos() { return this.http.get(`${this.api}/tecnico/requerimientos`); }

  materiales() { return this.http.get(`${this.api}/almacen/materiales`); }
  crearMaterial(datos: unknown) { return this.http.post<{ material: any; movimiento: any; stockActual: number }>(`${this.api}/almacen/materiales`, datos); }
  stockMaterial(id: number) { return this.http.get(`${this.api}/almacen/materiales/${id}/stock`); }
  registrarMovimiento(datos: MovimientoInventarioApi) { return this.http.post(`${this.api}/almacen/movimientos`, datos); }
  movimientos() { return this.http.get(`${this.api}/almacen/movimientos`); }
  requerimientosAlmacen() { return this.http.get(`${this.api}/almacen/requerimientos`); }
  atencionesAlmacen() { return this.http.get(`${this.api}/almacen/atenciones`); }
  atenderRequerimiento(idRequerimiento: number, observaciones?: string) { return this.http.post(`${this.api}/almacen/atenciones`, { idRequerimiento, observaciones }); }

  proveedores() { return this.http.get(`${this.api}/administracion/proveedores`); }
  crearProveedor(datos: unknown) { return this.http.post(`${this.api}/administracion/proveedores`, datos); }
  solicitudesCompra() { return this.http.get(`${this.api}/administracion/solicitudes-compra`); }
  crearSolicitudCompra(datos: unknown) { return this.http.post(`${this.api}/administracion/solicitudes-compra`, datos); }
  crearCotizacion(datos: unknown) { return this.http.post(`${this.api}/administracion/cotizaciones`, datos); }
  aceptarCotizacion(id: number) { return this.http.patch(`${this.api}/administracion/cotizaciones/${id}/aceptar`, {}); }
  ordenesCompra() { return this.http.get(`${this.api}/administracion/ordenes-compra`); }
  crearOrdenCompra(datos: unknown) { return this.http.post(`${this.api}/administracion/ordenes-compra`, datos); }
  agregarDetalleCompra(id: number, datos: unknown) { return this.http.post(`${this.api}/administracion/ordenes-compra/${id}/detalles`, datos); }
  recibirOrdenCompra(id: number) { return this.http.patch(`${this.api}/administracion/ordenes-compra/${id}/recibir`, {}); }

  checklists() { return this.http.get(`${this.api}/calidad/checklists`); }
  criteriosChecklist(id: number) { return this.http.get(`${this.api}/calidad/checklists/${id}/criterios`); }
  evaluaciones() { return this.http.get(`${this.api}/calidad/evaluaciones`); }
  evaluarCalidad(datos: EvaluacionCalidadApi) { return this.http.post(`${this.api}/calidad/evaluaciones`, datos); }
  notificarPago(datos: unknown) { return this.http.post(`${this.api}/entrega-pago/pagos/notificacion`, datos); }
  pagos() { return this.http.get(`${this.api}/entrega-pago/pagos`); }
  emitirComprobante(datos: unknown) { return this.http.post(`${this.api}/entrega-pago/comprobantes`, datos); }
  comprobantes() { return this.http.get(`${this.api}/entrega-pago/comprobantes`); }
  entregarEquipo(datos: unknown) { return this.http.post(`${this.api}/entrega-pago/entregas`, datos); }
  entregas() { return this.http.get(`${this.api}/entrega-pago/entregas`); }
}
