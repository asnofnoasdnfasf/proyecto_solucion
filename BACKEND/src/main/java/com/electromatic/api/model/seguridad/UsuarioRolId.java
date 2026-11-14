package com.electromatic.api.model.seguridad;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioRolId implements Serializable {
    public Long idUsuario;
    public Long idRol;
    public UsuarioRolId() {}
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof UsuarioRolId that)) return false; return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idRol, that.idRol); }
    @Override public int hashCode() { return Objects.hash(idUsuario, idRol); }
}
