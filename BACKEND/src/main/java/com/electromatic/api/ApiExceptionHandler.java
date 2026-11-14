package com.electromatic.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("mensaje",e.getMessage()==null?"Solicitud inválida.":e.getMessage()));}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> internal(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensaje","No se pudo completar la operación."));}
}
