package com.electromatic.api.service;

import com.electromatic.api.model.recepcion.Cliente;
import com.electromatic.api.repository.recepcion.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    public List<Cliente> listar() { return repository.findAll(); }
    public Cliente obtener(Long id) { return repository.findById(id).orElse(null); }
    @Transactional public Cliente crear(Cliente cliente) {
        if (cliente.getDocumento() == null || cliente.getDocumento().isBlank()) throw new IllegalArgumentException("El documento es obligatorio.");
        if (repository.existsByDocumento(cliente.getDocumento())) throw new IllegalArgumentException("El documento ya está registrado.");
        cliente.setEstado("ACTIVO");
        return repository.save(cliente);
    }
    @Transactional public Cliente actualizar(Long id, Cliente cambios) {
        Cliente cliente = obtener(id); if (cliente == null) return null;
        if (cambios.getDocumento() != null && !cliente.getDocumento().equals(cambios.getDocumento()) && repository.existsByDocumento(cambios.getDocumento())) throw new IllegalArgumentException("El documento ya está registrado.");
        if (cambios.getTipoCliente() != null) cliente.setTipoCliente(cambios.getTipoCliente());
        if (cambios.getNombreRazonSocial() != null) cliente.setNombreRazonSocial(cambios.getNombreRazonSocial());
        if (cambios.getDocumento() != null) cliente.setDocumento(cambios.getDocumento());
        cliente.setTelefono(cambios.getTelefono()); cliente.setCorreo(cambios.getCorreo()); cliente.setDireccion(cambios.getDireccion());
        return repository.save(cliente);
    }
    @Transactional public Cliente desactivar(Long id) { Cliente cliente=obtener(id); if(cliente==null)return null; cliente.setEstado("INACTIVO"); return repository.save(cliente); }
}
