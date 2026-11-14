package com.electromatic.api.model.administracion;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_orden_compra")
public class DetalleOrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_compra", nullable = false)
    private Long idDetalleCompra;
    @Column(name = "id_orden_compra", nullable = false)
    private Long idOrdenCompra;
    @Column(name = "id_material", nullable = false)
    private Long idMaterial;
    @Column(name = "cantidad", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal cantidad;
    @Column(name = "costo_unitario", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal costoUnitario;
    public Long getIdDetalleCompra() { return idDetalleCompra; }
    public void setIdDetalleCompra(Long value) { this.idDetalleCompra = value; }
    public Long getIdOrdenCompra() { return idOrdenCompra; }
    public void setIdOrdenCompra(Long value) { this.idOrdenCompra = value; }
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long value) { this.idMaterial = value; }
    public java.math.BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(java.math.BigDecimal value) { this.cantidad = value; }
    public java.math.BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(java.math.BigDecimal value) { this.costoUnitario = value; }
}
