package com.electromatic.api.model.entrega;

import jakarta.persistence.*;

@Entity
@Table(name = "intento_pago")
public class IntentoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_intento", nullable = false)
    private Long idIntento;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "identificador_unico", nullable = false, length = 60)
    private String identificadorUnico;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal monto;
    @Column(name = "moneda", nullable = false, length = 3)
    private String moneda;
    @Column(name = "estado", nullable = false, length = 12)
    private String estado;
    @Column(name = "respuesta_pago", length = 250)
    private String respuestaPago;
    @Column(name = "respuesta_proveedor")
    private String respuestaProveedor;
    public Long getIdIntento() { return idIntento; }
    public void setIdIntento(Long value) { this.idIntento = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public String getIdentificadorUnico() { return identificadorUnico; }
    public void setIdentificadorUnico(String value) { this.identificadorUnico = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public java.math.BigDecimal getMonto() { return monto; }
    public void setMonto(java.math.BigDecimal value) { this.monto = value; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String value) { this.moneda = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getRespuestaPago() { return respuestaPago; }
    public void setRespuestaPago(String value) { this.respuestaPago = value; }
    public String getRespuestaProveedor() { return respuestaProveedor; }
    public void setRespuestaProveedor(String value) { this.respuestaProveedor = value; }
}
