package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
