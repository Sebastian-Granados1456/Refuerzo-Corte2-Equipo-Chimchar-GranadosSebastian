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

/**
 * Demuestra OCP: GestorFlota (equivalente a "GestorMisiones" del reto) funciona
 * con cualquier EstrategiaAsignacion —incluida una creada aquí mismo, que nunca
 * vio su código— sin que GestorFlota.java necesite cambiar una sola línea.
 */
class GestorFlotaSolidTest {

    private final Drone d01 = new Drone("D-01", TipoDrone.MINI, 70, true, EstadoDrone.DISPONIBLE, 10);
    private final Drone d02 = new Drone("D-02", TipoDrone.MINI, 70, true, EstadoDrone.DISPONIBLE, 10);
    private final List<Drone> flota = List.of(d01, d02);
    private final Mision mision = new Mision("MF-100", null, "Bloque A", 200, Prioridad.NORMAL,
            EstadoMision.PENDIENTE, LocalDateTime.now());

    @Test
    void gestorFlota_funcionaConEstrategiasYaExistentes_sinModificarSuCodigo() {
        GestorFlota gestor = new GestorFlota(new PorMayorBateria());
        assertTrue(flota.contains(gestor.asignar(flota, mision).orElseThrow()));

        gestor.setEstrategia(new PorMenorUsoAcumulado());
        assertTrue(flota.contains(gestor.asignar(flota, mision).orElseThrow()));
    }

    @Test
    void gestorFlota_funcionaConUnaEstrategiaNuevaDefinidaFueraDeProduccion() {
        // Estrategia inventada en la propia prueba: elige siempre el último de la lista.
        // GestorFlota nunca fue tocado para soportarla.
        EstrategiaAsignacion siempreElUltimo = (f, m) -> f.isEmpty()
                ? Optional.empty()
                : Optional.of(f.get(f.size() - 1));

        GestorFlota gestor = new GestorFlota(siempreElUltimo);

        assertEquals(d02, gestor.asignar(flota, mision).orElseThrow());
    }
}
