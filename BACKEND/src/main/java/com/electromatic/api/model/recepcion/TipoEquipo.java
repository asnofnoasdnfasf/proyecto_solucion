package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_equipo")
public class TipoEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_equipo", nullable = false)
    private Long idTipoEquipo;
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    public Long getIdTipoEquipo() { return idTipoEquipo; }
    public void setIdTipoEquipo(Long value) { this.idTipoEquipo = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
}
