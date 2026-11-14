package com.electromatic.api.service;

import com.electromatic.api.model.recepcion.Garantia;
import com.electromatic.api.repository.recepcion.GarantiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service public class GarantiaService {
    private final GarantiaRepository repo;public GarantiaService(GarantiaRepository r){repo=r;}
    @Transactional public Garantia crear(Garantia g){if(g.getFechaInicio()==null||g.getFechaFin()==null||!g.getFechaFin().isAfter(g.getFechaInicio()))throw new IllegalArgumentException("La fecha final debe ser posterior a la inicial.");g.setEstado(estado(g));return repo.save(g);}
    @Transactional public List<Garantia> listar(){List<Garantia> gs=repo.findAll();for(Garantia g:gs){if("ANULADA".equals(g.getEstado()))continue;String e=estado(g);if(!e.equals(g.getEstado())){g.setEstado(e);repo.save(g);}}return gs;}
    private String estado(Garantia g){return LocalDate.now().isAfter(g.getFechaFin())?"VENCIDA":"VIGENTE";}
}
