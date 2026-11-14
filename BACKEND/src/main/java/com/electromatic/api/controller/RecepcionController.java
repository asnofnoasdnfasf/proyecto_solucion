package com.electromatic.api.controller;

import com.electromatic.api.model.recepcion.OrdenServicio;
import com.electromatic.api.repository.recepcion.OrdenServicioRepository;
import com.electromatic.api.repository.recepcion.ClienteRepository;
import com.electromatic.api.service.RecepcionOrdenService;
import com.electromatic.api.service.GarantiaService;
import com.electromatic.api.service.EquipoService;
import com.electromatic.api.model.recepcion.Equipo;
import com.electromatic.api.model.recepcion.TipoEquipo;
import com.electromatic.api.model.recepcion.Garantia;
import com.electromatic.api.repository.recepcion.TipoEquipoRepository;
import com.electromatic.api.repository.recepcion.HistorialOrdenRepository;
import com.electromatic.api.model.recepcion.HistorialOrden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@RestController @RequestMapping("/api/recepcion")
public class RecepcionController {
    private final RecepcionOrdenService service; private final OrdenServicioRepository ordenes; private final ClienteRepository clientes; private final GarantiaService garantias;private final EquipoService equipos;private final TipoEquipoRepository tipos;private final HistorialOrdenRepository historial;
    public RecepcionController(RecepcionOrdenService s,OrdenServicioRepository o,ClienteRepository c,GarantiaService g,EquipoService es,TipoEquipoRepository t,HistorialOrdenRepository h){service=s;ordenes=o;clientes=c;garantias=g;equipos=es;tipos=t;historial=h;}
    @GetMapping("/ordenes") public List<OrdenServicio> listar(){return ordenes.findAll();}
    @GetMapping("/ordenes/{id}") public OrdenServicio obtener(@PathVariable Long id){return ordenes.findById(id).orElseThrow(()->new IllegalArgumentException("Orden no encontrada."));}
    @GetMapping("/ordenes/{id}/historial") public List<HistorialOrden> historial(@PathVariable Long id){return historial.findByIdOrdenServicioOrderByFechaHoraAsc(id);}
    @PostMapping("/ordenes") @ResponseStatus(HttpStatus.CREATED) public OrdenServicio crear(@RequestBody Map<String,Object>b,HttpServletRequest request){return service.registrar(num(b,"idCliente"),num(b,"idEquipo"),(Long)request.getAttribute("idUsuarioAutenticado"),str(b,"condicionFisica"),str(b,"danosVisibles"),str(b,"accesorios"));}
    @GetMapping("/tipos-equipo") public List<TipoEquipo> tipos(){return tipos.findAll();}
    @GetMapping("/equipos") public List<Equipo> equipos(){return equipos.listar();}
    @PostMapping("/equipos") @ResponseStatus(HttpStatus.CREATED) public Equipo crearEquipo(@RequestBody Equipo e){return equipos.registrar(e);}
    @GetMapping("/garantias") public List<Garantia> garantias(){return garantias.listar();}
    @PostMapping("/garantias") @ResponseStatus(HttpStatus.CREATED) public Garantia crearGarantia(@RequestBody Garantia g){return garantias.crear(g);}
    @GetMapping("/seguimiento/{codigo}") public Map<String,Object> seguimiento(@PathVariable String codigo,@RequestParam String documento){OrdenServicio o=ordenes.findAll().stream().filter(x->codigo.equals(x.getCodigoSeguimiento())).findFirst().orElseThrow(()->new IllegalArgumentException("Código o validación inválidos."));boolean valida=clientes.findById(o.getIdCliente()).map(c->c.getDocumento().equals(documento)).orElse(false);if(!valida)throw new IllegalArgumentException("Código o validación inválidos.");return Map.of("codigoSeguimiento",o.getCodigoSeguimiento(),"estado",o.getIdEstadoOrden(),"fechaRecepcion",o.getFechaRecepcion());}
    private Long num(Map<String,Object>b,String k){Object v=b.get(k);if(v==null)throw new IllegalArgumentException("Falta el campo "+k);return Long.valueOf(v.toString());}
    private String str(Map<String,Object>b,String k){Object v=b.get(k);return v==null?null:v.toString();}
}
