package edu.eci.skycampus.tdd;

import edu.eci.skycampus.model.Drone;

import java.util.List;

public class ValidadorMision {

    private static final int BATERIA_MINIMA = 30;
    private static final List<String> DESTINOS_VALIDOS =
            List.of("Bloque A", "Bloque B", "Bloque C", "Bloque D", "Biblioteca");

    public boolean tieneBateriaSuficiente(Drone drone) {
        return requerirDrone(drone).bateria() >= BATERIA_MINIMA;
    }

    public void validarDestino(String destino) {
        if (destino == null || !DESTINOS_VALIDOS.contains(destino)) {
            throw new DestinoInvalidoException(
                    "Destino inválido: " + destino + ". Destinos válidos: " + String.join(", ", DESTINOS_VALIDOS));
        }
    }

    public boolean droneEstaDisponible(Drone drone) {
        return requerirDrone(drone).disponible();
    }

    private Drone requerirDrone(Drone drone) {
        if (drone == null) {
            throw new IllegalArgumentException("El drone no puede ser nulo");
        }
        return drone;
    }
}
