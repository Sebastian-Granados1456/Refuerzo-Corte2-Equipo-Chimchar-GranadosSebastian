package edu.eci.skycampus.patrones;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;
import edu.eci.skycampus.patrones.builder.MisionBuilder;
import edu.eci.skycampus.patrones.chain.Validador;
import edu.eci.skycampus.patrones.chain.ValidadorBateria;
import edu.eci.skycampus.patrones.chain.ValidadorCarga;
import edu.eci.skycampus.patrones.chain.ValidadorDestino;
import edu.eci.skycampus.patrones.strategy.AsignadorDrone;
import edu.eci.skycampus.patrones.strategy.MayorBateria;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
                new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C"),
                new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B")
        );

        // Strategy
        AsignadorDrone asignador = new AsignadorDrone(new MayorBateria());
        Drone elegido = asignador.asignar(flota).orElseThrow();
        System.out.println("Drone asignado: " + elegido.id());

        // Builder
        Mision mision = new MisionBuilder()
                .id("M-001").drone(elegido).origen("Bloque C").destino("Biblioteca")
                .tipoCarga(TipoCarga.CARPETA)
                .notas("Urgente: examen mañana")
                .build();
        System.out.println("Misión creada: " + mision);

        // Chain of Responsibility
        Validador cadena = new ValidadorBateria();
        cadena.enlazar(new ValidadorDestino(List.of("Biblioteca", "Bloque A", "Bloque B", "Bloque C")))
              .enlazar(new ValidadorCarga(TipoCarga.CARPETA));
        System.out.println("¿Misión válida? " + cadena.validar(mision));

        Mision conD04 = new MisionBuilder()
                .id("M-002").drone(flota.get(2)).origen("Bloque B").destino("Biblioteca").build();
        System.out.println("¿Misión con D-04 (18%) válida? " + cadena.validar(conD04));
    }
}
