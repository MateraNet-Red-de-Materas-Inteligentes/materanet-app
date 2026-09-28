# Spec Delta

## Purpose

Fija las condiciones bajo las cuales el sistema opera: a través de qué interfaz habla con
la plataforma, quién puede ver el listado de una región, cómo se ajusta esa regla sin
desarrollo, y cuál es el alcance de soporte que el sistema declara.

## ADDED Requirements

### Requirement: Comunicación exclusiva por la interfaz publicada
El sistema MUST obtener sus datos exclusivamente a través de la interfaz publicada por la
plataforma. El sistema MUST NOT requerir ningún otro canal para obtener datos.

_Derivado de SYR-14, SYR-R01. Verificable: el sistema no requiere otro canal._

#### Scenario: Único canal
- **WHEN** el sistema obtiene datos
- **THEN** los obtiene por la interfaz publicada por la plataforma y por ningún otro medio

### Requirement: Operación sin conocimiento interno ajeno
El sistema MUST operar sin requerir conocimiento del estado interno de la plataforma ni del
estado de desarrollo de las macetas.

_Derivado de SYR-15, SYR-R03. Verificable: el sistema funciona sin información sobre
implementación ajena._

#### Scenario: Cambio interno de la plataforma
- **WHEN** la plataforma cambia su implementación interna sin cambiar su interfaz publicada
- **THEN** el sistema sigue operando sin cambios

#### Scenario: Estado de desarrollo de las macetas
- **WHEN** una maceta cambia de estado de desarrollo
- **THEN** el sistema no requiere conocer ese estado para operar

### Requirement: Operación contra la interfaz del entorno
El sistema MUST operar contra la interfaz de plataforma correspondiente al entorno en que se
ejecuta, sin requerir modificación de su lógica.

_Derivado de SYR-16. Verificable: el sistema opera contra la interfaz de su entorno._

#### Scenario: Ejecución en un entorno distinto
- **WHEN** el sistema se ejecuta en un entorno con otra dirección de interfaz
- **THEN** opera contra la de ese entorno sin cambios de comportamiento

### Requirement: Regla de acceso a la región vigente y ajustable
El sistema MUST proteger la información de la región conforme a una regla de acceso
vigente. El sistema MUST NOT exponer el listado de una región fuera de lo que esa regla
permita.

_Derivado de SYR-12, SYR-19, DA-02. Verificable: el acceso y la exposición se ajustan a la
regla vigente._

#### Scenario: Usuario habilitado
- **WHEN** un usuario habilitado según la regla vigente consulta el listado
- **THEN** el listado le es accesible

#### Scenario: Usuario no habilitado
- **WHEN** un usuario no habilitado según la regla vigente consulta el listado
- **THEN** el listado no le es presentado en su totalidad

### Requirement: Ajuste de la regla de acceso sin modificar código
El ajuste de la regla de acceso MUST poder realizarse sin modificar el código del sistema.
Endurecer la regla —restringir quién ve el listado de una región— MUST NOT requerir un
nuevo ciclo de desarrollo.

_Derivado de SYR-31, R-008. Verificable: la regla puede endurecerse sin modificar el
sistema._

#### Scenario: Endurecimiento de la regla
- **WHEN** se decide restringir el acceso al listado de una región
- **THEN** el ajuste se aplica sin modificar el código del sistema

### Requirement: Decisiones de comportamiento ajustables por separado
La regla de acceso, la resolución de región y los criterios de filtrado MUST ser ajustables
de forma independiente. El ajuste de uno MUST NOT requerir el ajuste de otro.

_Derivado de SYR-32. Verificable: cambiar una decisión no altera las otras dos._

#### Scenario: Ajuste independiente
- **WHEN** se cambia el criterio de filtrado
- **THEN** la regla de acceso y la resolución de región quedan sin alterar

### Requirement: Alcance de soporte declarado
El sistema MUST declarar explícitamente su alcance de soporte —versión mínima de
plataforma— y su comportamiento ante condiciones no soportadas. Lo que no se declara MUST
NOT considerarse soportado.

_Derivado de SYR-23, SYR-33, DD-08. Verificable: el alcance está declarado._

#### Scenario: Dispositivo dentro del alcance
- **WHEN** el sistema se ejecuta en un dispositivo dentro del alcance declarado
- **THEN** opera con normalidad

#### Scenario: Dispositivo fuera del alcance
- **WHEN** el sistema se ejecuta en un dispositivo fuera del alcance declarado
- **THEN** comunica la condición no soportada en lugar de fallar silenciosamente
