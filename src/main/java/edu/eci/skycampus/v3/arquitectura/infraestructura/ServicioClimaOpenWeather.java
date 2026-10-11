package edu.eci.skycampus.v3.arquitectura.infraestructura;

import edu.eci.skycampus.v3.arquitectura.dominio.ServicioClima;

/** Infraestructura: implementación real contra la API Meteorológica (HTTP iría aquí). */
public class ServicioClimaOpenWeather implements ServicioClima {

    @Override
    public boolean condicionesAptas(String origen, String destino) {
        throw new UnsupportedOperationException("Requiere una llamada HTTP real; fuera del alcance de este reto");
    }
}
