package com.electromatic.api.model.recepcion;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    @Column(name = "tipo_cliente", nullable = false, length = 10)
    private String tipoCliente;
    @Column(name = "nombre_razon_social", nullable = false, length = 150)
    private String nombreRazonSocial;
    @Column(name = "documento", nullable = false, length = 20)
    private String documento;
    @Column(name = "telefono", length = 20)
    private String telefono;
    @Column(name = "correo", length = 150)
    private String correo;
    @Column(name = "direccion", length = 250)
    private String direccion;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long value) { this.idCliente = value; }
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String value) { this.tipoCliente = value; }
    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public void setNombreRazonSocial(String value) { this.nombreRazonSocial = value; }
    public String getDocumento() { return documento; }
    public void setDocumento(String value) { this.documento = value; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String value) { this.telefono = value; }
    public String getCorreo() { return correo; }
    public void setCorreo(String value) { this.correo = value; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String value) { this.direccion = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
