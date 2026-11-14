package com.proyecto.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol_permiso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermisoModel {

    @EmbeddedId
    private RolPermisoIdModel id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol", nullable = false)
    private RolModel rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPermiso")
    @JoinColumn(name = "id_permiso", nullable = false)
    private PermisoModel permiso;
}