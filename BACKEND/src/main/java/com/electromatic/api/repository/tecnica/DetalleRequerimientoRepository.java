package com.electromatic.api.repository.tecnica;

import com.electromatic.api.model.tecnica.DetalleRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRequerimientoRepository extends JpaRepository<DetalleRequerimiento, Long> { java.util.List<DetalleRequerimiento> findByIdRequerimiento(Long idRequerimiento); }
