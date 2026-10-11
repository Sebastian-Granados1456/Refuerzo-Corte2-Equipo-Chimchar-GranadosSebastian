package edu.eci.skycampus.v3.arquitectura.dominio;

import edu.eci.skycampus.v2.model.TipoDrone;
import edu.eci.skycampus.v3.model.Sede;

/** Dominio: sin anotaciones de Spring ni JPA, sin dependencias externas. */
public record DroneEnterprise(String id, Sede sede, TipoDrone tipo, int bateria, boolean disponible) {
}
