package edu.eci.skycampus.v3.ruta;

import java.util.ArrayList;
import java.util.List;

/** Ejecuta las etapas de una ruta y notifica a sus observadores al completar cada una. */
public class EjecutorRuta {

    private final List<ObservadorRuta> observadores = new ArrayList<>();

    public void suscribir(ObservadorRuta observador) {
        observadores.add(observador);
    }

    public void ejecutar(RutaCompuesta ruta) {
        for (ComponenteRuta componente : ruta.etapas()) {
            if (componente instanceof Etapa etapa) {
                for (ObservadorRuta observador : observadores) {
                    observador.onEtapaCompletada(etapa);
                }
            }
        }
    }
}
