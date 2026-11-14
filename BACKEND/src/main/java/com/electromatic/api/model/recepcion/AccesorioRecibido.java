package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "accesorio_recibido")
public class AccesorioRecibido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accesorio", nullable = false)
    private Long idAccesorio;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    public Long getIdAccesorio() { return idAccesorio; }
    public void setIdAccesorio(Long value) { this.idAccesorio = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer value) { this.cantidad = value; }
}
