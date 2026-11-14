package com.electromatic.api.model.administracion;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor", nullable = false)
    private Long idProveedor;
    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;
    @Column(name = "documento_fiscal", nullable = false, length = 20)
    private String documentoFiscal;
    @Column(name = "telefono", length = 20)
    private String telefono;
    @Column(name = "correo", length = 150)
    private String correo;
    @Column(name = "direccion", length = 250)
    private String direccion;
    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long value) { this.idProveedor = value; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String value) { this.razonSocial = value; }
    public String getDocumentoFiscal() { return documentoFiscal; }
    public void setDocumentoFiscal(String value) { this.documentoFiscal = value; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String value) { this.telefono = value; }
    public String getCorreo() { return correo; }
    public void setCorreo(String value) { this.correo = value; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String value) { this.direccion = value; }
}
