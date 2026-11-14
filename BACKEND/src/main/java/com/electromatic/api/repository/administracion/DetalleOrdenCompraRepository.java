package com.electromatic.api.repository.administracion;

import com.electromatic.api.model.administracion.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Long> { java.util.List<DetalleOrdenCompra> findByIdOrdenCompra(Long idOrdenCompra); }
