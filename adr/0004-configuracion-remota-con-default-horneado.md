---
status: "accepted"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Configuración remota con valor por defecto horneado

## Context and Problem Statement

`SyRS.md` **SYR-31** exige ajustar la regla de acceso sin modificar el código. **SYR-32**
exige que regla de acceso, resolución de región y criterios de filtrado estén **separadas
y ajustables de forma independiente**, no agrupadas en un único control. **SYR-33** exige
declarar el alcance de soporte. `SyRS.md` §5 difirió **DD-07** —el mecanismo de
configuración— al nivel de arquitectura.

El mecanismo no es una decisión de estilo: de ella depende el tratamiento que E-026 dio al
riesgo **R-008**. El riesgo era que el valor por defecto de la regla de acceso fuera
incompatible con la normativa del cluster. E-026 lo **desacopló del sistema** al volver la
regla ajustable, de modo que restricta no requiere desarrollo.

Ese desacoplamiento **solo se sostiene si el mecanismo lo permite**. Un valor horneado en el
build no requiere modificar el *código fuente*, pero exige publicar una versión nueva: para
una aplicación móvil eso es un release de código en todo salvo en el nombre. Con un
mecanismo de build, R-008 vuelve a ser un asunto de releases.

## Decision Drivers

- Que endurecer la regla de acceso no exija publicación (**SYR-31**, R-008)
- Que las tres decisiones sean ajustables **de forma independiente** (**SYR-32**)
- Que el sistema siga operando sin conectividad
- Fallo en cerrado: el comportamiento por defecto ante ausencia de configuración debe ser el
  más restrictivo, no el más permisivo

## Considered Options

- Configuración remota con valor por defecto horneado
- Constantes de tiempo de compilación
- Archivo de configuración local empaquetado
- Servicio externo de feature flags

## Decision Outcome

Chosen option: **configuración remota recuperada en ejecución desde la plataforma, con
valor por defecto seguro horneado en el build como respaldo y tres claves
independientes**, porque el propietario del proyecto la eligió explícitamente y porque es
la única opción que mantiene a R-008 genuinamente mitigado.

El respaldo **falla en cerrado**: sin conectividad se aplica el valor más restrictivo. Si el
respaldo fuera permisivo, un fallo de red abriría el acceso, que es el resultado que R-008
intenta cerrar.

Las tres claves **nunca** se agrupan en un bloque único: una configuración monolítica
impediría cumplir SYR-32 por construcción.

### Consequences

- Good, because endurecer la regla de acceso no requiere release, que es lo que hace real el
  tratamiento de R-008 dado en E-026.
- Good, because con tres claves independientes, SYR-32 se cumple por construcción.
- Good, because el sistema sigue operando sin conectividad.
- Bad, because introduce un **modo de fallo externo nuevo**: si la configuración remota
  devuelve un documento mal formado, el comportamiento depende de la robustez del
  parseo. Mitigado por el respaldo, que siempre está disponible.
- Bad, because añade una dependencia de red en el camino de arranque, lo que se suma a la
  dependencia de GPS de ADR-0003.
- Bad, because el respaldo horneado puede envejecer respecto a la configuración remota sin
  que nadie lo note en el código.

### Confirmation

Se confirma por tests que ejercitan el endurecimiento de la regla **sin republicar la
aplicación**, y por tests que verifican que la ausencia de red aplica el valor más
restrictivo.

## Pros and Cons of the Options

### Configuración remota con valor por defecto horneado

- Good, because el ajuste no requiere publicación.
- Good, because falla en cerrado ante ausencia de red.
- Good, because separa naturalmente las tres claves.
- Bad, because añade un modo de fallo externo y una dependencia de red en el arranque.

### Constantes de tiempo de compilación

- Good, because es la opción más simple y sin dependencias de red.
- Bad, because **cambiar la regla exige publicar una versión**. Incumple el espíritu de
  SYR-31 y reabre R-008 como asunto de releases.
- Bad, porque no permite ajustar nada sin ciclo de desarrollo.

### Archivo de configuración local empaquetado

- Good, because totalmente offline y fácil de inspeccionar.
- Good, because no introduce dependencia de red.
- Bad, because no es ajustable remotamente: cualquier cambio de regla vuelve a requerir
  publicación.
- Bad, because es la peor opción para R-008 de las cuatro.

### Servicio externo de feature flags

- Good, because potente, con segmentación por clave.
- Bad, because añade un **tercer** sistema externo, cuando el diseño ya tiene la plataforma
  como caja negra.
- Bad, because introduce un proveedor más y un modo de fallo más.
- Bad, because segmentar por usuario choca con R-008 sin una justificación de privacidad
  declarada.

## More Information

**Secuencia de despliegue.** Publicar la configuración remota **antes** que la aplicación
que la consume, para que la app nunca arranque contra una configuración ausente.

**Límite de «configurable».** Añadir un parámetro **no crea un requisito**. Un parámetro
nuevo que nadie pidió no está cubierto por SYR-31, y `SyRS.md` §3.13 lo dice explícitamente.

**Relacionado.** ADR-0002 (identidad), ADR-0003 (región), ADR-0005 (fallo y vacío).
