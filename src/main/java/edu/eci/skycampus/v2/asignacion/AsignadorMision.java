package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.TipoDrone;

import java.util.List;
import java.util.Optional;

public class AsignadorMision {

    public Optional<Drone> elegirDrone(List<Drone> flota, Mision mision) {
        return flota.stream().filter(Drone::disponible).findFirst();
    }

    public List<Drone> filtrarPorCapacidadPeso(List<Drone> flota, int pesoPaqueteGramos) {
        return flota.stream()
                .filter(d -> capacidadMaximaGramos(d.tipo()) >= pesoPaqueteGramos)
                .toList();
    }

    private int capacidadMaximaGramos(TipoDrone tipo) {
        return switch (tipo) {
            case MINI -> 500;
            case EXPRESS -> 800;
            case CARGO -> 2000;
        };
    }
}
