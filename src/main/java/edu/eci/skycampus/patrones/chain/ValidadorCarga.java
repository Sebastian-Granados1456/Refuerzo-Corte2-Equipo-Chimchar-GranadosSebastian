package edu.eci.skycampus.patrones.chain;

import edu.eci.skycampus.model.Mision;
import edu.eci.skycampus.model.TipoCarga;

public class ValidadorCarga extends Validador {
    // Carga más pesada que soporta el drone (SOBRE < CARPETA < LIBRO)
    private final TipoCarga capacidadMaxima;

    public ValidadorCarga(TipoCarga capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    @Override
    protected boolean verificar(Mision mision) {
        return mision.tipoCarga().compareTo(capacidadMaxima) <= 0;
    }
}
