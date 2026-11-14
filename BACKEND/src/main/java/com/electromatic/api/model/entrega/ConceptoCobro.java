package com.electromatic.api.model.entrega;

import jakarta.persistence.*;

@Entity
@Table(name = "concepto_cobro")
public class ConceptoCobro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_concepto_cobro", nullable = false)
    private Long idConceptoCobro;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "descripcion", nullable = false, length = 250)
    private String descripcion;
    @Column(name = "importe_base", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal importeBase;
    @Column(name = "descuento", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal descuento;
    @Column(name = "impuesto", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal impuesto;
    @Column(name = "total", precision = 12, scale = 2, insertable = false, updatable = false)
    private java.math.BigDecimal total;
    public Long getIdConceptoCobro() { return idConceptoCobro; }
    public void setIdConceptoCobro(Long value) { this.idConceptoCobro = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public java.math.BigDecimal getImporteBase() { return importeBase; }
    public void setImporteBase(java.math.BigDecimal value) { this.importeBase = value; }
    public java.math.BigDecimal getDescuento() { return descuento; }
    public void setDescuento(java.math.BigDecimal value) { this.descuento = value; }
    public java.math.BigDecimal getImpuesto() { return impuesto; }
    public void setImpuesto(java.math.BigDecimal value) { this.impuesto = value; }
    public java.math.BigDecimal getTotal() { return total; }
    public void setTotal(java.math.BigDecimal value) { this.total = value; }
}
