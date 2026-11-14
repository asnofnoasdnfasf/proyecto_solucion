package com.electromatic.api.controller;

import com.electromatic.api.model.seguridad.*;
import com.electromatic.api.repository.seguridad.*;
import com.electromatic.api.service.AuthService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/seguridad")
public class SeguridadController {
    public record UsuarioRequest(String nombre,String correoUsuario,String nombreUsuario,String clave,Long idRol){}
    private final UsuarioRepository usuarios;private final RolRepository roles;private final PermisoRepository permisos;private final JdbcTemplate jdbc;
    public SeguridadController(UsuarioRepository u,RolRepository r,PermisoRepository p,JdbcTemplate j){usuarios=u;roles=r;permisos=p;jdbc=j;}
    @GetMapping("/usuarios") public List<Map<String,Object>> usuarios(){return usuarios.findAll().stream().map(u->Map.<String,Object>of("idUsuario",u.getIdUsuario(),"nombre",u.getNombre(),"correoUsuario",u.getCorreoUsuario(),"nombreUsuario",u.getNombreUsuario(),"estado",u.getEstado())).toList();}
    @PostMapping("/usuarios") @Transactional public Map<String,Object> crear(@RequestBody UsuarioRequest r){if(r.clave()==null||r.clave().length()<10)throw new IllegalArgumentException("La clave debe tener al menos 10 caracteres.");if(usuarios.findByNombreUsuario(r.nombreUsuario()).isPresent()||usuarios.existsByCorreoUsuario(r.correoUsuario()))throw new IllegalArgumentException("Correo o nombre de usuario ya registrado.");Long rolSolicitado=r.idRol();if(usuarios.count()==0)rolSolicitado=roles.findByNombre("ADMINISTRADOR").orElseThrow(()->new IllegalStateException("No se configuró el rol administrador inicial.")).getIdRol();Rol rol=roles.findById(rolSolicitado).orElseThrow(()->new IllegalArgumentException("Rol no encontrado."));if(!"ACTIVO".equals(rol.getEstado()))throw new IllegalArgumentException("El rol debe estar habilitado.");Usuario u=new Usuario();u.setNombre(r.nombre());u.setCorreoUsuario(r.correoUsuario());u.setNombreUsuario(r.nombreUsuario());u.setClaveHash(AuthService.hash(r.clave()));u.setEstado("ACTIVO");u=usuarios.save(u);jdbc.update("INSERT INTO usuario_rol(id_usuario,id_rol) VALUES (?,?)",u.getIdUsuario(),rolSolicitado);return Map.of("idUsuario",u.getIdUsuario(),"nombreUsuario",u.getNombreUsuario(),"estado",u.getEstado());}
    @PatchMapping("/usuarios/{id}/desactivar") public Map<String,Object> desactivar(@PathVariable Long id){Usuario u=usuarios.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado."));u.setEstado("INACTIVO");usuarios.save(u);jdbc.update("UPDATE sesion SET estado='CERRADA',fecha_cierre=NOW(6) WHERE id_usuario=? AND estado='ACTIVA'",id);return Map.of("idUsuario",id,"estado","INACTIVO");}
    @GetMapping("/roles") public List<Rol> roles(){return roles.findAll();}
    @PostMapping("/roles") public Rol crearRol(@RequestBody Rol r){if(r.getNombre()==null||r.getNombre().isBlank())throw new IllegalArgumentException("Nombre de rol obligatorio.");if(r.getEstado()==null)r.setEstado("ACTIVO");return roles.save(r);}
    @GetMapping("/permisos") public List<Permiso> permisos(){return permisos.findAll();}
    @PostMapping("/permisos") public Permiso crearPermiso(@RequestBody Permiso p){if(p.getNombre()==null||p.getNombre().isBlank())throw new IllegalArgumentException("Nombre de permiso obligatorio.");return permisos.save(p);}
    @PutMapping("/roles/{idRol}/permisos/{idPermiso}") public Map<String,String> asignarPermiso(@PathVariable Long idRol,@PathVariable Long idPermiso){if(!roles.existsById(idRol)||!permisos.existsById(idPermiso))throw new IllegalArgumentException("Rol o permiso no encontrado.");Integer n=jdbc.update("INSERT IGNORE INTO rol_permiso(id_rol,id_permiso) VALUES (?,?)",idRol,idPermiso);return Map.of("resultado",n==0?"YA_ASIGNADO":"ASIGNADO");}
}
