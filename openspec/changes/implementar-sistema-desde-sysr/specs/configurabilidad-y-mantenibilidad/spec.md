# `configurabilidad-y-mantenibilidad`

Cubre SYR-23, SYR-31, SYR-32, SYR-33.

> **Origen:** instrucción explícita del usuario en E-026 — *«todo debe ser configurable, el
> sistema debe ser escalable y fácil de mantener»*. ISO 25010 los clasifica como
> **Maintainability**. En ISO 29148 son **requisitos de sistema**, no de diseño.
>
> **Límite de «configurable».** Un requisito de configurabilidad sin **enumerar qué se
> configura** es vacío: no falla nada, no se puede probar, y en la práctica significa «lo
> que haga falta». Por eso SYR-31 y SYR-32 nombran los puntos de ajuste concretos.
>
> **RN-01 aplicado a la mantenibilidad.** Facilitar OB-1 u OB-2 mediante fricción
> intencional **no** es un requisito de mantenibilidad: es un objetivo comercial disfrazado
> de atributo técnico. SYR-31…SYR-33 existen para que el sistema sea sostenible, **no para
> que venda más**.

## ADDED Requirements

### Requirement: La regla de acceso se ajusta sin modificar el código

El sistema SHALL permitir ajustar la regla de acceso a la región (SYR-12, SYR-19) **sin
requerir modificación de su código y sin publicar una versión nueva de la aplicación**.
Este es el tratamiento que E-026 dio a **R-008**.

> **Decisión DD-07 cerrada: configuración remota con valor por defecto seguro horneado en
> el build.** Un valor horneado en el build NO satisface este requisito: cambiarlo exigiría
> publicar una versión, que es un release de código en todo salvo en el nombre. La
> configuración remota es lo que mantiene a R-008 genuinamente mitigado.

#### Scenario: La regla se endurece sin publicar una versión

- **GIVEN** una regla de acceso vigente que permite el listado a cualquier usuario
- **WHEN** la configuración remota endurece esa regla
- **THEN** el sistema la aplica sin modificación de código
- **AND** sin publicación de una versión nueva

#### Scenario: El endurecimiento no requiere un ciclo de desarrollo

- **GIVEN** una decisión de acceso que requiere endurecerse
- **WHEN** se endurece
- **THEN** no se modifica el código del sistema
- **AND** no se inicia un nuevo ciclo de desarrollo

### Requirement: Las decisiones de comportamiento son separadas e independientes

Las decisiones de comportamiento del sistema —**regla de acceso, resolución de región,
criterios de filtrado**— SHALL estar **separadas entre sí y ser ajustables de forma
independiente**, no agrupadas en un único control. Cambiar una decisión no SHALL alterar
las otras dos.

> **Decisión DD-07: tres claves independientes**, nunca un bloque único. Una configuración
> monolítica que expusiera las tres decisiones en un solo valor impediría cumplir este
> requisito por construcción.

#### Scenario: Cambiar una decisión no altera las otras

- **GIVEN** un sistema con regla de acceso, resolución de región y criterios de filtrado
  configurados
- **WHEN** se cambia el valor de una de esas decisiones
- **THEN** las otras dos conservan su valor
- **AND** el cambio no arrastra a las demás

#### Scenario: Las decisiones no se agrupan en un control único

- **GIVEN** la configuración del sistema
- **WHEN** se inspecciona
- **THEN** la regla de acceso, la resolución de región y los criterios de filtrado constan
  como claves independientes
- **AND** no están agrupadas en un único valor

#### Scenario: El valor por defecto horneado cubre cada decisión por separado

- **GIVEN** un sistema sin conectividad para recuperar su configuración
- **WHEN** opera
- **THEN** cada decisión cae en su propio valor por defecto declarado
- **AND** ninguna decisión toma el valor de otra

### Requirement: El alcance de soporte está declarado

El sistema SHALL operar en **todos los dispositivos que se declaren como objetivo**, y
SHALL **declarar explícitamente** esa versión mínima en lugar de aceptarlos en silencio.
Lo no declarado **no se considera soportado**.

SYR-23 no nombra una versión, y eso es deliberado: la versión mínima pertenece a la
declaración de soporte, no al requisito de sistema. Lo que el nivel de sistema exige es
que **el alcance esté declarado**.

> **DD-08 permanece diferida:** la versión mínima concreta es una decisión de ingeniería
> que se declara al implementar, no una pregunta al interesado.

#### Scenario: El alcance soportado está declarado

- **GIVEN** un sistema en ejecución
- **WHEN** se inspecciona su alcance
- **THEN** declara explícitamente la versión mínima que soporta
- **AND** lo no declarado no se considera soportado

#### Scenario: No se aceptan dispositivos en silencio

- **GIVEN** un dispositivo fuera del alcance declarado
- **WHEN** se opera en él
- **THEN** el sistema no lo acepta en silencio
- **AND** declara la condición no soportada

#### Scenario: El sistema opera en todo dispositivo declarado

- **GIVEN** un dispositivo dentro del alcance declarado
- **WHEN** se opera el sistema en él
- **THEN** el sistema funciona según lo declarado
- **AND** sin comportamiento degradado no declarado

### Requirement: El comportamiento ante condiciones no soportadas está declarado

El sistema SHALL declarar explícitamente su comportamiento ante condiciones no soportadas.
El mecanismo concreto de configuración (archivo, variable de entorno, servidor de flags,
build) es diseño y permanece diferido (**DD-07** cerrado en diseño; el mecanismo no se fija
aquí).

> **Añadir un parámetro no crea un requisito.** Un parámetro nuevo que nadie pidió no está
> cubierto por SYR-31.

#### Scenario: La condición no soportada tiene comportamiento declarado

- **GIVEN** una condición no soportada
- **WHEN** se alcanza
- **THEN** el sistema se comporta según lo declarado
- **AND** ese comportamiento consta explícitamente

#### Scenario: Un parámetro no solicitado no se confunde con un requisito

- **GIVEN** un parámetro de configuración que nadie solicitó
- **WHEN** se añade
- **THEN** no se considera cubierto por el requisito de configurabilidad
- **AND** su presencia no se presenta como cumplimiento de SYR-31
