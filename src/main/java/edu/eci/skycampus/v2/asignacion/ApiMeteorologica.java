package edu.eci.skycampus.v2.asignacion;

/** Sistema externo que informa si las condiciones del clima permiten volar. */
public interface ApiMeteorologica {
    boolean condicionesAptas(String destino);
}
