# Reto 08 - Manual de identidad y UX/UI (Chimchar)

## 1. Manual de identidad de SkyCampus

### Paleta principal

| Rol | Nombre | Hex | Uso |
| --- | --- | --- | --- |
| Primario | Azul noche | `#0F2A4A` | Barra superior, títulos, encabezados de tabla |
| Secundario | Gris pizarra | `#334155` | Texto de apoyo, íconos, bordes activos |
| Acento | Cian técnico | `#0284C7` | Botones de acción principal y enlaces |
| Fondo | Niebla | `#F1F5F9` | Fondo general de la aplicación |
| Superficie | Blanco | `#FFFFFF` | Tarjetas y paneles |
| Texto principal | Tinta | `#0F172A` | Texto de contenido |
| Texto secundario | Gris medio | `#64748B` | Etiquetas, metadatos |
| Deshabilitado | Gris claro | `#CBD5E1` | Elementos no seleccionables |

**Justificación:** el azul noche y el gris pizarra transmiten control, seriedad y confianza, como un software de control de tráfico aéreo. El cian se reserva para la acción principal, así el operador siempre sabe dónde hacer clic. El fondo claro y neutro deja que los colores de estado sean lo único que resalta.

### Colores de estado del drone

| Estado | Color base | Fondo del badge | Texto del badge | Ícono |
| --- | --- | --- | --- | --- |
| Disponible | `#16A34A` | `#DCFCE7` | `#166534` | ● círculo |
| En vuelo | `#2563EB` | `#DBEAFE` | `#1E40AF` | ▲ avión / flecha |
| En carga | `#EAB308` | `#FEF9C3` | `#854D0E` | ⚡ rayo |
| Fallo | `#DC2626` | `#FEE2E2` | `#991B1B` | ✕ equis |

- El estado nunca se comunica solo con color: cada badge lleva **ícono + texto**, para usuarios con daltonismo.
- Los textos de badge sobre su fondo superan el contraste **4.5:1** (WCAG AA).
- La barra de batería usa los mismos colores: ≥ 50% verde, 30–49% amarillo, < 30% rojo.

### Tipografía

| Uso | Fuente | Pesos | Ejemplo |
| --- | --- | --- | --- |
| Interfaz (títulos, texto, botones) | **Inter** (sans-serif) | 400 regular, 600 semibold | "Panel de monitoreo de la flota" |
| Códigos de misión e IDs de drone | **JetBrains Mono** (monoespaciada) | 400 regular, 700 bold | `D-03`, `M-001` |

Escala: título 24 px / subtítulo 18 px / texto 14 px / etiqueta 12 px.

### Tono de voz

Técnico pero claro: el operador es un profesional. Mensajes directos, con el dato concreto y la acción a seguir.

| ✔ Así | ✘ No así |
| --- | --- |
| "El drone D-04 tiene batería insuficiente (18%). Mínimo requerido: 30%." | "Error de asignación." |
| "Misión M-001 registrada. Drone D-03 asignado." | "¡Listo! Todo salió genial 🎉" |

---

## 2. Mock del panel de monitoreo de la flota

Generado con IA (Claude + Figma MCP) aplicando el manual de identidad anterior.

- **Figma:** https://www.figma.com/design/OU9BxTSHijhHnXzWRVI6s3
- **Imagen:** `docs/images/mock-panel-flota.png`

![Panel de monitoreo de la flota](images/mock-panel-flota.png)

Muestra los 5 drones del MVP con su estado actual:

| Drone | Batería | Estado | Ubicación |
| --- | --- | --- | --- |
| `D-01` | 85% | Disponible | Bloque A |
| `D-02` | 42% | En vuelo (misión `M-002`) | Biblioteca |
| `D-03` | 91% | Disponible | Bloque C |
| `D-04` | 18% | En carga (no asignable) | Bloque B |
| `D-05` | 67% | Disponible | Bloque D |

---

## 3. Verificación de heurísticas de Nielsen

El mock cumple 7 de los 10 principios:

| # | Heurística | Cómo la cumple el mock |
| --- | --- | --- |
| 1 | Visibilidad del estado del sistema | Cada drone muestra su estado con badge de color + ícono + texto, y la batería con barra y porcentaje, sin hacer clic. Arriba a la derecha se indica "En vivo · actualizado hace 2 s" y las tarjetas resumen cuántos drones hay en cada estado. |
| 3 | Control y libertad del usuario | La misión `M-003` en estado PENDIENTE tiene el botón "Cancelar misión", así el operador puede deshacer una asignación antes del vuelo. |
| 4 | Consistencia y estándares | Los mismos colores e íconos de estado se usan en las tarjetas, en la tabla y en la leyenda. El botón de acción principal siempre es cian y los IDs siempre van en fuente monoespaciada. |
| 5 | Prevención de errores | El botón "Asignar" de `D-04` (18%) está deshabilitado y explica "Batería < 30%", en vez de dejar asignarlo y mostrar el error después. |
| 6 | Reconocer antes que recordar | El operador no tiene que memorizar umbrales: la leyenda de batería indica qué rango es asignable, y `D-02` muestra en qué misión está ("En misión M-002"). |
| 8 | Diseño estético y minimalista | La tabla solo muestra lo necesario para decidir: ID, modelo, batería, estado, ubicación y acción. Sin gráficos ni datos decorativos. |
| 9 | Ayudar a reconocer y recuperarse de errores | El mensaje de error es concreto y dice cómo resolverlo: "El drone D-04 tiene batería insuficiente (18%). Mínimo requerido: 30%." |

**No cubiertas en este mock:** #2 (relación con el mundo real, parcialmente por usar nombres de bloques reales), #7 (flexibilidad y eficiencia: no hay atajos de teclado ni filtros) y #10 (ayuda y documentación: solo existe el enlace "? Ayuda" en la barra superior, sin contenido).
