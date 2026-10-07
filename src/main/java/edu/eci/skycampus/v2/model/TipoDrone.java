package edu.eci.skycampus.v2.model;

public enum TipoDrone {
    MINI(500), CARGO(2000), EXPRESS(800);

    private final int capacidadMaximaGramos;

    TipoDrone(int capacidadMaximaGramos) {
        this.capacidadMaximaGramos = capacidadMaximaGramos;
    }

    public int capacidadMaximaGramos() {
        return capacidadMaximaGramos;
    }
}
