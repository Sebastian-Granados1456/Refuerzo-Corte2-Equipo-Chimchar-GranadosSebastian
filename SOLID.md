# Reto 04 - Principios SOLID (Chimchar)

## 1. Por qué `GestorDrone` viola cada principio

- **S (Responsabilidad única):** `GestorDrone` tiene cinco razones para cambiar (asignar, guardar en BD, enviar alertas, generar reportes y calcular rutas), así que un cambio en cualquiera de ellas obliga a tocar la misma clase.
- **O (Abierto/Cerrado):** `calcularRuta` usa `if-else` por tipo, de modo que agregar un nuevo tipo de ruta obliga a modificar la clase en vez de extenderla.
- **I (Segregación de interfaces):** Quien solo necesita asignar misiones queda acoplado a guardar, alertar y generar reportes, métodos que no usa.
- **D (Inversión de dependencias):** `guardarEnBD` crea la conexión con `DriverManager.getConnection` a MySQL, así que depende de un detalle concreto y no de una abstracción.
- **L (Sustitución de Liskov):** No se viola directamente porque el fragmento no tiene herencia.

## 2. Rediseño

| Clase | Única razón para cambiar |
|---|---|
| `AsignadorMision` | La regla de asignar una misión a un drone. Recibe sus dependencias por constructor. |
| `RepositorioMision` (interfaz) | Cómo se guardan las misiones. `RepositorioMisionMemoria` es una implementación; una de MySQL sería otra. |
| `AlertaOperador` (interfaz) | Cómo se avisa al operador. `AlertaConsola` es una implementación; una de email sería otra. |
| `GeneradorReporte` | El formato del reporte. |
| `EstrategiaRuta` (interfaz) | El algoritmo de ruta. Implementaciones: `RutaDirecta` y `RutaEvitandoEdificios`. Un tipo nuevo es una clase nueva. |

Código: `src/main/java/edu/eci/skycampus/solid/` (demo en `Main.java`).
