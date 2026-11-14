package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_rol")
@IdClass(UsuarioRolId.class)
public class UsuarioRol {
    @Id
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Id
    @Column(name = "id_rol", nullable = false)
    private Long idRol;
    @Column(name = "fecha_asignacion", nullable = false)
    private java.time.LocalDateTime fechaAsignacion;
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long value) { this.idUsuario = value; }
    public Long getIdRol() { return idRol; }
    public void setIdRol(Long value) { this.idRol = value; }
    public java.time.LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(java.time.LocalDateTime value) { this.fechaAsignacion = value; }
}
