# RF-07
## FUNCIONALIDAD

- **Código:** RF-07
- **Módulo/Agrupación:** Asignación
- **Nombre:** Asignar automáticamente drone a misión por batería
- **Descripción:** Asignar sin intervención del operador el drone de mayor batería disponible a una misión nueva.
- **Cómo se ejecutará:** Al registrarse una misión, el sistema filtra los drones disponibles y compatibles con el peso del paquete, y asigna el de mayor batería.
- **Actor principal:** Sistema
- **Precondiciones:** Debe existir al menos un drone disponible compatible con el peso del paquete.
- **Dependencia:** Ninguna
- **Prioridad (MoSCoW):** Must Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Peso del paquete | Peso en gramos del paquete a transportar | Integer | Determina qué tipos de drone son compatibles | Sí |
| Prioridad de la misión | Nivel de urgencia de la misión | Enum(URGENTE, NORMAL, BAJO) | Si es URGENTE, aplica RF-08 en su lugar | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Drone asignado | Drone elegido por el sistema | Drone(id:String, tipo:TipoDrone, bateria:Integer) | Sí |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Sistema | Filtra los drones disponibles compatibles con el peso del paquete. |
| 2 | Sistema | Entre los compatibles, elige el de mayor batería. |
| 3 | Sistema | Asigna el drone a la misión y notifica al operador. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si ningún drone disponible es compatible con el peso, la misión queda en espera y se notifica al operador. | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | Esta regla de "mayor batería" solo aplica a misiones NORMAL o BAJO. Las URGENTE se rigen por RF-08. |

# RF-08
## FUNCIONALIDAD

- **Código:** RF-08
- **Módulo/Agrupación:** Asignación
- **Nombre:** Priorizar drone más rápido para misiones urgentes
- **Descripción:** Asignar a una misión URGENTE el drone más rápido disponible, sin importar su nivel de batería.
- **Cómo se ejecutará:** Al registrarse una misión con prioridad URGENTE, el sistema ignora la regla de mayor batería (RF-07) y asigna el drone EXPRESS disponible; si no hay, el más rápido del tipo compatible restante.
- **Actor principal:** Sistema
- **Precondiciones:** Debe existir al menos un drone disponible compatible con el peso del paquete.
- **Dependencia:** RF-07
- **Prioridad (MoSCoW):** Must Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Prioridad de la misión | Nivel de urgencia de la misión | Enum(URGENTE, NORMAL, BAJO) | Dispara esta regla solo si es URGENTE | Sí |
| Peso del paquete | Peso en gramos del paquete a transportar | Integer | Determina qué tipos de drone son compatibles | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Drone asignado | Drone más rápido compatible elegido por el sistema | Drone(id:String, tipo:TipoDrone, bateria:Integer) | Sí |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Sistema | Detecta que la misión tiene prioridad URGENTE. |
| 2 | Sistema | Filtra los drones EXPRESS disponibles y compatibles con el peso. |
| 3 | Sistema | Asigna el EXPRESS disponible, sin comparar batería entre candidatos. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si ningún EXPRESS está disponible o compatible, el sistema asigna el drone más rápido entre los tipos restantes compatibles (ver RN-02). | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | Una misión URGENTE nunca se evalúa con la regla de mayor batería de RF-07. |
| 2 | Orden de velocidad entre tipos: EXPRESS > MINI > CARGO. |

**Tensión con RF-07:** "mayor batería" (RF-07) y "más rápido, sin importar batería" (RF-08) pueden señalar drones distintos para la misma misión. No son contradictorios porque actúan en momentos distintos: RF-08 se evalúa primero y, si la misión es URGENTE, **reemplaza** por completo a RF-07 en vez de competir con él. La RN-01 de RF-08 deja esto explícito para que el desarrollador no intente combinar ambos criterios en una sola misión.

# RF-09
## FUNCIONALIDAD

- **Código:** RF-09
- **Módulo/Agrupación:** Mantenimiento
- **Nombre:** Notificar y gestionar drone en estado FALLO
- **Descripción:** Avisar al técnico de mantenimiento cuando un drone entra en estado FALLO y permitirle marcarlo como reparado.
- **Cómo se ejecutará:** El sistema detecta el cambio de estado a FALLO, notifica al técnico (Observer) y este, al terminar la reparación, marca el drone como DISPONIBLE.
- **Actor principal:** Técnico de mantenimiento
- **Precondiciones:** El drone debe estar en estado FALLO.
- **Dependencia:** Ninguna
- **Prioridad (MoSCoW):** Must Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| ID del drone reparado | Drone que el técnico marca como reparado | String | Debe estar en estado FALLO | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Notificación de fallo | Alerta con ID del drone y hora del fallo | String | Sí |
| Estado actualizado | Nuevo estado del drone tras la reparación | Enum(EstadoDrone) | Sí |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Sistema | Detecta el cambio de estado de un drone a FALLO. |
| 2 | Sistema | Notifica al técnico de mantenimiento (y a los demás observadores). |
| 3 | Técnico de mantenimiento | Repara el drone y lo marca como DISPONIBLE en el sistema. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si el técnico intenta marcar como reparado un drone que no está en FALLO, el sistema rechaza la acción. | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | Un drone en FALLO no puede recibir misiones hasta que el técnico lo marque como DISPONIBLE. |

# RF-10
## FUNCIONALIDAD

- **Código:** RF-10
- **Módulo/Agrupación:** Asignación
- **Nombre:** Consultar condiciones meteorológicas antes del vuelo
- **Descripción:** Consultar la API Meteorológica para verificar viento y lluvia en el destino antes de autorizar el despegue.
- **Cómo se ejecutará:** Antes de asignar un drone, el sistema consulta la API Meteorológica con el destino de la misión; si las condiciones no son aptas, bloquea la asignación.
- **Actor principal:** Sistema
- **Precondiciones:** Debe existir un drone candidato para la misión (resultado de RF-07 u RF-08).
- **Dependencia:** RF-07, RF-08
- **Prioridad (MoSCoW):** Must Have

---

## DATOS DE ENTRADA

| Nombre | Descripción | Tipo de campo | Reglas / Aplicación | Obligatorio |
| --- | --- | --- | --- | --- |
| Destino de la misión | Punto del campus al que se dirige el drone | String | Se envía a la API Meteorológica | Sí |

---

## DATOS DE SALIDA

| Nombre | Descripción | Tipo de campo | Obligatorio |
| --- | --- | --- | --- |
| Condiciones aptas | Indica si el clima permite volar | Boolean | Sí |
| Motivo de bloqueo | Explica por qué no se autorizó el vuelo | String | No |

---

## FLUJO BÁSICO

| Paso | Actor | Descripción |
| --- | --- | --- |
| 1 | Sistema | Consulta viento y lluvia del destino en la API Meteorológica. |
| 2 | Sistema | Si las condiciones son aptas, autoriza el despegue del drone asignado. |

---

## FLUJO ALTERNO

| Paso | Actor | Descripción | Excepciones |
| --- | --- | --- | --- |
| 1 | Sistema | Si hay viento fuerte o lluvia, bloquea la asignación y notifica el motivo al operador. | |
| 2 | Sistema | Si la API Meteorológica no responde, el sistema bloquea el despegue por precaución y lo marca para reintento. | |

---

## REGLAS DE NEGOCIO

| No. | Descripción |
| --- | --- |
| 1 | Ningún drone despega si la API Meteorológica reporta condiciones no aptas o no responde. |

---

# RNF-04
## NO FUNCIONALIDAD

- **Código:** RNF-04
- **Nombre:** Tiempo de asignación automática
- **Categoría:** Rendimiento
- **Descripción:** El sistema debe asignar un drone a una misión NORMAL o BAJO en menos de 2 segundos desde que se registra.
- **Justificación:** Con 20 drones y misiones simultáneas, una asignación lenta retrasa todo el flujo de reparto.
- **Métrica:** Tiempo de asignación < 2 segundos en el 95% de los casos, medido en 50 misiones de prueba.

# RNF-05
## NO FUNCIONALIDAD

- **Código:** RNF-05
- **Nombre:** Notificación inmediata de fallo
- **Categoría:** Disponibilidad
- **Descripción:** El técnico de mantenimiento debe recibir la notificación de un drone en FALLO sin retraso perceptible.
- **Justificación:** Un drone en fallo sin atender reduce la flota operativa y puede implicar un riesgo físico.
- **Métrica:** Notificación entregada en menos de 3 segundos tras el cambio de estado, en el 100% de los casos.

# RNF-06
## NO FUNCIONALIDAD

- **Código:** RNF-06
- **Nombre:** Tolerancia a falla de la API Meteorológica
- **Categoría:** Confiabilidad
- **Descripción:** Si la API Meteorológica no responde, el sistema no debe autorizar vuelos por defecto.
- **Justificación:** Asumir "condiciones aptas" ante un error de red pondría en riesgo los drones y la carga.
- **Métrica:** 0% de despegues autorizados cuando la API Meteorológica falla o supera 5 segundos de respuesta, verificado en pruebas con la API caída.

# RNF-07
## NO FUNCIONALIDAD

- **Código:** RNF-07
- **Nombre:** Escalabilidad de la flota a 20 drones
- **Categoría:** Escalabilidad
- **Descripción:** El sistema debe calcular estadísticas de flota (por tipo, por estado) sin degradar el tiempo de respuesta del panel.
- **Justificación:** La v2 pasa de 5 a 20 drones con misiones simultáneas; el MVP no fue probado a este volumen.
- **Métrica:** El panel de flota carga en menos de 1 segundo con 20 drones y 50 misiones activas.

---

# Matriz de Priorización (MoSCoW)

| Código | Módulo | Nombre | Prioridad | Dependencia | Justificación |
| --- | --- | --- | --- | --- | --- |
| RF-07 | Asignación | Asignar automáticamente drone por batería | 🔴 Must Have | Ninguna | Es la novedad central de la v2: sin asignación automática, el operador sigue asignando manualmente como en el MVP. |
| RF-08 | Asignación | Priorizar drone más rápido en misiones urgentes | 🔴 Must Have | RF-07 | Una misión URGENTE mal asignada (drone lento) puede significar una entrega crítica tarde; el reto lo marca como caso explícito a resolver. |
| RF-09 | Mantenimiento | Notificar y gestionar drone en FALLO | 🔴 Must Have | Ninguna | Un drone en fallo sin atender reduce la flota disponible y es un riesgo físico. |
| RF-10 | Asignación | Consultar clima antes del vuelo | 🔴 Must Have | RF-07, RF-08 | Volar con viento fuerte o lluvia pone en riesgo el drone y la carga; es una condición de seguridad, no una mejora opcional. |
| RNF-04 | — | Tiempo de asignación automática | 🟠 Should Have | — | Mejora la experiencia del operador, pero una asignación algo más lenta no rompe el sistema. |
| RNF-05 | — | Notificación inmediata de fallo | 🔴 Must Have | — | Un retraso en la alerta de fallo retrasa la reparación y deja el drone fuera de servicio más tiempo del necesario. |
| RNF-06 | — | Tolerancia a falla de la API Meteorológica | 🔴 Must Have | — | Autorizar un vuelo sin saber el clima real es un riesgo de seguridad inaceptable. |
| RNF-07 | — | Escalabilidad de la flota a 20 drones | 🟠 Should Have | — | El sistema puede operar algo más lento al principio, pero debe planearse para no degradarse al crecer. |
