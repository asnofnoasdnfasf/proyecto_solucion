package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "rol_permiso")
@IdClass(RolPermisoId.class)
public class RolPermiso {
    @Id
    @Column(name = "id_rol", nullable = false)
    private Long idRol;
    @Id
    @Column(name = "id_permiso", nullable = false)
    private Long idPermiso;
    public Long getIdRol() { return idRol; }
    public void setIdRol(Long value) { this.idRol = value; }
    public Long getIdPermiso() { return idPermiso; }
    public void setIdPermiso(Long value) { this.idPermiso = value; }
}
