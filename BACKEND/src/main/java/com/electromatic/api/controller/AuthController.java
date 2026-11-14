package com.electromatic.api.controller;

import com.electromatic.api.service.AuthService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    public record Credenciales(String nombreUsuario,String clave){}
    private final AuthService service;public AuthController(AuthService s){service=s;}
    @PostMapping("/login") public AuthService.LoginResult login(@RequestBody Credenciales c){return service.login(c.nombreUsuario(),c.clave());}
    @PostMapping("/logout") public Map<String,String> logout(@RequestBody Map<String,Long>b,HttpServletRequest request){Long session=(Long)request.getAttribute("idSesionAutenticada");if(session==null||!session.equals(b.get("idSesion")))throw new IllegalArgumentException("La sesión no coincide con la solicitud.");service.logout(session);return Map.of("mensaje","Sesión cerrada.");}
    @GetMapping("/bootstrap-status") public Map<String,Boolean> bootstrapStatus(){return Map.of("necesitaUsuarioInicial",service.necesitaUsuarioInicial());}
}
