# `identidad-y-control-de-acceso`

Cubre SYR-01, SYR-12, SYR-17, SYR-18, SYR-19, SYR-20.

> **Nota de nivel.** La capacidad de autenticación de la plataforma no figura en
> `BRS.md` §8.2 `SA-01`, que define su capacidad como telemetría y actuadores. Este spec
> se construye sobre el supuesto declarado **SA-04**. Si SA-04 resulta falso, este
> archivo no es implementable desde el cliente: la caja negra (DA-01) impide que el
> frontend invente identidad.

## ADDED Requirements

### Requirement: Identidad establecida antes de habilitar capacidades

El sistema SHALL establecer la identidad de quien opera **antes** de habilitar cualquier
capacidad de listado, y ninguna capacidad protegida SHALL operar sin identidad
establecida. La identidad la emite la plataforma; el sistema no la origina.

SYR-01 y SYR-17 hacen de la identidad una **condición de posibilidad**, no un
requerimiento paralelo: sin identidad no hay «usuario del sistema» del que SYR-12 pueda
hablar, ni «propio» del que SYR-13 dependa.

#### Scenario: La identidad se establece antes de habilitar el listado

- **GIVEN** una persona que ha abierto la aplicación
- **AND** la plataforma ha emitido una sesión para ella
- **WHEN** intenta consultar el listado de su región
- **THEN** el sistema tiene su identidad establecida
- **AND** el listado se habilita conforme a la regla de acceso vigente

#### Scenario: Sin identidad establecida, ninguna capacidad protegida opera

- **GIVEN** una persona sin sesión emitida por la plataforma
- **WHEN** intenta consultar el listado de su región
- **THEN** el sistema no habilita la capacidad
- **AND** no expone ningún dato del conjunto de la región

#### Scenario: La identidad no se origina en el cliente

- **GIVEN** una persona que opera el sistema
- **WHEN** se establece su identidad
- **THEN** la identidad procede de la plataforma y no de una asignación local
- **AND** el cliente no fabrica un identificador que sustituya al de la plataforma

### Requirement: Regla de acceso vigente y ajustable

El sistema SHALL operar bajo una regla de acceso vigente para la información de la
región. La redacción inicial de esa regla es la establecida por la plataforma: el listado
de una región es visible para cualquier usuario del sistema. El valor por defecto está
condicionado al riesgo **R-008** y puede ser endurecido sin nuevo desarrollo.

#### Scenario: Con un usuario habilitado, el listado es accesible

- **GIVEN** un usuario habilitado conforme a la regla vigente
- **AND** una región con macetas registradas
- **WHEN** consulta el listado de esa región
- **THEN** el listado es accesible
- **AND** se ajusta a la redacción vigente de la regla, no a una copia local

#### Scenario: El valor por defecto observa la regla vigente

- **GIVEN** la regla de acceso vigente redactada como «cualquier usuario del sistema»
- **WHEN** un usuario habilitado consulta el listado de su región
- **THEN** el acceso se concede según esa redacción
- **AND** el sistema no aplica una restricción adicional no declarada

### Requirement: Endurecimiento de la regla sin nuevo desarrollo

El sistema SHALL poder endurecer la regla de acceso **sin modificar su código y sin
publicar una versión nueva de la aplicación**. Este es el tratamiento que E-026 dio a
**R-008**: desacoplar el valor por defecto de la unquestion legal.

> La aplicación móvil es un cliente de caja negra. Esta regla gobierna **presentación y
> refetch**; la protección efectiva reside en la plataforma. Ver el requisito siguiente.

#### Scenario: La regla se endurece por configuración, sin release

- **GIVEN** una regla de acceso vigente que permite el listado a cualquier usuario
- **WHEN** esa regla se endurece a un subconjunto de usuarios
- **AND** el sistema recupera su configuración
- **THEN** aplica la regla endurecida
- **AND** no se ha modificado su código ni se ha publicado una versión nueva

#### Scenario: Endurecer la regla no altera otras decisiones de comportamiento

- **GIVEN** un sistema con regla de acceso, resolución de región y criterios de filtrado
  configurados de forma independiente
- **WHEN** se endurece la regla de acceso
- **THEN** la resolución de región no cambia
- **AND** los criterios de filtrado no cambian

### Requirement: La regla del cliente no es una frontera de seguridad

El sistema SHALL tratar la regla de acceso del cliente como una política de
**presentación y refetch**, y SHALL NOT presentarla como protección efectiva. La
autorización efectiva MUST residir en la plataforma.

Cualquier persona con acceso directo a la API puede eludir una regla que solo se aplica
en el cliente. Un spec que presente la regla del cliente como frontera de seguridad sería
falso por construcción.

#### Scenario: La política de cliente se declara como presentación

- **GIVEN** una regla de acceso vigente
- **WHEN** el sistema la aplica
- **THEN** la usa para decidir qué información presentar y cuándo volver a consultar
- **AND** declara que la aplicación no constituye la frontera de autorización

#### Scenario: La plataforma conserva la autoridad de autorización

- **GIVEN** un cliente que aplica una regla de acceso restrictiva
- **WHEN** una consulta se emite contra la plataforma
- **THEN** la plataforma conserva la autoridad de autorizar esa consulta
- **AND** el cliente no la usurpa

### Requirement: Credenciales de la plataforma ausentes del cliente

El sistema SHALL NOT exponer credenciales de la plataforma en el cliente, y SHALL NOT
almacenar ni transmitirlas de forma recuperable por terceros. El sistema SHALL almacenar
únicamente el token de acceso de vida corta emitido por la plataforma, en almacenamiento
seguro del dispositivo.

SYR-18 y SYR-20 son la contraparte de DA-01: la caja negra no se rompe por el lado del
cliente.

#### Scenario: El cliente no contiene secretos de la plataforma

- **GIVEN** una persona autenticada con sesión vigente
- **WHEN** se inspecciona el cliente
- **THEN** no contiene credenciales de la plataforma
- **AND** solo conserva el token de acceso emitido

#### Scenario: El token no es recuperable por terceros

- **GIVEN** un token de acceso almacenado en el dispositivo
- **WHEN** se intenta extraerlo por medios ajenos al almacenamiento seguro
- **THEN** las credenciales no son recuperables
- **AND** el token no reside en almacenamiento legible por otras aplicaciones

#### Scenario: La credencial no viaja en claro

- **GIVEN** una consulta a la plataforma
- **WHEN** se emite
- **THEN** transporta el token emitido y ninguna credencial de plataforma en claro
