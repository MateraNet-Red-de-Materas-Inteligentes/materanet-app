---
status: "accepted"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Los dobles de prueba se separan por composición, no por tipo de compilación

## Context and Problem Statement

ADR-0006 declara un contrato con la plataforma. Para verificarlo hace falta un doble de
prueba, porque contra la plataforma real no se puede provocar un fallo cuando se quiere:
ADR-0005 exige confirmar que un fallo de plataforma produce `NoConsultable` y nunca `Vacio`,
y esa comprobación contra el sistema real exigiría esperar un corte de servicio.

Ese doble tiene una propiedad que lo hace peligroso si se distribuye: una aplicación
publicada que simule datos de plataforma mostraría a la jardinería macetas que no existen, o
un «no consultable» donde había un conjunto real. Es un fallo de seguridad funcional tanto
como lo sería confundir un fallo con una región sin macetas.

La forma habitual de evitarlo es una bandera: `if (debug) doble else adaptadorReal`. Es más
corta y más familiar, y por eso es la que se escribe primero.

## Decision Drivers

- Que una compilación de distribución no pueda contener el doble de prueba, por construcción
  y no por disciplina de revisión
- Que la verificación sea ejecutable en cualquier entorno, sin esperar un fallo real
- No introducir condiciones sobre el tipo de compilación en el código de producción

## Considered Options

- Condición por tipo de compilación (`if (debug) doble else adaptadorReal`)
- Excluir el doble por configuración de Gradle, con banderas por tipo de variante
- Puntos de composición separados: el de depuración pasa el doble, el de distribución pasa
  el adaptador real

## Decision Outcome

Chosen option: **puntos de composición separados**, porque convierte la propiedad en una
garantía del compilador. Si el adaptador real no existe, la compilación de distribución
**falla**; no se produce un artefacto que carezca de él.

No existe ninguna condición sobre depuración o tipo de compilación en ninguna parte del
código. La elección de implementación se hace **una vez**, en el punto de composición.

El doble vive en `commonTest` y en un conjunto de fuentes que solo entra en compilaciones de
depuración, de modo que ni siquiera está disponible para una distribución.

El doble es además **controlable**: puede fallar cuando se le pida. Ese es el motivo por el
que existe, y lo que lo hace útil para confirmar ADR-0005 en lugar de limitarse a demostrar
que la interfaz compila.

### Consequences

- Good, because la garantía no depende de que nadie recuerde no activar la bandera: el
  compilador la impone.
- Good, because habilita la verificación de la distinción fallo/vacío, que es el requisito
  de mayor consecuencia del SRS y el más difícil de comprobar contra la plataforma real.
- Good, because el fallo del doble es indistinguible del fallo de la plataforma real —es el
  mismo tipo—, así que el doble verifica el contrato y no solo la firma de la interfaz.
- Bad, because hay dos puntos de composición que mantener, y alguien podría olvidar
  actualizar el de distribución al añadir una capacidad al contrato.
- Bad, because el doble no puede ejercitar el camino de red real: las pruebas de contrato
  verifican la costura, no el transporte.
- Follow-up, because cada capacidad nueva del contrato de ADR-0006 obliga a revisar los dos
  puntos de composición.

## Pros and Cons of the Options

### Condición por tipo de compilación

- Good, because es la forma más corta y la más familiar.
- Bad, because la propiedad queda en la disciplina de revisión, que es exactamente donde
  falla un día que importa.
- Bad, because introduce una condición de entorno en el camino de datos.

### Excluir el doble por configuración de Gradle

- Good, because no aparece ninguna condición en el código.
- Bad, because la exclusión depende de que el conjunto de fuentes esté bien configurado, y
  un error de configuración no produce un fallo de compilación sino un artefacto silencioso.
- Bad, because es más configuración que leer, sin más garantía.

### Puntos de composición separados

- Good, because la garantía la impone el compilador.
- Good, because no hay ninguna condición sobre el tipo de compilación en el código.
- Neutral, because el coste es mantener dos puntos en lugar de uno, y el beneficio es que un
  error en cualquiera de ellos se ve al compilar.

## More Information

**Reversibilidad.** Alta, mientras no exista adaptador real: cambiar la elección es pasar un
argumento distinto. Una vez que exista el adaptador y haya distribución, la separación es la
garantía y no debe tocarse.

**Relacionado.** ADR-0005 (fallo y vacío como estados distintos, cuya confirmación es el
motivo de existir de esta decisión), ADR-0006 (el contrato que el doble implementa).
