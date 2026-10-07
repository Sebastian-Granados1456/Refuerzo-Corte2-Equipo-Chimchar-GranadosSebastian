package edu.eci.skycampus.v2.observer;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;

import java.util.logging.Level;
import java.util.logging.Logger;

/** Refleja en el panel del operador el último estado conocido de cada drone. */
public class PanelOperador implements ObservadorFlota {
    private static final Logger LOGGER = Logger.getLogger(PanelOperador.class.getName());

    @Override
    public void onCambioEstado(Drone drone, EstadoDrone nuevoEstado) {
        LOGGER.log(Level.INFO, "[Panel] {0} ahora está {1}", new Object[]{drone.id(), nuevoEstado});
    }
}
