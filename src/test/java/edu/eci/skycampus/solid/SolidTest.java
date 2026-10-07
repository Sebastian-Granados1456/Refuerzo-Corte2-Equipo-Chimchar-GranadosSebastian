package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolidTest {

    private final Drone d03 = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");
    private final Mision pendiente =
            new Mision("M-001", null, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE, 3, "", null);

    @Test
    void asignar_guardaMisionConDroneYEnviaAlerta() {
        RepositorioMision repo = new RepositorioMisionMemoria();
        List<String> alertas = new ArrayList<>();
        AsignadorMision asignador = new AsignadorMision(repo, (operador, mensaje) -> alertas.add(mensaje));

        Mision asignada = asignador.asignar(d03, pendiente);

        assertEquals(d03, asignada.drone());
        assertEquals(List.of(asignada), repo.listar());
        assertEquals(List.of("Misión M-001 asignada a D-03"), alertas);
    }

    @Test
    void generadorReporte_incluyeCadaMision() {
        Mision asignada = new AsignadorMision(new RepositorioMisionMemoria(), (o, m) -> { }).asignar(d03, pendiente);

        String reporte = new GeneradorReporte().generar(List.of(asignada));

        assertTrue(reporte.startsWith("Reporte de misiones (1)"));
        assertTrue(reporte.contains("M-001 | D-03 | Bloque A -> Biblioteca | PENDIENTE"));
    }

    @Test
    void estrategiasDeRuta_sonIntercambiables() {
        EstrategiaRuta directa = new RutaDirecta();
        EstrategiaRuta evitando = new RutaEvitandoEdificios();

        assertEquals("Bloque A -> Biblioteca", directa.calcular("Bloque A", "Biblioteca"));
        assertEquals("Bloque A -> Zona verde -> Biblioteca", evitando.calcular("Bloque A", "Biblioteca"));
    }

    @Test
    void alertaConsola_registraOperadorYMensaje() {
        Logger logger = Logger.getLogger(AlertaConsola.class.getName());
        List<String> registros = new ArrayList<>();
        Handler captura = new Handler() {
            @Override public void publish(LogRecord r) { registros.add(new SimpleFormatter().formatMessage(r)); }
            @Override public void flush() { }
            @Override public void close() { }
        };
        logger.addHandler(captura);
        try {
            new AlertaConsola().enviar("Operador", "Prueba");
        } finally {
            logger.removeHandler(captura);
        }
        assertEquals(List.of("[Alerta a Operador] Prueba"), registros);
    }
}
