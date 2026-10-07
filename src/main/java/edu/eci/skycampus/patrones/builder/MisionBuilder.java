package edu.eci.skycampus.patrones.builder;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;

import java.time.LocalTime;

public class MisionBuilder {
    private String id;
    private Drone drone;
    private String origen;
    private String destino;
    private TipoCarga tipoCarga = TipoCarga.SOBRE;
    private int prioridad = 3;
    private String notas = "";
    private LocalTime horaMaximaEntrega;

    public MisionBuilder id(String id) { this.id = id; return this; }
    public MisionBuilder drone(Drone drone) { this.drone = drone; return this; }
    public MisionBuilder origen(String origen) { this.origen = origen; return this; }
    public MisionBuilder destino(String destino) { this.destino = destino; return this; }
    public MisionBuilder tipoCarga(TipoCarga tipoCarga) { this.tipoCarga = tipoCarga; return this; }
    public MisionBuilder prioridad(int prioridad) { this.prioridad = prioridad; return this; }
    public MisionBuilder notas(String notas) { this.notas = notas; return this; }
    public MisionBuilder horaMaximaEntrega(LocalTime hora) { this.horaMaximaEntrega = hora; return this; }

    public Mision build() {
        if (id == null || drone == null || origen == null || destino == null)
            throw new IllegalStateException("id, drone, origen y destino son obligatorios");
        return new Mision(id, drone, origen, destino, tipoCarga, EstadoMision.PENDIENTE, prioridad, notas, horaMaximaEntrega);
    }
}
