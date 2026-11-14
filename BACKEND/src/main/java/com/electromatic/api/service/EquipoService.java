package com.electromatic.api.service;

import com.electromatic.api.model.recepcion.*;
import com.electromatic.api.repository.recepcion.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service public class EquipoService {
    private final EquipoRepository equipos;private final ClienteRepository clientes;private final TipoEquipoRepository tipos;
    public EquipoService(EquipoRepository e,ClienteRepository c,TipoEquipoRepository t){equipos=e;clientes=c;tipos=t;}
    public List<Equipo> listar(){return equipos.findAll();}
    @Transactional public Equipo registrar(Equipo e){Cliente c=clientes.findById(e.getIdCliente()).orElseThrow(()->new IllegalArgumentException("Cliente no encontrado."));if(!"ACTIVO".equals(c.getEstado()))throw new IllegalArgumentException("Solo se registra equipo para cliente activo.");if(!tipos.existsById(e.getIdTipoEquipo()))throw new IllegalArgumentException("Tipo de equipo no encontrado.");return equipos.save(e);}
}
