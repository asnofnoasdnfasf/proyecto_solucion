package com.electromatic.api.service;

import com.electromatic.api.model.entrega.*;
import com.electromatic.api.model.calidad.EvaluacionCalidad;
import com.electromatic.api.repository.entrega.*;
import com.electromatic.api.repository.calidad.EvaluacionCalidadRepository;
import com.electromatic.api.model.recepcion.OrdenServicio;
import com.electromatic.api.model.recepcion.HistorialOrden;
import com.electromatic.api.repository.recepcion.OrdenServicioRepository;
import com.electromatic.api.repository.recepcion.HistorialOrdenRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.List;

@Service public class PagoEntregaService {
    public record NotificacionPago(Long idOrdenServicio,String identificadorUnico,BigDecimal monto,String moneda,String estado,String respuesta,String firma){}
    private final ConceptoCobroRepository conceptos;private final IntentoPagoRepository pagos;private final ComprobanteFiscalRepository comprobantes;private final EvaluacionCalidadRepository evaluaciones;private final EntregaEquipoRepository entregas;private final OrdenServicioRepository ordenes;private final HistorialOrdenRepository historial;private final AuditoriaService auditoria;private final JdbcTemplate jdbc;
    @Value("${app.payment.webhook-secret:}") private String secreto;
    public PagoEntregaService(ConceptoCobroRepository c,IntentoPagoRepository p,ComprobanteFiscalRepository f,EvaluacionCalidadRepository e,EntregaEquipoRepository d,OrdenServicioRepository o,HistorialOrdenRepository h,AuditoriaService a,JdbcTemplate j){conceptos=c;pagos=p;comprobantes=f;evaluaciones=e;entregas=d;ordenes=o;historial=h;auditoria=a;jdbc=j;}
    @Transactional public IntentoPago procesar(NotificacionPago n){
        if(secreto==null||secreto.isBlank())throw new IllegalStateException("Falta configurar APP_PAYMENT_WEBHOOK_SECRET.");if(n.monto()==null||n.identificadorUnico()==null||n.identificadorUnico().isBlank()||pagos.existsByIdentificadorUnico(n.identificadorUnico()))throw new IllegalArgumentException("Identificador o importe de pago inválidos o duplicados.");
        String canon=String.join("|",String.valueOf(n.idOrdenServicio()),n.identificadorUnico(),n.monto().toPlainString(),n.moneda(),n.estado());if(!constante(sha256(canon),n.firma()))throw new IllegalArgumentException("Firma del proveedor de pago no válida.");
        BigDecimal total=conceptos.totalOrden(n.idOrdenServicio());if(n.monto()==null||n.monto().compareTo(total)!=0||!"PEN".equals(n.moneda()))throw new IllegalArgumentException("Orden, importe o moneda no coinciden.");
        if(!List.of("PENDIENTE","APROBADO","RECHAZADO","ERROR").contains(n.estado()))throw new IllegalArgumentException("Estado de pago no válido.");IntentoPago p=new IntentoPago();p.setIdOrdenServicio(n.idOrdenServicio());p.setIdentificadorUnico(n.identificadorUnico());p.setFechaHora(LocalDateTime.now());p.setMonto(n.monto());p.setMoneda(n.moneda());p.setEstado(n.estado());p.setRespuestaPago(n.respuesta());p.setRespuestaProveedor("Firma verificada");return pagos.save(p);
    }
    @Transactional public ComprobanteFiscal comprobante(Long orden,Long intento,String tipo,String identificador,BigDecimal importe){BigDecimal total=conceptos.totalOrden(orden);if(importe==null||importe.compareTo(total)!=0)throw new IllegalArgumentException("El importe del comprobante debe coincidir con el total de la orden.");if(identificador==null||identificador.isBlank())throw new IllegalArgumentException("Identificador fiscal obligatorio.");if(!List.of("BOLETA","FACTURA","NOTA_CREDITO").contains(tipo))throw new IllegalArgumentException("Tipo de comprobante inválido.");if(intento!=null){IntentoPago p=pagos.findById(intento).orElseThrow(()->new IllegalArgumentException("Intento de pago no encontrado."));if(!orden.equals(p.getIdOrdenServicio())||!"APROBADO".equals(p.getEstado())||p.getMonto().compareTo(importe)!=0)throw new IllegalArgumentException("El intento no corresponde al importe y orden del comprobante.");}ComprobanteFiscal c=new ComprobanteFiscal();c.setIdOrdenServicio(orden);c.setIdIntento(intento);c.setTipoComprobante(tipo);c.setIdentificador(identificador);c.setFechaEmision(LocalDateTime.now());c.setImporte(importe);c.setEstadoEmision("PENDIENTE");return comprobantes.save(c);}
    @Transactional public EntregaEquipo entregar(Long orden,Long usuario,String persona,String documento,String motivoExcepcion){EvaluacionCalidad e=evaluaciones.findAll().stream().filter(x->orden.equals(x.getIdOrdenServicio())&&"CERRADA".equals(x.getEstado())).max((a,b)->a.getFechaHora().compareTo(b.getFechaHora())).orElseThrow(()->new IllegalArgumentException("La orden requiere evaluación de calidad aprobada."));if(!"APROBADA".equals(e.getResultado()))throw new IllegalArgumentException("La calidad de la orden no está aprobada.");BigDecimal total=conceptos.totalOrden(orden);List<IntentoPago> confirmados=pagos.findByIdOrdenServicioAndEstado(orden,"APROBADO");BigDecimal pagado=confirmados.stream().map(IntentoPago::getMonto).reduce(BigDecimal.ZERO,BigDecimal::add);boolean sinPago=confirmados.isEmpty()||pagado.compareTo(total)<0;if(sinPago){if(motivoExcepcion==null||motivoExcepcion.isBlank()||!tienePermisoExcepcion(usuario))throw new IllegalArgumentException("Se requiere pago confirmado o permiso ENTREGA_SIN_PAGO y motivo registrado.");auditoria.registrar(usuario,"ENTREGA_SIN_PAGO","EXITO",motivoExcepcion,"orden_servicio",orden);}if(persona==null||persona.isBlank()||documento==null||documento.isBlank())throw new IllegalArgumentException("Persona y documento del receptor son obligatorios.");EntregaEquipo d=new EntregaEquipo();d.setIdOrdenServicio(orden);d.setIdUsuarioEntrega(usuario);d.setFechaHora(LocalDateTime.now());d.setPersonaRecibe(persona);d.setDocumentoReceptor(documento);d.setMotivoExcepcion(sinPago?motivoExcepcion:null);d=entregas.save(d);OrdenServicio o=ordenes.findById(orden).orElseThrow(()->new IllegalArgumentException("Orden no encontrada."));Short old=o.getIdEstadoOrden();o.setIdEstadoOrden((short)7);ordenes.save(o);HistorialOrden h=new HistorialOrden();h.setIdOrdenServicio(orden);h.setIdUsuario(usuario);h.setFechaHora(LocalDateTime.now());h.setAccion("EQUIPO_ENTREGADO");h.setIdEstadoAnterior(old);h.setIdEstadoNuevo((short)7);h.setDetalle(sinPago?"Entrega excepcional: "+motivoExcepcion:"Entrega con pago confirmado.");historial.save(h);return d;}
    private boolean tienePermisoExcepcion(Long usuario){Integer n=jdbc.queryForObject("SELECT COUNT(*) FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol AND r.estado='ACTIVO' JOIN rol_permiso rp ON rp.id_rol=r.id_rol JOIN permiso p ON p.id_permiso=rp.id_permiso WHERE ur.id_usuario=? AND p.nombre='ENTREGA_SIN_PAGO'",Integer.class,usuario);return n!=null&&n>0;}
    private String sha256(String text){try{Mac m=Mac.getInstance("HmacSHA256");m.init(new SecretKeySpec(secreto.getBytes(StandardCharsets.UTF_8),"HmacSHA256"));return HexFormat.of().formatHex(m.doFinal(text.getBytes(StandardCharsets.UTF_8)));}catch(Exception e){throw new IllegalStateException("No se pudo validar la firma del pago.",e);}}
    private boolean constante(String a,String b){if(b==null)return false;try{return MessageDigest.isEqual(HexFormat.of().parseHex(a),HexFormat.of().parseHex(b));}catch(IllegalArgumentException e){return false;}}
}
