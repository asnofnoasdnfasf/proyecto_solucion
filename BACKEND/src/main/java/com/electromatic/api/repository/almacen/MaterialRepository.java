package com.electromatic.api.repository.almacen;

import com.electromatic.api.model.almacen.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    boolean existsByCodigo(String codigo);
    @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select m from Material m where m.idMaterial = :id") java.util.Optional<Material> bloquearPorId(@Param("id") Long id);
}
