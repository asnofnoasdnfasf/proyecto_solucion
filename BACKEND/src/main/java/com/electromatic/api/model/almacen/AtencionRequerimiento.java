package com.electromatic.api.model.almacen;

import jakarta.persistence.*;

@Entity
@Table(name = "atencion_requerimiento")
public class AtencionRequerimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion", nullable = false)
    private Long idAtencion;
    @Column(name = "id_requerimiento", nullable = false)
    private Long idRequerimiento;
    @Column(name = "id_usuario_atiende", nullable = false)
    private Long idUsuarioAtiende;
    @Column(name = "fecha_atencion", nullable = false)
    private java.time.LocalDateTime fechaAtencion;
    @Column(name = "estado", nullable = false, length = 12)
    private String estado;
    @Column(name = "observaciones")
    private String observaciones;
    public Long getIdAtencion() { return idAtencion; }
    public void setIdAtencion(Long value) { this.idAtencion = value; }
    public Long getIdRequerimiento() { return idRequerimiento; }
    public void setIdRequerimiento(Long value) { this.idRequerimiento = value; }
    public Long getIdUsuarioAtiende() { return idUsuarioAtiende; }
    public void setIdUsuarioAtiende(Long value) { this.idUsuarioAtiende = value; }
    public java.time.LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(java.time.LocalDateTime value) { this.fechaAtencion = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
