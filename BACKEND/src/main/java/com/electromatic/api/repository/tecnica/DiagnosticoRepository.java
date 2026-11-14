package com.electromatic.api.repository.tecnica;

import com.electromatic.api.model.tecnica.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> { boolean existsByIdOrdenServicio(Long idOrdenServicio); }
