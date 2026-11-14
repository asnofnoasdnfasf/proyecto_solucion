package com.electromatic.api.controller;

import com.electromatic.api.model.entrega.*;
import com.electromatic.api.repository.entrega.*;
import com.electromatic.api.service.PagoEntregaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

@RestController @RequestMapping("/api/entrega-pago")
public class EntregaPagoController {
    public record ComprobanteRequest(Long idOrdenServicio,Long idIntento,String tipoComprobante,String identificador,BigDecimal importe){}
    public record EntregaRequest(Long idOrdenServicio,String personaRecibe,String documentoReceptor,String motivoExcepcion){}
    private final PagoEntregaService service;private final IntentoPagoRepository pagos;private final ComprobanteFiscalRepository comprobantes;private final EntregaEquipoRepository entregas;
    public EntregaPagoController(PagoEntregaService s,IntentoPagoRepository p,ComprobanteFiscalRepository c,EntregaEquipoRepository e){service=s;pagos=p;comprobantes=c;entregas=e;}
    @GetMapping("/pagos") public List<IntentoPago> pagos(){return pagos.findAll();}
    @PostMapping("/pagos/notificacion") @ResponseStatus(HttpStatus.CREATED) public IntentoPago procesar(@RequestBody PagoEntregaService.NotificacionPago n){return service.procesar(n);}
    @PostMapping("/comprobantes") @ResponseStatus(HttpStatus.CREATED) public ComprobanteFiscal comprobante(@RequestBody ComprobanteRequest r){return service.comprobante(r.idOrdenServicio(),r.idIntento(),r.tipoComprobante(),r.identificador(),r.importe());}
    @GetMapping("/comprobantes") public List<ComprobanteFiscal> comprobantes(){return comprobantes.findAll();}
    @PostMapping("/entregas") @ResponseStatus(HttpStatus.CREATED) public EntregaEquipo entregar(@RequestBody EntregaRequest r,HttpServletRequest request){return service.entregar(r.idOrdenServicio(),(Long)request.getAttribute("idUsuarioAutenticado"),r.personaRecibe(),r.documentoReceptor(),r.motivoExcepcion());}
    @GetMapping("/entregas") public List<EntregaEquipo> entregas(){return entregas.findAll();}
}
