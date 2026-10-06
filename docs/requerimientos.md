# RF-01
## FUNCIONALIDAD

- **Código:** RF-01
- **Módulo/Agrupación:** Flota
- **Nombre:** Consultar flota de drones
- **Descripción:** Mostrar al operador el estado actual de cada drone de la flota.
- **Cómo se ejecutará:** El operador abre el panel de flota y el sistema lista los 5 drones con su batería, disponibilidad y ubicación.
- **Actor principal:** Operador de drones
- **Precondiciones:** Debe existir al menos un drone registrado en el sistema.
- **Dependencia:** Ninguna
- **Prioridad (MoSCoW):** Must Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Filtro de disponibilidad | Permite ver solo los drones disponibles | Boolean | Si no se envía, se muestran todos los drones | No |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Flota | Lista de drones con su estado actual | List&lt;Drone(id:String, bateria:Integer, disponible:Boolean, ubicacion:String)&gt; | Sí |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Operador de drones | El operador abre el panel de flota. |
| 2 | Sistema | El sistema consulta el estado actual de cada drone. |
| 3 | Sistema | El sistema muestra la lista con ID, batería (%), disponibilidad y ubicación. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si ningún drone está disponible, el sistema muestra la lista completa con el mensaje "No hay drones disponibles". | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | Un drone con batería menor al 20% se marca como batería crítica en el panel. |

# RF-02
## FUNCIONALIDAD

- **Código:** RF-02
- **Módulo/Agrupación:** Misiones
- **Nombre:** Registrar misión de reparto
- **Descripción:** Crear una misión de reparto de documento y asignarle un drone manualmente.
- **Cómo se ejecutará:** El operador diligencia origen, destino, tipo de carga y drone; el sistema crea la misión y devuelve su código.
- **Actor principal:** Operador de drones
- **Precondiciones:** Debe existir al menos un drone disponible con batería ≥ 30%.
- **Dependencia:** RF-01
- **Prioridad (MoSCoW):** Must Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Origen | Lugar desde donde sale el documento | Enum(BLOQUE_A, BLOQUE_B, BLOQUE_C, BLOQUE_D, BIBLIOTECA) | Debe ser distinto del destino | Sí |
| Destino | Lugar al que se entrega el documento | Enum(BLOQUE_A, BLOQUE_B, BLOQUE_C, BLOQUE_D, BIBLIOTECA) | Solo destinos fijos del campus | Sí |
| Tipo de carga | Tipo de documento a transportar | Enum(SOBRE, CARPETA, LIBRO) | | Sí |
| Drone asignado | Drone elegido por el operador | Drone(id:String, bateria:Integer, disponible:Boolean) | Debe estar disponible y con batería ≥ 30% | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Código de misión | Identificador generado para la misión (ej. M-001) | String | Sí |
| Estado de la misión | Estado inicial de la misión | Enum(PENDIENTE, EN_VUELO, ENTREGADA, FALLIDA) | Sí |
| Mensaje de error | Indicación de drone no apto o destino inválido | String | No |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Operador de drones | El operador ingresa origen, destino y tipo de carga. |
| 2 | Operador de drones | El operador selecciona un drone disponible de la flota. |
| 3 | Sistema | El sistema valida batería del drone y destino. |
| 4 | Sistema | El sistema crea la misión en estado PENDIENTE y muestra el código generado. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si el drone tiene batería menor al 30%, el sistema rechaza la asignación con mensaje de error. | |
| 2 | Sistema | Si el destino es igual al origen, el sistema rechaza el registro. | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | Un drone no puede tener más de una misión activa al mismo tiempo. |

# RF-03
## FUNCIONALIDAD

- **Código:** RF-03
- **Módulo/Agrupación:** Misiones
- **Nombre:** Consultar historial de misiones del día
- **Descripción:** Mostrar las misiones registradas en el día con su estado actual.
- **Cómo se ejecutará:** El operador abre la vista de historial y el sistema lista las misiones del día ordenadas por hora de registro.
- **Actor principal:** Operador de drones
- **Precondiciones:** Ninguna.
- **Dependencia:** RF-02
- **Prioridad (MoSCoW):** Should Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Estado | Filtra las misiones por estado | Enum(PENDIENTE, EN_VUELO, ENTREGADA, FALLIDA) | Si no se envía, se muestran todas | No |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Misiones del día | Lista de misiones registradas hoy | List&lt;Mision(id:String, droneId:String, destino:String, estado:EstadoMision)&gt; | Sí |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Operador de drones | El operador abre la vista de historial. |
| 2 | Sistema | El sistema consulta las misiones registradas en el día. |
| 3 | Sistema | El sistema muestra código, drone, destino y estado de cada misión. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si no hay misiones en el día, el sistema muestra "No hay misiones registradas hoy". | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | El historial solo muestra misiones del día en curso (desde las 00:00). |

# RNF-01
## NO FUNCIONALIDAD

- **Código:** RNF-01
- **Nombre:** Actualización del panel de flota
- **Categoría:** Rendimiento
- **Descripción:** El panel de flota debe reflejar cualquier cambio de estado de un drone sin que el operador recargue la página.
- **Justificación:** Si el panel muestra datos viejos, el operador puede asignar un drone que ya está en vuelo.
- **Métrica:** Cambio visible en < 3 segundos, verificado con cronómetro en 10 intentos.

# RNF-02
## NO FUNCIONALIDAD

- **Código:** RNF-02
- **Nombre:** Facilidad para registrar una misión
- **Categoría:** Usabilidad
- **Descripción:** Un operador nuevo debe poder registrar una misión sin capacitación previa.
- **Justificación:** El registro es la acción que más repite el operador durante el día.
- **Métrica:** Registro en < 1 minuto y máximo 5 clics, en prueba con 3 operadores nuevos.

# RNF-03
## NO FUNCIONALIDAD

- **Código:** RNF-03
- **Nombre:** Persistencia de misiones
- **Categoría:** Confiabilidad
- **Descripción:** Ninguna misión registrada debe perderse si la aplicación se reinicia.
- **Justificación:** Perder una misión significa perder el rastro de un documento físico en el campus.
- **Métrica:** 0 misiones perdidas al registrar 20 misiones y reiniciar la aplicación.

---

# Matriz de Priorización (MoSCoW)

**Criterio:** MoSCoW (Must / Should / Could / Won't).

- 🔴 **Must have** — imprescindible para el MVP
- 🟠 **Should have** — importante, no bloqueante
- 🔵 **Could have** — deseable si hay tiempo
- ⚪ **Won't have (por ahora)** — fuera de este alcance

| Código | Módulo | Nombre | Prioridad | Dependencia | Justificación |
| --- | --- | --- | --- | --- | --- |
| RF-01 | Flota | Consultar flota de drones | 🔴 Must Have | Ninguna | Sin ver qué drones están disponibles y con batería, el operador no puede asignar ninguna misión. |
| RF-02 | Misiones | Registrar misión de reparto | 🔴 Must Have | RF-01 | Es el flujo principal del MVP; sin él el sistema no cumple su propósito de reparto. |
| RF-03 | Misiones | Consultar historial de misiones del día | 🟠 Should Have | RF-02 | Ayuda al control del operador, pero el reparto funciona aunque al inicio no exista. |
| RNF-01 | — | Actualización del panel de flota | 🟠 Should Have | — | Con 5 drones el operador puede refrescar manualmente, pero un panel lento lleva a asignar drones ocupados. |
| RNF-02 | — | Facilidad para registrar una misión | 🔵 Could Have | — | Mejora la experiencia, pero el MVP sigue siendo útil aunque el registro tome algo más. |
| RNF-03 | — | Persistencia de misiones | 🔴 Must Have | — | Perder una misión es perder un documento sin rastro, lo que hace inaceptable el sistema. |
