package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;

import java.util.List;
import java.util.Optional;

public interface EstrategiaAsignacion {
    Optional<Drone> elegir(List<Drone> flota, Mision mision);
}
