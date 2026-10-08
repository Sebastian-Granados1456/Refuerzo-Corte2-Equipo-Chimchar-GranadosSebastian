package edu.eci.skycampus.v2.streams;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.Prioridad;
import edu.eci.skycampus.v2.model.TipoDrone;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstadisticasFlotaTest {

    private static final LocalDateTime AHORA = LocalDateTime.of(2026, 10, 7, 10, 0);

    private final Drone m01 = new Drone("M-01", TipoDrone.MINI, 80, true, EstadoDrone.DISPONIBLE, 12);
    private final Drone m02 = new Drone("M-02", TipoDrone.MINI, 60, true, EstadoDrone.DISPONIBLE, 5);
    private final Drone c01 = new Drone("C-01", TipoDrone.CARGO, 90, false, EstadoDrone.EN_VUELO, 3);
    private final Drone e01 = new Drone("E-01", TipoDrone.EXPRESS, 45, true, EstadoDrone.DISPONIBLE, 20);

    private Mision mision(String id, Drone d, Prioridad p, EstadoMision e, int minutosAtras) {
        return new Mision(id, d, "Biblioteca", 300, p, e, AHORA.minusMinutes(minutosAtras));
    }

    private final List<Mision> misiones = List.of(
            mision("X-1", m01, Prioridad.NORMAL, EstadoMision.ENTREGADA, 120),
            mision("X-2", m01, Prioridad.BAJO, EstadoMision.ENTREGADA, 100),
            mision("X-3", m02, Prioridad.NORMAL, EstadoMision.ENTREGADA, 90),
            mision("X-4", c01, Prioridad.URGENTE, EstadoMision.ENTREGADA, 60),
            mision("X-5", e01, Prioridad.NORMAL, EstadoMision.FALLIDA, 30),
            mision("X-6", c01, Prioridad.URGENTE, EstadoMision.EN_VUELO, 5)
    );

    @Test
    void completadasPorTipo_cuentaEntregadasPorTipoDeDrone() {
        Map<TipoDrone, Long> resultado = EstadisticasFlota.completadasPorTipo(misiones);
        assertEquals(Map.of(TipoDrone.MINI, 3L, TipoDrone.CARGO, 1L), resultado);
    }

    @Test
    void droneConMasCompletadas_devuelveElDeMayorConteo() {
        assertEquals(Optional.of(m01), EstadisticasFlota.droneConMasCompletadas(misiones));
    }

    @Test
    void droneConMasCompletadas_sinEntregadas_vacio() {
        List<Mision> sinEntregas = List.of(mision("X-7", e01, Prioridad.NORMAL, EstadoMision.FALLIDA, 10));
        assertTrue(EstadisticasFlota.droneConMasCompletadas(sinEntregas).isEmpty());
    }

    @Test
    void porcentajeFallidas_unaDeSeis() {
        assertEquals(100.0 / 6, EstadisticasFlota.porcentajeFallidas(misiones), 0.001);
    }

    @Test
    void porcentajeFallidas_listaVacia_cero() {
        assertEquals(0, EstadisticasFlota.porcentajeFallidas(List.of()));
    }

    @Test
    void urgentePendienteHaceMasDe10Minutos_true() {
        List<Mision> conDemorada = List.of(mision("X-8", null, Prioridad.URGENTE, EstadoMision.PENDIENTE, 15));
        assertTrue(EstadisticasFlota.hayUrgentePendienteDemorada(conDemorada, AHORA));
    }

    @Test
    void urgentePendienteReciente_false() {
        List<Mision> reciente = List.of(
                mision("X-9", null, Prioridad.URGENTE, EstadoMision.PENDIENTE, 5),
                mision("X-10", null, Prioridad.NORMAL, EstadoMision.PENDIENTE, 30));
        assertFalse(EstadisticasFlota.hayUrgentePendienteDemorada(reciente, AHORA));
    }
}
