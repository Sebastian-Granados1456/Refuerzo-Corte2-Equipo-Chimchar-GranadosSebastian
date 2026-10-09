# Reto 03 Infernape - Patrones de rutas multi-etapa

Diagrama: `docs/uml/patrones-ruta-v3.drawio`. Código: `src/main/java/edu/eci/skycampus/v3/ruta/`.

## 1. Composite — rutas simples y compuestas

`ComponenteRuta` es la interfaz; `Etapa` es la hoja, `RutaCompuesta` es el nodo compuesto.

**Por qué Composite y no otro:** el `GestorMisiones` debe calcular y ejecutar una ruta simple (ECI → UNAL) igual que una ruta de varias etapas (ECI → estación de carga → UNAL), sin saber cuál es cuál. Composite permite tratarlas de forma uniforme: `distanciaKm()` funciona igual en ambas.

## 2. Strategy — algoritmos de optimización de ruta

`EstrategiaOptimizacion` es la interfaz; `RutaDirectaSiAlcanza` (≤5km, una etapa) y `RutaConEstacionCarga` (>5km, dos etapas con estación intermedia) son las implementaciones.

**Por qué Strategy y no otro:** el algoritmo para dividir una ruta en etapas puede cambiar (otra regla de distancia, otra forma de elegir la estación) sin tocar el código que la usa. Es la misma razón por la que v2 usa Strategy para elegir el drone.

## 3. Observer — alertas de cada etapa

`ObservadorRuta` es la interfaz; `EjecutorRuta` notifica a sus observadores cada vez que una etapa se completa.

**Por qué Observer y no otro:** varias partes del sistema necesitan enterarse de cada etapa completada (panel del operador, log, alertas), sin que `EjecutorRuta` conozca a ninguna en concreto. Es la misma relación que `GestorFlota`/`ObservadorFlota` en v2, pero para etapas de ruta en vez de cambios de estado de drone.

## 4. Factory Method — tipo de drone por etapa

`FabricaDroneEtapa` es la clase abstracta con `crearDrone(peso)`; `FabricaDroneMini`, `FabricaDroneExpress` y `FabricaDroneCargo` son las fábricas concretas. `paraPeso(peso)` elige cuál usar.

**Por qué Factory Method y no otro:** cada etapa puede necesitar un tipo de drone distinto según el peso que lleva en ese tramo. Encapsular la decisión en una fábrica evita un `if-else` repetido cada vez que se arma una ruta, y permite agregar un tipo de drone nuevo sin tocar el código que las usa.
