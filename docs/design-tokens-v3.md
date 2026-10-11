# Reto 08 Infernape - Design tokens de SkyCampus Enterprise

[Ver demo en Claude](https://claude.ai/artifact/4TnGWMu1DLh2qtNr4vbvya)

Una app, 4 identidades: los componentes (Tarjeta de drone, Panel de misión activa, Indicador de batería) son idénticos en HTML/CSS. Solo cambian 4 tokens por sede.

## Los 4 tokens

| Token | ECI | UNAL | Uniandes | EAFIT |
| --- | --- | --- | --- | --- |
| `--color-primary` | `#00457C` | `#7B0000` | `#0057A8` | `#2D6A4F` |
| `--color-alert` | `#E63946` | `#E63946` | `#E63946` | `#E63946` |
| `--font-ui` | Space Grotesk | Merriweather | Inter | Source Sans |
| `--border-radius` | 10px | 4px | 8px | 12px |

`--color-alert` es igual en las 4 sedes a propósito: una alerta roja debe reconocerse igual sin importar la identidad visual.

## Cómo funciona

Cada componente usa `var(--token)` en vez de un valor fijo. Cambiar de sede es poner un atributo `data-sede="UNAL"` en el contenedor; el CSS de esa sede sobreescribe las 4 variables, y todo lo que está dentro (Panel de misión activa, Tarjeta de drone) se repinta solo. El HTML y el CSS de los componentes no cambian una sola línea entre sedes.

## Configurar una quinta universidad (< 2 horas)

1. Definir los 4 valores de la tabla para la nueva sede (hex de color, nombre de fuente, radio en px).
2. Agregar un bloque `[data-sede="NUEVA"] { --color-primary: ...; --font-ui: ...; --border-radius: ...; }`.
3. Cargar la fuente si no está ya en el proyecto.
4. Sumar el botón del selector de sede.

No se toca ningún componente existente.
