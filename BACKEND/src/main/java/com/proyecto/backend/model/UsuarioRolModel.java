package com.proyecto.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "usuario_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRolModel {

    @EmbeddedId
    private UsuarioRolidModel id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", nullable = false)
    private usuarioModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol", nullable = false)
    private RolModel rol;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion;

    @PrePersist
    public void prePersist() {
        if (fechaAsignacion == null) {
            fechaAsignacion = LocalDateTime.now();
        }
    }
}
