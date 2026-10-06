package edu.eci.skycampus.solid;

public class AlertaConsola implements AlertaOperador {
    @Override
    public void enviar(String operador, String mensaje) {
        System.out.println("[Alerta a " + operador + "] " + mensaje);
    }
}
