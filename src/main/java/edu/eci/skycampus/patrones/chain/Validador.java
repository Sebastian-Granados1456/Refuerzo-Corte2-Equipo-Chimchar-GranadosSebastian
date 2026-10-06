package edu.eci.skycampus.patrones.chain;

import edu.eci.skycampus.model.Mision;

public abstract class Validador {
    private Validador siguiente;

    public Validador enlazar(Validador siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public boolean validar(Mision mision) {
        if (!verificar(mision)) return false;
        return siguiente == null || siguiente.validar(mision);
    }

    protected abstract boolean verificar(Mision mision);
}
