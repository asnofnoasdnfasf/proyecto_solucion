package com.electromatic.api.repository.entrega;

import com.electromatic.api.model.entrega.ConceptoCobro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptoCobroRepository extends JpaRepository<ConceptoCobro, Long> {
    @org.springframework.data.jpa.repository.Query(value="SELECT COALESCE(SUM(total),0) FROM concepto_cobro WHERE id_orden_servicio=:orden",nativeQuery=true) java.math.BigDecimal totalOrden(@org.springframework.data.repository.query.Param("orden") Long orden);
}
