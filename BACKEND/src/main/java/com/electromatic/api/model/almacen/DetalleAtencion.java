package com.electromatic.api.model.almacen;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_atencion")
public class DetalleAtencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_atencion", nullable = false)
    private Long idDetalleAtencion;
    @Column(name = "id_atencion", nullable = false)
    private Long idAtencion;
    @Column(name = "id_detalle_requerimiento", nullable = false)
    private Long idDetalleRequerimiento;
    @Column(name = "cantidad_reservada", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal cantidadReservada;
    @Column(name = "cantidad_entregada", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal cantidadEntregada;
    public Long getIdDetalleAtencion() { return idDetalleAtencion; }
    public void setIdDetalleAtencion(Long value) { this.idDetalleAtencion = value; }
    public Long getIdAtencion() { return idAtencion; }
    public void setIdAtencion(Long value) { this.idAtencion = value; }
    public Long getIdDetalleRequerimiento() { return idDetalleRequerimiento; }
    public void setIdDetalleRequerimiento(Long value) { this.idDetalleRequerimiento = value; }
    public java.math.BigDecimal getCantidadReservada() { return cantidadReservada; }
    public void setCantidadReservada(java.math.BigDecimal value) { this.cantidadReservada = value; }
    public java.math.BigDecimal getCantidadEntregada() { return cantidadEntregada; }
    public void setCantidadEntregada(java.math.BigDecimal value) { this.cantidadEntregada = value; }
}
