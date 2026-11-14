package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "evidencia_mantenimiento")
public class EvidenciaMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia", nullable = false)
    private Long idEvidencia;
    @Column(name = "id_actividad", nullable = false)
    private Long idActividad;
    @Column(name = "nombre_archivo", nullable = false, length = 200)
    private String nombreArchivo;
    @Column(name = "ruta_archivo", nullable = false, length = 400)
    private String rutaArchivo;
    @Column(name = "tipo_archivo", nullable = false, length = 30)
    private String tipoArchivo;
    @Column(name = "fecha_registro", nullable = false)
    private java.time.LocalDateTime fechaRegistro;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    public Long getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Long value) { this.idEvidencia = value; }
    public Long getIdActividad() { return idActividad; }
    public void setIdActividad(Long value) { this.idActividad = value; }
    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String value) { this.nombreArchivo = value; }
    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String value) { this.rutaArchivo = value; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String value) { this.tipoArchivo = value; }
    public java.time.LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.time.LocalDateTime value) { this.fechaRegistro = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
}
