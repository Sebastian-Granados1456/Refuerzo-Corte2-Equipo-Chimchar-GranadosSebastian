package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AsignadorMision {

    private static final int BATERIA_MINIMA = 30;

    public Optional<Drone> elegirDrone(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.bateria() >= BATERIA_MINIMA)
                .max(Comparator.comparingInt(Drone::bateria));
    }
}
