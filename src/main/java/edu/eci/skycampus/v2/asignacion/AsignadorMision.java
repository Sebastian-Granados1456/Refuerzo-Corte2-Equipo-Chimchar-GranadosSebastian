package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;

import java.util.List;
import java.util.Optional;

public class AsignadorMision {

    public Optional<Drone> elegirDrone(List<Drone> flota, Mision mision) {
        return flota.stream().filter(Drone::disponible).findFirst();
    }
}
