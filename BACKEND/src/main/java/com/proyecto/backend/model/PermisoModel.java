package com.proyecto.backend.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(
    name = "permiso",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_permiso_nombre",
            columnNames = "nombre"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long idPermiso;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @OneToMany(mappedBy = "permiso", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RolPermisoModel> roles = new HashSet<>();
}
