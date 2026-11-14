package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.RolPermiso;
import com.electromatic.api.model.seguridad.RolPermisoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, RolPermisoId> {}
