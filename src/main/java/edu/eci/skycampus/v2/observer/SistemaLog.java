package edu.eci.skycampus.v2.observer;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;

import java.util.logging.Level;
import java.util.logging.Logger;

/** Registra cada transición de estado para trazabilidad y auditoría. */
public class SistemaLog implements ObservadorFlota {
    private static final Logger LOGGER = Logger.getLogger(SistemaLog.class.getName());

    @Override
    public void onCambioEstado(Drone drone, EstadoDrone nuevoEstado) {
        LOGGER.log(Level.INFO, "[Log] drone={0} estado={1}", new Object[]{drone.id(), nuevoEstado});
    }
}
