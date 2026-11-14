package com.electromatic.api.repository.recepcion;

import com.electromatic.api.model.recepcion.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Short> {}
