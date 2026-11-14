package com.electromatic.api;

import com.electromatic.api.model.seguridad.Sesion;
import com.electromatic.api.repository.seguridad.SesionRepository;
import com.electromatic.api.repository.seguridad.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.LocalDateTime;

@Component public class ApiAuthorizationInterceptor implements HandlerInterceptor {
    private final SessionTokenService tokens;private final SesionRepository sesiones;private final UsuarioRepository usuarios;private final JdbcTemplate jdbc;
    @Value("${app.security.allow-anonymous:true}") private boolean allowAnonymous;
    public ApiAuthorizationInterceptor(SessionTokenService t,SesionRepository s,UsuarioRepository u,JdbcTemplate j){tokens=t;sesiones=s;usuarios=u;jdbc=j;}
    @Override public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)throws Exception{
        String path=request.getRequestURI(),method=request.getMethod();if(method.equals("OPTIONS")||!path.startsWith("/api/")||path.equals("/api/auth/login")||path.equals("/api/auth/bootstrap-status")||path.startsWith("/api/recepcion/seguimiento/")||(path.equals("/api/entrega-pago/pagos/notificacion")&&method.equals("POST")))return true;
        String token=request.getHeader("X-Session-Token");
        if(allowAnonymous&&(token==null||token.isBlank())&&anonymousActor(request))return true;
        if(publicClientOperation(path,method))return true;
        if(bootstrap(path,method))return true;
        Long id=tokens.verify(request.getHeader("X-Session-Token"));if(id==null){response.sendError(401,"Sesión ausente o expirada.");return false;}
        Sesion session=sesiones.findById(id).orElse(null);if(session==null||!"ACTIVA".equals(session.getEstado())||session.getFechaExpiracion().isBefore(LocalDateTime.now())){response.sendError(401,"Sesión cerrada o expirada.");return false;}
        var user=usuarios.findById(session.getIdUsuario()).orElse(null);if(user==null||!"ACTIVO".equals(user.getEstado())){response.sendError(401,"Cuenta inactiva.");return false;}
        String permission=permission(path,method);if(permission!=null&&!hasPermission(user.getIdUsuario(),permission)){response.sendError(403,"El rol no tiene el permiso requerido: "+permission);return false;}
        request.setAttribute("idUsuarioAutenticado",user.getIdUsuario());request.setAttribute("idSesionAutenticada",id);return true;
    }
    private boolean anonymousActor(HttpServletRequest request){Long actor=jdbc.query("SELECT u.id_usuario FROM usuario u WHERE u.estado='ACTIVO' ORDER BY EXISTS (SELECT 1 FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol AND r.estado='ACTIVO' WHERE ur.id_usuario=u.id_usuario AND r.nombre='ADMINISTRADOR') DESC,u.id_usuario LIMIT 1",rs->rs.next()?rs.getLong(1):null);if(actor==null)return false;request.setAttribute("idUsuarioAutenticado",actor);return true;}
    private boolean publicClientOperation(String path,String method){return (path.equals("/api/recepcion/tipos-equipo")&&method.equals("GET"))||(path.equals("/api/clientes")&&(method.equals("GET")||method.equals("POST")))||(path.equals("/api/clientes/solicitudes")&&method.equals("POST"))||(path.equals("/api/recepcion/equipos")&&method.equals("POST"));}
    private boolean bootstrap(String path,String method){Integer users=jdbc.queryForObject("SELECT COUNT(*) FROM usuario",Integer.class);if(users!=null&&users>0)return false;if(path.equals("/api/seguridad/roles")&&method.equals("GET"))return true;if(path.equals("/api/seguridad/permisos")&&method.equals("GET"))return true;return path.equals("/api/seguridad/usuarios")&&method.equals("POST");}
    private String permission(String path,String method){if(path.startsWith("/api/auth/"))return null;if(path.startsWith("/api/seguridad/"))return "USUARIOS_GESTIONAR";if(path.startsWith("/api/clientes"))return method.equals("GET")?"CLIENTES_VER":"CLIENTES_GESTIONAR";if(path.startsWith("/api/dashboard/"))return "DASHBOARD_VER";if(path.startsWith("/api/reportes/"))return "REPORTES_VER";if(path.startsWith("/api/recepcion/"))return "RECEPCION_GESTIONAR";if(path.startsWith("/api/tecnico/"))return "TECNICA_GESTIONAR";if(path.startsWith("/api/almacen/"))return "ALMACEN_GESTIONAR";if(path.startsWith("/api/administracion/"))return "COMPRAS_GESTIONAR";if(path.startsWith("/api/calidad/"))return "CALIDAD_GESTIONAR";if(path.startsWith("/api/entrega-pago/"))return "ENTREGA_GESTIONAR";return null;}
    private boolean hasPermission(Long user,String permission){Integer n=jdbc.queryForObject("SELECT COUNT(*) FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol AND r.estado='ACTIVO' JOIN rol_permiso rp ON rp.id_rol=r.id_rol JOIN permiso p ON p.id_permiso=rp.id_permiso WHERE ur.id_usuario=? AND p.nombre=?",Integer.class,user,permission);return n!=null&&n>0;}
}
