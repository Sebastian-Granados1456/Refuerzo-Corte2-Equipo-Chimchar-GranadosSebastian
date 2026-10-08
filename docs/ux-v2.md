# Reto 08 Monferno - Sistema de diseño v2 y Leyes UX

[Ver mock en Claude](https://claude.ai/artifact/NqVfmDJXg3jFf8rR3HrNZG)

Extiende la identidad de `docs/manual-identidad.md` (Chimchar), sin cambiar paleta ni tipografía.

## Componente: Tarjeta de drone (5 estados)

| Estado | Color | Acción |
| --- | --- | --- |
| Disponible | `#16A34A` verde | "Asignar" habilitado |
| En vuelo | `#2563EB` azul | Muestra el código de misión |
| En carga | `#EAB308` amarillo | "Asignar" deshabilitado, batería < 30% |
| Fallo | `#DC2626` rojo | "Asignar" deshabilitado, motivo del fallo |
| Mantenimiento *(nuevo en v2)* | `#475569` gris | "Asignar" deshabilitado, técnico asignado |

## Flujo de asignación automática (3 pantallas)

1. **Panel de flota** — los 20 drones agrupados por estado.
2. **Detalle de misión** — paquete, estrategia aplicada y drone elegido con su justificación.
3. **Confirmación** — código de misión generado y panel de alertas.

## Leyes UX aplicadas

| Ley | Dónde |
| --- | --- |
| Fitts | En el panel de flota, "Cancelar misión en vuelo" es un botón grande (44px+) junto a la misión urgente, no en un menú. |
| Hick | Los 20 drones del panel de flota se agrupan en 4 columnas por estado en vez de una lista plana de 20 filas. |
| Miller | El panel de alertas de la confirmación avisa al llegar a 7 notificaciones sin confirmar. |
