# Reto 12 Infernape - 3 capas de pruebas

Este proyecto no usa Spring Boot (es Maven con clases Java puras), así que no hay un endpoint REST real que probar con `MockMvc`. La prueba de integración usa `MisionEndpoint`, una fachada mínima con la misma firma que tendría un controlador REST, para probar el flujo de punta a punta sin HTTP ni base de datos reales.

## Capa 1: Unitarias de AsignadorMision

`AsignadorMisionTest` (Mockito) — 6 pruebas, cubriendo los 5 flujos alternos que pide el reto:

| Prueba | Flujo |
| --- | --- |
| `todoCorrecto_asignaElDroneAptoConMasBateria` | Camino feliz |
| `climaAdverso_noAsigna` | Clima adverso |
| `sinDronesDisponibles_noAsigna` | Sin drones |
| `paqueteMuyPesado_noAsigna` | Paquete pesado |
| `aerocivilRechaza_noAsignaYNoConsultaClimaNiFlota` | Aerocivil rechaza |
| `sedeInactiva_noAsignaYNoConsultaNadaMas` | Sede inactiva |

`AsignadorMision` ahora valida, en orden: sede activa → autorización Aerocivil → clima → capacidad del drone frente al peso del paquete. Cada validación corta el flujo si falla (verificado con `verifyNoInteractions` en los dos últimos casos).

## Capa 2: Integración (endpoint simulado)

`AsignadorMisionIntegrationTest` usa `RepositorioFlotaEnMemoria` (implementación real de `RepositorioFlota`, sin mocks) y `MisionEndpoint` (la fachada que haría de controlador) para probar las 3 capas juntas: dominio + aplicación + infraestructura en memoria.

## Capa 3: Cobertura JaCoCo

Quality Gate en `pom.xml`: línea ≥ 85%, rama ≥ 70%.

| Métrica | Resultado |
| --- | --- |
| Line coverage | 95.8% |
| Branch coverage | 91.3% |
| Pruebas totales | 72 |
