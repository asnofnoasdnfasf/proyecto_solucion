package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { java.util.Optional<Usuario> findByNombreUsuario(String nombreUsuario); boolean existsByCorreoUsuario(String correoUsuario); }
