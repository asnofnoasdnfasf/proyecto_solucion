package com.electromatic.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/reportes")
public class ReportesController {
    private final JdbcTemplate jdbc;public ReportesController(JdbcTemplate j){jdbc=j;}
    @GetMapping("/ordenes") public List<Map<String,Object>> ordenes(HttpServletRequest request){autorizar(request);return jdbc.queryForList("SELECT o.id_orden_servicio,o.codigo_seguimiento,o.fecha_recepcion,e.nombre AS estado,c.id_cliente,c.nombre_razon_social AS cliente,eq.marca,eq.modelo FROM orden_servicio o JOIN estado_orden e ON e.id_estado_orden=o.id_estado_orden JOIN cliente c ON c.id_cliente=o.id_cliente JOIN equipo eq ON eq.id_equipo=o.id_equipo ORDER BY o.fecha_recepcion DESC");}
    @GetMapping(value="/ordenes.csv",produces="text/csv") public ResponseEntity<String> exportar(HttpServletRequest request){List<Map<String,Object>> rows=ordenes(request);StringBuilder csv=new StringBuilder("id_orden,codigo_seguimiento,fecha_recepcion,estado,cliente,marca,modelo\r\n");for(Map<String,Object> r:rows)csv.append(csv(r.get("id_orden_servicio"))).append(',').append(csv(r.get("codigo_seguimiento"))).append(',').append(csv(r.get("fecha_recepcion"))).append(',').append(csv(r.get("estado"))).append(',').append(csv(r.get("cliente"))).append(',').append(csv(r.get("marca"))).append(',').append(csv(r.get("modelo"))).append("\r\n");return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=electromatic-ordenes.csv").contentType(MediaType.parseMediaType("text/csv; charset=utf-8")).body(csv.toString());}
    private void autorizar(HttpServletRequest req){Long user=(Long)req.getAttribute("idUsuarioAutenticado");Set<String> roles=Set.copyOf(jdbc.queryForList("SELECT r.nombre FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol AND r.estado='ACTIVO' WHERE ur.id_usuario=?",String.class,user));if(!roles.contains("SUPERVISOR")&&!roles.contains("ADMINISTRACION")&&!roles.contains("ADMINISTRADOR"))throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN,"Solo Supervisor o Administración puede consultar reportes.");}
    private String csv(Object v){String s=v==null?"":v.toString();return "\""+s.replace("\"","\"\"")+"\"";}
}
