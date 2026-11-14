import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Cliente {
  idCliente?: number;
  nomCliente?: string;
  apeCliente?: string;
  nombreRazonSocial?: string;
  tipoCliente?: 'PERSONA' | 'EMPRESA';
  documento?: string;
  dni?: string;
  correo?: string;
  telefono?: string;
  direccion?: string;
  direcc?: string;
  numTel?: string;
  MARCA?: string;
  MODELO?: string;
  NUMERO_SERIE?: string;
  DESCRIPCION?: string;
  estado?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private readonly apiUrl = `${environment.apiUrl}/clientes`;

  constructor(private http: HttpClient) { }

  crearCliente(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.apiUrl, this.toApi(cliente));
  }

  actualizarCliente(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/${id}`, this.toApi(cliente));
  }

  listar(): Observable<Cliente[]> { return this.http.get<Cliente[]>(this.apiUrl); }

  desactivar(id: number): Observable<Cliente> {
    return this.http.patch<Cliente>(`${this.apiUrl}/${id}/desactivar`, {});
  }

  private toApi(cliente: Cliente) {
    return {
      ...cliente,
      tipoCliente: cliente.tipoCliente ?? 'PERSONA',
      documento: cliente.documento ?? cliente.dni,
      nombreRazonSocial: cliente.nombreRazonSocial ??
        `${cliente.nomCliente ?? ''} ${cliente.apeCliente ?? ''}`.trim(),
      telefono: cliente.telefono ?? cliente.numTel,
      direccion: cliente.direccion ?? cliente.direcc
    };
  }
}
