package edu.eci.skycampus.patrones;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;
import edu.eci.skycampus.patrones.builder.MisionBuilder;
import edu.eci.skycampus.patrones.chain.Validador;
import edu.eci.skycampus.patrones.chain.ValidadorBateria;
import edu.eci.skycampus.patrones.chain.ValidadorCarga;
import edu.eci.skycampus.patrones.chain.ValidadorDestino;
import edu.eci.skycampus.patrones.strategy.AsignadorDrone;
import edu.eci.skycampus.patrones.strategy.EstrategiaAsignacion;
import edu.eci.skycampus.patrones.strategy.MayorBateria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatronesTest {

    private final Drone d01 = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");
    private final Drone d03 = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");
    private final Drone d04 = new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B");
    private final Drone d02 = new Drone("D-02", "DJI Mini 3", 95, false, "Biblioteca");

    private Validador cadena;

    @BeforeEach
    void setUp() {
        cadena = new ValidadorBateria();
        cadena.enlazar(new ValidadorDestino(List.of("Biblioteca", "Bloque A")))
              .enlazar(new ValidadorCarga(TipoCarga.CARPETA));
    }

    private MisionBuilder mision(Drone drone) {
        return new MisionBuilder().id("M-001").drone(drone).origen("Bloque C").destino("Biblioteca");
    }

    // Builder

    @Test
    void builder_conOpcionales_creaMisionPendiente() {
        LocalTime hora = LocalTime.of(10, 30);
        Mision m = mision(d03).tipoCarga(TipoCarga.CARPETA).prioridad(1).notas("Urgente").horaMaximaEntrega(hora).build();
        assertEquals(EstadoMision.PENDIENTE, m.estado());
        assertEquals(TipoCarga.CARPETA, m.tipoCarga());
        assertEquals(1, m.prioridad());
        assertEquals("Urgente", m.notas());
        assertEquals(hora, m.horaMaximaEntrega());
    }

    @Test
    void builder_sinOpcionales_usaValoresPorDefecto() {
        Mision m = mision(d03).build();
        assertEquals(TipoCarga.SOBRE, m.tipoCarga());
        assertEquals(3, m.prioridad());
        assertEquals("", m.notas());
    }

    @Test
    void builder_sinObligatorios_lanzaExcepcion() {
        MisionBuilder incompleto = new MisionBuilder().id("M-002").origen("Bloque A");
        assertThrows(IllegalStateException.class, incompleto::build);
    }

    // Chain of Responsibility

    @Test
    void cadena_misionValida_pasaTodosLosValidadores() {
        assertTrue(cadena.validar(mision(d03).tipoCarga(TipoCarga.CARPETA).build()));
    }

    @Test
    void cadena_bateriaBaja_rechaza() {
        assertFalse(cadena.validar(mision(d04).build()));
    }

    @Test
    void cadena_destinoNoPermitido_rechaza() {
        assertFalse(cadena.validar(mision(d03).destino("Bloque D").build()));
    }

    @Test
    void cadena_cargaSuperaCapacidad_rechaza() {
        assertFalse(cadena.validar(mision(d03).tipoCarga(TipoCarga.LIBRO).build()));
    }

    // Strategy

    @Test
    void mayorBateria_eligeDisponibleConMasBateria() {
        Optional<Drone> elegido = new MayorBateria().elegir(List.of(d01, d02, d03, d04));
        assertEquals(Optional.of(d03), elegido);
    }

    @Test
    void asignador_cambiarEstrategia_usaLaNueva() {
        AsignadorDrone asignador = new AsignadorDrone(new MayorBateria());
        assertEquals(Optional.of(d03), asignador.asignar(List.of(d01, d03)));

        EstrategiaAsignacion primero = flota -> flota.stream().findFirst();
        asignador.setEstrategia(primero);
        assertEquals(Optional.of(d01), asignador.asignar(List.of(d01, d03)));
    }
}
