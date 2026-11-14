package com.electromatic.api.repository.almacen;

import com.electromatic.api.model.almacen.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    @org.springframework.data.jpa.repository.Query(value="SELECT COALESCE(SUM(CASE tipo_movimiento WHEN 'INGRESO' THEN cantidad WHEN 'SALIDA' THEN -cantidad ELSE cantidad END),0) FROM movimiento_inventario WHERE id_material=:material",nativeQuery=true)
    java.math.BigDecimal stockActual(@org.springframework.data.repository.query.Param("material") Long material);
}
