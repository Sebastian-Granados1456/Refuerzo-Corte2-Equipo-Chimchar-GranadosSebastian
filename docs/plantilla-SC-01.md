# SKYCAMPUS<br>Desarrollo y Operaciones de Software<br>ANÁLISIS DE REQUERIMIENTOS

## FUNCIONALIDAD

- **Código:** SC-01
- **Nombre:** Registrar misión de reparto de documento
- **Descripción:** Crear una misión de reparto de un documento entre dos puntos fijos del campus, con un drone asignado manualmente por el operador.
- **Cómo se ejecutará:** El operador abre el formulario de nueva misión en el panel de SkyCampus, ingresa origen, destino y tipo de carga, selecciona un drone de la lista de disponibles y confirma; el sistema valida los datos y genera el código de misión.
- **Actor principal:** Operador de drones
- **Precondiciones:** Debe existir al menos un drone disponible con batería ≥ 30%.

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Origen | Punto del campus desde donde sale el documento | Enum(BLOQUE_A, BLOQUE_B, BLOQUE_C, BLOQUE_D, BIBLIOTECA) | Debe ser distinto del destino | Sí |
| Destino | Punto del campus donde se entrega el documento | Enum(BLOQUE_A, BLOQUE_B, BLOQUE_C, BLOQUE_D, BIBLIOTECA) | Solo destinos fijos del campus (RN-03) | Sí |
| Tipo de carga | Tipo de documento a transportar | Enum(SOBRE, CARPETA, LIBRO) | | Sí |
| Drone asignado | Drone elegido por el operador para la misión | Drone(id:String, modelo:String, bateria:Integer, disponible:Boolean, ubicacion:String) | Debe estar disponible (RN-01) y con batería ≥ 30% (RN-02) | Sí |
| Notas del operador | Indicaciones adicionales para la entrega (ej. "Entregar en secretaría") | String | Máximo 200 caracteres | No |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Código de misión | Identificador único de la misión creada | String | Formato `M-NNN`, consecutivo (ej. M-001) | Sí |
| Estado de la misión | Estado inicial de la misión | Enum(PENDIENTE, EN_VUELO, ENTREGADA, FALLIDA) | Siempre inicia en PENDIENTE | Sí |
| Mensaje de error | Motivo por el que no se pudo registrar la misión | String | Solo se muestra en los flujos alternos | No |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Operador de drones | El operador ingresa origen, destino, tipo de carga y, opcionalmente, notas. | FA-2 |
| 2 | Sistema | El sistema muestra los drones disponibles con su ID, batería y ubicación. | |
| 3 | Operador de drones | El operador selecciona el drone que realizará la misión y confirma. | |
| 4 | Sistema | El sistema valida que el drone tenga batería ≥ 30%, que no tenga una misión activa y que el destino sea válido. | FA-1, FA-3 |
| 5 | Sistema | El sistema crea la misión en estado PENDIENTE, marca el drone como no disponible y muestra el código de misión generado. | |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| FA-1 | Sistema | Se desvía en el paso 4. Si el drone seleccionado tiene batería menor al 30%, el sistema rechaza la asignación, muestra "Batería insuficiente: el drone debe tener al menos 30%" y el operador vuelve al paso 3. | Incumplimiento de RN-02 |
| FA-2 | Sistema | Se desvía en el paso 1. Si el destino no pertenece a los destinos fijos o es igual al origen, el sistema rechaza el registro, muestra "Destino inválido" y el operador corrige el dato. | Dato inválido (RN-03) |
| FA-3 | Sistema | Se desvía en el paso 4. Si el drone ya tiene una misión PENDIENTE o EN_VUELO, el sistema rechaza la asignación, muestra "El drone ya tiene una misión activa" y el operador vuelve al paso 3. | Incumplimiento de RN-01 |

**Notas y comentarios:**
- En el MVP la asignación del drone es manual; la asignación automática queda fuera de alcance (Monferno).
- Solo se transportan documentos; el tipo LIBRO existe en el modelo, pero su uso depende de la capacidad del drone.
- La ruta es predefinida por el par origen-destino; este RF no calcula rutas.

---

## ANEXOS

### Prototipos

_Pendiente: se agrega el mock del panel de SkyCampus elaborado en el reto 08._

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| RN-01 | Un drone no puede tener más de una misión activa (PENDIENTE o EN_VUELO) al mismo tiempo. |
| RN-02 | Un drone con batería menor al 30% no puede ser asignado a ninguna misión. |
| RN-03 | Origen y destino deben ser puntos fijos del campus (Bloque A, B, C, D o Biblioteca) y distintos entre sí. |

---

## ABREVIATURAS

| Abreviatura | Significado |
| --- | --- |
| SC | SkyCampus (prefijo de los requerimientos del sistema) |
| RN | Regla de negocio |
| FA | Flujo alterno |
| MVP | Producto mínimo viable (Minimum Viable Product) |
| M-NNN | Formato del código de misión (M + consecutivo de 3 dígitos) |

---

## HISTORIAL DE REVISIÓN

| Elaborado por | Aprobado por | Fecha | Descripción y Justificación de Cambios |
| --- | --- | --- | --- |
| Equipo DOSW - Granados Sebastián | | 06/10/2026 | Versión inicial del documento. |
