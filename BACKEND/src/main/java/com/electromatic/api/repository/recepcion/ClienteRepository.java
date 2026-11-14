package com.electromatic.api.repository.recepcion;

import com.electromatic.api.model.recepcion.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    java.util.Optional<com.electromatic.api.model.recepcion.Cliente> findByDocumento(String documento);
}
