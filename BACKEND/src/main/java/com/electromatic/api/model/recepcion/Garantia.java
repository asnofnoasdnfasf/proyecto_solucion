package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "garantia")
public class Garantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_garantia", nullable = false)
    private Long idGarantia;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "fecha_inicio", nullable = false)
    private java.time.LocalDate fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private java.time.LocalDate fechaFin;
    @Column(name = "cobertura", nullable = false, length = 250)
    private String cobertura;
    @Column(name = "condiciones")
    private String condiciones;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    public Long getIdGarantia() { return idGarantia; }
    public void setIdGarantia(Long value) { this.idGarantia = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public java.time.LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(java.time.LocalDate value) { this.fechaInicio = value; }
    public java.time.LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(java.time.LocalDate value) { this.fechaFin = value; }
    public String getCobertura() { return cobertura; }
    public void setCobertura(String value) { this.cobertura = value; }
    public String getCondiciones() { return condiciones; }
    public void setCondiciones(String value) { this.condiciones = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
