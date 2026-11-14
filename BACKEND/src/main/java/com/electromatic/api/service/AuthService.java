package com.electromatic.api.service;

import com.electromatic.api.model.seguridad.*;
import com.electromatic.api.repository.seguridad.*;
import com.electromatic.api.SessionTokenService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service public class AuthService {
    public record LoginResult(Long idSesion,String token,String usuario,List<String> roles,List<String> permisos,LocalDateTime expira){}
    private final UsuarioRepository usuarios;private final SesionRepository sesiones;private final AuditoriaService auditoria;private final JdbcTemplate jdbc;private final SessionTokenService tokens;
    public AuthService(UsuarioRepository u,SesionRepository s,AuditoriaService a,JdbcTemplate j,SessionTokenService t){usuarios=u;sesiones=s;auditoria=a;jdbc=j;tokens=t;}
    @Transactional public LoginResult login(String nombreUsuario,String clave){
        Usuario u=usuarios.findByNombreUsuario(nombreUsuario).orElseThrow(()->new IllegalArgumentException("Credenciales no válidas."));
        if(!"ACTIVO".equals(u.getEstado())||!verificar(clave,u.getClaveHash()))throw new IllegalArgumentException("Credenciales no válidas o cuenta inactiva.");
        List<String> roles=jdbc.queryForList("SELECT r.nombre FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol WHERE ur.id_usuario=? AND r.estado='ACTIVO'",String.class,u.getIdUsuario());if(roles.isEmpty())throw new IllegalArgumentException("El usuario no tiene un rol habilitado.");
        List<String> permisos=jdbc.queryForList("SELECT DISTINCT p.nombre FROM usuario_rol ur JOIN rol r ON r.id_rol=ur.id_rol JOIN rol_permiso rp ON rp.id_rol=r.id_rol JOIN permiso p ON p.id_permiso=rp.id_permiso WHERE ur.id_usuario=? AND r.estado='ACTIVO' ORDER BY p.nombre",String.class,u.getIdUsuario());
        LocalDateTime now=LocalDateTime.now(),expiry=now.plusHours(8);Sesion s=new Sesion();s.setIdUsuario(u.getIdUsuario());s.setFechaInicio(now);s.setFechaExpiracion(expiry);s.setEstado("ACTIVA");s=sesiones.save(s);audit(u.getIdUsuario(),"LOGIN","EXITO","Inicio de sesión");return new LoginResult(s.getIdSesion(),tokens.issue(s.getIdSesion(),expiry),u.getNombreUsuario(),roles,permisos,expiry);
    }
    @Transactional public void logout(Long idSesion){Sesion s=sesiones.findById(idSesion).orElseThrow(()->new IllegalArgumentException("Sesión no encontrada."));if("ACTIVA".equals(s.getEstado())){s.setEstado("CERRADA");s.setFechaCierre(LocalDateTime.now());sesiones.save(s);audit(s.getIdUsuario(),"LOGOUT","EXITO","Cierre de sesión");}}
    public boolean necesitaUsuarioInicial(){return usuarios.count()==0;}
    private void audit(Long user,String action,String result,String detail){auditoria.registrar(user,action,result,detail,"sesion",null);}
    private boolean verificar(String password,String encoded){if(password==null||encoded==null)return false;String[] p=encoded.split("\\$",-1);if(p.length!=4||!"pbkdf2-sha256".equals(p[0]))return false;try{int rounds=Integer.parseInt(p[1]);byte[] salt=Base64.getDecoder().decode(p[2]),expected=Base64.getDecoder().decode(p[3]);PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,rounds,expected.length*8);byte[] actual=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();return MessageDigest.isEqual(expected,actual);}catch(Exception e){return false;}}
    public static String hash(String password){try{byte[] salt=new byte[16];new SecureRandom().nextBytes(salt);int rounds=210000;PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,rounds,256);byte[] hash=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();return "pbkdf2-sha256$"+rounds+"$"+Base64.getEncoder().withoutPadding().encodeToString(salt)+"$"+Base64.getEncoder().withoutPadding().encodeToString(hash);}catch(Exception e){throw new IllegalStateException(e);}}
}
