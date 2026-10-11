# Reto 04 Infernape - AsignadorMision de Enterprise en 3 capas

Código: `src/main/java/edu/eci/skycampus/v3/arquitectura/`.

| Capa | Paquete | Contenido |
| --- | --- | --- |
| Dominio | `arquitectura.dominio` | `DroneEnterprise` (record), `RepositorioFlota` y `ServicioClima` (interfaces). Sin Spring, sin JPA, sin anotaciones externas. |
| Aplicación | `arquitectura.aplicacion` | `AsignadorMision`: solo depende de las interfaces de dominio, inyectadas por constructor (DIP). |
| Infraestructura | `arquitectura.infraestructura` | `RepositorioFlotaJPA` y `ServicioClimaOpenWeather`: implementaciones concretas (BD y HTTP reales). |

## Pruebas con Mockito

`AsignadorMisionTest` mockea `RepositorioFlota` y `ServicioClima` con `@Mock`. La capa de aplicación se prueba completa sin ninguna llamada HTTP real ni base de datos:

- Clima apto → asigna el primer drone disponible.
- Clima adverso → no asigna y ni siquiera consulta la flota.
