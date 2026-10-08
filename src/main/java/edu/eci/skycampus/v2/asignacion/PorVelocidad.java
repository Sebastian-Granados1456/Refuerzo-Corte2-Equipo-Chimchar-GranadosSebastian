package edu.eci.skycampus.v2.asignacion;

import edu.eci.skycampus.v2.model.Drone;
import edu.eci.skycampus.v2.model.Mision;
import edu.eci.skycampus.v2.model.TipoDrone;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Para misiones URGENTE: elige el drone más rápido compatible, sin importar la batería. Orden: EXPRESS > MINI > CARGO. */
public class PorVelocidad implements EstrategiaAsignacion {

    private static final List<TipoDrone> ORDEN_VELOCIDAD = List.of(TipoDrone.EXPRESS, TipoDrone.MINI, TipoDrone.CARGO);

    @Override
    public Optional<Drone> elegir(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.tipo().capacidadMaximaGramos() >= mision.pesoPaqueteGramos())
                .min(Comparator.comparingInt(d -> ORDEN_VELOCIDAD.indexOf(d.tipo())));
    }
}
