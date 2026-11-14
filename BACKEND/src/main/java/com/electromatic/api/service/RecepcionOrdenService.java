package com.electromatic.api.service;

import com.electromatic.api.model.recepcion.*;
import com.electromatic.api.repository.recepcion.*;
import com.electromatic.api.model.recepcion.HistorialOrden;
import com.electromatic.api.repository.seguridad.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecepcionOrdenService {
    private final OrdenServicioRepository ordenes; private final ClienteRepository clientes; private final EquipoRepository equipos;private final HistorialOrdenRepository historial;private final UsuarioRepository usuarios;private final InspeccionRecepcionRepository inspecciones;private final AccesorioRecibidoRepository accesorios;
    public RecepcionOrdenService(OrdenServicioRepository o, ClienteRepository c, EquipoRepository e,HistorialOrdenRepository h,UsuarioRepository u,InspeccionRecepcionRepository i,AccesorioRecibidoRepository a){ordenes=o;clientes=c;equipos=e;historial=h;usuarios=u;inspecciones=i;accesorios=a;}
    @Transactional public OrdenServicio registrar(Long idCliente,Long idEquipo,Long idUsuario,String condicion,String danos,String listaAccesorios){
        Cliente cliente=clientes.findById(idCliente).orElseThrow(()->new IllegalArgumentException("Cliente no encontrado."));
        if(!"ACTIVO".equals(cliente.getEstado()))throw new IllegalArgumentException("No se puede registrar una orden para un cliente inactivo.");
        if(!usuarios.findById(idUsuario).filter(u->"ACTIVO".equals(u.getEstado())).isPresent())throw new IllegalArgumentException("El usuario que registra debe estar activo.");
        Equipo equipo=equipos.findById(idEquipo).orElseThrow(()->new IllegalArgumentException("Equipo no encontrado."));
        if(!idCliente.equals(equipo.getIdCliente()))throw new IllegalArgumentException("El equipo no pertenece al cliente indicado.");
        OrdenServicio o=new OrdenServicio();o.setIdCliente(idCliente);o.setIdEquipo(idEquipo);o.setIdUsuarioRegistra(idUsuario);o.setIdEstadoOrden((short)1);o.setFechaRecepcion(LocalDateTime.now());
        String codigo;do{codigo="OS-"+UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();}while(ordenes.existsByCodigoSeguimiento(codigo));o.setCodigoSeguimiento(codigo);
        o=ordenes.save(o);InspeccionRecepcion in=new InspeccionRecepcion();in.setIdOrdenServicio(o.getIdOrdenServicio());in.setCondicionFisica(condicion==null?"Sin detalle":condicion);in.setDanosVisibles(danos==null?"Sin detalle":danos);in.setObservaciones("Inspección registrada al recibir el equipo.");in.setFechaInspeccion(LocalDateTime.now());inspecciones.save(in);if(listaAccesorios!=null&&!listaAccesorios.isBlank()){for(String nombre:listaAccesorios.split(",")){if(!nombre.isBlank()){AccesorioRecibido a=new AccesorioRecibido();a.setIdOrdenServicio(o.getIdOrdenServicio());a.setNombre(nombre.trim());a.setCantidad(1);accesorios.save(a);}}}HistorialOrden h=new HistorialOrden();h.setIdOrdenServicio(o.getIdOrdenServicio());h.setIdUsuario(idUsuario);h.setFechaHora(LocalDateTime.now());h.setAccion("ORDEN_CREADA");h.setIdEstadoNuevo((short)1);h.setDetalle("Orden registrada en recepción.");historial.save(h);return o;
    }
}
