package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "orden_servicio")
public class OrdenServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    @Column(name = "id_equipo", nullable = false)
    private Long idEquipo;
    @Column(name = "id_estado_orden", nullable = false)
    private Short idEstadoOrden;
    @Column(name = "id_usuario_registra", nullable = false)
    private Long idUsuarioRegistra;
    @Column(name = "codigo_seguimiento", nullable = false, length = 20)
    private String codigoSeguimiento;
    @Column(name = "fecha_recepcion", nullable = false)
    private java.time.LocalDateTime fechaRecepcion;
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long value) { this.idCliente = value; }
    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long value) { this.idEquipo = value; }
    public Short getIdEstadoOrden() { return idEstadoOrden; }
    public void setIdEstadoOrden(Short value) { this.idEstadoOrden = value; }
    public Long getIdUsuarioRegistra() { return idUsuarioRegistra; }
    public void setIdUsuarioRegistra(Long value) { this.idUsuarioRegistra = value; }
    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public void setCodigoSeguimiento(String value) { this.codigoSeguimiento = value; }
    public java.time.LocalDateTime getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(java.time.LocalDateTime value) { this.fechaRecepcion = value; }
}
