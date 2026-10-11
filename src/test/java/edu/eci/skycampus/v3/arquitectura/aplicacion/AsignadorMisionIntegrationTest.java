package edu.eci.skycampus.v3.arquitectura.aplicacion;

import edu.eci.skycampus.v2.model.TipoDrone;
import edu.eci.skycampus.v3.arquitectura.dominio.Aerocivil;
import edu.eci.skycampus.v3.arquitectura.dominio.DroneEnterprise;
import edu.eci.skycampus.v3.arquitectura.dominio.RepositorioFlota;
import edu.eci.skycampus.v3.arquitectura.dominio.ServicioClima;
import edu.eci.skycampus.v3.model.Sede;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Prueba de integración: las 3 capas trabajando juntas (dominio + aplicación + infraestructura
 * en memoria), sin HTTP real ni base de datos. Este proyecto no usa Spring Boot, así que el
 * "endpoint" se simula con una fachada simple (MisionEndpoint) que es lo que un controlador
 * REST llamaría.
 */
class AsignadorMisionIntegrationTest {

    /** Infraestructura en memoria: cumple el contrato real de RepositorioFlota sin base de datos. */
    static class RepositorioFlotaEnMemoria implements RepositorioFlota {
        private final Map<Sede, List<DroneEnterprise>> flotaPorSede;
        private final Map<Sede, Boolean> sedesActivas;

        RepositorioFlotaEnMemoria(Map<Sede, List<DroneEnterprise>> flotaPorSede, Map<Sede, Boolean> sedesActivas) {
            this.flotaPorSede = flotaPorSede;
            this.sedesActivas = sedesActivas;
        }

        @Override
        public List<DroneEnterprise> findDisponibles(Sede sede) {
            return flotaPorSede.getOrDefault(sede, List.of());
        }

        @Override
        public boolean sedeActiva(Sede sede) {
            return sedesActivas.getOrDefault(sede, true);
        }
    }

    private AsignadorMision asignadorConFlota(List<DroneEnterprise> flota, boolean sedeActiva) {
        RepositorioFlota repo = new RepositorioFlotaEnMemoria(
                Map.of(Sede.ECI, flota), Map.of(Sede.ECI, sedeActiva));
        ServicioClima climaSiempreApto = (origen, destino) -> true;
        Aerocivil aerocivilSiempreAutoriza = (origen, destino) -> true;
        return new AsignadorMision(repo, climaSiempreApto, aerocivilSiempreAutoriza);
    }

    /** MisionEndpoint: lo que un controlador REST llamaría; aquí se invoca directo (sin HTTP). */
    static class MisionEndpoint {
        private final AsignadorMision asignadorMision;

        MisionEndpoint(AsignadorMision asignadorMision) {
            this.asignadorMision = asignadorMision;
        }

        Optional<String> post(Sede sede, String origen, String destino, int pesoGramos) {
            return asignadorMision.asignar(sede, origen, destino, pesoGramos).map(DroneEnterprise::id);
        }
    }

    @Test
    void crearMision_climaApto_retornaDroneAsignado() {
        DroneEnterprise disponible = new DroneEnterprise("D-01", Sede.ECI, TipoDrone.MINI, 85, true);
        MisionEndpoint endpoint = new MisionEndpoint(asignadorConFlota(List.of(disponible), true));

        Optional<String> resultado = endpoint.post(Sede.ECI, "ECI", "UNAL", 300);

        assertEquals(Optional.of("D-01"), resultado);
    }

    @Test
    void crearMision_sedeInactiva_noAsignaDrone() {
        DroneEnterprise disponible = new DroneEnterprise("D-01", Sede.ECI, TipoDrone.MINI, 85, true);
        MisionEndpoint endpoint = new MisionEndpoint(asignadorConFlota(List.of(disponible), false));

        Optional<String> resultado = endpoint.post(Sede.ECI, "ECI", "UNAL", 300);

        assertTrue(resultado.isEmpty());
    }
}
