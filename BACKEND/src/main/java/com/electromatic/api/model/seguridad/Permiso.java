package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "permiso")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso", nullable = false)
    private Long idPermiso;
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    public Long getIdPermiso() { return idPermiso; }
    public void setIdPermiso(Long value) { this.idPermiso = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
}
