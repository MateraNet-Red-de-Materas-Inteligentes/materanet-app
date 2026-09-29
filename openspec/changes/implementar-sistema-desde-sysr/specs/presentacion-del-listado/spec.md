# `presentacion-del-listado`

Cubre SYR-03, SYR-10, SYR-13, SYR-25, SYR-26.

> **SYR-10 no especifica la forma de la presentación** —pantalla, mapa, listado— porque esa
> decisión es de diseño (DD-03). Este spec exige que la información esté **disponible para
> su consulta**, no cómo se ve.
>
> **Disciplina RN-01.** Un requisito de usabilidad que sirva a OB-2 —fricción
> intencional hacia la compra— es **inválido**, aunque sea más eficiente comercialmente.

## ADDED Requirements

### Requirement: Las macetas del conjunto son distinguibles

El sistema SHALL distinguir las macetas unas de otras dentro del conjunto consultado. Sin
identidad de maceta, los atributos de SYR-05 a SYR-09 no pueden atribuirse a nadie.

#### Scenario: Cada elemento del conjunto es distinguible

- **GIVEN** un conjunto de macetas de una región
- **WHEN** el sistema lo presenta
- **THEN** cada elemento es distinguible de los demás
- **AND** sus atributos se atribuyen a la maceta a la que corresponden

#### Scenario: Macetas indistinguibles no se colapsan

- **GIVEN** dos macetas con los mismos atributos visibles
- **WHEN** el sistema presenta el conjunto
- **THEN** no las fusiona en un solo elemento
- **AND** cada una conserva su identidad

### Requirement: El conjunto y sus atributos están disponibles para consulta

El sistema SHALL presentar a la jardinería el conjunto de macetas de la región con los
atributos de tipo de planta, estado de sensores, estado de planta, ubicación y comentarios
**disponibles para su consulta**. Los atributos los establece la plataforma
(`obtencion-de-datos`); aquí se establece que la persona puede consultarlos.

#### Scenario: La jardinería consulta el conjunto y sus atributos

- **GIVEN** una región con macetas registradas
- **WHEN** la jardinería consulta el listado
- **THEN** el conjunto y sus atributos están disponibles para su consulta
- **AND** los atributos corresponden a los entregados por la plataforma

#### Scenario: La presentación no inventa atributos ausentes

- **GIVEN** una maceta cuyos comentarios no fueron obtenidos
- **WHEN** el sistema la presenta
- **THEN** declara que ese atributo no está disponible
- **AND** no lo sustituye por un valor inventado

### Requirement: Las macetas propias se distinguen de las demás

El sistema SHALL distinguir, dentro del conjunto consultado, cuáles macetas son propias de
quien consulta. Esta distinción sostiene el deber de cuidado sobre lo propio (OB-6).

> **Trazabilidad declarada:** SYR-13 es toda la representación de la capacidad 3 de E-003
> en esta iteración. El **tablero** de control —presentar de forma continua el estado de las
> macetas propias— **no tiene requisito de sistema**, porque no existe un SR que lo pida
> (`SyRS.md` §6.1). No se añade aquí.

#### Scenario: Las macetas propias son identificables en el conjunto

- **GIVEN** un conjunto que contiene macetas propias y ajenas
- **WHEN** la jardinería consulta el conjunto
- **THEN** distingue cuáles le son propias
- **AND** las demás siguen apareciendo en el conjunto

#### Scenario: La propiedad no se infiere de la ubicación

- **GIVEN** una maceta cercana dentro de la misma región
- **WHEN** el sistema determina la propiedad
- **THEN** la propiedad procede de la plataforma
- **AND** la cercanía geográfica no implica propiedad

### Requirement: El listado es operable sin instrucción previa

El sistema SHALL poder ser operado por la jardinería **sin instrucción previa**. La
persona alcanza el listado sin documentación.

#### Scenario: La persona alcanza el listado sin documentación

- **GIVEN** una Jardinería nueva, sin instrucción previa
- **WHEN** abre la aplicación
- **THEN** alcanza el listado del conjunto de su región
- **AND** no requiere leer documentación para ello

#### Scenario: El arranque en frío no bloquea el acceso

- **GIVEN** una persona cuya única relación es su propia maceta (**RN-03**)
- **WHEN** abre la aplicación
- **THEN** alcanza el listado regional
- **AND** no se le exige relación social previa

### Requirement: El listado es navegable sin construir la consulta

El sistema SHALL presentar el conjunto de la región de forma navegable, sin requerir que
la persona construya la consulta.

#### Scenario: La persona obtiene la información sin escalar filtros

- **GIVEN** un conjunto de macetas de una región
- **WHEN** la jardinería intenta consultarlo
- **THEN** lo obtiene sin escalar filtros
- **AND** sin redactar una consulta

#### Scenario: La navegación no degrada a la lectura sin instrucción

- **GIVEN** un listado presentado para consulta
- **WHEN** la persona navega por sus macetas
- **THEN** no requiere construir la consulta en ningún momento
- **AND** la misión (OB-5, OB-7) no se subordina a una fricción comercial (RN-01)
