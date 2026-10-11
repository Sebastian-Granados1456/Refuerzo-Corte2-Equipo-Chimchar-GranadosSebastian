package edu.eci.skycampus.v3.arquitectura.dominio;

/** Dominio: puerto hacia el sistema externo de clima. La capa de aplicación solo conoce esta interfaz. */
public interface ServicioClima {
    boolean condicionesAptas(String origen, String destino);
}
