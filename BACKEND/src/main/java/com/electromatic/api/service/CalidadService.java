package com.electromatic.api.service;

import com.electromatic.api.model.calidad.*;
import com.electromatic.api.model.recepcion.OrdenServicio;
import com.electromatic.api.repository.calidad.*;
import com.electromatic.api.repository.recepcion.OrdenServicioRepository;
import com.electromatic.api.repository.recepcion.HistorialOrdenRepository;
import com.electromatic.api.model.recepcion.HistorialOrden;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service public class CalidadService {
    public record CriterioEvaluado(Long idCriterio,String resultado,String observaciones){}
    private final ChecklistCalidadRepository checklists;private final CriterioCalidadRepository criterios;private final EvaluacionCalidadRepository evaluaciones;private final ResultadoCriterioRepository resultados;private final OrdenServicioRepository ordenes;private final HistorialOrdenRepository historial;
    public CalidadService(ChecklistCalidadRepository c,CriterioCalidadRepository cr,EvaluacionCalidadRepository e,ResultadoCriterioRepository r,OrdenServicioRepository o,HistorialOrdenRepository h){checklists=c;criterios=cr;evaluaciones=e;resultados=r;ordenes=o;historial=h;}
    public List<ChecklistCalidad> checklists(){return checklists.findAll();}
    public List<CriterioCalidad> criterios(Long idChecklist){if(!checklists.existsById(idChecklist))throw new IllegalArgumentException("Checklist no encontrado.");return criterios.findByIdChecklist(idChecklist);}
    @Transactional public EvaluacionCalidad evaluar(Long orden,Long checklist,Long usuario,String resultado,String motivo,List<CriterioEvaluado> detalle,String observaciones){
        if(!checklists.existsById(checklist))throw new IllegalArgumentException("Checklist no encontrado.");List<CriterioCalidad> esperados=criterios.findByIdChecklist(checklist);Map<Long,CriterioEvaluado> entregados=new HashMap<>();for(CriterioEvaluado x:detalle)if(x.idCriterio()!=null)entregados.put(x.idCriterio(),x);
        if(esperados.isEmpty()||esperados.size()!=entregados.size()||esperados.stream().anyMatch(c->!entregados.containsKey(c.getIdCriterio())))throw new IllegalArgumentException("Debe evaluar todos los criterios del checklist.");
        if(!"APROBADA".equals(resultado)&&!"RECHAZADA".equals(resultado))throw new IllegalArgumentException("Resultado inválido.");if("RECHAZADA".equals(resultado)&&(motivo==null||motivo.isBlank()))throw new IllegalArgumentException("El rechazo requiere motivo.");
        EvaluacionCalidad e=new EvaluacionCalidad();e.setIdOrdenServicio(orden);e.setIdChecklist(checklist);e.setIdUsuarioEvalua(usuario);e.setFechaHora(LocalDateTime.now());e.setEstado("CERRADA");e.setResultado(resultado);e.setMotivoRechazo(motivo);e.setObservaciones(observaciones);e=evaluaciones.save(e);
        for(CriterioCalidad c:esperados){CriterioEvaluado x=entregados.get(c.getIdCriterio());if(x.resultado()==null||!List.of("CUMPLE","NO_CUMPLE","NO_APLICA").contains(x.resultado()))throw new IllegalArgumentException("Todos los criterios requieren un resultado válido.");if("APROBADA".equals(resultado)&&c.getObligatorio()&&"NO_CUMPLE".equals(x.resultado()))throw new IllegalArgumentException("Un criterio obligatorio no cumple; la evaluación debe ser rechazada.");ResultadoCriterio r=new ResultadoCriterio();r.setIdEvaluacion(e.getIdEvaluacion());r.setIdCriterio(c.getIdCriterio());r.setResultado(x.resultado());r.setObservaciones(x.observaciones());resultados.save(r);}
        OrdenServicio o=ordenes.findById(orden).orElseThrow(()->new IllegalArgumentException("Orden no encontrada."));if(o.getIdEstadoOrden()==null||o.getIdEstadoOrden()!=(short)5)throw new IllegalArgumentException("La orden solo puede evaluarse después de completar mantenimiento y prueba inicial.");Short old=o.getIdEstadoOrden(),next="APROBADA".equals(resultado)?(short)6:(short)3;o.setIdEstadoOrden(next);ordenes.save(o);HistorialOrden h=new HistorialOrden();h.setIdOrdenServicio(orden);h.setIdUsuario(usuario);h.setFechaHora(LocalDateTime.now());h.setAccion("CALIDAD_"+resultado);h.setIdEstadoAnterior(old);h.setIdEstadoNuevo(next);h.setDetalle("Evaluación de calidad "+resultado+(motivo==null?"":": "+motivo));historial.save(h);return e;
    }
}
