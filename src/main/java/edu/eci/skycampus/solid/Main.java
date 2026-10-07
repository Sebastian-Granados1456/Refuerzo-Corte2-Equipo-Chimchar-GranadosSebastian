package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String BLOQUE_A = "Bloque A";
    private static final String BIBLIOTECA = "Biblioteca";

    public static void main(String[] args) {
        Drone d01 = new Drone("D-01", "DJI Mini 3", 85, true, BLOQUE_A);
        Drone d03 = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");
        Mision m1 = new Mision("M-001", null, BLOQUE_A, BIBLIOTECA, TipoCarga.SOBRE, EstadoMision.PENDIENTE, 3, "", null);
        Mision m2 = new Mision("M-002", null, "Bloque C", "Bloque B", TipoCarga.LIBRO, EstadoMision.PENDIENTE, 1, "", null);

        RepositorioMision repositorio = new RepositorioMisionMemoria();
        AsignadorMision asignador = new AsignadorMision(repositorio, new AlertaConsola());
        asignador.asignar(d01, m1);
        asignador.asignar(d03, m2);

        LOGGER.log(Level.INFO, "{0}", new GeneradorReporte().generar(repositorio.listar()));

        EstrategiaRuta ruta = new RutaDirecta();
        LOGGER.log(Level.INFO, "Ruta directa: {0}", ruta.calcular(BLOQUE_A, BIBLIOTECA));
        ruta = new RutaEvitandoEdificios();
        LOGGER.log(Level.INFO, "Ruta evitando edificios: {0}", ruta.calcular(BLOQUE_A, BIBLIOTECA));
    }
}
