# Reto 05 Monferno - Diagrama de Contexto v2

Diagrama fuente: `docs/uml/c4-contexto-v2.drawio` (actualiza `docs/uml/c4-contexto.drawio` del MVP).

Convención de color (Semana 4): **azul** = sistema propio (SkyCampus), **rojo** = sistema externo ya existente.

## Qué se mantuvo

- Los 3 actores del MVP: Operador, Solicitante, Admin.
- Sus flujos de entrada/salida con el sistema (registrar solicitud, confirmar entrega, configurar flota).

## Qué creció

| Elemento | MVP (Chimchar) | v2 (Monferno) |
| --- | --- | --- |
| Actores | Operador, Solicitante, Admin | + Técnico de mantenimiento |
| Sistemas externos | Ninguno | API Meteorológica, Control Aéreo ECI, Sistema de Alertas |

## Sistemas externos nuevos

| Sistema | Flujo |
| --- | --- |
| API Meteorológica | SkyCampus ← condiciones de viento/lluvia del destino, antes de lanzar un drone |
| Control Aéreo ECI | SkyCampus → solicita autorización de ruta · Control Aéreo → autoriza y registra el vuelo |
| Sistema de Alertas | SkyCampus → notifica qué drone entró en estado FALLO |

El Técnico de mantenimiento recibe la alerta de fallo del sistema y marca el drone como reparado.
