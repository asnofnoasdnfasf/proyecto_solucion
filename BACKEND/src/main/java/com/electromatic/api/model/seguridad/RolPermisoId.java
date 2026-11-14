package com.electromatic.api.model.seguridad;

import java.io.Serializable;
import java.util.Objects;

public class RolPermisoId implements Serializable {
    public Long idRol;
    public Long idPermiso;
    public RolPermisoId() {}
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof RolPermisoId that)) return false; return Objects.equals(idRol, that.idRol) && Objects.equals(idPermiso, that.idPermiso); }
    @Override public int hashCode() { return Objects.hash(idRol, idPermiso); }
}
