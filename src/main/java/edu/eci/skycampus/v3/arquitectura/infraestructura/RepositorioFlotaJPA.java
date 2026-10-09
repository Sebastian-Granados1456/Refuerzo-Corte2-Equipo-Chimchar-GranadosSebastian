package edu.eci.skycampus.v3.arquitectura.infraestructura;

import edu.eci.skycampus.v3.arquitectura.dominio.DroneEnterprise;
import edu.eci.skycampus.v3.arquitectura.dominio.RepositorioFlota;
import edu.eci.skycampus.v3.model.Sede;

import java.util.List;

/** Infraestructura: implementación real contra la base de datos (JPA iría aquí). */
public class RepositorioFlotaJPA implements RepositorioFlota {

    @Override
    public List<DroneEnterprise> findDisponibles(Sede sede) {
        throw new UnsupportedOperationException("Requiere una base de datos real; fuera del alcance de este reto");
    }
}
