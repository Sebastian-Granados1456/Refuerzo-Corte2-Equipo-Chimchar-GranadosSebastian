package edu.eci.skycampus.v2.observer;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;

public interface ObservadorFlota {
    void onCambioEstado(Drone drone, EstadoDrone nuevoEstado);
}
