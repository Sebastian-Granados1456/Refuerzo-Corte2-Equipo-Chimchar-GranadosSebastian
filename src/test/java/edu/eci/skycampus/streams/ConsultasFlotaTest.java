package edu.eci.skycampus.streams;

import edu.eci.skycampus.model.Drone;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsultasFlotaTest {

    private final List<Drone> flota = List.of(
            new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
            new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca"),
            new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C"),
            new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B"),
            new Drone("D-05", "DJI Mini 3", 67, true, "Bloque D")
    );

    @Test
    void disponiblesConBateriaAlta_ordenadosDeMayorAMenor() {
        List<String> ids = ConsultasFlota.disponiblesConBateriaAlta(flota);
        assertEquals(List.of("D-03", "D-01", "D-05"), ids);
    }

    @Test
    void hayDisponibleEn_bloqueConDroneDisponible() {
        assertTrue(ConsultasFlota.hayDisponibleEn(flota, "Bloque C"));
    }

    @Test
    void hayDisponibleEn_bloqueSoloConDroneEnMision() {
        assertFalse(ConsultasFlota.hayDisponibleEn(flota, "Biblioteca"));
    }

    @Test
    void contarBateriaCritica_cuentaMenoresDe20() {
        assertEquals(1, ConsultasFlota.contarBateriaCritica(flota));
    }

    @Test
    void resumenBaterias_formatoIdPorcentaje() {
        List<String> resumen = ConsultasFlota.resumenBaterias(flota);
        assertEquals(5, resumen.size());
        assertEquals("D-01: 85%", resumen.get(0));
    }
}
