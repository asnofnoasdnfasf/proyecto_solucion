package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionRepository extends JpaRepository<Sesion, Long> {}
