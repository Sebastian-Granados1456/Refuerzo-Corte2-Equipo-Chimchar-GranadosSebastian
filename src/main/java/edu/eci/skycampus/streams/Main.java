package edu.eci.skycampus.streams;

import edu.eci.skycampus.model.Drone;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String MODELO = "DJI Mini 3";

    public static void main(String[] args) {
        List<Drone> flota = List.of(
                new Drone("D-01", MODELO, 85, true, "Bloque A"),
                new Drone("D-02", MODELO, 42, false, "Biblioteca"),
                new Drone("D-03", MODELO, 91, true, "Bloque C"),
                new Drone("D-04", MODELO, 18, true, "Bloque B"),
                new Drone("D-05", MODELO, 67, true, "Bloque D")
        );

        LOGGER.log(Level.INFO, "1. {0}", ConsultasFlota.disponiblesConBateriaAlta(flota));
        LOGGER.log(Level.INFO, "2. {0}", ConsultasFlota.hayDisponibleEn(flota, "Bloque C"));
        LOGGER.log(Level.INFO, "3. {0}", ConsultasFlota.contarBateriaCritica(flota));
        LOGGER.log(Level.INFO, "4. {0}", ConsultasFlota.resumenBaterias(flota));
    }
}
