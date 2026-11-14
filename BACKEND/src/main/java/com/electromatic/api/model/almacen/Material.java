package com.electromatic.api.model.almacen;

import jakarta.persistence.*;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material", nullable = false)
    private Long idMaterial;
    @Column(name = "codigo", nullable = false, length = 30)
    private String codigo;
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;
    @Column(name = "unidad_medida", nullable = false, length = 15)
    private String unidadMedida;
    @Column(name = "stock_minimo", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal stockMinimo;
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long value) { this.idMaterial = value; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String value) { this.codigo = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getTipo() { return tipo; }
    public void setTipo(String value) { this.tipo = value; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String value) { this.unidadMedida = value; }
    public java.math.BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(java.math.BigDecimal value) { this.stockMinimo = value; }
}
