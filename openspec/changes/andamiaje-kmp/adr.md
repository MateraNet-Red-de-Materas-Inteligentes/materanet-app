# Manifiesto de revisión de ADR

- Status: completed
- Review date: 2026-09-29

## Resumen de la revisión

Revisión de ADR completada para este change.

Se revisaron los cinco ADR existentes en `adr/` y se trazó el grafo de supersesión a partir
del campo `Supersedes` de cada uno. **Ningún ADR declara `Supersedes`**, de modo que los
cuatro con estatus `accepted` siguen en vigor y el quinto, con estatus `proposed`, no
compromete al diseño.

La revisión produjo **tres ADR nuevos a nivel de repositorio** y descartó dos decisiones del
`design.md` por no alcanzar el umbral de duración.

## ADR en vigor revisados

Contexto que constrain este diseño, ninguno de ellos modificado:

- `adr/0001-kotlin-multiplatform-como-stack-del-frontend.md` — *accepted*. KMP como stack,
  `commonMain` compartido, frontera `expect`/`actual` en dos puntos. **Este change es
  coherente con él y le da su instrumento de confirmación.**
- `adr/0002-identidad-delegada-a-sesion-de-plataforma.md` — *proposed*. Identidad por sesión
  de plataforma; su estatus sigue siendo `proposed` porque `SA-04` (`Q-030`) no está
  confirmada. **No está en vigor y por tanto no compromete este diseño**; el riesgo queda
  confinado al adaptador real.
- `adr/0003-region-resuelta-por-ubicacion-del-dispositivo.md` — *accepted*. La región se
  resuelve por el dispositivo. **Consecuencia directa:** el contrato no declara capacidad
  de región, y se retira la que D-02 declaraba.
- `adr/0004-configuracion-remota-con-default-horneado.md` — *accepted*. Sin implicación
  directa sobre la frontera con la plataforma; su clave de regla de acceso es el único punto
  de configuración que atraviesa el contrato.
- `adr/0005-fallo-y-vacio-como-estados-distintos.md` — *accepted*. **Es la razón de existir
  del doble de prueba:** su confirmación exige comprobar que un fallo produce
  `NoConsultable` y nunca `Vacio`, y eso solo se puede provocar a voluntad contra un doble.

## ADR nuevos creados

Los tres quedan en estatus **`proposed`**, no `accepted`. Ninguno está aprobado todavía: se
somete a revisión antes de que su contenido se vuelva inmutable. Ver *Estado de la revisión*.

- `adr/0006-contrato-de-plataforma-declarado-por-el-cliente.md` — *proposed*. El contrato con
  la plataforma lo declara el cliente, con cinco capacidades, y **no se le exige ninguna**.
  Resuelve `DD-01` como declaración propia y no cierra `Q-025`.
- `adr/0007-verificacion-por-punto-de-composicion.md` — *proposed*. Los dobles de prueba se
  separan de la distribución **por composición, no por tipo de compilación**, de modo que la
  garantía la impone el compilador.
- `adr/0008-objetivo-jvm-como-instrumento-de-verificacion.md` — *proposed*. Se declara un
  objetivo de JVM **como instrumento de verificación y no de distribución**, junto con
  Android e iOS. Reconcilia con la observación de ADR-0001 sobre objetivos que no se
  justifican entre sí.

## Estado de la revisión

**Los tres ADR nuevos están en revisión.** Se crean en `proposed` y pasan a `accepted` solo
cuando se aprueben.

El motivo es la inmutabilidad: **un ADR aceptado no se edita jamás** —ni su estatus, ni su
cuerpo, ni su fecha. Para cambiar una decisión aceptada hay que emitir otro que la superseda.
Escribir estos tres directamente en `accepted` habría hecho irreversible, antes de que nadie
los leyera, un texto cuya revisión es justamente lo que este change necesita.

**Consecuencia sobre los cambios ya redactados.** `design.md` y `tasks.md` se apoyan en
ADR-0006, ADR-0007 y ADR-0008 como si fueran compromisos firmes. Mientras estén en
`proposed`, esas referencias son **a decisiones propuestas**: si alguno cambia al revisarse,
`design.md` y `tasks.md` deben revisarse con él. El change no debe pasar a `apply` hasta que
los tres estén aceptados, porque `apply` ejecutaría tareas apoyadas en decisiones que
todavía pueden cambiar.

Los cuatro ADR anteriores (`0001`, `0003`, `0004`, `0005`) permanecen `accepted` e
intocables. `0002` sigue `proposed` por `SA-04`, que es una razón distinta y ya conocida.

## Decisiones descartadas por no alcanzar el umbral

El `design.md` registra cinco decisiones. Dos no se elevaron a ADR:

- **D-02 — el contrato no consume un punto de frontera.** No es una decisión nueva: es la
  aplicación de ADR-0001, cuya frase operativa ya fija la frontera en dos puntos. Lo que sí
  se añadió es una lectura que evita el malentendido de tomar *red* como tercer punto, y esa
  lectura quedó como **requisito** en la capacidad `portabilidad-y-verificacion`: un tercer
  punto exige decisión arquitectónica explícita.
- **D-05 — `commonMain` lleva vocabulario, no comportamiento.** Es alcance de este change, no
  un compromiso de largo plazo. Queda en `tasks.md`.

## Nota sobre ADR-0001

`design.md` señala que la observación de ADR-0001 sobre objetivos sin consumidor común
entra en tensión con el objetivo de JVM. **No se propone modificar ADR-0001**, que es
inmutable una vez aceptada. La tensión se resuelve en ADR-0008, que declara el objetivo como
instrumento de medición y fija su reevaluación para el caso de que aparezca una tercera
superficie de consumo.
