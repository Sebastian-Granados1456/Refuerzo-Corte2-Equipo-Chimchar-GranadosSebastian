# Reto 10 Infernape - CU de Enterprise en 5 paquetes

Diagrama: `docs/uml/cu-enterprise-v3.drawio`.

## Los 5 paquetes (literales del enunciado)

| Paquete | CU |
| --- | --- |
| Gestión de Misiones | Ver solicitudes pendientes, Crear misión, Cancelar misión, Ver historial |
| Gestión de Flota | Ver estado de drones, Asignar drone, Transferir drone entre sedes, Ver analytics |
| Rutas y Navegación | Calcular ruta simple, Calcular ruta multi-etapa, Autorizar con Aerocivil |
| Mantenimiento | Diagnosticar fallo, Marcar en mantenimiento, Aprobar retorno a servicio |
| Administración | Configurar sede, Gestionar usuarios, Ver dashboard Enterprise, Generar reportes |

## Herencia de actores

- **Coordinador de sede extiende Operador:** hereda Ver estado de drones, Asignar drone, Crear/Cancelar misión de su sede, y además configura el radio de vuelo y ve analytics de su sede.
- **Superadmin extiende Coordinador:** hereda todo lo anterior para las 4 sedes, y además ve el dashboard Enterprise y genera reportes de la red completa.

## Include (siempre se ejecutan)

| CU base | Incluye | Por qué |
| --- | --- | --- |
| Crear misión | Validar autorización Aerocivil | Ninguna misión se registra sin confirmar que el espacio aéreo lo permite. |
| Asignar drone | Elegir tipo de drone por etapa | La asignación siempre necesita decidir qué tipo de drone usar (Factory Method). |
| Calcular ruta multi-etapa | Autorizar con Aerocivil | Toda ruta inter-sede requiere autorización antes de calcularse. |
| Diagnosticar fallo | Marcar en mantenimiento | Diagnosticar un fallo siempre termina marcando el drone fuera de servicio. |
| Gestionar usuarios | Sincronizar usuarios con ERP | Los datos de usuario vienen del ERP universitario (sistema externo, reto 05). |

## Extend (condicionales)

| CU base | Extendido por | Condición |
| --- | --- | --- |
| Calcular ruta multi-etapa | Asignar estación de carga | `[distancia > 5km]` — solo si la ruta supera el límite de vuelo sin recargar (RN-01, reto 07). |
| Ver dashboard Enterprise | Diagnosticar fallo | `[hay drones en estado FALLO]` — el dashboard resalta el diagnóstico solo cuando hay algo que atender. |

## Por qué es comprensible sin conocimientos técnicos

Cada paquete corresponde a una actividad del día a día del reparto (misiones, flota, rutas, mantenimiento, administración), con nombres en lenguaje llano (verbo + objeto). Un rector no necesita saber qué es Aerocivil como API: ve "Autorizar con Aerocivil" y entiende que es un permiso externo antes de volar. La jerarquía de actores también se lee sin UML: un coordinador hace todo lo que hace un operador, y el superadmin todo lo que hace un coordinador, más la vista de toda la red.
