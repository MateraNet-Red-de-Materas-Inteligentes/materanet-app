---
status: "accepted"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Región resuelta por ubicación del dispositivo

## Context and Problem Statement

`SyRS.md` **SYR-02** exige que el sistema determine a qué región corresponde la consulta.
`SR-01` —la necesidad de la jardinería— dice «una región» sin definir cuál, de modo que sin
resolución de región no hay conjunto que listar. `SyRS.md` §5 difirió **DD-06** a este
nivel: *«el criterio es decisión de negocio y diseño»*.

La elección tiene una consecuencia ya anticipada en el propio documento. `SyRS.md` §6.1
advierte que **si DD-06 se resuelve sin ubicación, SYR-24 se retira y el total de requisitos
baja de 33 a 32**. La decisión es, por tanto, de alcance y no solo de implementación.

Las alternativas naturales eran: pertenencia (la plataforma deduce la región de las
macetas que el usuario compró, según `BRS.md` §2), selección explícita por la persona, o
ubicación del dispositivo.

## Decision Drivers

- Fricción mínima: **SYR-25** (operable sin instrucción previa) y **SYR-26** (navegable sin
  construir la consulta)
- **RN-03**: en un lanzamiento por cluster, el conjunto vacío y las cero relaciones
  sociales son el estado inicial, no una excepción
- **R-008**: el listado de una región con ubicación es un conjunto de datos personales
- Coherencia con **SYR-R03**: la ubicación del dispositivo no es el estado de desarrollo del
  hardware

## Considered Options

- Ubicación del dispositivo (GPS)
- Pertenencia: región derivada de las macetas propias
- Selección explícita por la persona
- Pertenencia con respaldo por GPS

## Decision Outcome

Chosen option: **ubicación del dispositivo (GPS)**, porque el propietario del proyecto la
eligió explícitamente entre las alternativas ofrecidas.

**Consecuencia directa y aceptada: SYR-24 permanece en alcance y el total de requisitos se
mantiene en 33**, no baja a 32.

La resolución por GPS crea un **tercer** resultado de consulta posible además de «conjunto»
y «vacío»: *región no resuelta*. Sin representarlo explícitamente, se colapsaría en «no hay
macetas», que es exactamente el defecto que **SYR-21** prohíbe.

### Consequences

- Good, because elimina la fricción de selección, lo que refuerza SYR-25 y SYR-26.
- Good, because no depende de que la plataforma conozca macetas del usuario, lo que reduce
  la superficie de dependencia de la caja negra.
- Bad, because introduce un **diálogo de permiso de ubicación** en el camino de entrada.
- Bad, because la precisión del GPS pasa a ser una **dependencia de arranque** bajo RN-03.
  Un dispositivo con GPS degradado no llega al listado.
- Bad, because constituye un **segundo vector de datos personales** que se suma al ya
  señalado por R-008, mientras R-008 sigue abierto en su valor por defecto.
- Good, because SYR-24 queda en alcance y por tanto **especificado y verificable** en lugar
  de retirado.

### Confirmation

La separación de `región no resuelta` de `conjunto vacío` se confirma por tests que
verifican que un permiso denegado **no** produce un listado vacío.

## Pros and Cons of the Options

### Ubicación del dispositivo (GPS)

- Good, because es la de menor fricción para la persona.
- Good, because es independiente de que la plataforma conozca macetas del usuario.
- Neutral, because depende de la precisión del GPS, cuyo comportamiento no es del sistema.
- Bad, because la precisión como dependencia de arranque tensiona RN-03.
- Bad, because agrava la exposición de datos personales de R-008.

### Pertenencia

- Good, because cero fricción y sin diálogo de permiso.
- Good, because no añade vector de datos personales.
- Bad, because una persona con macetas en dos regiones necesita regla de desempate, que es
  complejidad no resuelta.
- Bad, because habría retirado SYR-24 y bajado el total a 32.
- Bad, porque se descartó por decisión explícita del propietario.

### Selección explícita

- Good, because es la más predecible y totalmente explícita.
- Good, because también retiraría SYR-24.
- Bad, because introduce un paso de selección que tensiona SYR-25.
- Bad, porque se descartó por decisión explícita del propietario.

### Pertenencia con respaldo por GPS

- Good, because cubre el arranque en frío de RN-03 —maceta comprada, app nunca abierta—,
  que es el caso real que RN-03 describe.
- Bad, because mantiene dos rutas que hay que probar y mantener.
- Bad, because se descartó por decisión explícita del propietario.

## More Information

**Distinción obligatoria.** La ubicación del **dispositivo** la obtiene el sistema; la
ubicación de la **maceta** (SYR-08) la entrega la plataforma. No son el mismo dato y
`obtencion-de-datos` los mantiene separados.

**Relacionado.** ADR-0001 (stack), ADR-0005 (fallo y vacío como estados distintos).
