package com.electromatic.api.model.entrega;

import jakarta.persistence.*;

@Entity
@Table(name = "comprobante_fiscal")
public class ComprobanteFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante", nullable = false)
    private Long idComprobante;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_intento")
    private Long idIntento;
    @Column(name = "tipo_comprobante", nullable = false, length = 15)
    private String tipoComprobante;
    @Column(name = "identificador", nullable = false, length = 30)
    private String identificador;
    @Column(name = "fecha_emision", nullable = false)
    private java.time.LocalDateTime fechaEmision;
    @Column(name = "importe", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal importe;
    @Column(name = "estado_emision", nullable = false, length = 12)
    private String estadoEmision;
    public Long getIdComprobante() { return idComprobante; }
    public void setIdComprobante(Long value) { this.idComprobante = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdIntento() { return idIntento; }
    public void setIdIntento(Long value) { this.idIntento = value; }
    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String value) { this.tipoComprobante = value; }
    public String getIdentificador() { return identificador; }
    public void setIdentificador(String value) { this.identificador = value; }
    public java.time.LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(java.time.LocalDateTime value) { this.fechaEmision = value; }
    public java.math.BigDecimal getImporte() { return importe; }
    public void setImporte(java.math.BigDecimal value) { this.importe = value; }
    public String getEstadoEmision() { return estadoEmision; }
    public void setEstadoEmision(String value) { this.estadoEmision = value; }
}
