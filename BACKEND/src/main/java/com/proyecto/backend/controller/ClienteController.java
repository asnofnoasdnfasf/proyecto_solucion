package com.proyecto.backend.controller;

import com.proyecto.backend.model.Cliente;
import com.proyecto.backend.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }

    @GetMapping
    public List<Cliente> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        Cliente cliente = service.buscar(id);
        return cliente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        Cliente cliente = mapBody(body, new Cliente());
        if (cliente.getDocumento() == null || cliente.getDocumento().isBlank())
            return ResponseEntity.badRequest().body(Map.of("mensaje", "El documento es obligatorio."));
        if (service.documentoEnUso(cliente.getDocumento()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", "El documento ya está registrado."));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Cliente actual = service.buscar(id);
        if (actual == null) return ResponseEntity.notFound().build();
            Cliente actualizado = mapBody(body, actual);
            if (actualizado.getDocumento() == null || actualizado.getDocumento().isBlank())
                return ResponseEntity.badRequest().body(Map.of("mensaje", "El documento es obligatorio."));
            if (!actual.getDocumento().equals(actualizado.getDocumento()) && service.documentoEnUso(actualizado.getDocumento()))
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", "El documento ya está registrado."));
            return ResponseEntity.ok(service.guardar(actualizado));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        Cliente cliente = service.desactivar(id);
        return cliente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cliente);
    }

    private Cliente mapBody(Map<String, Object> body, Cliente cliente) {
        String fullName = text(body, "nombreRazonSocial");
        if (fullName == null) {
            String first = text(body, "nomCliente"), last = text(body, "apeCliente");
            fullName = ((first == null ? "" : first) + " " + (last == null ? "" : last)).trim();
        }
        if (!fullName.isBlank()) cliente.setNombreRazonSocial(fullName);
        String doc = text(body, "documento"); if (doc == null) doc = text(body, "dni");
        if (doc != null) cliente.setDocumento(doc.trim());
        String phone = text(body, "telefono"); if (phone == null) phone = text(body, "numTel");
        if (phone != null) cliente.setTelefono(phone);
        String email = text(body, "correo"); if (email != null) cliente.setCorreo(email);
        String address = text(body, "direccion"); if (address == null) address = text(body, "direcc");
        if (address != null) cliente.setDireccion(address);
        String type = text(body, "tipoCliente"); if (type != null) cliente.setTipoCliente(type.toUpperCase());
        return cliente;
    }
    private String text(Map<String, Object> body, String key) {
        Object value = body.get(key); return value == null ? null : value.toString().trim();
    }
}
