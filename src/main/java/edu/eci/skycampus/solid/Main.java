package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;

public class Main {
    public static void main(String[] args) {
        Drone d01 = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");
        Drone d03 = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");
        Mision m1 = new Mision("M-001", null, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE, 3, "", null);
        Mision m2 = new Mision("M-002", null, "Bloque C", "Bloque B", TipoCarga.LIBRO, EstadoMision.PENDIENTE, 1, "", null);

        RepositorioMision repositorio = new RepositorioMisionMemoria();
        AsignadorMision asignador = new AsignadorMision(repositorio, new AlertaConsola());
        asignador.asignar(d01, m1);
        asignador.asignar(d03, m2);

        System.out.println(new GeneradorReporte().generar(repositorio.listar()));

        EstrategiaRuta ruta = new RutaDirecta();
        System.out.println("Ruta directa: " + ruta.calcular("Bloque A", "Biblioteca"));
        ruta = new RutaEvitandoEdificios();
        System.out.println("Ruta evitando edificios: " + ruta.calcular("Bloque A", "Biblioteca"));
    }
}
