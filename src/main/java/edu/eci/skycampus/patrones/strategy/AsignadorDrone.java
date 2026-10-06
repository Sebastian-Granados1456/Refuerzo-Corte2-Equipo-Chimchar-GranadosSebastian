package edu.eci.skycampus.patrones.strategy;

import edu.eci.skycampus.model.Drone;

import java.util.List;
import java.util.Optional;

public class AsignadorDrone {
    private EstrategiaAsignacion estrategia;

    public AsignadorDrone(EstrategiaAsignacion estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaAsignacion estrategia) {
        this.estrategia = estrategia;
    }

    public Optional<Drone> asignar(List<Drone> flota) {
        return estrategia.elegir(flota);
    }
}
