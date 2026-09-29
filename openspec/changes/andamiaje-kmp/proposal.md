# Propuesta — `andamiaje-kmp`

## Por qué

La línea base SRS está completa y es archivable, pero **la primera rebanada no puede
empezar**. Dos bloqueos, ambos externos:

- `DD-01` — la interfaz de la plataforma está diferida y `Q-025` sigue sin respuesta. La
  forma real del API no la conoce nadie en este repositorio.
- El entorno **no verifica nada**. No hay SDK de Android —solo `platform-tools`— ni
  toolchain de Apple, y en Linux el target de iOS no se va a poder compilar nunca.

Reordenar las tareas del SRS no resuelve ninguno de los dos: no hay secuencia que esquive
una respuesta que solo puede dar quien controla la plataforma. Una puerta que no se puede
comprobar es una promesa, y este proyecto ya corrigió una por escribir requisitos que
nadie iba a cumplir (E-028).

Lo que sí funciona es dejar de depender de que la plataforma responda hoy. El cliente
declara **su propio contrato mínimo** —qué necesita para ser correcto, no qué debe
construir otro equipo— y se verifica contra un doble de prueba que él controla. Eso
convierte dos incógnitas externas en una costura local, y es lo que hace ejecutable la
primera rebanada.

## Qué cambia

- **Se crea el change `andamiaje-kmp`**: esqueleto Kotlin Multiplatform, `commonMain` con
  **tipos únicamente y ningún comportamiento propio**, el contrato de plataforma, el doble
  de prueba y las pruebas de contrato.
- **`DD-01` queda resuelta como declaración del cliente**, no como descripción del API de
  la plataforma. La forma real sigue siendo `Q-025`; el adaptador real se escribirá
  después, contra lo que exista.
- **Se elimina una capacidad del contrato recuperado.** D-02 (E-027) declaraba seis
  capacidades; *determinar la región de la cuenta* desaparece porque DD-06 resolvió la
  región por GPS del dispositivo. **Quedan cinco, y ninguna se le exige a la plataforma.**
- **El conjunto se declara para las tres formas en que puede llegar** —entero, por partes,
  por páginas— sin asumir que quepa. Es el criterio que E-028 ya estableció, y el que evita
  volver a convertir la incertidumbre en dependencia.
- **Se agrega `jvm()` como target de verificación.** Es un tercer target que la SRS no
  pidió, y su razón es environmental: que el dominio sea comprobable en la máquina donde
  se trabaja. Su alternativa descartada queda en ADR.
- **Android e iOS se declaran como targets.** Android exige instalar el SDK completo; iOS
  quedará declarado y **no compilable en este entorno**, lo que se registra como
  limitación asumida y no como tarea pendiente.
- **§3-bis de `implementar-sistema-desde-sysr` queda satisfecha**: el andamiaje que aquel
  documento sacó de su alcance pasa a existir como change propio y con nombre.

## Capacidades

### Capacidades nuevas

- `contrato-plataforma`: las cinco capacidades que el cliente necesita de la plataforma, la
  regla de que **no se le exige ninguna** y las tres formas en que el conjunto puede llegar.
- `portabilidad-y-verificacion`: el dominio se compila y sus pruebas se ejecutan en
  `commonMain` sin emulador ni dispositivo, y la frontera `expect`/`actual` está en
  exactamente dos puntos.

### Capacidades modificadas

Ninguna. Las siete capacidades del SRS describen comportamientos del sistema y **este
change no cambia ninguno**: solo les construye la frontera por la que van a hablar con la
plataforma. Declarar aquí una modificación sería insinuar un cambio de comportamiento que
no existe.

El doble de prueba **no es una capacidad**. Es infraestructura de verificación, y sus
propiedades —fallar cuando se le pida, no poder viajar en una compilación de
distribución— son tareas, no comportamientos del sistema entregado. Declararlo requisito
sería una errata de categoría.

## Impacto

**Afecta:** proyecto Kotlin Multiplatform nuevo; `openspec/config.yaml` sin cambios;
`BITÁCORA.md` suma entradas; tres ADRs nuevos o ampliados (target `jvm()`, frontera de dos
puntos, y el registro de la limitación de iOS).

**Depende de:** JDK 21, presente. Gradle, por wrapper. SDK de Android, **a instalar** —
es el precio aceptado de esta decisión y se registra como tal, no como descubrimiento.

**No resuelve, y conviene que no se lea como que sí:**

- **`SA-04` / `Q-030` sigue abierta.** El riesgo queda confinado al adaptador real, que es
  la posición que DD-05 ya tenía con estatus `proposed`. Si `SA-04` resulta falsa, el
  cambio afecta a un adaptador, no al dominio.
- **La forma real del API sigue siendo `Q-025`.** Este change declara una costura propia;
  no afirma conocer la plataforma.
- **Un contrato probado contra un doble de prueba demuestra consistencia interna, no que la
  plataforma pueda satisfacerlo.** Esa comprobación sigue siendo la tarea 1.x del SRS.
- **iOS no se verifica.** No es esfuerzo pendiente: es que compilarlo exige macOS.

**Límite de alcance:** este change no implementa ninguna de las 33 SYR. `commonMain` lleva
el vocabulario que el contrato necesita para hablarse —maceta, región, sesión, conjunto,
resultado de tres estados— y ningún comportamiento propio. La primera rebanada, que es la
que le da uso, se propone aparte como `rebanada-1-listado-regional`.
