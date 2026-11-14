package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "sesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion", nullable = false)
    private Long idSesion;
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Column(name = "fecha_inicio", nullable = false)
    private java.time.LocalDateTime fechaInicio;
    @Column(name = "fecha_expiracion", nullable = false)
    private java.time.LocalDateTime fechaExpiracion;
    @Column(name = "fecha_cierre")
    private java.time.LocalDateTime fechaCierre;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    public Long getIdSesion() { return idSesion; }
    public void setIdSesion(Long value) { this.idSesion = value; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long value) { this.idUsuario = value; }
    public java.time.LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(java.time.LocalDateTime value) { this.fechaInicio = value; }
    public java.time.LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(java.time.LocalDateTime value) { this.fechaExpiracion = value; }
    public java.time.LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(java.time.LocalDateTime value) { this.fechaCierre = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
