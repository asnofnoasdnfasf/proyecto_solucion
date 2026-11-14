package com.electromatic.api.model.calidad;

import jakarta.persistence.*;

@Entity
@Table(name = "evaluacion_calidad")
public class EvaluacionCalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion", nullable = false)
    private Long idEvaluacion;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_checklist", nullable = false)
    private Long idChecklist;
    @Column(name = "id_usuario_evalua", nullable = false)
    private Long idUsuarioEvalua;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "resultado", length = 12)
    private String resultado;
    @Column(name = "estado", nullable = false, length = 12)
    private String estado;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "motivo_rechazo")
    private String motivoRechazo;
    public Long getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(Long value) { this.idEvaluacion = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdChecklist() { return idChecklist; }
    public void setIdChecklist(Long value) { this.idChecklist = value; }
    public Long getIdUsuarioEvalua() { return idUsuarioEvalua; }
    public void setIdUsuarioEvalua(Long value) { this.idUsuarioEvalua = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public String getResultado() { return resultado; }
    public void setResultado(String value) { this.resultado = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public void setMotivoRechazo(String value) { this.motivoRechazo = value; }
}
