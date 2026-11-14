package com.electromatic.api.controller;

import com.electromatic.api.model.calidad.*;
import com.electromatic.api.repository.calidad.EvaluacionCalidadRepository;
import com.electromatic.api.service.CalidadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@RestController @RequestMapping("/api/calidad")
public class CalidadController {
    public record EvaluacionRequest(Long idOrdenServicio,Long idChecklist,String resultado,String motivoRechazo,String observaciones,List<CalidadService.CriterioEvaluado> criterios){}
    private final CalidadService service;private final EvaluacionCalidadRepository evaluaciones;
    public CalidadController(CalidadService s,EvaluacionCalidadRepository e){service=s;evaluaciones=e;}
    @GetMapping("/checklists") public List<ChecklistCalidad> checklists(){return service.checklists();}
    @GetMapping("/checklists/{id}/criterios") public List<CriterioCalidad> criterios(@PathVariable Long id){return service.criterios(id);}
    @GetMapping("/evaluaciones") public List<EvaluacionCalidad> evaluaciones(){return evaluaciones.findAll();}
    @PostMapping("/evaluaciones") @ResponseStatus(HttpStatus.CREATED) public EvaluacionCalidad evaluar(@RequestBody EvaluacionRequest r,HttpServletRequest request){if(r.criterios()==null)throw new IllegalArgumentException("Falta evaluar los criterios.");return service.evaluar(r.idOrdenServicio(),r.idChecklist(),(Long)request.getAttribute("idUsuarioAutenticado"),r.resultado(),r.motivoRechazo(),r.criterios(),r.observaciones());}
}
