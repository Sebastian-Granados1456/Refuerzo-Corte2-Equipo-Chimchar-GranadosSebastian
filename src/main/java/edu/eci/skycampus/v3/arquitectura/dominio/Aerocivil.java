package edu.eci.skycampus.v3.arquitectura.dominio;

/** Dominio: puerto hacia la regulación de espacio aéreo. La capa de aplicación solo conoce esta interfaz. */
public interface Aerocivil {
    boolean autorizaVuelo(String origen, String destino);
}
