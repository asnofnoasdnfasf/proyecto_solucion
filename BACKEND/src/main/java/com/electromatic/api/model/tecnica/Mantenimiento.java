package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "mantenimiento")
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento", nullable = false)
    private Long idMantenimiento;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_usuario_tecnico", nullable = false)
    private Long idUsuarioTecnico;
    @Column(name = "fecha_inicio", nullable = false)
    private java.time.LocalDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private java.time.LocalDateTime fechaFin;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    @Column(name = "observaciones")
    private String observaciones;
    public Long getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(Long value) { this.idMantenimiento = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdUsuarioTecnico() { return idUsuarioTecnico; }
    public void setIdUsuarioTecnico(Long value) { this.idUsuarioTecnico = value; }
    public java.time.LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(java.time.LocalDateTime value) { this.fechaInicio = value; }
    public java.time.LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(java.time.LocalDateTime value) { this.fechaFin = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
