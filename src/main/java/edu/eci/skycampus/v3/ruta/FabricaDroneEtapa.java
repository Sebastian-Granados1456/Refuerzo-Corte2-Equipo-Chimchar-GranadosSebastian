package edu.eci.skycampus.v3.ruta;

import edu.eci.skycampus.v2.model.TipoDrone;

/** Factory Method: decide el tipo de drone correcto para una etapa, según el peso que lleva en ese tramo. */
public abstract class FabricaDroneEtapa {

    public abstract TipoDrone crearDrone(int pesoPaqueteGramos);

    /** Elige la fábrica compatible con el peso, de menor a mayor capacidad. */
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
