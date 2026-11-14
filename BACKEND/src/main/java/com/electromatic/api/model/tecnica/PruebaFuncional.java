package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "prueba_funcional")
public class PruebaFuncional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prueba", nullable = false)
    private Long idPrueba;
    @Column(name = "id_mantenimiento", nullable = false)
    private Long idMantenimiento;
    @Column(name = "nombre_prueba", nullable = false, length = 120)
    private String nombrePrueba;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "resultado", length = 12)
    private String resultado;
    @Column(name = "estado", nullable = false, length = 12)
    private String estado;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "fecha_prueba")
    private java.time.LocalDateTime fechaPrueba;
    public Long getIdPrueba() { return idPrueba; }
    public void setIdPrueba(Long value) { this.idPrueba = value; }
    public Long getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(Long value) { this.idMantenimiento = value; }
    public String getNombrePrueba() { return nombrePrueba; }
    public void setNombrePrueba(String value) { this.nombrePrueba = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public String getResultado() { return resultado; }
    public void setResultado(String value) { this.resultado = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public java.time.LocalDateTime getFechaPrueba() { return fechaPrueba; }
    public void setFechaPrueba(java.time.LocalDateTime value) { this.fechaPrueba = value; }
}
