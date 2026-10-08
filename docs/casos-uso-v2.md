# Reto 10 Monferno - Casos de uso del módulo de misiones v2

Diagrama fuente: `docs/uml/cu-modulo-mision-v2.drawio`.

## Actores (4)

| Actor | Rol |
| --- | --- |
| Operador de drones | Registra misiones, ve la flota, cancela misiones pendientes |
| Técnico de mantenimiento | **Hereda de Operador** (generalización UML): además de ver la flota, repara drones en FALLO |
| Solicitante | Pide el reparto de un documento |
| Admin | Configura destinos y registra drones |

El Técnico hereda de Operador porque comparte su necesidad de ver el estado de la flota antes de decidir qué drone atender; su CU exclusivo es "Marcar drone reparado".

## Include (siempre se ejecutan)

| CU base | Incluye | Por qué |
| --- | --- | --- |
| Registrar misión | Validar condiciones climáticas | Sin consultar el clima, el sistema no puede decidir si autoriza el despegue (RF-10). |
| Registrar misión | Aplicar estrategia de asignación | Toda misión registrada necesita un drone asignado automáticamente (RF-07). |

## Extend (condicionales)

| CU base | Extendido por | Condición |
| --- | --- | --- |
| Registrar misión | Priorizar drone más rápido | `[prioridad = URGENTE]` — solo reemplaza la estrategia normal en este caso (RF-08). |
| Ver flota de drones | Notificar alerta de fallo | `[drone entra en estado FALLO]` — solo se dispara ante ese cambio de estado (RF-09). |
