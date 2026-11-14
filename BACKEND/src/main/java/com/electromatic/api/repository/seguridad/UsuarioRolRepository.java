package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.UsuarioRol;
import com.electromatic.api.model.seguridad.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {}
