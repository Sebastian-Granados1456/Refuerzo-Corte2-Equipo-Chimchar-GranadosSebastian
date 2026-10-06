package edu.eci.skycampus.solid;

import edu.eci.skycampus.model.Mision;

import java.util.List;
import java.util.stream.Collectors;

public class GeneradorReporte {
    public String generar(List<Mision> misiones) {
        return misiones.stream()
                .map(m -> m.id() + " | " + m.drone().id() + " | " + m.origen() + " -> " + m.destino() + " | " + m.estado())
                .collect(Collectors.joining("\n", "Reporte de misiones (" + misiones.size() + ")\n", ""));
    }
}
