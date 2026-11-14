package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false)
    private Long idRol;
    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    public Long getIdRol() { return idRol; }
    public void setIdRol(Long value) { this.idRol = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
