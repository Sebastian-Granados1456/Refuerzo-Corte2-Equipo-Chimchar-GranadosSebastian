# Reto 04 - Principios SOLID

## Violaciones en GestorDrone

- **S:** tiene cinco responsabilidades (asignar, guardar, alertar, reportar y calcular rutas).
- **O:** cada tipo de ruta nuevo obliga a modificar `calcularRuta`.
- **I:** quien solo asigna misiones depende también de guardar, alertar y reportar.
- **D:** depende directamente de MySQL con `DriverManager.getConnection`.
- **L:** no se viola, no hay herencia.

## Rediseño

| Clase | Responsabilidad |
| --- | --- |
| `AsignadorMision` | Asignar un drone a una misión. |
| `RepositorioMision` (interfaz) | Guardar misiones. |
| `AlertaOperador` (interfaz) | Avisar al operador. |
| `GeneradorReporte` | Generar el reporte. |
| `EstrategiaRuta` (interfaz) | Calcular la ruta (`RutaDirecta`, `RutaEvitandoEdificios`). |

Código en `src/main/java/edu/eci/skycampus/solid/`.
