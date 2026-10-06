package edu.eci.skycampus.patrones.strategy;

import edu.eci.skycampus.model.Drone;

import java.util.List;
import java.util.Optional;

public interface EstrategiaAsignacion {
    Optional<Drone> elegir(List<Drone> flota);
}
