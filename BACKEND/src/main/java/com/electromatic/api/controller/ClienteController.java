package com.electromatic.api.controller;

import com.electromatic.api.model.recepcion.Cliente;
import com.electromatic.api.service.ClienteService;
import com.electromatic.api.model.recepcion.Equipo;
import com.electromatic.api.model.recepcion.OrdenServicio;
import com.electromatic.api.repository.recepcion.ClienteRepository;
import com.electromatic.api.repository.recepcion.EquipoRepository;
import com.electromatic.api.repository.recepcion.OrdenServicioRepository;
import com.electromatic.api.service.EquipoService;
import com.electromatic.api.service.RecepcionOrdenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    private final ClienteRepository clientes;
    private final EquipoRepository equipos;
    private final OrdenServicioRepository ordenes;
    private final EquipoService equipoService;
    private final RecepcionOrdenService ordenService;
    public ClienteController(ClienteService service, ClienteRepository clientes, EquipoRepository equipos, OrdenServicioRepository ordenes, EquipoService equipoService, RecepcionOrdenService ordenService) { this.service=service; this.clientes=clientes; this.equipos=equipos; this.ordenes=ordenes; this.equipoService=equipoService; this.ordenService=ordenService; }
    @GetMapping public List<Cliente> listar(){return service.listar();}
    @GetMapping("/{id}") public ResponseEntity<Cliente> obtener(@PathVariable Long id){Cliente c=service.obtener(id);return c==null?ResponseEntity.notFound().build():ResponseEntity.ok(c);}
    @PostMapping public ResponseEntity<Cliente> crear(@RequestBody Map<String,Object> body){return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(adapt(body)));}
    /** Registro integral, transaccional y recuperable si el cliente ya se guardó en un intento anterior. */
    @PostMapping("/solicitudes") @Transactional
    public ResponseEntity<Map<String,Object>> registrarSolicitud(@RequestBody Map<String,Object> body, HttpServletRequest request) {
        Cliente solicitado=adapt(body);
        if(solicitado.getDocumento()==null||solicitado.getDocumento().isBlank()) throw new IllegalArgumentException("El documento es obligatorio.");
        Cliente cliente=clientes.findByDocumento(solicitado.getDocumento()).orElse(null);
        if(cliente==null) cliente=service.crear(solicitado);
        else {
            if(!"ACTIVO".equals(cliente.getEstado())) throw new IllegalArgumentException("El documento pertenece a un cliente inactivo.");
            if(!iguales(cliente.getNombreRazonSocial(),solicitado.getNombreRazonSocial()) || !iguales(cliente.getTelefono(),solicitado.getTelefono()) || !iguales(cliente.getCorreo(),solicitado.getCorreo()))
                throw new IllegalArgumentException("El documento ya está registrado con otros datos. Verifica el documento o contacta a recepción.");
        }
        Equipo solicitadoEquipo=new Equipo();
        solicitadoEquipo.setIdCliente(cliente.getIdCliente()); solicitadoEquipo.setIdTipoEquipo(num(body,"idTipoEquipo"));
        solicitadoEquipo.setMarca(str(body,"marca","MARCA")); solicitadoEquipo.setModelo(str(body,"modelo","MODELO"));
        solicitadoEquipo.setNumeroSerie(str(body,"numeroSerie","NUMERO_SERIE")); solicitadoEquipo.setDescripcion(str(body,"descripcion","DESCRIPCION"));
        Equipo equipo=null;
        for(Equipo existente:equipos.findAll()) if(cliente.getIdCliente().equals(existente.getIdCliente())&&equipoIgual(existente,solicitadoEquipo)){equipo=existente;break;}
        if(equipo==null) equipo=equipoService.registrar(solicitadoEquipo);
        Long actor=(Long)request.getAttribute("idUsuarioAutenticado");
        OrdenServicio orden=ordenes.findFirstByIdEquipoAndIdEstadoOrdenOrderByIdOrdenServicioDesc(equipo.getIdEquipo(),(short)1).orElse(null);
        if(orden==null) orden=ordenService.registrar(cliente.getIdCliente(),equipo.getIdEquipo(),actor,
                "Registrado desde el formulario de cliente",str(body,"descripcion","DESCRIPCION"),"");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("cliente",cliente,"equipo",equipo,"orden",orden));
    }
    @PutMapping("/{id}") public ResponseEntity<Cliente> actualizar(@PathVariable Long id,@RequestBody Map<String,Object> body){Cliente c=service.actualizar(id,adapt(body));return c==null?ResponseEntity.notFound().build():ResponseEntity.ok(c);}
    @PatchMapping("/{id}/desactivar") public ResponseEntity<Cliente> desactivar(@PathVariable Long id){Cliente c=service.desactivar(id);return c==null?ResponseEntity.notFound().build():ResponseEntity.ok(c);}
    private Cliente adapt(Map<String,Object> b){Cliente c=new Cliente();String first=s(b,"nomCliente"),last=s(b,"apeCliente");c.setNombreRazonSocial(or(s(b,"nombreRazonSocial"),(first+" "+last).trim()));c.setDocumento(or(s(b,"documento"),s(b,"dni")));c.setTipoCliente(or(s(b,"tipoCliente"),"PERSONA").toUpperCase());c.setTelefono(or(s(b,"telefono"),s(b,"numTel")));c.setCorreo(s(b,"correo"));c.setDireccion(or(s(b,"direccion"),s(b,"direcc")));return c;}
    private boolean iguales(String a,String b){return a==null?b==null:b!=null&&a.trim().equalsIgnoreCase(b.trim());}
    private boolean equipoIgual(Equipo a,Equipo b){return java.util.Objects.equals(a.getIdTipoEquipo(),b.getIdTipoEquipo())&&iguales(a.getMarca(),b.getMarca())&&iguales(a.getModelo(),b.getModelo())&&iguales(a.getNumeroSerie(),b.getNumeroSerie())&&iguales(a.getDescripcion(),b.getDescripcion());}
    private Long num(Map<String,Object>b,String k){Object v=b.get(k);if(v==null||v.toString().isBlank())throw new IllegalArgumentException("Falta el campo "+k+".");return Long.valueOf(v.toString());}
    private String str(Map<String,Object>b,String... keys){for(String k:keys){Object v=b.get(k);if(v!=null&&!v.toString().isBlank())return v.toString().trim();}return null;}
    private String s(Map<String,Object>b,String k){Object v=b.get(k);return v==null?null:v.toString().trim();} private String or(String a,String b){return a==null?b:a;}
}
