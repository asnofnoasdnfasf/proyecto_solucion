package com.electromatic.api.model.calidad;

import jakarta.persistence.*;

@Entity
@Table(name = "checklist_calidad")
public class ChecklistCalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_checklist", nullable = false)
    private Long idChecklist;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "version", nullable = false, length = 10)
    private String version;
    @Column(name = "estado", nullable = false, length = 10)
    private String estado;
    public Long getIdChecklist() { return idChecklist; }
    public void setIdChecklist(Long value) { this.idChecklist = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
