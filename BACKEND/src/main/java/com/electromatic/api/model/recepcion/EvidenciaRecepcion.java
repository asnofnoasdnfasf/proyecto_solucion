package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "evidencia_recepcion")
public class EvidenciaRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia", nullable = false)
    private Long idEvidencia;
    @Column(name = "id_orden_servicio", nullable = false)
    private Long idOrdenServicio;
    @Column(name = "nombre_archivo", nullable = false, length = 200)
    private String nombreArchivo;
    @Column(name = "ruta_archivo", nullable = false, length = 400)
    private String rutaArchivo;
    @Column(name = "tipo_archivo", nullable = false, length = 30)
    private String tipoArchivo;
    @Column(name = "fecha_registro", nullable = false)
    private java.time.LocalDateTime fechaRegistro;
    public Long getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Long value) { this.idEvidencia = value; }
    public Long getIdOrdenServicio() { return idOrdenServicio; }
    public void setIdOrdenServicio(Long value) { this.idOrdenServicio = value; }
    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String value) { this.nombreArchivo = value; }
    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String value) { this.rutaArchivo = value; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String value) { this.tipoArchivo = value; }
    public java.time.LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.time.LocalDateTime value) { this.fechaRegistro = value; }
}
