package edu.eci.skycampus.patrones;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;
import edu.eci.skycampus.patrones.builder.MisionBuilder;
import edu.eci.skycampus.patrones.chain.Validador;
import edu.eci.skycampus.patrones.chain.ValidadorBateria;
import edu.eci.skycampus.patrones.chain.ValidadorCarga;
import edu.eci.skycampus.patrones.chain.ValidadorDestino;
import edu.eci.skycampus.patrones.strategy.AsignadorDrone;
import edu.eci.skycampus.patrones.strategy.MayorBateria;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String MODELO = "DJI Mini 3";
    private static final String BLOQUE_B = "Bloque B";
    private static final String BLOQUE_C = "Bloque C";
    private static final String BIBLIOTECA = "Biblioteca";

    public static void main(String[] args) {
        List<Drone> flota = List.of(
                new Drone("D-01", MODELO, 85, true, "Bloque A"),
                new Drone("D-03", MODELO, 91, true, BLOQUE_C),
                new Drone("D-04", MODELO, 18, true, BLOQUE_B)
        );

        // Strategy
        AsignadorDrone asignador = new AsignadorDrone(new MayorBateria());
        Drone elegido = asignador.asignar(flota).orElseThrow();
        LOGGER.log(Level.INFO, "Drone asignado: {0}", elegido.id());

        // Builder
        Mision mision = new MisionBuilder()
                .id("M-001").drone(elegido).origen(BLOQUE_C).destino(BIBLIOTECA)
                .tipoCarga(TipoCarga.CARPETA)
                .notas("Urgente: examen mañana")
                .build();
        LOGGER.log(Level.INFO, "Misión creada: {0}", mision);

        // Chain of Responsibility
        Validador cadena = new ValidadorBateria();
        cadena.enlazar(new ValidadorDestino(List.of(BIBLIOTECA, "Bloque A", BLOQUE_B, BLOQUE_C)))
              .enlazar(new ValidadorCarga(TipoCarga.CARPETA));
        LOGGER.log(Level.INFO, "¿Misión válida? {0}", cadena.validar(mision));

        Mision conD04 = new MisionBuilder()
                .id("M-002").drone(flota.get(2)).origen(BLOQUE_B).destino(BIBLIOTECA).build();
        LOGGER.log(Level.INFO, "¿Misión con D-04 (18%) válida? {0}", cadena.validar(conD04));
    }
}
