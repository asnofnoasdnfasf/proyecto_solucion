package com.electromatic.api.repository.calidad;

import com.electromatic.api.model.calidad.CriterioCalidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriterioCalidadRepository extends JpaRepository<CriterioCalidad, Long> { java.util.List<CriterioCalidad> findByIdChecklist(Long idChecklist); }
