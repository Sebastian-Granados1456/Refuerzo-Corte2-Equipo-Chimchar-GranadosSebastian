package edu.eci.skycampus.v3.ruta;

import java.util.ArrayList;
import java.util.List;

/** Composite - compuesto: una ruta multi-etapa (ej. sede A -> estación de carga -> sede B). */
public class RutaCompuesta implements ComponenteRuta {

    private final List<ComponenteRuta> etapas = new ArrayList<>();

    public void agregar(ComponenteRuta etapa) {
        etapas.add(etapa);
    }

    public List<ComponenteRuta> etapas() {
        return List.copyOf(etapas);
    }

    @Override
    public int distanciaKm() {
        return etapas.stream().mapToInt(ComponenteRuta::distanciaKm).sum();
    }
}
