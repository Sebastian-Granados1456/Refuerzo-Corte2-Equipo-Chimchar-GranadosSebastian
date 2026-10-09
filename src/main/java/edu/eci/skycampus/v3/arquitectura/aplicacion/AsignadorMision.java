package edu.eci.skycampus.v3.arquitectura.aplicacion;

import edu.eci.skycampus.v3.arquitectura.dominio.DroneEnterprise;
import edu.eci.skycampus.v3.arquitectura.dominio.RepositorioFlota;
import edu.eci.skycampus.v3.arquitectura.dominio.ServicioClima;
import edu.eci.skycampus.v3.model.Sede;

import java.util.List;
import java.util.Optional;

/** Aplicación: depende solo de interfaces de dominio (DIP), inyectadas por constructor. 100% testeable con mocks. */
public class AsignadorMision {

    private final RepositorioFlota repositorioFlota;
    private final ServicioClima servicioClima;

    public AsignadorMision(RepositorioFlota repositorioFlota, ServicioClima servicioClima) {
        this.repositorioFlota = repositorioFlota;
        this.servicioClima = servicioClima;
    }

    public Optional<DroneEnterprise> asignar(Sede sede, String origen, String destino) {
        if (!servicioClima.condicionesAptas(origen, destino)) {
            return Optional.empty();
        }
        List<DroneEnterprise> disponibles = repositorioFlota.findDisponibles(sede);
        return disponibles.stream().findFirst();
    }
}
