package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.Prioridad;
import edu.eci.skycampus.v2.model.TipoDrone;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstrategiaAsignacionTest {

    private final Drone mini = new Drone("M-01", TipoDrone.MINI, 60, true, EstadoDrone.DISPONIBLE, 20);
    private final Drone cargo = new Drone("C-01", TipoDrone.CARGO, 90, true, EstadoDrone.DISPONIBLE, 2);
    private final Drone express = new Drone("E-01", TipoDrone.EXPRESS, 40, true, EstadoDrone.DISPONIBLE, 8);
    private final Drone noDisponible = new Drone("E-02", TipoDrone.EXPRESS, 95, false, EstadoDrone.EN_VUELO, 1);

    private final List<Drone> flota = List.of(mini, cargo, express, noDisponible);
    private final Mision misionLiviana = new Mision("MF-001", null, "Bloque A", 300, Prioridad.NORMAL,
            EstadoMision.PENDIENTE, LocalDateTime.now());
    private final Mision misionPesada = new Mision("MF-002", null, "Bloque A", 1500, Prioridad.NORMAL,
            EstadoMision.PENDIENTE, LocalDateTime.now());

    @Test
    void porMayorBateria_eligeElDeMasBateriaEntreDisponibles() {
        Optional<Drone> elegido = new PorMayorBateria().elegir(flota, misionLiviana);
        assertEquals(Optional.of(cargo), elegido);
    }

    @Test
    void porMenorUsoAcumulado_eligeElDeMenosMisionesEntreDisponibles() {
        Optional<Drone> elegido = new PorMenorUsoAcumulado().elegir(flota, misionLiviana);
        assertEquals(Optional.of(cargo), elegido);
    }

    @Test
    void porTipoCompatibleConCarga_pesoLiviano_eligeMayorBateriaCompatible() {
        Optional<Drone> elegido = new PorTipoCompatibleConCarga().elegir(flota, misionLiviana);
        assertEquals(Optional.of(cargo), elegido);
    }

    @Test
    void porTipoCompatibleConCarga_pesoSoloAptoParaCargo_descartaMiniYExpress() {
        Optional<Drone> elegido = new PorTipoCompatibleConCarga().elegir(flota, misionPesada);
        assertEquals(Optional.of(cargo), elegido);
    }

    @Test
    void gestorFlota_cambiarEstrategiaEnTiempoDeEjecucion() {
        GestorFlota gestor = new GestorFlota(new PorMayorBateria());
        assertEquals(Optional.of(cargo), gestor.asignar(flota, misionLiviana));

        gestor.setEstrategia(new PorMenorUsoAcumulado());
        assertTrue(gestor.asignar(flota, misionLiviana).isPresent());
    }
}
