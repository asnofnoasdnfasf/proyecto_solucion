package com.proyecto.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(name = "uq_cliente_documento", columnNames = "documento"))
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;
    @Column(name = "tipo_cliente", nullable = false, length = 10)
    private String tipoCliente = "PERSONA";
    @Column(name = "nombre_razon_social", nullable = false, length = 150)
    private String nombreRazonSocial;
    @Column(name = "documento", nullable = false, length = 20)
    private String documento;
    @Column(name = "telefono", length = 20) private String telefono;
    @Column(name = "correo", length = 150) private String correo;
    @Column(name = "direccion", length = 250) private String direccion;
    @Column(name = "estado", nullable = false, length = 15) private String estado = "ACTIVO";
    public Long getIdCliente(){return idCliente;} public void setIdCliente(Long v){idCliente=v;}
    public String getTipoCliente(){return tipoCliente;} public void setTipoCliente(String v){tipoCliente=v;}
    public String getNombreRazonSocial(){return nombreRazonSocial;} public void setNombreRazonSocial(String v){nombreRazonSocial=v;}
    public String getDocumento(){return documento;} public void setDocumento(String v){documento=v;}
    public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=v;}
    public String getCorreo(){return correo;} public void setCorreo(String v){correo=v;}
    public String getDireccion(){return direccion;} public void setDireccion(String v){direccion=v;}
    public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
}
