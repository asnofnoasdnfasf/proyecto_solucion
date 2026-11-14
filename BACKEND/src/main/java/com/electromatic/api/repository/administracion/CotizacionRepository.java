package com.electromatic.api.repository.administracion;

import com.electromatic.api.model.administracion.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> { java.util.Optional<Cotizacion> findByIdSolicitudCompraAndIdProveedor(Long idSolicitudCompra,Long idProveedor); }
