package com.electromatic.api.model.administracion;

import jakarta.persistence.*;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion", nullable = false)
    private Long idCotizacion;
    @Column(name = "id_solicitud_compra", nullable = false)
    private Long idSolicitudCompra;
    @Column(name = "id_proveedor", nullable = false)
    private Long idProveedor;
    @Column(name = "fecha_cotizacion", nullable = false)
    private java.time.LocalDate fechaCotizacion;
    @Column(name = "estado", nullable = false, length = 12)
    private String estado;
    public Long getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(Long value) { this.idCotizacion = value; }
    public Long getIdSolicitudCompra() { return idSolicitudCompra; }
    public void setIdSolicitudCompra(Long value) { this.idSolicitudCompra = value; }
    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long value) { this.idProveedor = value; }
    public java.time.LocalDate getFechaCotizacion() { return fechaCotizacion; }
    public void setFechaCotizacion(java.time.LocalDate value) { this.fechaCotizacion = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
