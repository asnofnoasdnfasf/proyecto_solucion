package com.electromatic.api.repository.seguridad;

import com.electromatic.api.model.seguridad.RegistroAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroAuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {}
