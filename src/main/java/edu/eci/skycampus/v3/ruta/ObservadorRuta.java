package edu.eci.skycampus.v3.ruta;

/** Observer: se notifica cuando el drone completa una etapa de la ruta. */
public interface ObservadorRuta {
    void onEtapaCompletada(Etapa etapa);
}
