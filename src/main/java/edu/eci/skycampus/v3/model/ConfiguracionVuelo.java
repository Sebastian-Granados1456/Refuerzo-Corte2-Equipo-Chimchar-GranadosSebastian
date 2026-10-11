package edu.eci.skycampus.v3.model;

/**
 * RF-12 / RNF-09: el coordinador configura el radio de vuelo de su sede, pero nunca puede
 * superar el límite de altura que impone la Aerocivil en zona urbana. La Aerocivil gana siempre.
 */
public class ConfiguracionVuelo {

    private static final int LIMITE_ALTURA_URBANA_METROS = 120;

    /** Altura efectiva que puede usar la sede: la que configura el coordinador, acotada por el límite regulatorio. */
    public int alturaMaximaPermitida(int alturaConfiguradaPorCoordinador) {
        return Math.min(alturaConfiguradaPorCoordinador, LIMITE_ALTURA_URBANA_METROS);
    }
}
