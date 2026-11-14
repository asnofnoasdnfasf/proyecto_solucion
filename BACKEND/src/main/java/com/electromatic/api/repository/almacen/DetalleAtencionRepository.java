package com.electromatic.api.repository.almacen;

import com.electromatic.api.model.almacen.DetalleAtencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleAtencionRepository extends JpaRepository<DetalleAtencion, Long> {
    @org.springframework.data.jpa.repository.Query("select coalesce(sum(d.cantidadEntregada),0) from DetalleAtencion d where d.idDetalleRequerimiento=:id") java.math.BigDecimal totalEntregado(@org.springframework.data.repository.query.Param("id") Long idDetalleRequerimiento);
}
