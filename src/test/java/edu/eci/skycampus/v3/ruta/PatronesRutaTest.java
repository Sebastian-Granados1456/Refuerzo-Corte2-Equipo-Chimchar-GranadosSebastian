package edu.eci.skycampus.v3.ruta;

import edu.eci.skycampus.v2.model.TipoDrone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatronesRutaTest {

    // Composite: una ruta compuesta suma la distancia de sus etapas (hoja y compuesto se tratan igual)
    @Test
    void rutaCompuesta_sumaDistanciaDeSusEtapas() {
        RutaCompuesta ruta = new RutaCompuesta();
        ruta.agregar(new Etapa("ECI", "Estación de carga", 5));
        ruta.agregar(new Etapa("Estación de carga", "UNAL", 7));

        assertEquals(12, ruta.distanciaKm());
    }

    // Strategy: ruta directa si la distancia no supera el límite de vuelo con carga
    @Test
    void rutaDirectaSiAlcanza_unaSolaEtapa() {
        EstrategiaOptimizacion estrategia = new RutaDirectaSiAlcanza();

        RutaCompuesta ruta = estrategia.calcular("ECI", "UNAL", 4);

        assertEquals(1, ruta.etapas().size());
        assertEquals(4, ruta.distanciaKm());
    }

    // Strategy: ruta con estación de carga si la distancia supera el límite
    @Test
    void rutaConEstacionCarga_partenDosEtapas() {
        EstrategiaOptimizacion estrategia = new RutaConEstacionCarga();

        RutaCompuesta ruta = estrategia.calcular("ECI", "UNAL", 9);

        assertEquals(2, ruta.etapas().size());
        assertEquals(9, ruta.distanciaKm());
    }

    // Observer: cada etapa completada notifica a los observadores
    @Test
    void ejecutorRuta_notificaCadaEtapaCompletada() {
        RutaCompuesta ruta = new RutaCompuesta();
        ruta.agregar(new Etapa("ECI", "Estación de carga", 5));
        ruta.agregar(new Etapa("Estación de carga", "UNAL", 7));

        List<String> notificadas = new ArrayList<>();
        EjecutorRuta ejecutor = new EjecutorRuta();
        ejecutor.suscribir(etapa -> notificadas.add(etapa.origen() + "->" + etapa.destino()));

        ejecutor.ejecutar(ruta);

        assertEquals(List.of("ECI->Estación de carga", "Estación de carga->UNAL"), notificadas);
    }

    // Factory Method: cada fábrica crea el tipo de drone correcto según el peso de la etapa
    @Test
    void fabricaDroneEtapa_eligeTipoSegunPeso() {
        assertEquals(TipoDrone.MINI, FabricaDroneEtapa.paraPeso(300).crearDrone(300));
        assertEquals(TipoDrone.EXPRESS, FabricaDroneEtapa.paraPeso(700).crearDrone(700));
        assertEquals(TipoDrone.CARGO, FabricaDroneEtapa.paraPeso(1500).crearDrone(1500));
    }
}
