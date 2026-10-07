package edu.eci.skycampus.tdd;

import edu.eci.skycampus.model.Drone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorMisionTest {

    private ValidadorMision v;

    @BeforeEach
    void setUp() {
        v = new ValidadorMision();
    }

    // tieneBateriaSuficiente

    @Test
    @DisplayName("Drone con batería ≥ 30% puede ser asignado")
    void droneBateriaSuficiente_puedeAsignarse() {
        // ARRANGE
        Drone d = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");
        // ACT
        boolean resultado = v.tieneBateriaSuficiente(d);
        // ASSERT
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Drone con batería < 30% NO puede ser asignado")
    void droneBateriaCritica_noAsignable() {
        // ARRANGE
        Drone d = new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B");
        // ACT
        boolean resultado = v.tieneBateriaSuficiente(d);
        // ASSERT
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Drone con batería exactamente en 30% puede ser asignado")
    void droneBateriaEnElLimite_puedeAsignarse() {
        // ARRANGE
        Drone d = new Drone("D-02", "DJI Mini 3", 30, true, "Biblioteca");
        // ACT
        boolean resultado = v.tieneBateriaSuficiente(d);
        // ASSERT
        assertTrue(resultado);
    }

    // validarDestino

    @Test
    @DisplayName("Destino fijo del campus es válido")
    void destinoValido_noLanzaExcepcion() {
        // ARRANGE
        String destino = "Biblioteca";
        // ACT + ASSERT
        assertDoesNotThrow(() -> v.validarDestino(destino));
    }

    @Test
    @DisplayName("Destino inválido lanza excepción")
    void destinoInvalido_lanzaExcepcion() {
        // ARRANGE
        String destino = "Edificio Inexistente";
        // ACT + ASSERT
        assertThrows(DestinoInvalidoException.class, () -> v.validarDestino(destino));
    }

    @Test
    @DisplayName("Destino vacío o nulo lanza excepción")
    void destinoVacio_lanzaExcepcion() {
        // ARRANGE
        String vacio = "";
        // ACT + ASSERT
        assertThrows(DestinoInvalidoException.class, () -> v.validarDestino(vacio));
        assertThrows(DestinoInvalidoException.class, () -> v.validarDestino(null));
    }

    // droneEstaDisponible

    @Test
    @DisplayName("Drone marcado como disponible está disponible")
    void droneDisponible_retornaTrue() {
        // ARRANGE
        Drone d = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");
        // ACT
        boolean resultado = v.droneEstaDisponible(d);
        // ASSERT
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Drone en misión NO está disponible")
    void droneEnMision_retornaFalse() {
        // ARRANGE
        Drone d = new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca");
        // ACT
        boolean resultado = v.droneEstaDisponible(d);
        // ASSERT
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Drone nulo lanza excepción")
    void droneNulo_lanzaExcepcion() {
        // ARRANGE
        Drone d = null;
        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> v.droneEstaDisponible(d));
    }
}
