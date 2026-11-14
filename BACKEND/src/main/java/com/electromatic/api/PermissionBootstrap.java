package com.electromatic.api;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import com.electromatic.api.service.AuthService;

@Component public class PermissionBootstrap implements ApplicationRunner {
    private final JdbcTemplate jdbc;
    @Value("${app.security.allow-anonymous:true}") private boolean allowAnonymous;
    public PermissionBootstrap(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override @Transactional public void run(ApplicationArguments args){
        String[] roleNames={"ADMINISTRADOR","SUPERVISOR","RECEPCION","TECNICO","ALMACEN","ADMINISTRACION","CALIDAD","ENTREGA"};
        String[] perms={"USUARIOS_GESTIONAR","CLIENTES_VER","CLIENTES_GESTIONAR","RECEPCION_GESTIONAR","TECNICA_GESTIONAR","ALMACEN_GESTIONAR","COMPRAS_GESTIONAR","CALIDAD_GESTIONAR","ENTREGA_GESTIONAR","INVENTARIO_STOCK_NEGATIVO","ENTREGA_SIN_PAGO","REPORTES_VER","DASHBOARD_VER"};
        for(String r:roleNames)jdbc.update("INSERT IGNORE INTO rol(nombre,descripcion,estado) VALUES (?,?,'ACTIVO')",r,"Rol "+r);
        for(String p:perms)jdbc.update("INSERT IGNORE INTO permiso(nombre,descripcion) VALUES (?,?)",p,"Permiso "+p);
        assign("ADMINISTRADOR",List.of(perms));assign("SUPERVISOR",List.of("CLIENTES_VER","RECEPCION_GESTIONAR","TECNICA_GESTIONAR","ALMACEN_GESTIONAR","COMPRAS_GESTIONAR","CALIDAD_GESTIONAR","ENTREGA_GESTIONAR","REPORTES_VER","DASHBOARD_VER"));
        assign("RECEPCION",List.of("CLIENTES_VER","CLIENTES_GESTIONAR","RECEPCION_GESTIONAR"));assign("TECNICO",List.of("RECEPCION_GESTIONAR","TECNICA_GESTIONAR"));assign("ALMACEN",List.of("ALMACEN_GESTIONAR"));assign("ADMINISTRACION",List.of("COMPRAS_GESTIONAR","ENTREGA_GESTIONAR","REPORTES_VER","DASHBOARD_VER"));assign("CALIDAD",List.of("CALIDAD_GESTIONAR"));assign("ENTREGA",List.of("ENTREGA_GESTIONAR"));
        if(allowAnonymous)asegurarActorTecnico();
    }
    private void asegurarActorTecnico(){Integer activos=jdbc.queryForObject("SELECT COUNT(*) FROM usuario WHERE estado='ACTIVO'",Integer.class);if(activos!=null&&activos>0)return;String sufijo=UUID.randomUUID().toString().replace("-","").substring(0,12);String claveAleatoria=UUID.randomUUID().toString();jdbc.update("INSERT INTO usuario(nombre,correo_usuario,nombre_usuario,clave_hash,estado) VALUES (?,?,?,?,'ACTIVO')","Sistema de registro local","sistema_"+sufijo+"@local.invalid","sistema_"+sufijo,AuthService.hash(claveAleatoria));Long id=jdbc.queryForObject("SELECT id_usuario FROM usuario WHERE nombre_usuario=?",Long.class,"sistema_"+sufijo);Long admin=jdbc.queryForObject("SELECT id_rol FROM rol WHERE nombre='ADMINISTRADOR'",Long.class);jdbc.update("INSERT IGNORE INTO usuario_rol(id_usuario,id_rol) VALUES (?,?)",id,admin);}
    private void assign(String role,List<String> permissions){Long roleId=jdbc.queryForObject("SELECT id_rol FROM rol WHERE nombre=?",Long.class,role);for(String p:permissions){Long permissionId=jdbc.queryForObject("SELECT id_permiso FROM permiso WHERE nombre=?",Long.class,p);jdbc.update("INSERT IGNORE INTO rol_permiso(id_rol,id_permiso) VALUES (?,?)",roleId,permissionId);}}
}
