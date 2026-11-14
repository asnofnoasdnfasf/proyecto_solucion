package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "inspeccion_recepcion")
public class InspeccionRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspeccion", nullable = false)
    private Long idInspeccion;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "condicion_fisica", nullable = false, length = 250)
    private String condicionFisica;
    @Column(name = "danos_visibles", length = 250)
    private String danosVisibles;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "fecha_inspeccion", nullable = false)
    private java.time.LocalDateTime fechaInspeccion;
    public Long getIdInspeccion() { return idInspeccion; }
    public void setIdInspeccion(Long value) { this.idInspeccion = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public String getCondicionFisica() { return condicionFisica; }
    public void setCondicionFisica(String value) { this.condicionFisica = value; }
    public String getDanosVisibles() { return danosVisibles; }
    public void setDanosVisibles(String value) { this.danosVisibles = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public java.time.LocalDateTime getFechaInspeccion() { return fechaInspeccion; }
    public void setFechaInspeccion(java.time.LocalDateTime value) { this.fechaInspeccion = value; }
}
