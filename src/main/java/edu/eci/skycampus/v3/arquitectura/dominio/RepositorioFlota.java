package edu.eci.skycampus.v3.arquitectura.dominio;

import edu.eci.skycampus.v3.model.Sede;

import java.util.List;

/** Dominio: puerto hacia la persistencia. La capa de aplicación solo conoce esta interfaz. */
public interface RepositorioFlota {
    List<DroneEnterprise> findDisponibles(Sede sede);
}
