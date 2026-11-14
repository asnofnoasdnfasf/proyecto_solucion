package com.electromatic.api.repository.recepcion;

import com.electromatic.api.model.recepcion.HistorialOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialOrdenRepository extends JpaRepository<HistorialOrden, Long> { java.util.List<HistorialOrden> findByIdOrdenServicioOrderByFechaHoraAsc(Long idOrdenServicio); }
