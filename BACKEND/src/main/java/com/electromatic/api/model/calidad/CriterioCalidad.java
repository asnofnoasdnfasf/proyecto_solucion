package com.electromatic.api.model.calidad;

import jakarta.persistence.*;

@Entity
@Table(name = "criterio_calidad")
public class CriterioCalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_criterio", nullable = false)
    private Long idCriterio;
    @Column(name = "id_checklist", nullable = false)
    private Long idChecklist;
    @Column(name = "descripcion", nullable = false, length = 250)
    private String descripcion;
    @Column(name = "obligatorio", nullable = false)
    private Boolean obligatorio;
    public Long getIdCriterio() { return idCriterio; }
    public void setIdCriterio(Long value) { this.idCriterio = value; }
    public Long getIdChecklist() { return idChecklist; }
    public void setIdChecklist(Long value) { this.idChecklist = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }
    public Boolean getObligatorio() { return obligatorio; }
    public void setObligatorio(Boolean value) { this.obligatorio = value; }
}
