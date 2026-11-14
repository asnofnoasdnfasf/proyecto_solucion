package com.electromatic.api.model.entrega;

import jakarta.persistence.*;

@Entity
@Table(name = "entrega_equipo")
public class EntregaEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega", nullable = false)
    private Long idEntrega;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "id_usuario_entrega", nullable = false)
    private Long idUsuarioEntrega;
    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;
    @Column(name = "persona_recibe", nullable = false, length = 120)
    private String personaRecibe;
    @Column(name = "documento_receptor", nullable = false, length = 20)
    private String documentoReceptor;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "motivo_excepcion")
    private String motivoExcepcion;
    public Long getIdEntrega() { return idEntrega; }
    public void setIdEntrega(Long value) { this.idEntrega = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public Long getIdUsuarioEntrega() { return idUsuarioEntrega; }
    public void setIdUsuarioEntrega(Long value) { this.idUsuarioEntrega = value; }
    public java.time.LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(java.time.LocalDateTime value) { this.fechaHora = value; }
    public String getPersonaRecibe() { return personaRecibe; }
    public void setPersonaRecibe(String value) { this.personaRecibe = value; }
    public String getDocumentoReceptor() { return documentoReceptor; }
    public void setDocumentoReceptor(String value) { this.documentoReceptor = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public String getMotivoExcepcion() { return motivoExcepcion; }
    public void setMotivoExcepcion(String value) { this.motivoExcepcion = value; }
}
