package com.electromatic.api.service;

import com.electromatic.api.model.almacen.*;
import com.electromatic.api.repository.almacen.*;
import com.electromatic.api.model.tecnica.*;
import com.electromatic.api.model.administracion.SolicitudCompra;
import com.electromatic.api.repository.tecnica.*;
import com.electromatic.api.repository.administracion.SolicitudCompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service public class AlmacenInventarioService {
    private final MaterialRepository materiales;private final MovimientoInventarioRepository movimientos;private final RequerimientoRepuestoRepository requerimientos;private final DetalleRequerimientoRepository detalles;private final AtencionRequerimientoRepository atenciones;private final DetalleAtencionRepository detallesAtencion;private final SolicitudCompraRepository solicitudesCompra;private final JdbcTemplate jdbc;private final AuditoriaService auditoria;
    public AlmacenInventarioService(MaterialRepository m,MovimientoInventarioRepository r,RequerimientoRepuestoRepository rr,DetalleRequerimientoRepository dr,AtencionRequerimientoRepository a,DetalleAtencionRepository da,SolicitudCompraRepository sc,JdbcTemplate j,AuditoriaService au){materiales=m;movimientos=r;requerimientos=rr;detalles=dr;atenciones=a;detallesAtencion=da;solicitudesCompra=sc;jdbc=j;auditoria=au;}
    public List<Material> materiales(){return materiales.findAll();}
    public BigDecimal stock(Long id){if(!materiales.existsById(id))throw new IllegalArgumentException("Material no encontrado.");BigDecimal q=movimientos.stockActual(id);return q==null?BigDecimal.ZERO:q;}
    @Transactional public MovimientoInventario movimiento(MovimientoInventario m){
        if(m.getCantidad()==null||m.getCantidad().compareTo(BigDecimal.ZERO)==0)throw new IllegalArgumentException("La cantidad no puede ser cero.");
        if(m.getJustificacion()==null||m.getJustificacion().isBlank())throw new IllegalArgumentException("Justificación obligatoria.");
        Material material=materiales.bloquearPorId(m.getIdMaterial()).orElseThrow(()->new IllegalArgumentException("Material no encontrado."));
        if(!List.of("INGRESO","SALIDA","AJUSTE").contains(m.getTipoMovimiento()))throw new IllegalArgumentException("Tipo de movimiento inválido.");
        if(!"AJUSTE".equals(m.getTipoMovimiento())&&m.getCantidad().signum()<0)throw new IllegalArgumentException("La cantidad debe ser positiva.");
        BigDecimal actual=stock(m.getIdMaterial());BigDecimal delta="SALIDA".equals(m.getTipoMovimiento())?m.getCantidad().negate():m.getCantidad();boolean bajaNegativa=actual.add(delta).signum()<0;
        if(bajaNegativa&&!tienePermisoNegativo(m.getIdUsuario()))throw new IllegalArgumentException("Stock insuficiente. Se requiere permiso INVENTARIO_STOCK_NEGATIVO para autorizar saldo negativo.");
        if(bajaNegativa)auditoria.registrar(m.getIdUsuario(),"STOCK_NEGATIVO_AUTORIZADO","EXITO",m.getJustificacion(),"material",m.getIdMaterial());
        m.setFechaHora(LocalDateTime.now());return movimientos.save(m);
    }
    private boolean tienePermisoNegativo(Long usuario){Integer n=jdbc.queryForObject("SELECT COUNT(*) FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol AND r.estado='ACTIVO' JOIN rol_permiso rp ON rp.id_rol=r.id_rol JOIN permiso p ON p.id_permiso=rp.id_permiso WHERE ur.id_usuario=? AND p.nombre='INVENTARIO_STOCK_NEGATIVO'",Integer.class,usuario);return n!=null&&n>0;}
    @Transactional public AtencionRequerimiento atender(Long idRequerimiento,Long idUsuario,String observaciones){
        RequerimientoRepuesto r=requerimientos.findById(idRequerimiento).orElseThrow(()->new IllegalArgumentException("Requerimiento no encontrado."));
        if(!"PENDIENTE".equals(r.getEstado())&&!"EN_COMPRA".equals(r.getEstado()))throw new IllegalArgumentException("El requerimiento no está pendiente de atención.");
        List<DetalleRequerimiento> lineas=detalles.findByIdRequerimiento(idRequerimiento);if(lineas.isEmpty())throw new IllegalArgumentException("El requerimiento no tiene artículos.");
        AtencionRequerimiento at=new AtencionRequerimiento();at.setIdRequerimiento(idRequerimiento);at.setIdUsuarioAtiende(idUsuario);at.setFechaAtencion(LocalDateTime.now());at.setObservaciones(observaciones);at.setEstado("PARCIAL");at=atenciones.save(at);
        boolean completa=true;
        for(DetalleRequerimiento linea:lineas){Material mat=materiales.bloquearPorId(linea.getIdMaterial()).orElseThrow(()->new IllegalArgumentException("Artículo no encontrado."));BigDecimal yaEntregada=detallesAtencion.totalEntregado(linea.getIdDetalle());BigDecimal pendiente=linea.getCantidadSolicitada().subtract(yaEntregada).max(BigDecimal.ZERO);BigDecimal disponible=stock(mat.getIdMaterial()).max(BigDecimal.ZERO);BigDecimal salida=disponible.min(pendiente);
            DetalleAtencion da=new DetalleAtencion();da.setIdAtencion(at.getIdAtencion());da.setIdDetalleRequerimiento(linea.getIdDetalle());da.setCantidadReservada(salida);da.setCantidadEntregada(salida);da=detallesAtencion.save(da);
            if(salida.signum()>0){MovimientoInventario mov=new MovimientoInventario();mov.setIdMaterial(mat.getIdMaterial());mov.setIdUsuario(idUsuario);mov.setIdDetalleAtencion(da.getIdDetalleAtencion());mov.setTipoMovimiento("SALIDA");mov.setCantidad(salida);mov.setFechaHora(LocalDateTime.now());mov.setJustificacion("Entrega de requerimiento "+r.getCodigoRequerimiento());movimientos.save(mov);}
            if(salida.compareTo(pendiente)<0){completa=false;SolicitudCompra sc=new SolicitudCompra();sc.setIdRequerimiento(idRequerimiento);sc.setIdUsuarioSolicita(idUsuario);sc.setFechaSolicitud(LocalDateTime.now());sc.setEstado("PENDIENTE");sc.setJustificacion("Stock insuficiente para material "+mat.getCodigo()+": aún pendiente "+pendiente.subtract(salida));solicitudesCompra.save(sc);}
        }
        at.setEstado(completa?"COMPLETA":"PARCIAL");atenciones.save(at);r.setEstado(completa?"ATENDIDO":"EN_COMPRA");requerimientos.save(r);return at;
    }
}
