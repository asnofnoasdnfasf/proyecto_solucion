package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo", nullable = false)
    private Long idEquipo;
    @Column(name = "id_tipo_equipo", nullable = false)
    private Long idTipoEquipo;
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    @Column(name = "marca", nullable = false, length = 60)
    private String marca;
    @Column(name = "modelo", nullable = false, length = 80)
    private String modelo;
    @Column(name = "numero_serie", length = 80)
    private String numeroSerie;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long value) { this.idEquipo = value; }
    public Long getIdTipoEquipo() { return idTipoEquipo; }
    public void setIdTipoEquipo(Long value) { this.idTipoEquipo = value; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long value) { this.idCliente = value; }
    public String getMarca() { return marca; }
    public void setMarca(String value) { this.marca = value; }
    public String getModelo() { return modelo; }
    public void setModelo(String value) { this.modelo = value; }
    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String value) { this.numeroSerie = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
}
