# Reto 09 Monferno - Sprint 1 de SkyCampus v2

Épica **SC-E1**. Las 5 HU se crean como historias hijas de la épica, vinculadas con "relates to" a sus features correspondientes, igual que en el backlog de Chimchar (`docs/jira-backlog.md`).

## Estimación (Fibonacci) y velocidad

| HU | Nombre | RF | Puntos |
| --- | --- | --- | --- |
| HU-04 | Asignar automáticamente drone por batería | RF-07 | 5 |
| HU-05 | Priorizar drone más rápido en misión urgente | RF-08 | 3 |
| HU-06 | Consultar clima antes del vuelo | RF-10 | 5 |
| HU-07 | Notificar y gestionar drone en fallo | RF-09 | 3 |
| HU-08 | Ver flota de 20 drones agrupada por estado | RF-07 | 3 |
| | | **Total** | **19 / 20** |

Las 5 caben en la velocidad de 20 points del equipo; queda 1 point de margen para imprevistos del sprint.

---

## HU-04: Asignar automáticamente drone por batería

**Como** operador de drones, **quiero** que el sistema asigne automáticamente el drone de mayor batería disponible, **para** no tener que elegirlo manualmente en cada misión.

```gherkin
Característica: Asignación automática por batería

Escenario: Asignación exitosa al drone con más batería
  Dado que los drones D-03 (91%), D-08 (73%) y D-12 (60%) están disponibles y son compatibles con el paquete
  Cuando el sistema asigna automáticamente la misión
  Entonces el drone D-03 queda asignado a la misión

Escenario: Sin drones disponibles compatibles
  Dado que ningún drone disponible soporta el peso del paquete
  Cuando el sistema intenta asignar automáticamente la misión
  Entonces la misión queda en espera y se notifica al operador
```

## HU-05: Priorizar drone más rápido en misión urgente

**Como** operador de drones, **quiero** que una misión URGENTE reciba el drone más rápido disponible, **para** que las entregas críticas no esperen por batería.

```gherkin
Característica: Prioridad absoluta en misiones urgentes

Escenario: Misión urgente asigna el EXPRESS aunque tenga menos batería
  Dado que la misión es URGENTE y el drone EXPRESS D-14 (50%) y el MINI D-03 (91%) están disponibles y son compatibles
  Cuando el sistema asigna automáticamente la misión
  Entonces el drone D-14 queda asignado, aunque D-03 tenga más batería

Escenario: Sin EXPRESS disponible, se usa el más rápido restante
  Dado que la misión es URGENTE y ningún drone EXPRESS está disponible
  Cuando el sistema asigna automáticamente la misión
  Entonces se asigna el drone MINI disponible antes que un CARGO
```

## HU-06: Consultar clima antes del vuelo

**Como** sistema, **quiero** consultar la API Meteorológica antes de autorizar un despegue, **para** no arriesgar el drone ni la carga por mal clima.

```gherkin
Característica: Validación meteorológica previa al vuelo

Escenario: Clima apto autoriza el despegue
  Dado que la API Meteorológica reporta condiciones aptas para el destino
  Cuando el sistema valida la misión antes de asignar
  Entonces el despegue queda autorizado

Escenario: Clima adverso bloquea la asignación
  Dado que la API Meteorológica reporta viento fuerte o lluvia en el destino
  Cuando el sistema valida la misión antes de asignar
  Entonces la asignación se bloquea y se muestra "Condiciones climáticas no aptas para el vuelo"
```

## HU-07: Notificar y gestionar drone en fallo

**Como** técnico de mantenimiento, **quiero** recibir una notificación cuando un drone entra en FALLO, **para** repararlo y devolverlo a la flota.

```gherkin
Característica: Gestión de drones en fallo

Escenario: El técnico recibe la alerta de fallo
  Dado que el drone D-05 cambia de estado a FALLO
  Cuando el sistema notifica a sus observadores
  Entonces el técnico de mantenimiento recibe la alerta con el ID del drone

Escenario: El técnico marca el drone como reparado
  Dado que el drone D-05 está en estado FALLO
  Cuando el técnico lo marca como reparado
  Entonces el drone D-05 pasa a estado DISPONIBLE
```

## HU-08: Ver flota de 20 drones agrupada por estado

**Como** operador de drones, **quiero** ver la flota agrupada por estado, **para** encontrar un drone disponible sin revisar una lista plana de 20.

```gherkin
Característica: Panel de flota agrupado (Ley de Hick)

Escenario: La flota se muestra agrupada
  Dado que hay 20 drones en distintos estados
  Cuando el operador abre el panel de flota
  Entonces los drones se muestran agrupados en columnas por estado (Disponible, En vuelo, En carga, Fallo, Mantenimiento)

Escenario: El conteo por grupo es correcto
  Dado que 8 drones están DISPONIBLE y 9 están EN_VUELO
  Cuando el operador abre el panel de flota
  Entonces el grupo "Disponibles" muestra 8 y el grupo "En vuelo" muestra 9
```

---

## Definición de Terminado (DoD) del equipo

- Código con pruebas unitarias en verde y cobertura ≥ 80% (JaCoCo).
- Los 2 criterios Gherkin de la HU pasan manualmente o con prueba automatizada.
- Sin code smells críticos nuevos en SonarQube.
- Pull request revisado y aprobado por al menos 1 compañero.
- Merge a `develop` sin conflictos pendientes.
