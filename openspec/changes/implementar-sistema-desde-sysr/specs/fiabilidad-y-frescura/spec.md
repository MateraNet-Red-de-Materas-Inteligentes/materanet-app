# `fiabilidad-y-frescura`

Cubre SYR-11, SYR-21, SYR-22.

> **SYR-21 es un requisito de seguridad funcional, no de usabilidad.** Si la plataforma
> falla y el sistema muestra el conjunto vacío, la jardinería ve *«no hay macetas en tu
> región»* cuando la verdad es *«no se pudo consultar»*. Convierte un fallo en un **dato
> falso**, y con un lanzamiento por cluster y macetas que cambian de estado, esa distinción
> miente sobre la realidad del barrio.
>
> **SYR-22 se deriva de OB-6.** Una lectura de humedad de hace tres horas, presentada como
> actual, puede llevar a una decisión de cuidado equivocada. Es el mismo principio que
> **RN-01.b** aplica a los actuadores, aplicado a la información.

## ADDED Requirements

### Requirement: El conjunto vacío no se trata como fallo

El sistema SHALL operar **sin degradar** cuando el conjunto de una región está vacío, y
SHALL NOT tratar la ausencia de macetas como un fallo. En un lanzamiento por cluster, el
conjunto vacío es el **estado inicial de la base**, no una excepción (**RN-03**, **R-001**).

#### Scenario: Con cero macetas, el sistema opera normalmente

- **GIVEN** una región sin ninguna maceta registrada
- **WHEN** la jardinería consulta el listado
- **THEN** el sistema opera normalmente
- **AND** no degrada su comportamiento

#### Scenario: El conjunto vacío se distingue del error

- **GIVEN** una región consultada
- **WHEN** el conjunto resulta vacío
- **THEN** el sistema lo presenta como conjunto vacío
- **AND** no lo presenta como un fallo de consulta

#### Scenario: El arranque en frío es un estado operable

- **GIVEN** una región recién abierta sin macetas (**RN-03**)
- **WHEN** la persona consulta el listado
- **THEN** el sistema es viable en ese estado
- **AND** no exige conexiones sociales previas para operarlo

### Requirement: La imposibilidad de obtener datos se distingue de la ausencia de macetas

El sistema SHALL distinguir **la imposibilidad de obtener datos** de **la ausencia de
macetas** en la región. Ante un fallo de la plataforma, el sistema SHALL NOT presentar el
fallo como «no hay macetas».

Este es el requisito que impide convertir un fallo de plataforma en un dato falso.

#### Scenario: Ante fallo de plataforma, no se muestra el conjunto vacío

- **GIVEN** una región que sí contiene macetas
- **AND** la plataforma no puede responder
- **WHEN** la persona consulta el listado
- **THEN** el sistema declara que no se pudo consultar
- **AND** no presenta el resultado como «no hay macetas»

#### Scenario: El fallo declarado no se confunde con la ausencia

- **GIVEN** una consulta que falla
- **WHEN** el sistema informa el resultado
- **THEN** declara una imposibilidad de obtener datos
- **AND** no afirma que la región carezca de macetas

#### Scenario: La información no se conserva como si fuera actual tras un fallo

- **GIVEN** un listado previamente obtenido
- **AND** una consulta posterior que falla
- **WHEN** la persona observa el listado
- **THEN** el sistema declara el estado de fallo
- **AND** no presenta los datos previos como si fueran resultado de la consulta actual

### Requirement: La información presentada indica su antigüedad

El sistema SHALL indicar la **antigüedad de la información** presentada cuando la
plataforma la proporcione. Si la plataforma no la proporciona, el sistema SHALL declarar
que la antigüedad es desconocida, en lugar de presentar el dato como actual.

#### Scenario: La información presentada indica cuándo corresponde

- **GIVEN** una lectura cuyos datos la plataforma fecha
- **WHEN** el sistema la presenta
- **THEN** indica la antigüedad de esa lectura
- **AND** la persona puede distinguirla de una lectura actual

#### Scenario: Un dato antiguo no se presenta como actual

- **GIVEN** una lectura de datos con antigüedad considerable
- **WHEN** el sistema la presenta
- **THEN** no la presenta como actual
- **AND** su antigüedad queda visible

#### Scenario: La antigüedad desconocida se declara

- **GIVEN** datos que la plataforma entrega sin fecha
- **WHEN** el sistema los presenta
- **THEN** declara que su antigüedad se desconoce
- **AND** no los presenta como actuales por omisión
