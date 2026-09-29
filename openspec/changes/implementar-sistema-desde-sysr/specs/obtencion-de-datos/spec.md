# `obtencion-de-datos`

Cubre SYR-04, SYR-05, SYR-06, SYR-07, SYR-08, SYR-09, SYR-14, SYR-15, SYR-16.

> **SYR-R01 — caja negra.** El sistema obtiene la totalidad de sus datos de la API y no
> dispone de fuente alternativa. Un requisito de sistema se verifica contra una
> **capacidad de la API**, nunca contra una implementación propia.
>
> **SYR-R02 — no se calculan valores de dominio.** El sistema no calcula el estado de una
> planta: lo recibe. Este es el error de mayor consecuencias del documento (**R-006**):
> un estado de planta es un juicio, no un dato, y su invención local convertiría un dato
> falso en una presentación aparentemente fiable.

## ADDED Requirements

### Requirement: El conjunto de macetas de la región se obtiene de la plataforma

El sistema SHALL obtener de la plataforma el conjunto de macetas asociadas a la región
resuelta. El conjunto recibido SHALL corresponder a esa región.

#### Scenario: El conjunto se obtiene para la región resuelta

- **GIVEN** una región determinada y una identidad establecida
- **WHEN** el sistema obtiene el conjunto de macetas
- **THEN** lo obtiene de la plataforma
- **AND** el conjunto recibido corresponde a la región resuelta

#### Scenario: No existe fuente alternativa a la plataforma

- **GIVEN** una solicitud del conjunto de macetas
- **WHEN** el sistema la satisface
- **THEN** la obtiene exclusivamente de la interfaz publicada por la plataforma
- **AND** no recurre a ninguna otra fuente

### Requirement: Cada maceta expone sus atributos

El sistema SHALL obtener y atribuir a cada maceta del conjunto: el **tipo de planta**
(SYR-05), el **estado de los sensores** (SYR-06), la **ubicación** (SYR-08) y los
**comentarios obtenidos** (SYR-09). Cada atributo se atribuye a la maceta a la que
pertenece, lo que presupone SYR-03.

#### Scenario: Cada maceta tiene tipo de planta atribuible

- **GIVEN** un conjunto de macetas recibido
- **WHEN** el sistema lo expone
- **THEN** cada maceta tiene un tipo de planta atribuible a esa maceta

#### Scenario: Cada maceta tiene estado de sensores atribuible

- **GIVEN** un conjunto de macetas recibido
- **WHEN** el sistema lo expone
- **THEN** cada maceta tiene un estado de sensores atribuible a esa maceta

#### Scenario: Cada maceta tiene ubicación atribuible

- **GIVEN** un conjunto de macetas recibido
- **WHEN** el sistema lo expone
- **THEN** cada maceta tiene una ubicación atribuible a esa maceta

#### Scenario: Cada maceta tiene comentarios atribuibles

- **GIVEN** un conjunto de macetas recibido
- **WHEN** el sistema lo expone
- **THEN** los comentarios obtenidos se atribuyen a la maceta a la que corresponden

#### Scenario: La ubicación de la maceta no se confunde con la del dispositivo

- **GIVEN** una maceta con ubicación obtenida de la plataforma
- **AND** un dispositivo con su propia ubicación
- **WHEN** el sistema expone ambos datos
- **THEN** los distingue como datos de orígenes distintos

### Requirement: El estado de la planta se recibe y no se calcula

El sistema SHALL obtener el estado de la planta de cada maceta y SHALL **recibirlo de la
plataforma**. El sistema SHALL NOT derivar, inferir ni calcular localmente el estado de una
planta, ni cuando la plataforma lo omita.

Este requisito se verifica por lo que el sistema **no** hace, y es el que protege **R-006**.

#### Scenario: El estado de planta se recibe de la plataforma

- **GIVEN** una maceta con estado de planta publicado por la plataforma
- **WHEN** el sistema obtiene sus datos
- **THEN** presenta ese estado
- **AND** no lo ha derivado localmente

#### Scenario: El estado de planta no se infiere de los sensores

- **GIVEN** una maceta con estado de sensores pero sin estado de planta publicado
- **WHEN** el sistema obtiene sus datos
- **THEN** no deduce un estado de planta a partir de los sensores
- **AND** declara que el estado de planta no está disponible

#### Scenario: La ausencia de estado de planta no se sustituye por un juicio propio

- **GIVEN** que la plataforma no publica el estado de una planta
- **WHEN** el sistema presenta esa maceta
- **THEN** no le atribuye un estado de planta propio
- **AND** no lo presenta como si fuera un dato de la plataforma

### Requirement: La comunicación ocurre solo por la interfaz publicada

El sistema SHALL comunicarse con la plataforma **exclusivamente** a través de la interfaz
publicada por ella, y SHALL NOT requerir otro canal para obtener datos. La forma de esa
interfaz —queries, campos, tipos, paginación— permanece diferida (**DD-01**); lo que se
exige en este nivel es la existencia del requisito, no su estructura.

#### Scenario: La obtención no requiere otro canal

- **GIVEN** una necesidad de datos
- **WHEN** el sistema la satisface
- **THEN** lo hace por la interfaz publicada por la plataforma
- **AND** no requiere un canal adicional

#### Scenario: La estructura de la interfaz no se fija en este nivel

- **GIVEN** el requisito de usar la interfaz publicada
- **WHEN** se especifica este capability
- **THEN** no fija queries, campos, tipos ni paginación
- **AND** deja su estructura diferida conforme a DD-01

### Requirement: El sistema opera sin conocer el estado interno

El sistema SHALL operar sin requerir conocimiento del estado interno de la plataforma ni
de las macetas. No SHALL representar el estado de desarrollo del hardware
(**SYR-R03**, E-014: al frontend no le corresponde el estado de desarrollo de las macetas).

#### Scenario: El sistema no inspecciona el interior de la plataforma

- **GIVEN** una consulta a la plataforma
- **WHEN** el sistema la formula
- **THEN** se apoya solo en lo que la interfaz publicada expone
- **AND** no requiere información sobre la implementación ajena

#### Scenario: El estado de desarrollo del hardware no se representa

- **GIVEN** macetas con distintos grados de cumplimiento de la plataforma
- **WHEN** el sistema presenta el conjunto
- **THEN** no representa el estado de desarrollo del hardware
- **AND** solo expone lo que la plataforma entrega

### Requirement: Operación contra la interfaz del entorno

El sistema SHALL operar contra la interfaz de plataforma correspondiente al entorno en que
se ejecuta, y SHALL NOT requerir una interfaz ajena a ese entorno.

#### Scenario: Cada entorno usa su interfaz correspondiente

- **GIVEN** un entorno de ejecución determinado
- **WHEN** el sistema obtiene datos
- **THEN** opera contra la interfaz de plataforma de ese entorno
- **AND** no requiere la interfaz de otro entorno
