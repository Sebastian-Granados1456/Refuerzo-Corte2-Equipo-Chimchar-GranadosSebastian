package edu.eci.skycampus.v3.arquitectura.aplicacion;

import edu.eci.skycampus.v2.model.TipoDrone;
import edu.eci.skycampus.v3.arquitectura.dominio.Aerocivil;
import edu.eci.skycampus.v3.arquitectura.dominio.DroneEnterprise;
import edu.eci.skycampus.v3.arquitectura.dominio.RepositorioFlota;
import edu.eci.skycampus.v3.arquitectura.dominio.ServicioClima;
import edu.eci.skycampus.v3.model.Sede;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de la capa de aplicación con Mockito: ninguna llamada HTTP real ni base de
 * datos. RepositorioFlota, ServicioClima y Aerocivil son interfaces de dominio simuladas con mocks.
 */
@ExtendWith(MockitoExtension.class)
class AsignadorMisionTest {

    @Mock
    private RepositorioFlota repositorioFlota;

    @Mock
    private ServicioClima servicioClima;

    @Mock
    private Aerocivil aerocivil;

    private AsignadorMision asignador;

    @BeforeEach
    void setUp() {
        asignador = new AsignadorMision(repositorioFlota, servicioClima, aerocivil);
        lenient().when(repositorioFlota.sedeActiva(Sede.ECI)).thenReturn(true);
        lenient().when(aerocivil.autorizaVuelo("ECI", "UNAL")).thenReturn(true);
        lenient().when(servicioClima.condicionesAptas("ECI", "UNAL")).thenReturn(true);
    }

    @Test
    void todoCorrecto_asignaElDroneAptoConMasBateria() {
        DroneEnterprise bajo = new DroneEnterprise("D-01", Sede.ECI, TipoDrone.MINI, 40, true);
        DroneEnterprise alto = new DroneEnterprise("D-02", Sede.ECI, TipoDrone.MINI, 90, true);
        when(repositorioFlota.findDisponibles(Sede.ECI)).thenReturn(List.of(bajo, alto));

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL", 300);

        assertEquals(Optional.of(alto), resultado);
    }

    // Flujo alterno 1: clima adverso
    @Test
    void climaAdverso_noAsigna() {
        when(servicioClima.condicionesAptas("ECI", "UNAL")).thenReturn(false);

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL", 300);

        assertTrue(resultado.isEmpty());
    }

    // Flujo alterno 2: sin drones disponibles
    @Test
    void sinDronesDisponibles_noAsigna() {
        when(repositorioFlota.findDisponibles(Sede.ECI)).thenReturn(List.of());

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL", 300);

        assertTrue(resultado.isEmpty());
    }

    // Flujo alterno 3: paquete demasiado pesado para los drones disponibles
    @Test
    void paqueteMuyPesado_noAsigna() {
        DroneEnterprise mini = new DroneEnterprise("D-01", Sede.ECI, TipoDrone.MINI, 90, true);
        when(repositorioFlota.findDisponibles(Sede.ECI)).thenReturn(List.of(mini));

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL", 2500);

        assertTrue(resultado.isEmpty());
    }

    // Flujo alterno 4: la Aerocivil rechaza el vuelo
    @Test
    void aerocivilRechaza_noAsignaYNoConsultaClimaNiFlota() {
        when(aerocivil.autorizaVuelo("ECI", "UNAL")).thenReturn(false);

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL", 300);

        assertTrue(resultado.isEmpty());
        verifyNoInteractions(servicioClima);
    }

    // Flujo alterno 5: la sede no está activa
    @Test
    void sedeInactiva_noAsignaYNoConsultaNadaMas() {
        when(repositorioFlota.sedeActiva(Sede.ECI)).thenReturn(false);

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL", 300);

        assertTrue(resultado.isEmpty());
        verifyNoInteractions(aerocivil, servicioClima);
    }
}
