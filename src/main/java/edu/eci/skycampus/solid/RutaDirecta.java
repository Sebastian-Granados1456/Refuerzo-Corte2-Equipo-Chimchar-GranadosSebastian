package edu.eci.skycampus.solid;

public class RutaDirecta implements EstrategiaRuta {
    @Override
    public String calcular(String origen, String destino) {
        return origen + " -> " + destino;
    }
}
