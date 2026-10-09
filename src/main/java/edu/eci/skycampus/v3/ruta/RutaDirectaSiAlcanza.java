package edu.eci.skycampus.v3.ruta;

/** Strategy: si la distancia no supera el límite de vuelo con carga, una sola etapa. */
public class RutaDirectaSiAlcanza implements EstrategiaOptimizacion {

    @Override
    public RutaCompuesta calcular(String origen, String destino, int distanciaTotalKm) {
        RutaCompuesta ruta = new RutaCompuesta();
        ruta.agregar(new Etapa(origen, destino, distanciaTotalKm));
        return ruta;
    }
}
