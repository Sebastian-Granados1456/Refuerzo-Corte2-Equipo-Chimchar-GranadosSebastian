package edu.eci.skycampus.solid;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertaConsola implements AlertaOperador {
    private static final Logger LOGGER = Logger.getLogger(AlertaConsola.class.getName());

    @Override
    public void enviar(String operador, String mensaje) {
        LOGGER.log(Level.INFO, "[Alerta a {0}] {1}", new Object[]{operador, mensaje});
    }
}
