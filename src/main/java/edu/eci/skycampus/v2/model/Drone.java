package edu.eci.skycampus.v2.model;

public record Drone(String id, TipoDrone tipo, int bateria, boolean disponible, EstadoDrone estado,
                     int misionesCompletadas) {}
