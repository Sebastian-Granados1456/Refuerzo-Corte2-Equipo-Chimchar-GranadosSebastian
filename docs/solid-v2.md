# Reto 04 Monferno - SOLID: de GestorDrone a v2

## SRP

- **Antes:** `GestorDrone` asignaba, guardaba en BD, enviaba alertas, generaba reportes y calculaba rutas en una sola clase.
- **Ahora:** cada responsabilidad vive en su propia clase. `GestorFlota` solo coordina la estrategia de asignación y notifica a sus observadores; no guarda, no reporta, no calcula rutas.

## OCP

- **Antes:** `calcularRuta` tenía un `if-else` por tipo de ruta; cada tipo nuevo obligaba a modificar la clase.
- **Ahora:** `EstrategiaAsignacion` es una interfaz. `PorMayorBateria`, `PorMenorUsoAcumulado` y `PorTipoCompatibleConCarga` son implementaciones intercambiables. Un algoritmo nuevo es una clase nueva; `GestorFlota` no cambia (ver prueba abajo). Lo mismo aplica al Observer: un suscriptor nuevo no toca `GestorFlota`.

## DIP

- **Antes:** `guardarEnBD` llamaba directo a `DriverManager.getConnection`, acoplado a MySQL.
- **Ahora:** `GestorFlota` depende de la interfaz `EstrategiaAsignacion` y de la interfaz `ObservadorFlota`, nunca de una implementación concreta. Recibe la estrategia por constructor.

## Prueba: GestorFlota funciona con cualquier EstrategiaAsignacion

`GestorFlotaSolidTest` prueba dos cosas:

1. `GestorFlota` funciona igual con las 3 estrategias ya existentes, solo cambiando cuál se inyecta.
2. `GestorFlota` funciona con una **estrategia inventada dentro de la propia prueba** (que nunca formó parte de producción), sin tocar una sola línea de `GestorFlota.java`.
