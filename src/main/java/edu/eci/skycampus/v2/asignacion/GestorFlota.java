package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.EstadoDrone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.observer.ObservadorFlota;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Asigna misiones con la estrategia configurada y notifica a sus observadores
 * cada vez que un drone cambia de estado. No conoce a los suscriptores concretos.
 */
public class GestorFlota {

    private final List<ObservadorFlota> observadores = new ArrayList<>();
    private EstrategiaAsignacion estrategia;

    public GestorFlota(EstrategiaAsignacion estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaAsignacion estrategia) {
        this.estrategia = estrategia;
    }

    public void suscribir(ObservadorFlota observador) {
        observadores.add(observador);
    }

    public void desuscribir(ObservadorFlota observador) {
        observadores.remove(observador);
    }

    public Optional<Drone> asignar(List<Drone> flota, Mision mision) {
        return estrategia.elegir(flota, mision);
    }

    public void cambiarEstado(Drone drone, EstadoDrone nuevoEstado) {
        for (ObservadorFlota observador : observadores) {
            observador.onCambioEstado(drone, nuevoEstado);
        }
    }
}
