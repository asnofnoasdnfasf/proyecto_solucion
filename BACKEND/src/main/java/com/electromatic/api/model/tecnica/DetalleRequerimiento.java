package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_requerimiento")
public class DetalleRequerimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle", nullable = false)
    private Long idDetalle;
    @Column(name = "id_requerimiento", nullable = false)
    private Long idRequerimiento;
    @Column(name = "id_material", nullable = false)
    private Long idMaterial;
    @Column(name = "cantidad_solicitada", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal cantidadSolicitada;
    @Column(name = "justificacion_tecnica")
    private String justificacionTecnica;
    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long value) { this.idDetalle = value; }
    public Long getIdRequerimiento() { return idRequerimiento; }
    public void setIdRequerimiento(Long value) { this.idRequerimiento = value; }
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long value) { this.idMaterial = value; }
    public java.math.BigDecimal getCantidadSolicitada() { return cantidadSolicitada; }
    public void setCantidadSolicitada(java.math.BigDecimal value) { this.cantidadSolicitada = value; }
    public String getJustificacionTecnica() { return justificacionTecnica; }
    public void setJustificacionTecnica(String value) { this.justificacionTecnica = value; }
}
