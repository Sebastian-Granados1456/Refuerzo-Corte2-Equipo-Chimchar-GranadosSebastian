| SKYCAMPUS<br>Desarrollo y Operaciones de Software | ANÁLISIS DE REQUERIMIENTOS | Fecha: 09/10/2026 |
| --- | --- | --- |
| | | Página: 1 de 1 |

## FUNCIONALIDAD

- **Código:** SC-15
- **Nombre:** Planificar ruta multi-etapa inter-sede
- **Descripción:** Calcular y confirmar la ruta de un drone que viaja entre dos sedes, dividiéndola en etapas con estaciones de carga cuando la distancia lo requiere, y validando la autorización de la Aerocivil.
- **Cómo se ejecutará:** El sistema recibe la misión inter-sede, verifica la autorización de la Aerocivil, calcula las etapas, asigna estaciones de carga y drones por etapa, y confirma la ruta antes de iniciar el vuelo.
- **Actor principal:** Sistema
- **Precondiciones:** Debe existir una misión inter-sede registrada, con sede origen y sede destino distintas.

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| mision | La misión inter-sede a planificar | — | — | Sí |
| mision.paquete | Carga a transportar | — | — | Sí |
| mision.paquete.peso | Peso del paquete en gramos | Integer | Entre 1 y 2000 gramos | Sí |
| mision.paquete.tipo | Tipo de contenido del paquete | Enum(SOBRE, CARPETA, LIBRO, EQUIPO) | — | Sí |
| mision.paquete.restriccionesEspeciales | Condiciones adicionales del paquete (ej. frágil) | String | — | No |
| mision.sedeOrigen | Sede desde donde sale el drone | Enum(ECI, UNAL, UNIANDES, EAFIT) | Debe ser distinta de sedeDestino | Sí |
| mision.sedeDestino | Sede a la que llega el drone | Enum(ECI, UNAL, UNIANDES, EAFIT) | Debe ser distinta de sedeOrigen | Sí |
| condicionesEspacioAereo | Estado actual del espacio aéreo entre las dos sedes | — | Se obtiene de la Aerocivil | Sí |
| condicionesEspacioAereo.autorizado | Si la Aerocivil permite el vuelo inter-sede en este momento | Boolean | — | Sí |
| condicionesEspacioAereo.alturaMaximaMetros | Altura máxima permitida en el tramo | Integer | ≤ 120m en zona urbana (RNF-09) | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| rutaConfirmada | Ruta multi-etapa lista para ejecutar | — | Calculada por el sistema | No (salida) |
| rutaConfirmada.etapas | Lista de etapas de la ruta | List&lt;Etapa(origen:String, destino:String, distanciaKm:Integer, droneId:String)&gt; | Cada etapa ≤ 5km con carga | No (salida) |
| rutaConfirmada.estacionesCarga | Estaciones de carga usadas en la ruta | List&lt;String&gt; | Vacía si la ruta no las necesita | No (salida) |
| motivoRechazo | Explica por qué no se pudo planificar la ruta | String | Solo se devuelve en los flujos alternos | No |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Verifica la autorización de la Aerocivil para el tramo entre sedeOrigen y sedeDestino. | FA-1 |
| 2 | Sistema | Calcula las etapas de la ruta según la distancia total (directa si ≤5km, con estación si supera el límite). | |
| 3 | Sistema | Asigna las estaciones de carga intermedias necesarias. | FA-2 |
| 4 | Sistema | Asigna un drone a cada etapa, según el peso del paquete en ese tramo. | FA-3 |
| 5 | Sistema | Confirma la ruta completa con sus etapas y drones asignados. | |
| 6 | Sistema | Inicia el vuelo de la primera etapa. | |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| FA-1 | Sistema | Se desvía en el paso 1. Si la Aerocivil no autoriza el tramo, el sistema rechaza la planificación y muestra "Vuelo no autorizado por la Aerocivil". | Aerocivil rechaza |
| FA-2 | Sistema | Se desvía en el paso 3. Si no hay ninguna estación de carga disponible en el tramo requerido, el sistema rechaza la planificación y muestra "No hay estación de carga disponible en la ruta". | Sin estación de carga |
| FA-3 | Sistema | Se desvía en el paso 4. Si el paquete supera la capacidad de todos los tipos de drone para una ruta larga, el sistema rechaza la planificación y muestra "El paquete es demasiado pesado para esta ruta". | Paquete demasiado pesado |

**Notas y comentarios:**
- La división en etapas reutiliza la estrategia de optimización de ruta de SC-13 (`RutaDirectaSiAlcanza` / `RutaConEstacionCarga`).
- La asignación de drone por etapa reutiliza la fábrica de drones de SC-16 (`FabricaDroneEtapa`).
- La altura máxima de `condicionesEspacioAereo` se valida con la regla RN-02, que resuelve la tensión entre el radio configurado por el coordinador de sede (RF-12) y el límite de la Aerocivil (RNF-09).

---

## ANEXOS

### Prototipos

_Pendiente: se agrega el mock del flujo de planificación de ruta inter-sede del reto 11._

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| RN-01 | Un drone no puede volar más de 5km con carga sin recargar en una estación. |
| RN-02 | El paquete no puede permanecer en una estación de carga más de 30 minutos. |

---

## ABREVIATURAS

| Abreviatura | Significado |
| --- | --- |
| SC | SkyCampus (prefijo de los requerimientos del sistema) |
| RN | Regla de negocio |
| FA | Flujo alterno |

---

## HISTORIAL DE REVISIÓN

| Elaborado por | Aprobado por | Fecha | Descripción y Justificación de Cambios |
| --- | --- | --- | --- |
| Equipo DOSW - Granados Sebastián | | 09/10/2026 | Versión inicial del documento. |
