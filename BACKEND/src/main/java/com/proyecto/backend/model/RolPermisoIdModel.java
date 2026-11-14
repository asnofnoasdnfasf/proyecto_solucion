package com.proyecto.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolPermisoIdModel implements Serializable {

    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "id_permiso")
    private Long idPermiso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof RolPermisoIdModel that)) {
            return false;
        }

        return Objects.equals(idRol, that.idRol)
                && Objects.equals(idPermiso, that.idPermiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol, idPermiso);
    }
}