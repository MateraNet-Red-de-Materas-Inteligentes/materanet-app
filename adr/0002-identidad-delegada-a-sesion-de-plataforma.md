---
status: "proposed"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Identidad delegada a una sesión emitida por la plataforma

## Context and Problem Statement

La identidad es **condición de posibilidad** en este sistema, no un requisito paralelo.
`SyRS.md` **SYR-01** exige establecer la identidad de quien opera antes de habilitar
cualquier capacidad de listado, y **SYR-12** habla del «usuario del sistema». Sin identidad
no hay sujeto del que hablar. **SYR-17** lo repite para capacidades protegidas, y **SYR-13**
—distinguir las macetas propias— presupone propiedad, que presupone identidad.

`SyRS.md` §5 difirió **DD-05** a este nivel: SYR-01 declara la **capacidad** de establecer
identidad, y el mecanismo es diseño.

**El problema:** la capacidad de plataforma que se asume no está declarada en ninguna parte
de la cadena documental. `BRS.md` §8.2 define **SA-01** —la capacidad de plataforma
existente— como *«telemetría y comandos de actuadores»*. La autenticación **no figura**.
SYR-01, SYR-12, SYR-17, SYR-19 y SYR-20 descansan sobre una capacidad que ningún
documento establecido.

## Decision Drivers

- Coherencia con DA-01 / caja negra: el frontend no puede inventar identidad
  (**SYR-R01**, **SYR-15**)
- No exponer ni almacenar credenciales de plataforma (**SYR-18**, **SYR-20**)
- Viabilidad de SYR-13: un identificador de dispositivo no produce un «propio»
- Evitar un bloqueo total del SRS por una dependencia externa no verificada

## Considered Options

- Sesión emitida por la plataforma, con token de acceso de vida corta
- Identidad de dispositivo, anónima, sin cuenta
- Tratar la autenticación de plataforma como dependencia bloqueante
- Sesión gestionada íntegramente por la plataforma

## Decision Outcome

Chosen option: **sesión emitida por la plataforma, con token de acceso de vida corta
almacenado en almacenamiento seguro del dispositivo** (Android Keystore / iOS Keychain),
porque es la única lectura coherente con la caja negra y porque deja las especificaciones
completas en vez de detenerlas por una dependencia externa.

**El estado de esta decisión es `proposed`, no `accepted`.** No hay evidencia suficiente
para aceptarla: **SA-04** es un supuesto nuevo y sin respaldo documental. Aceptarla sería
declarar comprometido un supuesto que nadie ha verificado.

### Consequences

- Good, because respeta la caja negra: la identidad procede de la plataforma y el cliente
  no la origina.
- Good, because el cliente nunca posee credenciales de la plataforma (**SYR-18**,
  **SYR-20**); solo conserva el token emitido.
- Good, because deja la línea base SRS completa y verificable, con la dependencia externa
  declarada en lugar de oculta.
- Bad, because **si SA-04 resulta falso, cuatro requisitos quedan sin soporte** — SYR-01,
  SYR-12, SYR-17 y SYR-19 — y el cambio necesario es de arquitectura, no de
  implementación. No es recuperable desde el cliente: la caja negra impide que el frontend
  invente identidad.
- Bad, because introduce dependencia de un mecanismo de renovación de sesión cuya interfaz
  tampoco está declarada.

### Confirmation

**No confirmable todavía.** La primera acción de implementación debe ser confirmar SA-04
contra la plataforma. Esta decisión pasa a `accepted` solo con esa confirmación, y debe
generar un ADR sustituyente si la respuesta fuera negativa.

## Pros and Cons of the Options

### Sesión emitida por la plataforma

- Good, because es la única opción compatible con DA-01.
- Good, because habilita SYR-13, que las otras dos opciones no pueden sostener.
- Neutral, because depende por completo de una capacidad externa no declarada.
- Bad, because deja cuatro requisitos sin respaldo si SA-04 es falsa.

### Identidad de dispositivo, anónima

- Good, because no requiere ninguna capacidad nueva de la plataforma.
- Good, because SYR-01 se satisface mecánicamente.
- Bad, because **SYR-12 queda sin sujeto**: «cualquier usuario del sistema» no admite un
  identificador de dispositivo como «usuario del sistema».
- Bad, because **SYR-13 se vuelve imposible**: no hay «propio» sin propiedad, y un
  identificador de dispositivo no es propiedad.
- Bad, porque se descartó.

### Autenticación como dependencia bloqueante

- Good, because es la opción más segura para la corrección.
- Neutral, because no avanza nada.
- Bad, because detiene toda la línea base SRS por una incógnita externa.
- Bad, because se descartó: las especificaciones pueden escribirse contra el supuesto, con
  el supuesto declarado.

### Sesión gestionada íntegramente por la plataforma

- Good, because es la lectura más limpia de la caja negra.
- Good, because satisface SYR-18 y SYR-20 sin trabajo de credenciales en el cliente.
- Bad, porque requiere igualmente que la plataforma ofrezca la capacidad, que es
  precisamente lo no confirmado.
- Bad, because concentrate más responsabilidad en la plataforma sin obtener mejoría
  verificable.

## More Information

**Origen del hueco.** `BITÁCORA.md` —citada por `BRS.md`, `StRS.md` y `SyRS.md` como
registro de decisiones con entradas `E-001…E-026`, `Q-021/026/028/029` y `DA-01/DA-02`— no
existe en el repositorio. Es posible que la capacidad sí esté registrada allí y se haya
perdido el documento. **Debe rastrearse antes de asumir que SA-04 es una brecha real.**

**Supuesto registrado.** SA-04: la plataforma emite sesiones y es la única autoridad sobre
identidad y sobre la regla de acceso. Si resulta falso, el proyecto se detiene aquí
—`SA-01` establece que sin capacidad de plataforma el proyecto no tiene objeto—.

**Acción de seguimiento.** Confirmar SA-04 antes de implementar. Es la pregunta bloqueante
número 1 del `design.md`.
