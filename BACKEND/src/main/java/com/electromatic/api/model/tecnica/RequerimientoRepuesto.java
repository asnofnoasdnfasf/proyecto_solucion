package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "requerimiento_repuesto")
public class RequerimientoRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requerimiento", nullable = false)
    private Long idRequerimiento;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_usuario_solicita", nullable = false)
    private Long idUsuarioSolicita;
    @Column(name = "codigo_requerimiento", nullable = false, length = 20)
    private String codigoRequerimiento;
    @Column(name = "fecha_solicitud", nullable = false)
    private java.time.LocalDateTime fechaSolicitud;
    @Column(name = "prioridad", nullable = false, length = 10)
    private String prioridad;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    public Long getIdRequerimiento() { return idRequerimiento; }
    public void setIdRequerimiento(Long value) { this.idRequerimiento = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdUsuarioSolicita() { return idUsuarioSolicita; }
    public void setIdUsuarioSolicita(Long value) { this.idUsuarioSolicita = value; }
    public String getCodigoRequerimiento() { return codigoRequerimiento; }
    public void setCodigoRequerimiento(String value) { this.codigoRequerimiento = value; }
    public java.time.LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(java.time.LocalDateTime value) { this.fechaSolicitud = value; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String value) { this.prioridad = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
