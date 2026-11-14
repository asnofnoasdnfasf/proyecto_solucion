package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnostico")
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diagnostico", nullable = false)
    private Long idDiagnostico;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_usuario_tecnico", nullable = false)
    private Long idUsuarioTecnico;
    @Column(name = "sintomas", nullable = false)
    private String sintomas;
    @Column(name = "causa_probable")
    private String causaProbable;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "recomendaciones")
    private String recomendaciones;
    @Column(name = "clasificacion_mantenimiento", nullable = false, length = 15)
    private String clasificacionMantenimiento;
    @Column(name = "alcance", length = 250)
    private String alcance;
    @Column(name = "fecha_registro", nullable = false)
    private java.time.LocalDateTime fechaRegistro;
    public Long getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(Long value) { this.idDiagnostico = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdUsuarioTecnico() { return idUsuarioTecnico; }
    public void setIdUsuarioTecnico(Long value) { this.idUsuarioTecnico = value; }
    public String getSintomas() { return sintomas; }
    public void setSintomas(String value) { this.sintomas = value; }
    public String getCausaProbable() { return causaProbable; }
    public void setCausaProbable(String value) { this.causaProbable = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String value) { this.recomendaciones = value; }
    public String getClasificacionMantenimiento() { return clasificacionMantenimiento; }
    public void setClasificacionMantenimiento(String value) { this.clasificacionMantenimiento = value; }
    public String getAlcance() { return alcance; }
    public void setAlcance(String value) { this.alcance = value; }
    public java.time.LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.time.LocalDateTime value) { this.fechaRegistro = value; }
}
