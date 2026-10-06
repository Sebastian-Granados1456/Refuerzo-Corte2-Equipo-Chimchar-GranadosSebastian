package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Mision;

import java.util.List;

public interface RepositorioMision {
    void guardar(Mision mision);
    List<Mision> listar();
}
