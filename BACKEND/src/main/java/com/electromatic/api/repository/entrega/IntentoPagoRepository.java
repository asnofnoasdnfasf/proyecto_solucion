package com.electromatic.api.repository.entrega;

import com.electromatic.api.model.entrega.IntentoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntentoPagoRepository extends JpaRepository<IntentoPago, Long> { java.util.List<IntentoPago> findByIdOrdenServicioAndEstado(Long idOrdenServicio,String estado); boolean existsByIdentificadorUnico(String identificadorUnico); }
