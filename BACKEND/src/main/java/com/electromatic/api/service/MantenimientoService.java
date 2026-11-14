package com.electromatic.api.service;

import com.electromatic.api.model.recepcion.OrdenServicio;
import com.electromatic.api.model.tecnica.*;
import com.electromatic.api.repository.recepcion.OrdenServicioRepository;
import com.electromatic.api.repository.recepcion.HistorialOrdenRepository;
import com.electromatic.api.model.recepcion.HistorialOrden;
import com.electromatic.api.repository.tecnica.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class MantenimientoService {
    private final DiagnosticoRepository diagnosticos; private final MantenimientoRepository mantenimientos;
    private final PruebaFuncionalRepository pruebas; private final OrdenServicioRepository ordenes;private final HistorialOrdenRepository historial;
    public MantenimientoService(DiagnosticoRepository d,MantenimientoRepository m,PruebaFuncionalRepository p,OrdenServicioRepository o,HistorialOrdenRepository h){diagnosticos=d;mantenimientos=m;pruebas=p;ordenes=o;historial=h;}
    @Transactional public Diagnostico registrarDiagnostico(Diagnostico d){
        if(!"PREVENTIVO".equals(d.getClasificacionMantenimiento())&&!"CORRECTIVO".equals(d.getClasificacionMantenimiento()))throw new IllegalArgumentException("Clasificación debe ser PREVENTIVO o CORRECTIVO.");
        d.setFechaRegistro(LocalDateTime.now());Diagnostico saved=diagnosticos.save(d);OrdenServicio o=ordenes.findById(d.getIdOrdenServicio()).orElseThrow(()->new IllegalArgumentException("Orden no encontrada."));Short old=o.getIdEstadoOrden();o.setIdEstadoOrden((short)2);ordenes.save(o);log(o,d.getIdUsuarioTecnico(),old,(short)2,"DIAGNOSTICO_REGISTRADO");return saved;
    }
    @Transactional public Mantenimiento iniciar(Long idOrden,Long idTecnico){if(!diagnosticos.existsByIdOrdenServicio(idOrden))throw new IllegalArgumentException("Registre diagnóstico y clasificación antes del mantenimiento.");Mantenimiento m=new Mantenimiento();m.setIdOrdenServicio(idOrden);m.setIdUsuarioTecnico(idTecnico);m.setFechaInicio(LocalDateTime.now());m.setEstado("EN_CURSO");Mantenimiento saved=mantenimientos.save(m);OrdenServicio o=ordenes.findById(idOrden).orElseThrow();Short old=o.getIdEstadoOrden();o.setIdEstadoOrden((short)3);ordenes.save(o);log(o,idTecnico,old,(short)3,"MANTENIMIENTO_INICIADO");return saved;}
    @Transactional public PruebaFuncional pruebaInicial(Long idMantenimiento,String resultado,String observaciones){Mantenimiento m=mantenimientos.findById(idMantenimiento).orElseThrow(()->new IllegalArgumentException("Mantenimiento no encontrado."));if(!"APROBADA".equals(resultado)&&!"FALLIDA".equals(resultado))throw new IllegalArgumentException("Resultado de prueba inválido.");PruebaFuncional p=new PruebaFuncional();p.setIdMantenimiento(idMantenimiento);p.setNombrePrueba("PRUEBA_INICIAL");p.setResultado(resultado);p.setEstado("EJECUTADA");p.setObservaciones(observaciones);p.setFechaPrueba(LocalDateTime.now());PruebaFuncional saved=pruebas.save(p);if("APROBADA".equals(resultado)){m.setEstado("FINALIZADO");m.setFechaFin(LocalDateTime.now());mantenimientos.save(m);OrdenServicio o=ordenes.findById(m.getIdOrdenServicio()).orElseThrow();Short old=o.getIdEstadoOrden();o.setIdEstadoOrden((short)5);ordenes.save(o);log(o,m.getIdUsuarioTecnico(),old,(short)5,"PRUEBA_INICIAL_APROBADA");}return saved;}
    private void log(OrdenServicio o,Long user,Short previous,Short next,String action){HistorialOrden h=new HistorialOrden();h.setIdOrdenServicio(o.getIdOrdenServicio());h.setIdUsuario(user);h.setFechaHora(LocalDateTime.now());h.setAccion(action);h.setIdEstadoAnterior(previous);h.setIdEstadoNuevo(next);h.setDetalle(action);historial.save(h);}
}
