package com.proyecto.backend.model;
import com.proyecto.backend.model.enums.EstadoRol;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "rol",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_rol_nombre",
            columnNames = "nombre"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolModel {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    @Builder.Default
    private EstadoRol estado = EstadoRol.ACTIVO;

    // Relación con usuario_rol
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RolModel> usuarios = new HashSet<>();
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
@Builder.Default
private Set<RolPermisoModel> permisos = new HashSet<>();
}
