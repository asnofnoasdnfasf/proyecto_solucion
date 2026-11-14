package com.proyecto.backend.service;

import com.proyecto.backend.model.Cliente;
import com.proyecto.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    public List<Cliente> listar() { return repository.findAll(); }
    public Cliente buscar(Long id) { return repository.findById(id).orElse(null); }
    public boolean documentoEnUso(String documento) { return repository.existsByDocumento(documento); }
    @Transactional
    public Cliente guardar(Cliente cliente) { return repository.save(cliente); }
    @Transactional
    public Cliente desactivar(Long id) {
        Cliente cliente = buscar(id);
        if (cliente == null) return null;
        cliente.setEstado("INACTIVO");
        return repository.save(cliente);
    }
}
