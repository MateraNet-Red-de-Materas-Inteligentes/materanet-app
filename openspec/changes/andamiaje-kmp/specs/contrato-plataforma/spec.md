# `contrato-plataforma`

Declara la costura entre el cliente y la plataforma, y sobre todo **lo que esa costura no
exige**.

> **El contrato es del cliente, no un requisito para la plataforma.** E-027 propuso
> «fraccionamiento» como algo que la plataforma debía exponer, y E-028 lo corrigió: *«Pedir
> una capacidad nueva a otro sistema es una dependencia que puede frenar el proyecto
> entero»*. Este spec existe para que ese error no se repita. Cada capacidad declarada
> responde a una pregunta sobre **qué necesita el cliente para ser correcto**, nunca sobre
> **qué debería construir el otro equipo**.
>
> **Lo que se le pide a la plataforma: nada.** Ni fraccionamiento, ni un tamaño máximo, ni
> campos concretos, ni tipos, ni una forma de respuesta. El contrato se adapta a lo que
> exista; no al revés.

## ADDED Requirements

### Requirement: El contrato declara solo capacidades que el cliente necesita

El sistema SHALL declarar, como contrato con la plataforma, exactamente las capacidades que
el cliente necesita para operar correctamente, y SHALL NOT declarar ninguna capacidad
nueva que la plataforma no publique hoy.

Las capacidades del contrato son **cinco**: obtener sesión de plataforma, obtener el
conjunto de macetas de una región con sus atributos, filtros aplicables al conjunto, fecha
de los datos cuando la plataforma la tenga, e indicación de qué macetas son de la cuenta.

#### Scenario: El contrato declara exactamente cinco capacidades

- **GIVEN** el contrato declarado por el cliente
- **WHEN** se enumeran sus capacidades
- **THEN** son las cinco declaradas
- **AND** ninguna capacidad adicional figura en el contrato

#### Scenario: La ausencia de una capacidad no detiene la construcción

- **GIVEN** una capacidad que la plataforma no publica
- **WHEN** se observa su ausencia
- **THEN** se registra como dato
- **AND** no detiene la construcción del cliente
- **AND** no se convierte en un requisito que otro sistema deba cumplir

#### Scenario: La incertidumbre no se convierte en exigencia

- **GIVEN** una capacidad de la que no se sabe si la plataforma la tiene
- **WHEN** se decide el diseño
- **THEN** se diseña para el caso en que no la tenga
- **AND** no se registra como algo que la plataforma deba proporcionar

#### Scenario: El contrato no fija la forma de la respuesta

- **GIVEN** el contrato declarado
- **WHEN** se examina qué exige sobre la forma de la respuesta
- **THEN** no exige campos concretos, ni tipos, ni estructura
- **AND** el cliente se ajusta a lo que la plataforma ya publica

### Requirement: El conjunto se obtiene completo, sea cual sea la forma en que llegue

El sistema SHALL obtener el conjunto de macetas de una región con independencia de si la
plataforma lo entrega entero, por partes o por páginas. El sistema SHALL NOT suponer que el
conjunto cabe, y por tanto SHALL NOT depender de que la plataforma lo separe.

**Consecuencia asumida y escrita:** con el conjunto entero, la primera pantalla espera a
que llegue todo. Es una desventaja real, aceptada a cambio de no bloquear. Si el volumen
resultara grande, la optimización es agregar fraccionamiento en la plataforma **después**,
cuando exista la medición que hoy no existe.

#### Scenario: El conjunto llega entero

- **GIVEN** una plataforma que entrega el conjunto completo en una sola respuesta
- **WHEN** el cliente lo obtiene
- **THEN** opera con la totalidad del conjunto

#### Scenario: El conjunto llega por partes

- **GIVEN** una plataforma que entrega el conjunto dividido en varias respuestas
- **WHEN** el cliente lo obtiene
- **THEN** opera con la totalidad una vez reunidas las partes

#### Scenario: El conjunto llega por páginas

- **GIVEN** una plataforma que entrega el conjunto paginado
- **WHEN** el cliente lo obtiene
- **THEN** opera con la totalidad de páginas reunidas

#### Scenario: No se exige fraccionamiento a la plataforma

- **GIVEN** una plataforma que no separa el conjunto
- **WHEN** se evalúa si el cliente puede operar
- **THEN** puede operar igualmente
- **AND** la falta de fraccionamiento no se registra como incumplimiento

### Requirement: La región se resuelve en el cliente y no se pide a la plataforma

El sistema SHALL determinar la región a partir del dispositivo y SHALL NOT solicitar a la
plataforma que determine la región de una cuenta. El contrato SHALL NOT declarar una
capacidad de resolución de región.

Esta es una capacidad **retirada**: D-02 (E-027) la declaraba, y DD-06 la volvió
innecesaria al resolver la región por GPS. Pedirla sería pedir menos de lo necesario.

#### Scenario: El contrato no declara resolución de región

- **GIVEN** el contrato declarado por el cliente
- **WHEN** se enumeran sus capacidades
- **THEN** ninguna se refiere a determinar la región
- **AND** la región no se pide a la plataforma

#### Scenario: El cliente resuelve la región por el dispositivo

- **GIVEN** una sesión de plataforma establecida
- **WHEN** el sistema necesita la región
- **THEN** la obtiene del dispositivo
- **AND** no consulta a la plataforma por ella

### Requirement: La fecha de los datos y la pertenencia a la cuenta se piden como opcionales

El sistema SHALL declarar la fecha de los datos y la indicación de qué macetas son de la
cuenta como **capacidades opcionales**. Cuando la plataforma no las proporcione, el sistema
SHALL NOT afirmar que un dato es actual ni que una maceta pertenece a la cuenta.

#### Scenario: La plataforma fecha los datos

- **GIVEN** una plataforma que fecha sus respuestas
- **WHEN** el cliente recibe el conjunto
- **THEN** dispone de la antigüedad de los datos

#### Scenario: La plataforma no fecha los datos

- **GIVEN** una plataforma que entrega el conjunto sin fecha
- **WHEN** el cliente lo recibe
- **THEN** declara que la antigüedad es desconocida
- **AND** no presenta los datos como actuales por omisión

#### Scenario: La plataforma indica qué macetas son de la cuenta

- **GIVEN** una plataforma que distingue las macetas de la cuenta
- **WHEN** el cliente recibe el conjunto
- **THEN** puede distinguir cuáles pertenecen a la cuenta

#### Scenario: La plataforma no hace esa distinción

- **GIVEN** una plataforma que no distingue las macetas de la cuenta
- **WHEN** el cliente recibe el conjunto
- **THEN** no afirma que ninguna maceta pertenezca a la cuenta
- **AND** no lo infiere por otra vía

### Requirement: El contrato no afirma conocer la forma del API de la plataforma

El sistema SHALL expresar el contrato en el vocabulario del cliente. Un cambio en la forma
en que la plataforma publica sus datos SHALL NOT alcanzar el dominio ni el contrato, y
SHALL NOT requerir cambio en ninguna de las 33 SYR.

La forma real del API **sigue sin conocerse** (`Q-025`). Este spec declara una costura
propia; no afirma conocer la plataforma.

#### Scenario: El contrato se expresa en el vocabulario del cliente

- **GIVEN** el contrato declarado
- **WHEN** se expresan sus tipos
- **THEN** son los del dominio, no los de la plataforma

#### Scenario: Un cambio en la plataforma no alcanza al dominio

- **GIVEN** un cambio futuro en la forma en que la plataforma publica sus datos
- **WHEN** el sistema se adapta a ese cambio
- **THEN** el cambio se confina al adaptador
- **AND** el dominio no se modifica

#### Scenario: La forma real del API permanece sin conocer

- **GIVEN** que la forma real del API no está descrita
- **WHEN** se revisa el estado de `DD-01`
- **THEN** aparece resuelta **como declaración del cliente**, no como descripción del API
- **AND** `Q-025` sigue abierta
