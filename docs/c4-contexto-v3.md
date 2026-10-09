# Reto 05 Infernape - C4 Nivel 1 y Nivel 2 de Enterprise

Diagramas: `docs/uml/c4-contexto-v3.drawio` (Nivel 1) y `docs/uml/c4-contenedores-v3.drawio` (Nivel 2).

## Evolución de los 3 niveles de contexto

| Nivel | Actores | Sistemas externos |
| --- | --- | --- |
| Chimchar (MVP) | Operador, Solicitante, Admin | Ninguno |
| Monferno (v2) | + Técnico de mantenimiento | API Clima, Control Aéreo, Sistema de Alertas |
| Infernape (Enterprise) | + Superadmin, Coordinador de sede | + Aerocivil, ERP universitario, Analytics platform |

## Qué se mantuvo

- Los 3 actores originales (Operador, Solicitante, Admin) y sus flujos básicos con el sistema.
- La convención visual: azul para el sistema propio, rojo para sistemas externos.
- Los 3 sistemas externos de v2 (clima, control aéreo, alertas) siguen presentes.

## Qué creció

- **Actores:** de 3 a 6, sumando roles con alcance de red completa (Superadmin) o de una sede (Coordinador).
- **Sistemas externos:** de 0 a 6, sumando los que exige operar entre 4 universidades (Aerocivil para vuelos inter-sede, ERP para usuarios, Analytics para métricas).
- **Complejidad interna:** el Nivel 2 agrega 7 contenedores (2 apps, 1 gateway, 4 servicios, 2 BD) que en el MVP y v2 no existían como tal — era "el sistema" como una sola caja.

## Cómo evolucionó sin perder coherencia

Cada actor y sistema externo nuevo resuelve un problema puntual del enunciado (Superadmin para la red completa, Aerocivil para vuelos inter-sede), no una funcionalidad inventada. El sistema central sigue siendo una sola caja en el Nivel 1; el Nivel 2 solo abre esa caja para mostrar cómo se reparte el trabajo por dentro (API Gateway como punto único de entrada, un servicio por responsabilidad), sin cambiar lo que los actores ven desde afuera.
