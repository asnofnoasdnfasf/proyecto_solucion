package com.electromatic.api.controller;

import com.electromatic.api.model.almacen.*;
import com.electromatic.api.repository.almacen.MovimientoInventarioRepository;
import com.electromatic.api.repository.almacen.AtencionRequerimientoRepository;
import com.electromatic.api.repository.tecnica.RequerimientoRepuestoRepository;
import com.electromatic.api.service.AlmacenInventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@RestController @RequestMapping("/api/almacen")
public class AlmacenController {
    private final AlmacenInventarioService service;private final MovimientoInventarioRepository movimientos;private final AtencionRequerimientoRepository atenciones;private final RequerimientoRepuestoRepository requerimientos;
    public AlmacenController(AlmacenInventarioService s,MovimientoInventarioRepository m,AtencionRequerimientoRepository a,RequerimientoRepuestoRepository r){service=s;movimientos=m;atenciones=a;requerimientos=r;}
    @GetMapping("/materiales") public List<Material> materiales(){return service.materiales();}
    @PostMapping("/materiales") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> crearMaterial(@RequestBody Map<String,Object>b,HttpServletRequest request){Material m=new Material();m.setCodigo(texto(b,"codigo"));m.setNombre(texto(b,"nombre"));m.setTipo(texto(b,"tipo"));m.setUnidadMedida(texto(b,"unidadMedida"));m.setStockMinimo(decimal(b,"stockMinimo"));return service.crearMaterial(m,decimal(b,"stockInicial"),(Long)request.getAttribute("idUsuarioAutenticado"),texto(b,"justificacion"));}
    @GetMapping("/materiales/{id}/stock") public Map<String,Object> stock(@PathVariable Long id){return Map.of("idMaterial",id,"stockActual",service.stock(id));}
    @GetMapping("/movimientos") public List<MovimientoInventario> movimientos(){return movimientos.findAll();}
    @PostMapping("/movimientos") @ResponseStatus(HttpStatus.CREATED) public MovimientoInventario registrar(@RequestBody MovimientoInventario m,HttpServletRequest request){m.setIdUsuario((Long)request.getAttribute("idUsuarioAutenticado"));return service.movimiento(m);}
    @GetMapping("/requerimientos") public List<com.electromatic.api.model.tecnica.RequerimientoRepuesto> requerimientos(){return requerimientos.findAll();}
    @GetMapping("/atenciones") public List<AtencionRequerimiento> atenciones(){return atenciones.findAll();}
    @PostMapping("/atenciones") @ResponseStatus(HttpStatus.CREATED) public AtencionRequerimiento atender(@RequestBody Map<String,Object>b,HttpServletRequest request){Object id=b.get("idRequerimiento");if(id==null)throw new IllegalArgumentException("Se requiere idRequerimiento.");return service.atender(Long.valueOf(id.toString()),(Long)request.getAttribute("idUsuarioAutenticado"),String.valueOf(b.getOrDefault("observaciones","")));}
    private String texto(Map<String,Object>b,String key){Object value=b.get(key);return value==null?null:value.toString().trim();}
    private BigDecimal decimal(Map<String,Object>b,String key){Object value=b.get(key);try{return value==null?null:new BigDecimal(value.toString());}catch(NumberFormatException ex){throw new IllegalArgumentException("El campo "+key+" debe ser numérico.");}}
}
