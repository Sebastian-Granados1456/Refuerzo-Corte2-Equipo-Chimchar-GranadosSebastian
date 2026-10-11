package edu.eci.skycampus.v3.ruta;

import edu.eci.skycampus.v2.model.TipoDrone;

public class FabricaDroneCargo extends FabricaDroneEtapa {
    @Override
    public TipoDrone crearDrone(int pesoPaqueteGramos) {
        return TipoDrone.CARGO;
    }
}
