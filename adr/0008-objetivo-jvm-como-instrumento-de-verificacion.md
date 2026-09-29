---
status: "proposed"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Objetivo de JVM como instrumento de verificación

## Context and Problem Statement

ADR-0001 exige que la frontera `expect`/`actual` se mantenga en exactamente dos puntos —y
el resultado de esa frontera es que el dominio se **prueba sin emulador ni dispositivo**, sin
embargo su propio criterio de confirmación lo es: *tests del dominio en `commonMain`
ejecutándose sin referencia a ninguna plataforma*. Para que ese criterio sea comprobable hace
falta un objetivo que corra en cualquier máquina.

El entorno de trabajo no tiene SDK de Android —solo `platform-tools`— ni toolchain de Apple.
Peor aún: en Linux el objetivo de iOS **no se va a poder compilar nunca**, porque compilarlo
exige macOS.

Hay una dificultad técnica que cierra la vía obvia: en Kotlin Multiplatform la configuración
es todo-o-nada. Declarar `androidTarget()` sin SDK hace fallar la configuración de Gradle
completa, y con ella falla también el objetivo de JVM. Instalar el SDK completo de Android
—gigas de descarga y permisos— sería el precio de tener Android verificable.

ADR-0001 registra además, entre sus consecuencias negativas, que *un segundo objetivo solo
justifica KMP cuando existe un segundo consumidor*, y que Android e iOS son el mismo
consumidor en dos formas.

## Decision Drivers

- Cumplir el criterio de confirmación de ADR-0001 en la máquina donde se trabaja
- Evitar que la puerta de un change sea una promesa que nadie puede comprobar
- No crear un tercer objetivo que se justifique por reutilización, porque no la hay
- No depender de macOS para cualquier verificación

## Considered Options

- Solo objetivos Android e iOS, sin objetivo verificable
- Instalar el SDK de Android y declarar los tres objetivos
- Declarar el objetivo de JVM como instrumento de verificación, con los tres objetivos

## Decision Outcome

Chosen option: **declarar el objetivo de JVM como instrumento de verificación**, e instalar
el SDK de Android para que el objetivo de Android también compile.

El objetivo de JVM existe **para poder medir**, no para servir a un segundo consumidor. Por
eso **no se distribuye y no se publica**, y esa restricción es parte de la decisión, no una
convención.

**Los tres objetivos se declaran**: JVM, Android e iOS. El SDK de Android se instala porque
sin él la configuración de KMP no arranca. **iOS queda declarado y no compilable en este
entorno**: no es una tarea pendiente, es una limitación asumida, y su consecuencia real es que
el primer error de compilación en iOS aparecerá en la primera rebanada y no aquí.

Declarar iOS aun sin poder compilarlo tiene un propósito: que la configuración exista, de modo
que el fallo aparezca en la primera rebanada y no en la segunda.

### Consequences

- Good, because el criterio de confirmación de ADR-0001 se vuelve comprobable hoy, en lugar de
  ser una promesa.
- Good, because un cambio cuya puerta se puede ejecutar no se archiva por tener una puerta
  imaginaria.
- Neutral, because iOS declarado sin compilar traslada el fallo, no lo evita. Se acepta: el
  fallo temprano y visible en la primera rebanada es mejor que una configuración ausente que
  aparece más tarde.
- Bad, because instalar el SDK de Android cuesta gigas de descarga y permisos, y es un coste
  fijo que no aporta funcionalidad.
- Bad, because un tercer objetivo aumenta el coste de configuración de Gradle, que ADR-0001 ya
  señala como el mayor coste conocido de aquella decisión. Esta decisión **no aumenta** esa
  deuda, pero tampoco la reduce: paga para poder medir.
- Follow-up, because si `jvm()` llegara alguna vez a distribuirse, dejaría de ser un
  instrumento de verificación y la objeción de ADR-0001 sobre objetivos que no se justifican
  entre sí aplicaría de lleno. Sería entonces una decisión nueva, no una evolución natural.

## Pros and Cons of the Options

### Solo objetivos Android e iOS

- Good, because el proyecto nace con la forma que exige la SRS, sin objetivos añadidos.
- Bad, because sin SDK ni toolchain de Apple no se compila nada aquí, y la puerta del change
  queda como promesa.
- Bad, because la verificación del dominio quedaría atada a una máquina con SDK o macOS.

### Instalar el SDK de Android y declarar los tres objetivos

- Good, because Android se compila de verdad.
- Good, because evita la dependencia de una máquina concreta para probar el dominio.
- Bad, porque la descarga y los permisos del SDK son un coste alto para un beneficio limitado.
- Bad, porque la configuración de KMP es todo-o-nada: si el SDK falta o cambia, deja de
  compilarse **todo**, incluido el objetivo de JVM.

### Objetivo de JVM como instrumento de verificación, con los tres objetivos

- Good, because hace comprobable el criterio de ADR-0001 en cualquier máquina.
- Good, because los objetivos reales se declaran, de modo que la configuración existe aunque
  iOS no se compile.
- Neutral, because es un objetivo que no se distribuye: su valor es la medición, no la
  entrega.
- Bad, because suma configuración de Gradle a una decisión que ADR-0001 ya señalaba como su
  mayor coste conocido.
- Bad, because su justification no es la reutilización, y se aparta de la lógica que ADR-0001
  usa para evaluar un segundo objetivo. Por eso se declara explícitamente que es un
  instrumento, y no un consumidor.

## More Information

**Reversibilidad.** Alta. Borrar el objetivo de JVM no toca el dominio ni el contrato; solo
devuelve la puerta del change a una promesa, que es el estado en que se estaba.

**Punto de reevaluación.** Si el proyecto llega a tener una tercera superficie de consumo —
la web del e-commerce (**RS-03**) o escritorio—, `jvm()` dejaría de ser instrumento y la
decisión debe revisarse: entonces sí sería un segundo consumidor real, y la objeción de
ADR-0001 dejaría de aplicar.

**Relacionado.** ADR-0001 (Kotlin Multiplatform, `commonMain` y la frontera de dos puntos, de
la que este objetivo es el instrumento de confirmación), ADR-0006 (el contrato que este
proyecto verifica).
