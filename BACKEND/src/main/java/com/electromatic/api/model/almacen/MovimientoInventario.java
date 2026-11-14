package com.electromatic.api.model.almacen;

import jakarta.persistence.*;

@Entity
@Table(name = "movimiento_inventario")
public class MovimientoInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento", nullable = false)
    private Long idMovimiento;
    @Column(name = "id_material", nullable = false)
    private Long idMaterial;
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Column(name = "id_detalle_atencion")
    private Long idDetalleAtencion;
    @Column(name = "id_detalle_compra")
    private Long idDetalleCompra;
    @Column(name = "tipo_movimiento", nullable = false, length = 10)
    private String tipoMovimiento;
    @Column(name = "cantidad", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal cantidad;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "justificacion")
    private String justificacion;
    public Long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Long value) { this.idMovimiento = value; }
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long value) { this.idMaterial = value; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long value) { this.idUsuario = value; }
    public Long getIdDetalleAtencion() { return idDetalleAtencion; }
    public void setIdDetalleAtencion(Long value) { this.idDetalleAtencion = value; }
    public Long getIdDetalleCompra() { return idDetalleCompra; }
    public void setIdDetalleCompra(Long value) { this.idDetalleCompra = value; }
    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String value) { this.tipoMovimiento = value; }
    public java.math.BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(java.math.BigDecimal value) { this.cantidad = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String value) { this.justificacion = value; }
}
