# Manifiesto de revisión de ADR

- Estado: completado
- Fecha de revisión: 2026-09-29

## Resumen de la revisión

Revisión de ADR completada para este change.

Se revisaron las decisiones de `design.md` y se evaluaron contra el criterio de
compromiso arquitectónico duradero. `<repo>/adr/` no existía antes de este change, de modo
que no hay ADR vigentes que restrinjan el diseño y ninguna decisión previa constitue
compromiso vivo.

## ADR vigentes revisados

- Ninguno — `<repo>/adr/` no contenía ADR vigentes antes de este change.

## Nuevos ADR duraderos creados

Cinco decisiones de `design.md` superan el criterio de compromiso duradero: cada una fija
una frontera, tecnología o invariante que condicionará changes futuros, y ninguna puede
alterarse sin una decisión de arquitectura, no con un ajuste de implementación. El detalle
—contexto, opciones, consecuencias— reside en cada archivo de `<repo>/adr/`, y **no** se
repite aquí.

| # | Archivo | Decisión | Estado |
|---|---|---|---|
| ADR-0001 | `adr/0001-kotlin-multiplatform-como-stack-del-frontend.md` | Kotlin Multiplatform como stack del frontend | accepted |
| ADR-0002 | `adr/0002-identidad-delegada-a-sesion-de-plataforma.md` | Identidad delegada a sesión emitida por la plataforma | **proposed** |
| ADR-0003 | `adr/0003-region-resuelta-por-ubicacion-del-dispositivo.md` | Región resuelta por ubicación del dispositivo | accepted |
| ADR-0004 | `adr/0004-configuracion-remota-con-default-horneado.md` | Configuración remota con valor por defecto horneado | accepted |
| ADR-0005 | `adr/0005-fallo-y-vacio-como-estados-distintos.md` | Fallo y vacío como estados distintos en el tipo | accepted |

### Nota sobre el estado de ADR-0002

ADR-0002 queda **`proposed`**, no `accepted`, deliberadamente. Se apoya en el supuesto
**SA-04** —que la plataforma expone autenticación—, y **`SA-04` no está respaldado por
ningún documento de la cadena**: `BRS.md` §8.2 define la capacidad de plataforma (SA-01)
como telemetría y actuadores, sin incluir autenticación. Aceptar la decisión sería
comprometer el proyecto a un supuesto sin verificar.

Si SA-04 se confirma, ADR-0002 debe pasar a `accepted`. Si se refuta, debe emitirse un ADR
sustituyente, y SYR-01, SYR-12, SYR-17 y SYR-19 quedarían sin soporte.

### Decisiones de `design.md` que no calificaron como ADR

- **`resultadoConsulta` como módulo** (`fiabilidad-y-frescura`): la invariante sí es
  duradera y quedó en ADR-0005; el módulo concreto que la implementa es detalle de
  implementación.
- **Correspondencia 1:1 componente ↔ especificación** en el nivel Component: útil como
  guía de navegación, pero es una consecuencia de la organización de este change, no un
  compromiso arquitectónico.
- **`expect`/`actual` limitado a dos puntos**: se registra en ADR-0001 como consecuencia y
  criterio de confirmación, no como decisión independiente.

### Ningún ADR vigente necesita revisarse

`<repo>/adr/` no existía antes de este change, de modo que no hay decisiones vigentes que
este diseño sugiera revisar y ninguna sustitución que registrar.
