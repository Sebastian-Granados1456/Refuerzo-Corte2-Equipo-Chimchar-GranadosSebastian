# Reto 03 - Patrones de diseño (Chimchar)

| Problema | Patrón | Por qué |
|---|---|---|
| 1. `Mision` con campos obligatorios y opcionales | **Builder** | Permite construir la misión paso a paso dando solo los campos necesarios, sin un constructor por cada combinación. |
| 2. Validaciones en orden antes de lanzar el drone | **Chain of Responsibility** | Cada validador revisa una sola regla y decide si rechaza la misión o la pasa al siguiente. |
| 3. Algoritmo de asignación de drone intercambiable | **Strategy** | Cada algoritmo vive en su propia clase y se cambia sin tocar `AsignadorDrone`. |

## Dónde está el código

- Builder: `src/main/java/edu/eci/skycampus/patrones/builder/MisionBuilder.java`
- Chain: `src/main/java/edu/eci/skycampus/patrones/chain/` (`Validador`, `ValidadorBateria`, `ValidadorDestino`, `ValidadorCarga`)
- Strategy: `src/main/java/edu/eci/skycampus/patrones/strategy/` (`EstrategiaAsignacion`, `MayorBateria`, `AsignadorDrone`)
- Demo: `src/main/java/edu/eci/skycampus/patrones/Main.java`
