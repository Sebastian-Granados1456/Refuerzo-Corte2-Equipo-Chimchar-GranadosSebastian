# Reto 06 Infernape - Matriz de trazabilidad

8 RF de SkyCampus Enterprise. Ninguno queda sin CU ni sin prueba.

## RF + CU + HU + Prueba

| Código | Nombre | MoSCoW | CU relacionado | HU en Jira | Prueba que lo valida |
| --- | --- | --- | --- | --- | --- |
| RF-11 | Calcular tasa de éxito y tiempo promedio por sede | Must Have | Ver analytics de sede | SC-HU11 | `EstadisticasSedeTest.sedeConUnaMision_calculaSusPropiosValores`, `.redCompleta_cadaSedeTieneSusPropiasMetricas` |
| RF-12 | Configurar radio máximo de vuelo por sede | Should Have | Configurar sede | SC-HU12 | `ConfiguracionVueloTest` (ver tensión con RNF-09 abajo) |
| RF-13 | Devolver vacío para una sede sin actividad | Must Have | Ver analytics de sede | SC-HU13 | `EstadisticasSedeTest.sedeVacia_devuelveOptionalEmpty` |
| RF-14 | Calcular ruta directa o con estación de carga según distancia | Must Have | Calcular ruta multi-etapa | SC-HU14 | `PatronesRutaTest.rutaDirectaSiAlcanza_unaSolaEtapa`, `.rutaConEstacionCarga_partenDosEtapas` |
| RF-15 | Notificar cada etapa completada de una ruta | Must Have | Calcular ruta multi-etapa | SC-HU15 | `PatronesRutaTest.ejecutorRuta_notificaCadaEtapaCompletada` |
| RF-16 | Elegir el tipo de drone según el peso de la etapa | Must Have | Asignar drone | SC-HU16 | `PatronesRutaTest.fabricaDroneEtapa_eligeTipoSegunPeso` |
| RF-17 | Asignar drone consultando clima sin acoplarse a infraestructura | Must Have | Asignar drone | SC-HU17 | `AsignadorMisionTest.climaApto_asignaElPrimerDroneDisponible`, `.climaAdverso_noConsultaLaFlota` |
| RF-18 | Rechazar un tiempo de entrega negativo | Must Have | Ver analytics de sede | SC-HU18 | `EstadisticasSedeTest.minutosEntregaNegativo_lanzaExcepcion` |

Los CU de la tabla son los definidos en `docs/casos-uso-v2.md` (misiones) y el diagrama de contexto Enterprise (`docs/c4-contexto-v3.md`), extendidos con los paquetes de Mantenimiento y Administración que el nivel Infernape introduce.

## RNF (4)

| Código | Categoría | Descripción | Métrica |
| --- | --- | --- | --- |
| RNF-09 | Seguridad / regulación | Ningún drone vuela a más de 120m de altura en zona urbana inter-sede. | 0 vuelos que superen 120m, verificado por el Servicio de Rutas antes de autorizar. |
| RNF-10 | Rendimiento | El cálculo de analytics por sede responde en menos de 1s con 1000 misiones. | Tiempo de `analizarPorSede` < 1000ms con dataset de prueba. |
| RNF-11 | Escalabilidad | El sistema soporta 100 drones de 5 tipos sin degradar el tiempo de asignación. | Tiempo de `AsignadorMision.asignar` estable con flota de 100 drones. |
| RNF-12 | Disponibilidad | El Servicio de Rutas sigue operando si la Plataforma Analytics no responde. | 0% de fallos en asignación de ruta cuando Analytics está caído. |

## Tensión RF-12 / RNF-09

- **RF-12:** el coordinador de cada sede puede configurar el radio máximo de vuelo de los drones de su sede.
- **RNF-09:** ningún drone puede volar a más de 120m de altura en zona urbana, por regulación de la Aerocivil.

**Resolución:** el radio que configura el coordinador (RF-12) es una preferencia operativa de la sede; el límite de 120m de la Aerocivil (RNF-09) es una restricción regulatoria que no se negocia. El sistema valida el RNF **después** de aplicar la configuración del coordinador: si el radio configurado implica superar los 120m en zona urbana, la ruta se recalcula o se rechaza, nunca se permite el vuelo. El coordinador puede configurar un radio menor al límite, nunca uno que lo supere.

Esto se resuelve en el `Servicio de Rutas` (Nivel 2, `docs/uml/c4-contenedores-v3.drawio`): antes de autorizar una ruta, consulta tanto la configuración de radio de la sede (RF-12) como la restricción de Aerocivil (RNF-09, vía `ServicioClima`/control aéreo), y la Aerocivil siempre tiene la última palabra.

**Implementación mínima:** `ConfiguracionVuelo.alturaMaximaPermitida(...)` toma la altura que configura el coordinador y la acota al límite de 120m, sin importar qué tan alto la haya puesto. Probado en `ConfiguracionVueloTest`: un radio dentro del límite se respeta, uno que lo supera se recorta.
