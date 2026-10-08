package edu.eci.skycampus.v2.observer;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;

import java.util.logging.Level;
import java.util.logging.Logger;

/** Avisa al técnico de mantenimiento solo cuando un drone entra en FALLO. */
public class AlertaTecnico implements ObservadorFlota {
    private static final Logger LOGGER = Logger.getLogger(AlertaTecnico.class.getName());

    @Override
    public void onCambioEstado(Drone drone, EstadoDrone nuevoEstado) {
        if (nuevoEstado == EstadoDrone.FALLO) {
            LOGGER.log(Level.WARNING, "[Técnico] {0} entró en FALLO, requiere mantenimiento", drone.id());
        }
    }
}
