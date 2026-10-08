package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Elige el drone disponible con menos misiones completadas, para repartir el desgaste de la flota. */
public class PorMenorUsoAcumulado implements EstrategiaAsignacion {

    @Override
    public Optional<Drone> elegir(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(Drone::disponible)
                .min(Comparator.comparingInt(Drone::misionesCompletadas));
    }
}
