package edu.eci.skycampus.v3.streams;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Prioridad;
import edu.eci.skycampus.v3.model.MisionEnterprise;
import edu.eci.skycampus.v3.model.Sede;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstadisticasSede {

    public record Resultado(double tasaExito, double tiempoPromedioMinutos, String droneMasUtilizado,
                             double porcentajeUrgentes) {}

    private EstadisticasSede() {
    }

    /** Agrupa las misiones por sede y calcula sus estadísticas. Vacío para una sede -> Optional.empty(). */
    public static Map<Sede, Optional<Resultado>> analizarPorSede(List<MisionEnterprise> misiones) {
        return misiones.stream()
                .collect(Collectors.groupingBy(MisionEnterprise::sede,
                        Collectors.collectingAndThen(Collectors.toList(), EstadisticasSede::calcular)));
    }

    private static Optional<Resultado> calcular(List<MisionEnterprise> misiones) {
        if (misiones.isEmpty()) {
            return Optional.empty();
        }
        long entregadas = misiones.stream().filter(m -> m.estado() == EstadoMision.ENTREGADA).count();
        double tasaExito = (double) entregadas / misiones.size();

        double tiempoPromedio = misiones.stream()
                .mapToLong(MisionEnterprise::minutosEntrega)
                .average()
                .orElse(0);

        String droneMasUtilizado = misiones.stream()
                .collect(Collectors.groupingBy(MisionEnterprise::droneId, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        long urgentes = misiones.stream().filter(m -> m.prioridad() == Prioridad.URGENTE).count();
        double porcentajeUrgentes = 100.0 * urgentes / misiones.size();

        return Optional.of(new Resultado(tasaExito, tiempoPromedio, droneMasUtilizado, porcentajeUrgentes));
    }
}
