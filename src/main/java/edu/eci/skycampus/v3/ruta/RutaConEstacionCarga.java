package edu.eci.skycampus.v3.ruta;

/** Strategy: si la distancia supera el límite de vuelo con carga (5km, RN del reto SC-07 v2), parte en dos etapas con una estación de carga intermedia. */
public class RutaConEstacionCarga implements EstrategiaOptimizacion {

    private static final int LIMITE_KM_SIN_RECARGA = 5;

    @Override
    public RutaCompuesta calcular(String origen, String destino, int distanciaTotalKm) {
        RutaCompuesta ruta = new RutaCompuesta();
        if (distanciaTotalKm <= LIMITE_KM_SIN_RECARGA) {
            ruta.agregar(new Etapa(origen, destino, distanciaTotalKm));
            return ruta;
        }
        String estacion = "Estación de carga";
        ruta.agregar(new Etapa(origen, estacion, LIMITE_KM_SIN_RECARGA));
        ruta.agregar(new Etapa(estacion, destino, distanciaTotalKm - LIMITE_KM_SIN_RECARGA));
        return ruta;
    }
}
