package edu.eci.skycampus.v3.model;

import edu.eci.skycampus.model.EstadoMision;
import edu.eci.skycampus.v2.model.Prioridad;

public record MisionEnterprise(String id, Sede sede, String droneId, EstadoMision estado, Prioridad prioridad,
                               long minutosEntrega) {}
