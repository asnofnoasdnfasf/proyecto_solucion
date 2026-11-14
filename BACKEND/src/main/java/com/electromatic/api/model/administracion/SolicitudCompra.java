package com.electromatic.api.model.administracion;

import jakarta.persistence.*;

@Entity
@Table(name = "solicitud_compra")
public class SolicitudCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_compra", nullable = false)
    private Long idSolicitudCompra;
    @Column(name = "id_requerimiento", nullable = false)
    private Long idRequerimiento;
    @Column(name = "id_usuario_solicita", nullable = false)
    private Long idUsuarioSolicita;
    @Column(name = "fecha_solicitud", nullable = false)
    private java.time.LocalDateTime fechaSolicitud;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    @Column(name = "justificacion")
    private String justificacion;
    public Long getIdSolicitudCompra() { return idSolicitudCompra; }
    public void setIdSolicitudCompra(Long value) { this.idSolicitudCompra = value; }
    public Long getIdRequerimiento() { return idRequerimiento; }
    public void setIdRequerimiento(Long value) { this.idRequerimiento = value; }
    public Long getIdUsuarioSolicita() { return idUsuarioSolicita; }
    public void setIdUsuarioSolicita(Long value) { this.idUsuarioSolicita = value; }
    public java.time.LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(java.time.LocalDateTime value) { this.fechaSolicitud = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String value) { this.justificacion = value; }
}
