package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "registro_auditoria")
public class RegistroAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento", nullable = false)
    private Long idEvento;
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "accion", nullable = false, length = 80)
    private String accion;
    @Column(name = "resultado", nullable = false, length = 10)
    private String resultado;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "entidad", length = 80)
    private String entidad;
    @Column(name = "id_registro_afectado")
    private Long idRegistroAfectado;
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long value) { this.idEvento = value; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long value) { this.idUsuario = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public String getAccion() { return accion; }
    public void setAccion(String value) { this.accion = value; }
    public String getResultado() { return resultado; }
    public void setResultado(String value) { this.resultado = value; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String value) { this.detalle = value; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String value) { this.entidad = value; }
    public Long getIdRegistroAfectado() { return idRegistroAfectado; }
    public void setIdRegistroAfectado(Long value) { this.idRegistroAfectado = value; }
}
