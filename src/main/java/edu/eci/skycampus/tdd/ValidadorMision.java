package edu.eci.skycampus.tdd;

import edu.eci.skycampus.model.Drone;

import java.util.List;

public class ValidadorMision {

    public boolean tieneBateriaSuficiente(Drone drone) {
        return drone.bateria() >= 30;
    }

    public void validarDestino(String destino) {
        List<String> destinos = List.of("Bloque A", "Bloque B", "Bloque C", "Bloque D", "Biblioteca");
        if (destino == null || !destinos.contains(destino)) {
            throw new DestinoInvalidoException("Destino inválido: " + destino);
        }
    }

    public boolean droneEstaDisponible(Drone drone) {
        if (drone == null) {
            throw new IllegalArgumentException("El drone no puede ser nulo");
        }
        return drone.disponible();
    }
}
