package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_orden")
public class HistorialOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial", nullable = false)
    private Long idHistorial;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "accion", nullable = false, length = 80)
    private String accion;
    @Column(name = "id_estado_anterior")
    private Short idEstadoAnterior;
    @Column(name = "id_estado_nuevo", nullable = false)
    private Short idEstadoNuevo;
    @Column(name = "detalle")
    private String detalle;
    public Long getIdHistorial() { return idHistorial; }
    public void setIdHistorial(Long value) { this.idHistorial = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long value) { this.idUsuario = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public String getAccion() { return accion; }
    public void setAccion(String value) { this.accion = value; }
    public Short getIdEstadoAnterior() { return idEstadoAnterior; }
    public void setIdEstadoAnterior(Short value) { this.idEstadoAnterior = value; }
    public Short getIdEstadoNuevo() { return idEstadoNuevo; }
    public void setIdEstadoNuevo(Short value) { this.idEstadoNuevo = value; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String value) { this.detalle = value; }
}
