# Reto 11 Infernape - Prototipo navegable del dashboard Enterprise

[Ver prototipo en Claude](https://claude.ai/artifact/8NE2fq88vjkqV3ovm88qYu)

3 pantallas clicables, sin que nadie tenga que explicarlas: **Dashboard → Planificar ruta → Confirmación**.

## 1. Red completa con estado en tiempo real

El dashboard muestra las 4 sedes (ECI, UNAL, Uniandes, EAFIT) con el conteo de drones por estado (disponible, en vuelo, en carga, fallo) y un indicador "En vivo".

## 2. Dos identidades solo cambiando tokens

Dos botones ("Identidad ECI" / "Identidad UNAL") cambian `--color-primary`, `--font-ui` y `--border-radius` en todo el dashboard, igual que en el reto 08 (`docs/design-tokens-v3.md`). El HTML del dashboard es el mismo en ambos casos.

## 3. Flujo de planificación de ruta inter-sede con todos los errores

La pantalla "Planificar ruta" (SC-15) tiene un selector de escenario para recorrer los 4 caminos sin reescribir el formulario:

| Escenario | Qué muestra |
| --- | --- |
| Camino feliz | Ruta calculada en 2 etapas con estación de carga, drone asignado, botón para confirmar |
| FA-1 | Aerocivil rechaza el vuelo |
| FA-2 | No hay estación de carga disponible |
| FA-3 | El paquete supera la capacidad de todos los drones |

La confirmación muestra el código de las 2 etapas y el drone asignado.

## Prueba con un compañero

No se realizó: queda pendiente para cuando el equipo tenga oportunidad de hacerla con una persona real, siguiendo la instrucción del reto (si pregunta algo durante la prueba, es un problema de Nielsen a corregir).
