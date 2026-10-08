package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.Prioridad;

import java.util.List;
import java.util.Optional;

public class AsignadorMision {

    private final ApiMeteorologica clima;
    private final EstrategiaAsignacion porBateria = new PorMayorBateria();
    private final EstrategiaAsignacion porVelocidad = new PorVelocidad();

    public AsignadorMision(ApiMeteorologica clima) {
        this.clima = clima;
    }

    /** SC-07: consulta clima, filtra por capacidad y aplica la estrategia según la prioridad. */
    public Optional<Drone> asignar(List<Drone> flota, Mision mision) {
        if (!clima.condicionesAptas(mision.destino())) {
            return Optional.empty();
        }
        List<Drone> aptos = filtrarPorCapacidadPeso(flota, mision.pesoPaqueteGramos());
        EstrategiaAsignacion estrategia = mision.prioridad() == Prioridad.URGENTE ? porVelocidad : porBateria;
        return estrategia.elegir(aptos, mision);
    }

    public List<Drone> filtrarPorCapacidadPeso(List<Drone> flota, int pesoPaqueteGramos) {
        return flota.stream()
                .filter(d -> d.tipo().capacidadMaximaGramos() >= pesoPaqueteGramos)
                .toList();
    }
}
