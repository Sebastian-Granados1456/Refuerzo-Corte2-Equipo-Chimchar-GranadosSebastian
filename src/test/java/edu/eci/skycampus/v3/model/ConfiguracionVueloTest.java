package edu.eci.skycampus.v3.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfiguracionVueloTest {

    private final ConfiguracionVuelo configuracion = new ConfiguracionVuelo();

    // RF-12: el coordinador configura un radio por debajo del límite -> se respeta
    @Test
    void coordinadorConfiguraMenosDelLimite_seRespeta() {
        assertEquals(80, configuracion.alturaMaximaPermitida(80));
    }

    // RNF-09: el coordinador configura un radio que supera el límite de Aerocivil -> se recorta a 120m
    @Test
    void coordinadorConfiguraMasDelLimite_seRecortaAlLimiteDeAerocivil() {
        assertEquals(120, configuracion.alturaMaximaPermitida(200));
    }
}
