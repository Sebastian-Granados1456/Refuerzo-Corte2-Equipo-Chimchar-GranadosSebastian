package edu.eci.skycampus.v3.ruta;

import edu.eci.skycampus.v2.model.TipoDrone;

/** Elige la FabricaDroneEtapa compatible con el peso del paquete, de menor a mayor capacidad. */
public final class FabricasDroneEtapa {

    private FabricasDroneEtapa() {
    }

    public static FabricaDroneEtapa paraPeso(int pesoPaqueteGramos) {
        if (pesoPaqueteGramos <= TipoDrone.MINI.capacidadMaximaGramos()) {
            return new FabricaDroneMini();
        }
        if (pesoPaqueteGramos <= TipoDrone.EXPRESS.capacidadMaximaGramos()) {
            return new FabricaDroneExpress();
        }
        return new FabricaDroneCargo();
    }
}
