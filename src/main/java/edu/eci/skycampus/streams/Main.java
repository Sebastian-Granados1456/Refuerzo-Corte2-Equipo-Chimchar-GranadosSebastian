package edu.eci.skycampus.streams;

import edu.eci.skycampus.model.Drone;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
                new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca"),
                new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C"),
                new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B"),
                new Drone("D-05", "DJI Mini 3", 67, true, "Bloque D")
        );

        System.out.println("1. " + ConsultasFlota.disponiblesConBateriaAlta(flota));
        System.out.println("2. " + ConsultasFlota.hayDisponibleEn(flota, "Bloque C"));
        System.out.println("3. " + ConsultasFlota.contarBateriaCritica(flota));
        System.out.println("4. " + ConsultasFlota.resumenBaterias(flota));
    }
}
