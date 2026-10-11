package edu.eci.skycampus.v3.ruta;

/** Strategy: cada algoritmo de optimización de ruta decide cuántas etapas necesita el tramo total. */
public interface EstrategiaOptimizacion {
    RutaCompuesta calcular(String origen, String destino, int distanciaTotalKm);
}
