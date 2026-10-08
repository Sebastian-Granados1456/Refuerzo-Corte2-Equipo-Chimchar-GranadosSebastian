# Reto 11 Monferno - Mocks del flujo de asignación automática

[Ver flujo en Claude](https://claude.ai/artifact/NqVfmDJXg3jFf8rR3HrNZG)

Reutiliza las 3 pantallas del reto 08 (`docs/ux-v2.md`): Panel de flota → Detalle de misión → Confirmación, con la identidad de SkyCampus. Se añade una 4ª pantalla con los 3 estados de error del flujo SC-07, enlazada desde Detalle de misión.

## Los 3 estados de error (flujos alternos de SC-07)

| Error | Mensaje | Corresponde a |
| --- | --- | --- |
| Sin drones disponibles | "No hay drones disponibles para este paquete." | FA-1 |
| Clima adverso | "Condiciones climáticas no aptas para el vuelo." | FA-2 |
| Paquete supera capacidad | "El paquete supera la capacidad máxima de la flota." | FA-3 |

## Principios de Nielsen (7 de 10)

| # | Heurística | Dónde se cumple |
| --- | --- | --- |
| 1 | Visibilidad del estado | El detalle de misión muestra cada validación en curso (clima, peso, batería) antes de asignar. |
| 3 | Control del usuario | "Cancelar misión en vuelo" en el panel de flota. |
| 4 | Consistencia | Los mismos colores de estado (verde/azul/amarillo/rojo/gris) se repiten en las 4 pantallas. |
| 5 | Prevención de errores | El drone con batería < 30% aparece sin el botón "Asignar" habilitado. |
| 6 | Reconocer antes que recordar | El detalle de misión explica por qué se eligió ese drone en vez de otro, en vez de que el operador deba inferirlo. |
| 8 | Diseño minimalista | El panel de flota agrupa 20 drones en 4 columnas por estado (Ley de Hick), en vez de una lista plana. |
| 9 | Mensajes de error claros | Los 3 estados de error dicen la causa y qué pasa con la misión, no solo "Error". |
