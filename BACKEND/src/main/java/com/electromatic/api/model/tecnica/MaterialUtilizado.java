package com.electromatic.api.model.tecnica;

import jakarta.persistence.*;

@Entity
@Table(name = "material_utilizado")
public class MaterialUtilizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material_utilizado", nullable = false)
    private Long idMaterialUtilizado;
    @Column(name = "id_mantenimiento", nullable = false)
    private Long idMantenimiento;
    @Column(name = "id_material", nullable = false)
    private Long idMaterial;
    @Column(name = "cantidad_utilizada", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal cantidadUtilizada;
    @Column(name = "fecha_utilizacion", nullable = false)
    private java.time.LocalDateTime fechaUtilizacion;
    public Long getIdMaterialUtilizado() { return idMaterialUtilizado; }
    public void setIdMaterialUtilizado(Long value) { this.idMaterialUtilizado = value; }
    public Long getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(Long value) { this.idMantenimiento = value; }
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long value) { this.idMaterial = value; }
    public java.math.BigDecimal getCantidadUtilizada() { return cantidadUtilizada; }
    public void setCantidadUtilizada(java.math.BigDecimal value) { this.cantidadUtilizada = value; }
    public java.time.LocalDateTime getFechaUtilizacion() { return fechaUtilizacion; }
    public void setFechaUtilizacion(java.time.LocalDateTime value) { this.fechaUtilizacion = value; }
}
