package edu.eci.skycampus.patrones.chain;

import edu.eci.skycampus.model.Mision;

public class ValidadorBateria extends Validador {
    @Override
    protected boolean verificar(Mision mision) {
        return mision.drone().bateria() >= 30;
    }
}
