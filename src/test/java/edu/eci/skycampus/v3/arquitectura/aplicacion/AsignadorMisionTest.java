package edu.eci.skycampus.v3.arquitectura.aplicacion;

import edu.eci.skycampus.v2.model.TipoDrone;
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
import static org.mockito.Mockito.when;

/**
 * Prueba la capa de aplicación con Mockito: ninguna llamada HTTP real ni base de datos.
 * RepositorioFlota y ServicioClima son interfaces de dominio; aquí se simulan con mocks.
 */
@ExtendWith(MockitoExtension.class)
class AsignadorMisionTest {

    @Mock
    private RepositorioFlota repositorioFlota;

    @Mock
    private ServicioClima servicioClima;

    private AsignadorMision asignador;

    @BeforeEach
    void setUp() {
        asignador = new AsignadorMision(repositorioFlota, servicioClima);
    }

    @Test
    void climaApto_asignaElPrimerDroneDisponible() {
        DroneEnterprise drone = new DroneEnterprise("D-01", Sede.ECI, TipoDrone.MINI, 80, true);
        when(servicioClima.condicionesAptas("ECI", "UNAL")).thenReturn(true);
        when(repositorioFlota.findDisponibles(Sede.ECI)).thenReturn(List.of(drone));

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL");

        assertEquals(Optional.of(drone), resultado);
    }

    @Test
    void climaAdverso_noConsultaLaFlota() {
        when(servicioClima.condicionesAptas("ECI", "UNAL")).thenReturn(false);

        Optional<DroneEnterprise> resultado = asignador.asignar(Sede.ECI, "ECI", "UNAL");

        assertTrue(resultado.isEmpty());
    }
}
