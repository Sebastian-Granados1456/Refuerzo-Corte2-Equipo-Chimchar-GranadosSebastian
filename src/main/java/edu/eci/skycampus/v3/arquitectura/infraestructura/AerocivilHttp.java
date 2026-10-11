package edu.eci.skycampus.v3.arquitectura.infraestructura;

import edu.eci.skycampus.v3.arquitectura.dominio.Aerocivil;

/** Infraestructura: implementación real contra el servicio de la Aerocivil (HTTP iría aquí). */
public class AerocivilHttp implements Aerocivil {

    @Override
    public boolean autorizaVuelo(String origen, String destino) {
        throw new UnsupportedOperationException("Requiere una llamada HTTP real; fuera del alcance de este reto");
    }
}
