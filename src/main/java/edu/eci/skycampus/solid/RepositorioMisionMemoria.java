package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Mision;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMisionMemoria implements RepositorioMision {
    private final List<Mision> misiones = new ArrayList<>();

    @Override
    public void guardar(Mision mision) {
        misiones.add(mision);
    }

    @Override
    public List<Mision> listar() {
        return List.copyOf(misiones);
    }
}
