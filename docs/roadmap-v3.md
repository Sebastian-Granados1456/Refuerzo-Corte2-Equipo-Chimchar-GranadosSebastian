# Reto 09 Infernape - Roadmap de Enterprise (3 sprints)

Trimestre de 3 sprints de 2 semanas. Velocidad base del equipo: 20 points/sprint (igual que en v2, `docs/sprint1-v2.md`).

## Sprint 1 — Conectividad multi-sede básica

| HU | RF | Puntos |
| --- | --- | --- |
| SC-HU11 Calcular analytics por sede | RF-11 | 5 |
| SC-HU13 Devolver vacío para sede sin actividad | RF-13 | 2 |
| SC-HU17 Asignar drone sin acoplarse a infraestructura (3 capas) | RF-17 | 8 |
| SC-HU18 Rechazar tiempo de entrega negativo | RF-18 | 2 |
| **Total** | | **17 / 20** |

**Story points totales:** 17. **Velocity estimada:** 17 (todas las HU completadas, sin arrastre).

## Sprint 2 — Rutas multi-etapa + estaciones de carga

| HU | RF | Puntos |
| --- | --- | --- |
| SC-HU14 Calcular ruta directa o con estación de carga | RF-14 | 8 |
| SC-HU15 Notificar cada etapa completada | RF-15 | 3 |
| SC-HU16 Elegir tipo de drone según peso de la etapa | RF-16 | 5 |
| SC-HU12 Configurar radio de vuelo sin violar el límite de Aerocivil | RF-12 | 3 |
| **Total** | | **19 / 20** |

**Story points totales:** 19. **Velocity estimada:** 19.

## Sprint 3 — Analytics + panel superadmin

| HU | RF | Puntos |
| --- | --- | --- |
| Panel Superadmin: visualizar las 4 sedes en tiempo real | — | 8 |
| Servicio de Analytics: enviar métricas a la plataforma externa | — | 5 |
| Dashboard de analytics por sede (consume SC-HU11) | RF-11 | 5 |
| **Total** | | **18 / 20** |

**Story points totales:** 18. **Velocity estimada:** 18 (el Panel Superadmin depende del C4 Nivel 2 ya diseñado en `docs/uml/c4-contenedores-v3.drawio`).

---

## Retrospectiva del Sprint 1

### Qué salió bien

1. Las 4 HU se resolvieron con código ya probado (62→66 pruebas entre los retos 03-06), sin retrabajo.
2. La arquitectura de 3 capas (SC-HU17) quedó 100% testeable con Mockito desde el primer intento, sin necesitar infraestructura real.
3. Reutilizar `TipoDrone` y patrones de v2 redujo el tiempo de SC-HU11 y SC-HU13 a menos de lo estimado.

### Qué mejorar

1. SC-HU17 (8 puntos) resultó la tarea más grande del sprint; se pudo partir en 2 HU más chicas (dominio+aplicación, luego infraestructura) para repartir mejor la carga.
2. Las reglas de negocio (RN-01, RN-02 de rutas) se definieron tarde, después de ya tener el código de Composite/Strategy escrito; definirlas antes habría evitado un pequeño ajuste.
3. No hubo prueba de integración entre capas (solo unitaria de aplicación); falta decidir si se agrega en Sprint 2.

### Compromisos

| Compromiso | Responsable | Fecha |
| --- | --- | --- |
| Dividir HU grandes (>5 puntos) en sub-HU antes de iniciar el sprint | Granados Sebastián | 17/10/2026 |
| Redactar las reglas de negocio de cada módulo antes de escribir código | Granados Sebastián | 17/10/2026 |
| Evaluar agregar una prueba de integración por capa en Sprint 2 | Granados Sebastián | 24/10/2026 |
