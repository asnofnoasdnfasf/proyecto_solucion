package com.electromatic.api.service;

import com.electromatic.api.model.administracion.*;
import com.electromatic.api.repository.administracion.*;
import com.electromatic.api.model.almacen.MovimientoInventario;
import com.electromatic.api.repository.almacen.MovimientoInventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service public class ComprasService {
    private final ProveedorRepository proveedores;private final SolicitudCompraRepository solicitudes;private final CotizacionRepository cotizaciones;private final OrdenCompraRepository ordenes;private final DetalleOrdenCompraRepository detalles;private final MovimientoInventarioRepository movimientos;
    public ComprasService(ProveedorRepository p,SolicitudCompraRepository s,CotizacionRepository c,OrdenCompraRepository o,DetalleOrdenCompraRepository d,MovimientoInventarioRepository m){proveedores=p;solicitudes=s;cotizaciones=c;ordenes=o;detalles=d;movimientos=m;}
    public List<Proveedor> proveedores(){return proveedores.findAll();}
    @Transactional public Proveedor guardarProveedor(Proveedor p){if(p.getDocumentoFiscal()==null||p.getDocumentoFiscal().isBlank())throw new IllegalArgumentException("Documento fiscal obligatorio.");return proveedores.save(p);}
    public List<SolicitudCompra> solicitudes(){return solicitudes.findAll();}
    @Transactional public SolicitudCompra solicitar(Long requerimiento,Long usuario,String justificacion){if(justificacion==null||justificacion.isBlank())throw new IllegalArgumentException("Justificación obligatoria.");SolicitudCompra s=new SolicitudCompra();s.setIdRequerimiento(requerimiento);s.setIdUsuarioSolicita(usuario);s.setFechaSolicitud(LocalDateTime.now());s.setEstado("PENDIENTE");s.setJustificacion(justificacion);return solicitudes.save(s);}
    @Transactional public Cotizacion cotizar(Cotizacion c){if(!proveedores.existsById(c.getIdProveedor()))throw new IllegalArgumentException("El proveedor debe estar registrado.");if(!solicitudes.existsById(c.getIdSolicitudCompra()))throw new IllegalArgumentException("Solicitud de compra no encontrada.");if(c.getFechaCotizacion()==null)c.setFechaCotizacion(LocalDate.now());c.setEstado("RECIBIDA");return cotizaciones.save(c);}
    @Transactional public Cotizacion aceptarCotizacion(Long id){Cotizacion c=cotizaciones.findById(id).orElseThrow(()->new IllegalArgumentException("Cotización no encontrada."));if(!proveedores.existsById(c.getIdProveedor()))throw new IllegalArgumentException("El proveedor ya no está registrado.");c.setEstado("ACEPTADA");cotizaciones.save(c);SolicitudCompra s=solicitudes.findById(c.getIdSolicitudCompra()).orElseThrow();s.setEstado("APROBADA");solicitudes.save(s);return c;}
    @Transactional public OrdenCompra emitir(Long solicitud,Long proveedor){Cotizacion c=cotizaciones.findByIdSolicitudCompraAndIdProveedor(solicitud,proveedor).orElseThrow(()->new IllegalArgumentException("Solo se emite compra con cotización registrada."));if(!"ACEPTADA".equals(c.getEstado()))throw new IllegalArgumentException("La cotización debe estar aceptada.");OrdenCompra o=new OrdenCompra();o.setIdSolicitudCompra(solicitud);o.setIdProveedor(proveedor);o.setFechaEmision(LocalDate.now());o.setEstado("EMITIDA");return ordenes.save(o);}
    @Transactional public DetalleOrdenCompra agregarDetalle(Long idOrden,Long material,java.math.BigDecimal cantidad,java.math.BigDecimal costo){OrdenCompra o=ordenes.findById(idOrden).orElseThrow(()->new IllegalArgumentException("Orden de compra no encontrada."));if(!"EMITIDA".equals(o.getEstado()))throw new IllegalArgumentException("Solo se agregan detalles a una orden emitida.");if(cantidad==null||cantidad.signum()<=0||costo==null||costo.signum()<0)throw new IllegalArgumentException("Cantidad/costo no válidos.");DetalleOrdenCompra d=new DetalleOrdenCompra();d.setIdOrdenCompra(idOrden);d.setIdMaterial(material);d.setCantidad(cantidad);d.setCostoUnitario(costo);return detalles.save(d);}
    @Transactional public OrdenCompra recibir(Long idOrden,Long usuario){OrdenCompra o=ordenes.findById(idOrden).orElseThrow(()->new IllegalArgumentException("Orden de compra no encontrada."));if(!"EMITIDA".equals(o.getEstado()))throw new IllegalArgumentException("La orden debe estar emitida y pendiente de recepción.");List<DetalleOrdenCompra> lineas=detalles.findByIdOrdenCompra(idOrden);if(lineas.isEmpty())throw new IllegalArgumentException("La orden no tiene detalles.");for(DetalleOrdenCompra d:lineas){MovimientoInventario m=new MovimientoInventario();m.setIdMaterial(d.getIdMaterial());m.setIdUsuario(usuario);m.setIdDetalleCompra(d.getIdDetalleCompra());m.setTipoMovimiento("INGRESO");m.setCantidad(d.getCantidad());m.setFechaHora(LocalDateTime.now());m.setJustificacion("Ingreso por orden de compra "+idOrden);movimientos.save(m);}o.setEstado("RECIBIDA");return ordenes.save(o);}
}
