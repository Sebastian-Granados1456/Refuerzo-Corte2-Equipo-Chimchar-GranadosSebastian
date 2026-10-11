package edu.eci.skycampus.v3.arquitectura.aplicacion;

import edu.eci.skycampus.v3.arquitectura.dominio.Aerocivil;
import edu.eci.skycampus.v3.arquitectura.dominio.DroneEnterprise;
import edu.eci.skycampus.v3.arquitectura.dominio.RepositorioFlota;
import edu.eci.skycampus.v3.arquitectura.dominio.ServicioClima;
import edu.eci.skycampus.v3.model.Sede;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Aplicación: depende solo de interfaces de dominio (DIP), inyectadas por constructor. 100% testeable con mocks. */
public class AsignadorMision {

    private final RepositorioFlota repositorioFlota;
    private final ServicioClima servicioClima;
    private final Aerocivil aerocivil;

    public AsignadorMision(RepositorioFlota repositorioFlota, ServicioClima servicioClima, Aerocivil aerocivil) {
        this.repositorioFlota = repositorioFlota;
        this.servicioClima = servicioClima;
        this.aerocivil = aerocivil;
    }

    public Optional<DroneEnterprise> asignar(Sede sede, String origen, String destino, int pesoPaqueteGramos) {
        if (!repositorioFlota.sedeActiva(sede)) {
            return Optional.empty();
        }
        if (!aerocivil.autorizaVuelo(origen, destino)) {
            return Optional.empty();
        }
        if (!servicioClima.condicionesAptas(origen, destino)) {
            return Optional.empty();
        }
        List<DroneEnterprise> aptos = repositorioFlota.findDisponibles(sede).stream()
                .filter(d -> d.tipo().capacidadMaximaGramos() >= pesoPaqueteGramos)
                .toList();
        return aptos.stream().max(Comparator.comparingInt(DroneEnterprise::bateria));
    }
}
