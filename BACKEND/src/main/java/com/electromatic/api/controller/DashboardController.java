package com.electromatic.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
    private final JdbcTemplate jdbc;public DashboardController(JdbcTemplate j){jdbc=j;}
    @GetMapping("/indicadores") public Map<String,Object> indicadores(HttpServletRequest request){Long user=(Long)request.getAttribute("idUsuarioAutenticado");Set<String> perms=new HashSet<>(jdbc.queryForList("SELECT DISTINCT p.nombre FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol AND r.estado='ACTIVO' JOIN rol_permiso rp ON rp.id_rol=r.id_rol JOIN permiso p ON p.id_permiso=rp.id_permiso WHERE ur.id_usuario=?",String.class,user));Map<String,Object> result=new LinkedHashMap<>();if(perms.contains("RECEPCION_GESTIONAR")||perms.contains("CLIENTES_VER")){result.put("ordenesTotales",count("SELECT COUNT(*) FROM orden_servicio"));result.put("ordenesRecepcionadas",count("SELECT COUNT(*) FROM orden_servicio WHERE id_estado_orden IN (1,2,3,4,5,6)"));}if(perms.contains("ALMACEN_GESTIONAR"))result.put("requerimientosPendientes",count("SELECT COUNT(*) FROM requerimiento_repuesto WHERE estado IN ('PENDIENTE','EN_COMPRA')"));if(perms.contains("CALIDAD_GESTIONAR"))result.put("ordenesEnCalidad",count("SELECT COUNT(*) FROM orden_servicio WHERE id_estado_orden=5"));if(perms.contains("ENTREGA_GESTIONAR"))result.put("ordenesListasEntrega",count("SELECT COUNT(*) FROM orden_servicio WHERE id_estado_orden=6"));return result;}
    private long count(String sql){Long n=jdbc.queryForObject(sql,Long.class);return n==null?0:n;}
}
