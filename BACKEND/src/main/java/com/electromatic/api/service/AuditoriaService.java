package com.electromatic.api.service;

import com.electromatic.api.model.seguridad.RegistroAuditoria;
import com.electromatic.api.repository.seguridad.RegistroAuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service public class AuditoriaService {
    private final RegistroAuditoriaRepository repository;
    public AuditoriaService(RegistroAuditoriaRepository r){repository=r;}
    @Transactional public RegistroAuditoria registrar(Long usuario,String accion,String resultado,String detalle,String entidad,Long idRegistro){RegistroAuditoria a=new RegistroAuditoria();a.setIdUsuario(usuario);a.setFechaHora(LocalDateTime.now());a.setAccion(accion);a.setResultado(resultado);a.setDetalle(detalle);a.setEntidad(entidad);a.setIdRegistroAfectado(idRegistro);return repository.save(a);}
}
