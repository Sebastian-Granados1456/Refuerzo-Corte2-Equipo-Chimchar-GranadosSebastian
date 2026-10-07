package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Drone;
import edu.eci.skycampus.model.Mision;

public class AsignadorMision {
    private final RepositorioMision repositorio;
    private final AlertaOperador alerta;

    public AsignadorMision(RepositorioMision repositorio, AlertaOperador alerta) {
        this.repositorio = repositorio;
        this.alerta = alerta;
    }

    public Mision asignar(Drone drone, Mision mision) {
        Mision asignada = new Mision(mision.id(), drone, mision.origen(), mision.destino(), mision.tipoCarga(),
                mision.estado(), mision.prioridad(), mision.notas(), mision.horaMaximaEntrega());
        repositorio.guardar(asignada);
        alerta.enviar("Operador", "Misión " + asignada.id() + " asignada a " + drone.id());
        return asignada;
    }
}
