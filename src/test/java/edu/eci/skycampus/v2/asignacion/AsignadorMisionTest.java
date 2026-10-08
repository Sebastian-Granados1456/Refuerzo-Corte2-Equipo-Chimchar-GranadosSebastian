package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.Prioridad;
import edu.eci.skycampus.v2.model.TipoDrone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsignadorMisionTest {

    @Mock
    private ApiMeteorologica clima;

    private AsignadorMision asignador;

    private final Drone mini = new Drone("D-03", TipoDrone.MINI, 91, true, EstadoDrone.DISPONIBLE, 10);
    private final Drone express = new Drone("D-14", TipoDrone.EXPRESS, 50, true, EstadoDrone.DISPONIBLE, 5);
    private final Drone cargo = new Drone("D-07", TipoDrone.CARGO, 70, true, EstadoDrone.DISPONIBLE, 2);

    @BeforeEach
    void setUp() {
        asignador = new AsignadorMision(clima);
    }

    private Mision mision(int peso, Prioridad prioridad) {
        return new Mision("MF-100", null, "Biblioteca", peso, prioridad, EstadoMision.PENDIENTE, LocalDateTime.now());
    }

    @Test
    void asignacionExitosa_climaApto_eligeMayorBateria() {
        when(clima.condicionesAptas("Biblioteca")).thenReturn(true);

        Optional<Drone> resultado = asignador.asignar(List.of(mini, cargo), mision(300, Prioridad.NORMAL));

        assertEquals(Optional.of(mini), resultado);
    }

    @Test
    void climaAdverso_noAsigna() {
        when(clima.condicionesAptas("Biblioteca")).thenReturn(false);

        Optional<Drone> resultado = asignador.asignar(List.of(mini, cargo), mision(300, Prioridad.NORMAL));

        assertTrue(resultado.isEmpty());
    }

    @Test
    void sinDronesAptos_noAsigna() {
        when(clima.condicionesAptas("Biblioteca")).thenReturn(true);
        Drone ocupado = new Drone("D-09", TipoDrone.MINI, 80, false, EstadoDrone.EN_VUELO, 1);

        Optional<Drone> resultado = asignador.asignar(List.of(ocupado), mision(300, Prioridad.NORMAL));

        assertTrue(resultado.isEmpty());
    }

    @Test
    void paqueteMuyPesado_noAsigna() {
        when(clima.condicionesAptas("Biblioteca")).thenReturn(true);

        Optional<Drone> resultado = asignador.asignar(List.of(mini, express, cargo), mision(2500, Prioridad.NORMAL));

        assertTrue(resultado.isEmpty());
    }

    @Test
    void misionUrgente_eligeElDroneExpress() {
        when(clima.condicionesAptas("Biblioteca")).thenReturn(true);

        Optional<Drone> resultado = asignador.asignar(List.of(mini, express, cargo), mision(300, Prioridad.URGENTE));

        assertEquals(Optional.of(express), resultado);
    }
}
