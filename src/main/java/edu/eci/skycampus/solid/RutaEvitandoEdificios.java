package edu.eci.skycampus.solid;

public class RutaEvitandoEdificios implements EstrategiaRuta {
    @Override
    public String calcular(String origen, String destino) {
        return origen + " -> Zona verde -> " + destino;
    }
}
