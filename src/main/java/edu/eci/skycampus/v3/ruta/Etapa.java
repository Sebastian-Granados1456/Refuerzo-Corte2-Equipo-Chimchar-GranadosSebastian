package edu.eci.skycampus.v3.ruta;

/** Composite - hoja: un tramo entre dos puntos (dos sedes, o una sede y una estación de carga). */
public record Etapa(String origen, String destino, int distanciaKm) implements ComponenteRuta {
}
