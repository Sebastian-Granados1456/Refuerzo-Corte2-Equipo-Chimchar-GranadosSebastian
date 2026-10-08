package edu.eci.skycampus.v2.model;

import edu.eci.skycampus.model.EstadoMision;

import java.time.LocalDateTime;

public record Mision(String id, Drone drone, String destino, int pesoPaqueteGramos, Prioridad prioridad,
                     EstadoMision estado, LocalDateTime creadaEn) {}
