package com.electromatic.api.controller;

import com.electromatic.api.model.administracion.*;
import com.electromatic.api.repository.administracion.OrdenCompraRepository;
import com.electromatic.api.service.ComprasService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@RestController @RequestMapping("/api/administracion")
public class AdministracionController {
    private final ComprasService service; private final OrdenCompraRepository ordenes;
    public AdministracionController(ComprasService s,OrdenCompraRepository o){service=s;ordenes=o;}
    @GetMapping("/proveedores") public List<Proveedor> proveedores(){return service.proveedores();}
    @PostMapping("/proveedores") @ResponseStatus(HttpStatus.CREATED) public Proveedor proveedor(@RequestBody Proveedor p){return service.guardarProveedor(p);}
    @GetMapping("/solicitudes-compra") public List<SolicitudCompra> solicitudes(){return service.solicitudes();}
    @PostMapping("/solicitudes-compra") @ResponseStatus(HttpStatus.CREATED) public SolicitudCompra solicitar(@RequestBody Map<String,Object>b,HttpServletRequest request){return service.solicitar(num(b,"idRequerimiento"),(Long)request.getAttribute("idUsuarioAutenticado"),String.valueOf(b.getOrDefault("justificacion","")));}
    @PostMapping("/cotizaciones") @ResponseStatus(HttpStatus.CREATED) public Cotizacion cotizacion(@RequestBody Cotizacion c){return service.cotizar(c);}
    @PatchMapping("/cotizaciones/{id}/aceptar") public Cotizacion aceptar(@PathVariable Long id){return service.aceptarCotizacion(id);}
    @GetMapping("/ordenes-compra") public List<OrdenCompra> ordenes(){return ordenes.findAll();}
    @PostMapping("/ordenes-compra") @ResponseStatus(HttpStatus.CREATED) public OrdenCompra emitir(@RequestBody Map<String,Object>b){return service.emitir(num(b,"idSolicitudCompra"),num(b,"idProveedor"));}
    @PostMapping("/ordenes-compra/{id}/detalles") @ResponseStatus(HttpStatus.CREATED) public DetalleOrdenCompra detalle(@PathVariable Long id,@RequestBody Map<String,Object>b){return service.agregarDetalle(id,num(b,"idMaterial"),decimal(b,"cantidad"),decimal(b,"costoUnitario"));}
    @PatchMapping("/ordenes-compra/{id}/recibir") public OrdenCompra recibir(@PathVariable Long id,HttpServletRequest request){return service.recibir(id,(Long)request.getAttribute("idUsuarioAutenticado"));}
    private Long num(Map<String,Object>b,String k){Object v=b.get(k);if(v==null)throw new IllegalArgumentException("Falta el campo "+k);return Long.valueOf(v.toString());}
    private BigDecimal decimal(Map<String,Object>b,String k){Object v=b.get(k);if(v==null)throw new IllegalArgumentException("Falta el campo "+k);return new BigDecimal(v.toString());}
}
