# Reto 11 - Mocks con IA (Chimchar)

Panel de monitoreo de la flota en sus 3 estados, generado con IA (Claude + Figma MCP) siguiendo el proceso de 4 pasos.

- **Figma:** https://www.figma.com/design/OU9BxTSHijhHnXzWRVI6s3

---

## 1. Referencias

Paneles de control reales usados como referencia para la pantalla de monitoreo:

| Referencia | Qué se tomó |
| --- | --- |
| [Axxon — Rastreo GPS](https://get.axxon.co/es/rastreo-gps) | Mapa en vivo con la posición de cada vehículo, **geocercas** (zonas autorizadas) y **alertas en tiempo real** de eventos y desvíos. En SkyCampus: mapa del campus con la posición de cada drone, la geocerca del campus y el panel "Alertas recientes". |
| [Alfred — Flotas Pro](https://alfred.co/flotas-pro) | "Tableros que hablan claro": indicadores de la salud de la flota visibles de un vistazo y **alertas preventivas de mantenimiento**. En SkyCampus: tarjetas de conteo por estado, filtros por estado con su cantidad y la alerta "Mantenimiento preventivo de D-01 en 5 vuelos". |

## 2. Estilo

Se usa el manual de identidad del reto 08 (`docs/manual-identidad.md`):

- Paleta: azul noche `#0F2A4A`, gris pizarra `#334155`, cian `#0284C7` para la acción principal, fondo `#F1F5F9`.
- Estados: Disponible `#16A34A` ●, En vuelo `#2563EB` ▲, En carga `#EAB308` ▮, Fallo `#DC2626` ✕ (siempre con ícono + texto).
- Tipografía: Inter (interfaz) y JetBrains Mono (IDs de drone y códigos de misión).

La plantilla del reto sugiere fondo oscuro, pero se mantuvo el fondo claro del manual de identidad para que las pantallas sean coherentes con la identidad ya definida.

## 3. Datos del RF SC-01

- **Entrada para el operador:** ver la flota para elegir un drone apto (disponible y con batería ≥ 30%).
- **Lo que muestra la pantalla por drone:** ID (`D-XX`), batería en %, estado (DISPONIBLE / EN_VUELO / EN_CARGA / FALLO) y ubicación actual.
- **Acciones:** seleccionar drone, asignar a misión, ver detalle.

## 4. Prompt utilizado

```text
Actúa como diseñador UX/UI senior de sistemas de control.
SISTEMA: SkyCampus — Panel de control de flota de drones ECI
PANTALLA: Panel de monitoreo de la flota (vista principal del operador)
ESTILO: Primario azul noche #0F2A4A, secundario gris pizarra #334155, acento cian #0284C7
  para la acción principal, fondo claro #F1F5F9 y tarjetas blancas. Tipografía Inter
  400/600 para la interfaz y JetBrains Mono para IDs de drone y códigos de misión.
  Colores de estado: Disponible #16A34A (círculo), En vuelo #2563EB (triángulo),
  En carga #EAB308 (barra), Fallo #DC2626 (equis); cada estado con ícono + texto.
ACTOR: Operador de drones — necesita tomar decisiones rápidas
DATOS A MOSTRAR POR DRONE: ID (formato D-XX), batería en % con barra
  (≥50% verde, 30–49% amarillo, <30% rojo), estado (DISPONIBLE/EN_VUELO/EN_CARGA/FALLO),
  ubicación actual. Flota: 5 drones DJI Mini 3 (D-01 a D-05).
ACCIONES DEL OPERADOR: Seleccionar drone, Asignar a misión, Ver detalle, Filtrar por estado
REFERENCIAS: tomar de Axxon (rastreo GPS) el mapa en vivo con geocerca y el feed de
  alertas en tiempo real, y de Alfred (Flotas Pro) los indicadores de salud de la flota
  y las alertas preventivas de mantenimiento.
COMPONENTES: tarjetas de conteo por estado, mapa del campus (Bloques A–D y Biblioteca)
  con la posición de cada drone y sus rutas en curso, filtros por estado sobre la tabla,
  tabla de drones, misiones pendientes y alertas recientes.
ESTADOS DE LA PANTALLA:
  1. Normal: flota con drones en distintos estados
  2. Alerta: un drone en estado FALLO (destacado visualmente)
  3. Vacío: todos los drones en misión simultáneamente
Nielsen: visibilidad del estado (#1), minimalismo (#8), prevención errores (#5)
Regla: un drone con batería < 30% no puede asignarse; mostrarlo deshabilitado con el motivo.
```

---

## 5. Pantallas generadas

### Estado 1 · Normal

![Estado 1 Normal](images/mock-estado-1-normal.png)

Flota con drones en los distintos estados: 3 disponibles, `D-02` en vuelo y `D-04` en carga con 18%. El operador tiene seleccionado `D-03` (fila resaltada en cian).

| Heurística | Cómo la cumple |
| --- | --- |
| #1 Visibilidad del estado | Estado de cada drone con badge (color + ícono + texto), batería con barra y porcentaje, tarjetas con el conteo por estado e indicador "En vivo · actualizado hace 2 s". |
| #5 Prevención de errores | "Asignar" de `D-04` está deshabilitado con el motivo "Batería < 30%". |
| #6 Reconocer antes que recordar | El mapa muestra dónde está cada drone y su ruta, sin tener que recordar en qué bloque quedó; los filtros indican cuántos drones hay en cada estado. |
| #8 Diseño minimalista | La tabla muestra solo ID, modelo, batería, estado, ubicación y acción. |

### Estado 2 · Alerta (drone en fallo)

![Estado 2 Alerta](images/mock-estado-2-alerta.png)

`D-05` pasa a FALLO: su fila se resalta en rojo con borde lateral, la tarjeta "Fallo" se marca, el indicador superior cambia a "1 alerta activa" y aparece un mensaje con la causa y qué hacer.

| Heurística | Cómo la cumple |
| --- | --- |
| #1 Visibilidad del estado | La alerta se ve sin hacer clic en la fila, la tarjeta de resumen, el indicador superior, el mapa (marcador rojo con halo sobre el Bloque D) y el primer ítem de "Alertas recientes". |
| #5 Prevención de errores | `D-05` queda con "Asignar" deshabilitado y el texto "Fuera de servicio". |
| #9 Mensajes de error claros | "Fallo en D-05: pérdida de señal GPS sobre el Bloque D. El drone quedó fuera de servicio y no se puede asignar. Avisa al técnico de mantenimiento." |

### Estado 3 · Vacío (todos en misión)

![Estado 3 Vacío](images/mock-estado-3-vacio.png)

Los 5 drones están en vuelo: ningún botón "Asignar" aparece, cada fila indica su misión y destino, y un mensaje informativo explica cuándo vuelve el próximo drone. La misión `M-008` queda pendiente en espera.

| Heurística | Cómo la cumple |
| --- | --- |
| #1 Visibilidad del estado | Contadores 0 disponibles / 5 en vuelo, el mapa con las 5 rutas en curso y cada drone con su misión (`M-002`…`M-007`) y destino. |
| #3 Control del usuario | La misión pendiente puede cancelarse mientras espera un drone. |
| #8 Diseño minimalista | En lugar de una tabla vacía de acciones, un único mensaje dice qué pasa y qué puede hacer el operador. |
