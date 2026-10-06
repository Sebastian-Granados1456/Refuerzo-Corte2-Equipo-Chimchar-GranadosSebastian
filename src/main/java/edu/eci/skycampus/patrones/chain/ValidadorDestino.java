package edu.eci.skycampus.patrones.chain;

import edu.eci.skycampus.model.Mision;

import java.util.List;

public class ValidadorDestino extends Validador {
    private final List<String> destinosValidos;

    public ValidadorDestino(List<String> destinosValidos) {
        this.destinosValidos = destinosValidos;
    }

    @Override
    protected boolean verificar(Mision mision) {
        return destinosValidos.contains(mision.destino());
    }
}
