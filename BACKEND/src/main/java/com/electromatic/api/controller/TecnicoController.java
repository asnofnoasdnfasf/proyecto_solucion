package com.electromatic.api.controller;

import com.electromatic.api.model.tecnica.*;
import com.electromatic.api.repository.tecnica.*;
import com.electromatic.api.service.MantenimientoService;
import com.electromatic.api.service.RequerimientoService;
import com.electromatic.api.repository.tecnica.RequerimientoRepuestoRepository;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@RestController @RequestMapping("/api/tecnico")
public class TecnicoController {
    private final DiagnosticoRepository diagnosticos; private final MantenimientoRepository mantenimientos; private final MantenimientoService service; private final RequerimientoService requerimientoService; private final RequerimientoRepuestoRepository requerimientos;
    public TecnicoController(DiagnosticoRepository d,MantenimientoRepository m,MantenimientoService s,RequerimientoService rs,RequerimientoRepuestoRepository rr){diagnosticos=d;mantenimientos=m;service=s;requerimientoService=rs;requerimientos=rr;}
    @GetMapping("/diagnosticos") public List<Diagnostico> listarDiagnosticos(){return diagnosticos.findAll();}
    @PostMapping("/diagnosticos") @ResponseStatus(HttpStatus.CREATED) public Diagnostico diagnostico(@RequestBody Diagnostico d,HttpServletRequest request){d.setIdUsuarioTecnico((Long)request.getAttribute("idUsuarioAutenticado"));return service.registrarDiagnostico(d);}
    @GetMapping("/mantenimientos") public List<Mantenimiento> listarMantenimientos(){return mantenimientos.findAll();}
    @PostMapping("/mantenimientos") @ResponseStatus(HttpStatus.CREATED) public Mantenimiento iniciar(@RequestBody Map<String,Object>b,HttpServletRequest request){return service.iniciar(num(b,"idOrdenServicio"),(Long)request.getAttribute("idUsuarioAutenticado"));}
    @PostMapping("/mantenimientos/{id}/prueba-inicial") @ResponseStatus(HttpStatus.CREATED) public PruebaFuncional prueba(@PathVariable Long id,@RequestBody Map<String,String>b){return service.pruebaInicial(id,b.get("resultado"),b.get("observaciones"));}
    @GetMapping("/requerimientos") public List<RequerimientoRepuesto> requerimientos(){return requerimientos.findAll();}
    @PostMapping("/requerimientos") @ResponseStatus(HttpStatus.CREATED) public RequerimientoRepuesto requerir(@RequestBody Map<String,Object>b,HttpServletRequest request){return requerimientoService.crear(num(b,"idOrdenServicio"),(Long)request.getAttribute("idUsuarioAutenticado"),num(b,"idMaterial"),new BigDecimal(String.valueOf(b.get("cantidad"))),String.valueOf(b.get("prioridad")),String.valueOf(b.get("justificacionTecnica")));}
    private Long num(Map<String,Object>b,String k){Object v=b.get(k);if(v==null)throw new IllegalArgumentException("Falta el campo "+k);return Long.valueOf(v.toString());}
}
