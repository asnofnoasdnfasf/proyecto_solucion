package com.electromatic.api.repository.tecnica;

import com.electromatic.api.model.tecnica.PruebaFuncional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PruebaFuncionalRepository extends JpaRepository<PruebaFuncional, Long> { boolean existsByIdMantenimientoAndNombrePruebaAndResultado(Long idMantenimiento, String nombrePrueba, String resultado); }
