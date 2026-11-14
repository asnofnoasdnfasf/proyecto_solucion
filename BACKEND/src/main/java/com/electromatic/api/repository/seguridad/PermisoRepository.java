package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {}
