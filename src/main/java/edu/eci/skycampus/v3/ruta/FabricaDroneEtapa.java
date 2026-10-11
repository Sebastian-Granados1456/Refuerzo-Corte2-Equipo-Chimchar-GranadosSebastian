package edu.eci.skycampus.v3.ruta;

import edu.eci.skycampus.v2.model.TipoDrone;

/** Factory Method: decide el tipo de drone correcto para una etapa, según el peso que lleva en ese tramo. */
public interface FabricaDroneEtapa {
    TipoDrone crearDrone(int pesoPaqueteGramos);
}
