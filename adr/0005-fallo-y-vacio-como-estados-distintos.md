---
status: "accepted"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Fallo y vacío como estados distintos en el tipo

## Context and Problem Statement

`SyRS.md` **SYR-21** exige que el sistema distinga **la imposibilidad de obtener datos** de
**la ausencia de macetas** en la región, y afirma que se trata de un requisito de
**seguridad funcional, no de usabilidad**. El razonamiento del documento: si la plataforma
falla y el sistema muestra el conjunto vacío, la jardinería ve *«no hay macetas en tu
región»* cuando la verdad es *«no se pudo consultar»*, lo que convierte un fallo en un
**dato falso**. En un lanzamiento por cluster, con macetas que cambian de estado, esa
distinción miente sobre la realidad del barrio.

**SYR-11** refuerza la presión desde el otro lado: el sistema debe operar **sin degradar**
cuando el conjunto está vacío, porque en un lanzamiento por cluster el conjunto vacío es el
**estado inicial de la base** (`RN-03`, `R-001`), no una excepción.

El riesgo es que estas dos exigencias se resuelvan en la vista, donde una decisión de
presentación puede colapsar los estados por descuido. Y la decisión de región por GPS
(ADR-0003) añade un **tercer** estado posible: *región no resuelta*.

## Decision Drivers

- Imposibilidad estructural de presentar un fallo como ausencia de macetas
- Operación sin degradar con conjunto vacío, sin tratarlo como excepción
- Distinción del tercer estado: región no resuelta
- Verificabilidad: el comportamiento debe poder comprobarse sin inspeccionar la vista

## Considered Options

- Dos estados: conjunto y vacío, con el fallo como caso de error separado
- Excepción de red como mecanismo de fallo
- Tipo de resultado con tres estados explícitos
- Marca de tiempo en el conjunto como indicador único

## Decision Outcome

Chosen option: **tipo de resultado de consulta con tres estados explícitos** —`Conjunto`,
`Vacio`, `NoConsultable`— porque hace que la confusión sea un **error de compilación** en
lugar de un defecto de presentación, y porque el tercer estado no es opcional desde que la
región se resuelve por GPS.

La distinción se representa en el **tipo**, y la capa de presentación no puede colapsar
estados porque el compilador se lo impide.

`NoConsultable` cubre tanto el fallo de la plataforma como la región no resuelta, y en
ambos casos la presentación declara que no se pudo consultar. No se permite que un
`NoConsultable` se presente como `Vacio`.

### Consequences

- Good, because un fallo no puede convertirse en un dato falso: la distinción la garantiza
  el sistema de tipos, no la disciplina de quien programa la vista.
- Good, because un fallo de plataforma y una región no resoluble se tratan de forma
  coherente para la persona: en ninguno de los dos se afirma que la región carezca de
  macetas.
- Good, because la operación con conjunto vacío queda como estado de primer orden, no como
  caso límite.
- Bad, because añade una enumeración que todo consumidor debe recorrer, y el caso
  `NoConsultable` no se descompone: la persona no puede distinguir «falló la plataforma» de
  «no se pudo determinar la región» sin examinar más.
- Bad, because obliga a un tratamiento explícito en cada punto de la interfaz, lo que
  aumenta el trabajo de implementación de la primera rebanada.

### Confirmation

Se confirma por tests de la capa de datos que verifican que un fallo de plataforma produce
`NoConsultable` y nunca `Vacio`, y por tests de la capa de presentación que verifican que
ningún `NoConsultable` se renderiza como conjunto vacío.

## Pros and Cons of the Options

### Tipo de resultado con tres estados

- Good, because el compilador impide la confusión que SYR-21 prohíbe.
- Good, because `Vacio` es un estado de primer orden, no una ausencia.
- Neutral, because el caso `NoConsultable` es único para dos causas.
- Bad, because obliga a tratar el estado en cada punto de la interfaz.
- Bad, because cada consumidor debe recorrer la enumeración.

### Dos estados con el fallo como excepción

- Good, because es el modelo más simple de implementar.
- Bad, because el fallo queda fuera del tipo, y su representación depende de que nadie
  capture la excepción y la presente como vacío.
- Bad, because es exactamente el mecanismo que SYR-21 identifica como origen del defecto.

### Excepción de red como mecanismo de fallo

- Good, because no requiere tipos nuevos.
- Bad, because obliga a que cada llamador capture la excepción, y basta un solo punto que
  no la capture para producir el defecto.
- Bad, because no distingue región no resuelta de fallo de plataforma.

### Marca de tiempo como indicador

- Good, because es barato y responde a SYR-22.
- Bad, because la antigüedad **no** es el mismo problema que la indistinción de fallo y
  vacío: un conjunto puede tener datos frescos y ser un fallo que se presenta como vacío.
- Bad, porque no aporta nada cuando la plataforma no entrega fecha, caso previsto en
  `fiabilidad-y-frescura`.

## More Information

**Ámbito.** Esta decisión trata la **distinción**, no la presentación. La forma de
presentación —pantalla, mapa o ambos— sigue diferida (DD-03).

**Interacción con SYR-22.** La antigüedad de la información se especifica por separado en
`fiabilidad-y-frescura`; no sustituye a esta distinción ni la reemplaza.

**Relacionado.** ADR-0003 (región, que crea el tercer estado), ADR-0004 (configuración,
cuya ausencia produce `NoConsultable`).
