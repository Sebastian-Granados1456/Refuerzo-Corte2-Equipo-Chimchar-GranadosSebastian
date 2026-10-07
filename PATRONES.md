# Reto 03 - Patrones de diseño

| Problema | Patrón | Por qué |
| --- | --- | --- |
| Misión con campos obligatorios y opcionales | Builder | Construye la misión paso a paso solo con los campos necesarios. |
| Validaciones en orden antes del vuelo | Chain of Responsibility | Cada validador revisa una regla y rechaza o pasa al siguiente. |
| Algoritmo de asignación intercambiable | Strategy | Cada algoritmo está en su clase y se cambia sin tocar el asignador. |

Código en `src/main/java/edu/eci/skycampus/patrones/`.
