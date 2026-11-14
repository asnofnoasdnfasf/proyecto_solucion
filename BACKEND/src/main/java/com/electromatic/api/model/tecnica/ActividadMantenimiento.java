package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "actividad_mantenimiento")
public class ActividadMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private Long idActividad;
    @Column(name = "id_mantenimiento", nullable = false)
    private Long idMantenimiento;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "fecha_fin")
    private java.time.LocalDateTime fechaFin;
    @Column(name = "tiempo_empleado")
    private java.time.LocalTime tiempoEmpleado;
    @Column(name = "observaciones")
    private String observaciones;
    public Long getIdActividad() { return idActividad; }
    public void setIdActividad(Long value) { this.idActividad = value; }
    public Long getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(Long value) { this.idMantenimiento = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public java.time.LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(java.time.LocalDateTime value) { this.fechaFin = value; }
    public java.time.LocalTime getTiempoEmpleado() { return tiempoEmpleado; }
    public void setTiempoEmpleado(java.time.LocalTime value) { this.tiempoEmpleado = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
