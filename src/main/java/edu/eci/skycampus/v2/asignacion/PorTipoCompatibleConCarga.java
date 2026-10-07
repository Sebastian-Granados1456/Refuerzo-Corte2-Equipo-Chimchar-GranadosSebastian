package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Elige, entre los drones cuyo tipo soporta el peso del paquete, el de mayor batería. */
public class PorTipoCompatibleConCarga implements EstrategiaAsignacion {

    @Override
    public Optional<Drone> elegir(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.tipo().capacidadMaximaGramos() >= mision.pesoPaqueteGramos())
                .max(Comparator.comparingInt(Drone::bateria));
    }
}
