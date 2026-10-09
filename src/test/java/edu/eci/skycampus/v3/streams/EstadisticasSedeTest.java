package edu.eci.skycampus.v3.streams;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Prioridad;
import edu.eci.skycampus.v3.model.MisionEnterprise;
import edu.eci.skycampus.v3.model.Sede;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstadisticasSedeTest {

    private static MisionEnterprise mision(Sede sede, String droneId, EstadoMision estado, Prioridad prioridad, long minutos) {
        return new MisionEnterprise("M-" + droneId, sede, droneId, estado, prioridad, minutos);
    }

    // Hotfix v3.0.1: minutosEntrega negativo rompía el promedio de EstadisticasSede
    @Test
    void minutosEntregaNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> mision(Sede.ECI, "D-01", EstadoMision.ENTREGADA, Prioridad.NORMAL, -5));
    }

    // 1. Sede vacía: no hay misiones para ECI -> Optional.empty()
    @Test
    void sedeVacia_devuelveOptionalEmpty() {
        Map<Sede, Optional<EstadisticasSede.Resultado>> resultado = EstadisticasSede.analizarPorSede(List.of());

        assertTrue(resultado.getOrDefault(Sede.ECI, Optional.empty()).isEmpty());
    }

    // 2. Sede con 1 misión: la tasa de éxito y el tiempo son los de esa única misión
    @Test
    void sedeConUnaMision_calculaSusPropiosValores() {
        List<MisionEnterprise> misiones = List.of(
                mision(Sede.UNAL, "D-01", EstadoMision.ENTREGADA, Prioridad.NORMAL, 20));

        EstadisticasSede.Resultado r = EstadisticasSede.analizarPorSede(misiones).get(Sede.UNAL).orElseThrow();

        assertEquals(1.0, r.tasaExito());
        assertEquals(20.0, r.tiempoPromedioMinutos());
        assertEquals("D-01", r.droneMasUtilizado());
        assertEquals(0.0, r.porcentajeUrgentes());
    }

    // 3. Empate entre drones de una misma sede: cualquiera de los empatados es un resultado válido
    @Test
    void empateEntreDrones_devuelveUnoDeLosEmpatados() {
        List<MisionEnterprise> misiones = List.of(
                mision(Sede.ECI, "D-01", EstadoMision.ENTREGADA, Prioridad.NORMAL, 10),
                mision(Sede.ECI, "D-02", EstadoMision.ENTREGADA, Prioridad.NORMAL, 10));

        EstadisticasSede.Resultado r = EstadisticasSede.analizarPorSede(misiones).get(Sede.ECI).orElseThrow();

        assertTrue(List.of("D-01", "D-02").contains(r.droneMasUtilizado()));
    }

    // 4. Red completa: varias sedes con varias misiones cada una
    @ParameterizedTest
    @MethodSource("redCompleta")
    void redCompleta_cadaSedeTieneSusPropiasMetricas(Sede sede, double tasaExitoEsperada, double urgentesEsperado) {
        List<MisionEnterprise> misiones = List.of(
                mision(Sede.ECI, "D-01", EstadoMision.ENTREGADA, Prioridad.NORMAL, 15),
                mision(Sede.ECI, "D-02", EstadoMision.FALLIDA, Prioridad.URGENTE, 30),
                mision(Sede.UNAL, "D-05", EstadoMision.ENTREGADA, Prioridad.NORMAL, 25),
                mision(Sede.UNIANDES, "D-07", EstadoMision.ENTREGADA, Prioridad.URGENTE, 12),
                mision(Sede.EAFIT, "D-09", EstadoMision.ENTREGADA, Prioridad.NORMAL, 18));

        EstadisticasSede.Resultado r = EstadisticasSede.analizarPorSede(misiones).get(sede).orElseThrow();

        assertEquals(tasaExitoEsperada, r.tasaExito());
        assertEquals(urgentesEsperado, r.porcentajeUrgentes());
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> redCompleta() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(Sede.ECI, 0.5, 50.0),
                org.junit.jupiter.params.provider.Arguments.of(Sede.UNAL, 1.0, 0.0),
                org.junit.jupiter.params.provider.Arguments.of(Sede.UNIANDES, 1.0, 100.0),
                org.junit.jupiter.params.provider.Arguments.of(Sede.EAFIT, 1.0, 0.0)
        );
    }
}
