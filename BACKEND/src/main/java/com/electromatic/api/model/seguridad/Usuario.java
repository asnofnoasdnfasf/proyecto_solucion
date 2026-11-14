package com.electromatic.api.model.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;
    @Column(name = "correo_usuario", nullable = false, length = 150)
    private String correoUsuario;
    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;
    @Column(name = "clave_hash", nullable = false, length = 255)
    private String claveHash;
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long value) { this.idUsuario = value; }
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String value) { this.correoUsuario = value; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String value) { this.nombreUsuario = value; }
    public String getClaveHash() { return claveHash; }
    public void setClaveHash(String value) { this.claveHash = value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }
}
