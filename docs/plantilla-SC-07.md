| SKYCAMPUS<br>Desarrollo y Operaciones de Software | ANÁLISIS DE REQUERIMIENTOS | Fecha: 07/10/2026 |
| --- | --- | --- |
| | | Página: 1 de 1 |

## FUNCIONALIDAD

- **Código:** SC-07
- **Nombre:** Asignar automáticamente drone a misión
- **Descripción:** Elegir y asignar sin intervención del operador un drone apto para una misión nueva, consultando primero el clima y aplicando la estrategia de selección activa.
- **Cómo se ejecutará:** El sistema recibe la solicitud de misión, consulta la API Meteorológica, filtra los drones aptos para el paquete, aplica la estrategia de selección activa, valida al candidato y lo asigna, notificando a los observadores de la flota.
- **Actor principal:** Sistema
- **Precondiciones:** Debe existir al menos un drone registrado en la flota.

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| paquete | Carga a transportar en la misión | — | — | Sí |
| paquete.peso | Peso del paquete en gramos | Integer | Entre 1 y 2000 gramos | Sí |
| paquete.tipo | Tipo de contenido del paquete | Enum(SOBRE, CARPETA, LIBRO, EQUIPO) | — | Sí |
| paquete.prioridad | Urgencia de la misión | Enum(URGENTE, NORMAL, BAJO) | Si es URGENTE, aplica SC-08 en vez de esta estrategia | Sí |
| destino | Punto del campus al que vuela el drone | Enum(BLOQUE_A, BLOQUE_B, BLOQUE_C, BLOQUE_D, BIBLIOTECA) | Se envía a la API Meteorológica | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| droneAsignado | Drone elegido por el sistema | — | Calculado por el sistema según la estrategia activa | No (salida) |
| droneAsignado.id | Identificador del drone | String | Formato D-XX | No (salida) |
| droneAsignado.bateria | Nivel de batería del drone asignado | Integer | — | No (salida) |
| motivoRechazo | Explica por qué no se pudo asignar un drone | String | Solo se devuelve en los flujos alternos | No |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Recibe la solicitud de misión con su paquete y destino. | |
| 2 | Sistema | Consulta la API Meteorológica para el destino de la misión. | FA-2 |
| 3 | Sistema | Filtra los drones disponibles cuyo tipo soporta el peso del paquete. | FA-1 |
| 4 | Sistema | Aplica la estrategia de selección activa sobre los drones filtrados. | FA-3 |
| 5 | Sistema | Valida que el candidato tenga batería ≥ 30%, capacidad suficiente y esté disponible. | |
| 6 | Sistema | Asigna el drone a la misión y notifica a los observadores (panel, log, técnico). | |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| FA-1 | Sistema | Se desvía en el paso 3. Si ningún drone disponible es compatible con el peso del paquete, el sistema rechaza la asignación y muestra "No hay drones disponibles para este paquete". | Sin drones disponibles |
| FA-2 | Sistema | Se desvía en el paso 2. Si la API Meteorológica reporta condiciones adversas (viento fuerte o lluvia) o no responde, el sistema bloquea la asignación y muestra "Condiciones climáticas no aptas para el vuelo". | Clima adverso |
| FA-3 | Sistema | Se desvía en el paso 4. Si el paquete supera la capacidad máxima de todos los tipos de drone (2000g), el sistema rechaza la misión y muestra "El paquete supera la capacidad máxima de la flota". | Paquete supera capacidad |

**Notas y comentarios:**
- Si `paquete.prioridad` es URGENTE, este RF no se ejecuta: se aplica SC-08 (prioridad absoluta del drone más rápido), que reemplaza por completo la estrategia de este flujo.
- La consulta meteorológica (paso 2) corresponde al RF-10 del documento de requerimientos v2.

---

## ANEXOS

### Prototipos

_Pendiente: se agrega el mock del flujo de asignación automática del reto 11._

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| RN-01 | Un drone con batería menor al 30% no puede ser asignado a ninguna misión. |
| RN-02 | El drone de tipo CARGO no puede usarse para paquetes con peso menor a 100 gramos. |

---

## ABREVIATURAS

| Abreviatura | Significado |
| --- | --- |
| SC | SkyCampus (prefijo de los requerimientos del sistema) |
| RN | Regla de negocio |
| FA | Flujo alterno |
| API | Interfaz de programación de aplicaciones (Application Programming Interface) |

---

## HISTORIAL DE REVISIÓN

| Elaborado por | Aprobado por | Fecha | Descripción y Justificación de Cambios |
| --- | --- | --- | --- |
| Equipo DOSW - Granados Sebastián | | 07/10/2026 | Versión inicial del documento. |
