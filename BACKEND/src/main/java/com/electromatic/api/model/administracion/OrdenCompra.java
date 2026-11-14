package com.electromatic.api.model.administracion;

import jakarta.persistence.*;

@Entity
@Table(name = "orden_compra")
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_compra", nullable = false)
    private Long idOrdenCompra;
    @Column(name = "id_solicitud_compra", nullable = false)
    private Long idSolicitudCompra;
    @Column(name = "id_proveedor", nullable = false)
    private Long idProveedor;
    @Column(name = "fecha_emision", nullable = false)
    private java.time.LocalDate fechaEmision;
    @Column(name = "estado", nullable = false, length = 12)
    private String estado;
    @Column(name = "references")
    private String references;
    public Long getIdOrdenCompra() { return idOrdenCompra; }
    public void setIdOrdenCompra(Long value) { this.idOrdenCompra = value; }
    public Long getIdSolicitudCompra() { return idSolicitudCompra; }
    public void setIdSolicitudCompra(Long value) { this.idSolicitudCompra = value; }
    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long value) { this.idProveedor = value; }
    public java.time.LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(java.time.LocalDate value) { this.fechaEmision = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getReferences() { return references; }
    public void setReferences(String value) { this.references = value; }
}
