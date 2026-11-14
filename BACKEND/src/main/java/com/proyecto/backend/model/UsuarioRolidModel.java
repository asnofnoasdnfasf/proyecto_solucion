package com.proyecto.backend.model;
import lombok.*;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRolidModel implements Serializable {
  
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_rol")
    private Long idRol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UsuarioRolidModel that)) {
            return false;
        }

        return Objects.equals(idUsuario, that.idUsuario)
                && Objects.equals(idRol, that.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idRol);
    }


}
