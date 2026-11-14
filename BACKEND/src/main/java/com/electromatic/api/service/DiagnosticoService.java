package com.electromatic.api.service;

import com.electromatic.api.model.tecnica.Diagnostico;
import org.springframework.stereotype.Service;

@Service public class DiagnosticoService {
    private final MantenimientoService mantenimiento;
    public DiagnosticoService(MantenimientoService mantenimiento){this.mantenimiento=mantenimiento;}
    public Diagnostico registrar(Diagnostico diagnostico){return mantenimiento.registrarDiagnostico(diagnostico);}
}
