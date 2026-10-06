package edu.eci.skycampus.streams;

import edu.eci.skycampus.model.Drone;

import java.util.Comparator;
import java.util.List;

public class ConsultasFlota {

    public static List<String> disponiblesConBateriaAlta(List<Drone> flota) {
        return flota.stream()
                .filter(d -> d.disponible() && d.bateria() >= 50)
                .sorted(Comparator.comparingInt(Drone::bateria).reversed())
                .map(Drone::id)
                .toList();
    }

    public static boolean hayDisponibleEn(List<Drone> flota, String ubicacion) {
        return flota.stream()
                .anyMatch(d -> d.disponible() && d.ubicacion().equals(ubicacion));
    }

    public static long contarBateriaCritica(List<Drone> flota) {
        return flota.stream()
                .filter(d -> d.bateria() < 20)
                .count();
    }

    public static List<String> resumenBaterias(List<Drone> flota) {
        return flota.stream()
                .map(d -> d.id() + ": " + d.bateria() + "%")
                .toList();
    }
}
