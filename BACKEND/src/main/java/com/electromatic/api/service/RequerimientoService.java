package com.electromatic.api.service;

import com.electromatic.api.model.tecnica.*;
import com.electromatic.api.repository.tecnica.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service public class RequerimientoService {
    private final RequerimientoRepuestoRepository requerimientos;private final DetalleRequerimientoRepository detalles;private final DiagnosticoRepository diagnosticos;
    public RequerimientoService(RequerimientoRepuestoRepository r,DetalleRequerimientoRepository d,DiagnosticoRepository g){requerimientos=r;detalles=d;diagnosticos=g;}
    @Transactional public RequerimientoRepuesto crear(Long orden,Long usuario,Long material,BigDecimal cantidad,String prioridad,String justificacion){if(cantidad==null||cantidad.compareTo(BigDecimal.ZERO)<=0)throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");if(justificacion==null||justificacion.isBlank())throw new IllegalArgumentException("La justificación técnica es obligatoria.");if(prioridad==null||!java.util.List.of("BAJA","MEDIA","ALTA","URGENTE").contains(prioridad.toUpperCase()))throw new IllegalArgumentException("Prioridad inválida.");if(!diagnosticos.existsByIdOrdenServicio(orden))throw new IllegalArgumentException("El requerimiento debe corresponder a una orden diagnosticada.");RequerimientoRepuesto r=new RequerimientoRepuesto();r.setIdOrdenServicio(orden);r.setIdUsuarioSolicita(usuario);r.setCodigoRequerimiento("REQ-"+UUID.randomUUID().toString().substring(0,8).toUpperCase());r.setFechaSolicitud(LocalDateTime.now());r.setPrioridad(prioridad.toUpperCase());r.setEstado("PENDIENTE");r=requerimientos.save(r);DetalleRequerimiento d=new DetalleRequerimiento();d.setIdRequerimiento(r.getIdRequerimiento());d.setIdMaterial(material);d.setCantidadSolicitada(cantidad);d.setJustificacionTecnica(justificacion);detalles.save(d);return r;}
}
