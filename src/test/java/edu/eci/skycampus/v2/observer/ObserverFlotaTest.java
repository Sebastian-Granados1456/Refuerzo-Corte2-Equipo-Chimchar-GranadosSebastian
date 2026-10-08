package edu.eci.skycampus.v2.observer;

import edu.eci.skycampus.v2.asignacion.GestorFlota;
import edu.eci.skycampus.v2.asignacion.PorMayorBateria;
import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;
import edu.eci.skycampus.v2.model.TipoDrone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverFlotaTest {

    private final Drone drone = new Drone("E-01", TipoDrone.EXPRESS, 55, true, EstadoDrone.DISPONIBLE, 4);

    @Test
    void panelOperador_sistemaLog_alertaTecnico_sonNotificadosPorIgual() {
        List<String> llamadas = new ArrayList<>();
        ObservadorFlota panel = (d, e) -> llamadas.add("panel:" + e);
        ObservadorFlota log = (d, e) -> llamadas.add("log:" + e);
        ObservadorFlota tecnico = (d, e) -> llamadas.add("tecnico:" + e);

        GestorFlota gestor = new GestorFlota(new PorMayorBateria());
        gestor.suscribir(panel);
        gestor.suscribir(log);
        gestor.suscribir(tecnico);

        gestor.cambiarEstado(drone, EstadoDrone.EN_VUELO);

        assertEquals(List.of("panel:EN_VUELO", "log:EN_VUELO", "tecnico:EN_VUELO"), llamadas);
    }

    @Test
    void alertaTecnico_soloAvisaCuandoElEstadoEsFallo() {
        List<String> avisos = new ArrayList<>();
        AlertaTecnico alertaTecnico = new AlertaTecnico();

        GestorFlota gestor = new GestorFlota(new PorMayorBateria());
        gestor.suscribir(alertaTecnico);
        gestor.suscribir((d, e) -> avisos.add(e.name())); // testigo para confirmar que SÍ se notifica siempre

        gestor.cambiarEstado(drone, EstadoDrone.EN_VUELO);
        gestor.cambiarEstado(drone, EstadoDrone.FALLO);

        assertEquals(List.of("EN_VUELO", "FALLO"), avisos);
    }

    /**
     * Demuestra que GestorFlota es cerrado para modificación (OCP): agregar un
     * cuarto observador en tiempo de ejecución no requiere tocar su código,
     * solo implementar ObservadorFlota y suscribirlo.
     */
    @Test
    void agregarCuartoObservador_noRequiereModificarGestorFlota() {
        GestorFlota gestor = new GestorFlota(new PorMayorBateria());
        gestor.suscribir(new PanelOperador());
        gestor.suscribir(new SistemaLog());
        gestor.suscribir(new AlertaTecnico());

        List<String> notificado = new ArrayList<>();
        ObservadorFlota cuartoObservador = (d, e) -> notificado.add(d.id() + ":" + e);
        gestor.suscribir(cuartoObservador);

        gestor.cambiarEstado(drone, EstadoDrone.ATERRIZANDO);

        assertTrue(notificado.contains("E-01:ATERRIZANDO"));
    }
}
