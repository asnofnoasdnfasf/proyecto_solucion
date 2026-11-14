package com.proyecto.backend.model;
import com.proyecto.backend.model.enums.EstadoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(
    name = "usuario",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_usuario_correo",
            columnNames = "correo_usuario"
        ),
        @UniqueConstraint(
            name = "uq_usuario_nombre",
            columnNames = "nombre_usuario"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class usuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "correo_usuario", nullable = false, length = 150)
    private String correoUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;

    @Column(name = "clave_hash", nullable = false, length = 255)
    private String claveHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    @Builder.Default
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    // Relación con usuario_rol
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<usuarioModel> roles = new HashSet<>();
@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
@Builder.Default
private Set<SesionModel> sesiones = new HashSet<>();
}

