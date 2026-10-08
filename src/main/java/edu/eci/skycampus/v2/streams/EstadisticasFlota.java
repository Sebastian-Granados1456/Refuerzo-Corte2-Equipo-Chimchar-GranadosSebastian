package edu.eci.skycampus.v2.streams;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.Prioridad;
import edu.eci.skycampus.v2.model.TipoDrone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstadisticasFlota {

    private EstadisticasFlota() {
    }

    public static Map<TipoDrone, Long> completadasPorTipo(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA)
                .collect(Collectors.groupingBy(m -> m.drone().tipo(), Collectors.counting()));
    }

    public static Optional<Drone> droneConMasCompletadas(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA)
                .collect(Collectors.groupingBy(Mision::drone, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public static double porcentajeFallidas(List<Mision> misiones) {
        if (misiones.isEmpty()) {
            return 0;
        }
        long fallidas = misiones.stream()
                .filter(m -> m.estado() == EstadoMision.FALLIDA)
                .count();
        return fallidas * 100.0 / misiones.size();
    }

    public static boolean hayUrgentePendienteDemorada(List<Mision> misiones, LocalDateTime ahora) {
        return misiones.stream()
                .anyMatch(m -> m.prioridad() == Prioridad.URGENTE
                        && m.estado() == EstadoMision.PENDIENTE
                        && m.creadaEn().isBefore(ahora.minusMinutes(10)));
    }
}
