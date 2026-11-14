package com.electromatic.api.model.calidad;

import jakarta.persistence.*;

@Entity
@Table(name = "resultado_criterio")
public class ResultadoCriterio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado", nullable = false)
    private Long idResultado;
    @Column(name = "id_evaluacion", nullable = false)
    private Long idEvaluacion;
    @Column(name = "id_criterio", nullable = false)
    private Long idCriterio;
    @Column(name = "resultado", nullable = false, length = 10)
    private String resultado;
    @Column(name = "observaciones")
    private String observaciones;
    public Long getIdResultado() { return idResultado; }
    public void setIdResultado(Long value) { this.idResultado = value; }
    public Long getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(Long value) { this.idEvaluacion = value; }
    public Long getIdCriterio() { return idCriterio; }
    public void setIdCriterio(Long value) { this.idCriterio = value; }
    public String getResultado() { return resultado; }
    public void setResultado(String value) { this.resultado = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
