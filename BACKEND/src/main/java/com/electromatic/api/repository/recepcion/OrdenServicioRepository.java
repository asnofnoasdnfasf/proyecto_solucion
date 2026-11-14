package com.electromatic.api.repository.recepcion;

import com.electromatic.api.model.recepcion.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    boolean existsByCodigoSeguimiento(String codigoSeguimiento);
    java.util.Optional<OrdenServicio> findFirstByIdEquipoAndIdEstadoOrdenOrderByIdOrdenServicioDesc(Long idEquipo, Short idEstadoOrden);
}
