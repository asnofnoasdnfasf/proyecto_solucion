package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_orden")
public class EstadoOrden {
    @Id
    @Column(name = "id_estado_orden", nullable = false)
    private Short idEstadoOrden;
    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;
    public Short getIdEstadoOrden() { return idEstadoOrden; }
    public void setIdEstadoOrden(Short value) { this.idEstadoOrden = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
}
