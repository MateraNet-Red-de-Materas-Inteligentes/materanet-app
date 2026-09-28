# Spec Delta

## Purpose

Permite que la jardinería conozca el conjunto de macetas de su región y lo recorra sin
tener que construir la consulta, distinguiendo en todo momento entre la información
obtenida, la información ausente y la información que no se pudo obtener.

## ADDED Requirements

### Requirement: Región determinada antes de obtener el conjunto
El sistema MUST determinar a qué región corresponde la consulta ANTES de obtener el
conjunto de macetas. La región resuelta MUST ser consultable y visible para la persona.

_Derivado de SYR-02. Verificable: la región queda determinada y se muestra._

#### Scenario: Región resuelta
- **WHEN** se inicia la consulta del listado
- **THEN** el sistema determina la región y la presenta antes de mostrar macetas

#### Scenario: Resolución de región revisable
- **WHEN** la región ha sido determinada
- **THEN** la persona puede ver qué región se está consultando

### Requirement: Conjunto de macetas obtenido de la plataforma
El sistema MUST obtener de la plataforma el conjunto de macetas asociadas a la región
resuelta. El sistema MUST NOT obtener esos datos de ninguna otra fuente.

_Derivado de SYR-04, SYR-R01. Verificable: el conjunto recibido corresponde a la región
resuelta._

#### Scenario: Conjunto correspondiente a la región
- **WHEN** se obtiene el conjunto para una región resuelta
- **THEN** todas las macetas recibidas pertenecen a esa región

#### Scenario: Sin fuente alternativa
- **WHEN** la plataforma no responde
- **THEN** el sistema no sustituye la información por ninguna otra fuente

### Requirement: Macetas distinguibles entre sí
El sistema MUST presentar cada maceta del conjunto de forma distinguible de las demás,
de modo que los atributos de cada una puedan atribuirse sin ambigüedad.

_Derivado de SYR-03. Verificable: cada elemento del conjunto es distinguible._

#### Scenario: Elementos distinguibles
- **WHEN** el conjunto contiene varias macetas
- **THEN** cada una es identificable de forma independiente de las demás

### Requirement: Macetas propias distinguibles dentro del conjunto
El sistema MUST distinguir las macetas propias de las demás dentro del conjunto consultado.

_Derivado de SYR-13. Verificable: las macetas propias son identificables dentro del
conjunto._

#### Scenario: Identificación de macetas propias
- **WHEN** el conjunto consultado contiene macetas de la cuenta y de otras cuentas
- **THEN** el sistema identifica cuáles son propias

### Requirement: Listado navegable sin construir la consulta
El sistema MUST presentar el conjunto de la región de forma navegable, sin requerir que
la persona construya ni combine filtros para llegar a la información.

_Derivado de SYR-26, SYR-10. Verificable: la persona obtiene la información sin escalar
filtros._

#### Scenario: Acceso directo a la información
- **WHEN** la persona abre el listado de su región
- **THEN** accede a la información sin tener que aplicar filtros previos

### Requirement: Operable sin instrucción previa
El sistema MUST poder ser operado por la jardinería sin instrucción previa: llegar al
listado de la región no requiere documentación, tutorial ni asistencia.

_Derivado de SYR-25. Verificable: la persona alcanza el listado sin documentación._

#### Scenario: Primer uso sin acompañamiento
- **WHEN** una persona que nunca ha usado el sistema lo abre
- **THEN** alcanza el listado de su región por sí misma

### Requirement: Conjunto vacío operable
El sistema MUST operar normalmente cuando el conjunto de macetas de la región está vacío.
La ausencia de macetas MUST NOT presentarse como un fallo.

_Derivado de SYR-11, RN-03. Verificable: con cero macetas en la región, el sistema opera
normalmente._

#### Scenario: Región sin macetas
- **WHEN** la región consultada no tiene ninguna maceta
- **THEN** el sistema lo comunica como un estado normal, no como un error

#### Scenario: Arranque en frío
- **WHEN** se consulta una región en el arranque en frío de la base
- **THEN** el sistema opera sin fallar

### Requirement: El fallo de obtención se distingue de la ausencia de macetas
El sistema MUST distinguir el caso en que no se pudo obtener información del caso en que la
región no tiene macetas. Un fallo de obtención MUST NOT presentarse como conjunto vacío.

_Derivado de SYR-21, R-001. Verificable: ante fallo de la plataforma, el sistema no
presenta el fallo como "no hay macetas"._

#### Scenario: Plataforma no disponible
- **WHEN** la plataforma no permite obtener el conjunto de la región
- **THEN** el sistema comunica que no se pudo consultar, y no que la región está vacía

#### Scenario: Distinción visible
- **WHEN** el listado se presenta
- **THEN** la persona puede distinguir entre "no hay macetas" y "no se pudo consultar"

### Requirement: El conjunto parcial se distingue del conjunto completo
Cuando el sistema presente una fracción del conjunto, MUST distinguirla de la
presentación del conjunto completo.

_Derivado de SYR-30, SYR-28. Verificable: con fraccionamiento activo, la persona puede
saber que está viendo una parte._

#### Scenario: Fracción activa
- **WHEN** el sistema presenta solo una parte del conjunto de la región
- **THEN** comunica que lo presentado es una parte y no el total

### Requirement: Volumen no acotado por diseño
El sistema MUST obtener y presentar el conjunto de una región sin requerir un límite
superior conocido de antemano. El sistema MUST NOT truncar el conjunto en silencio.

_Derivado de SYR-27. Verificable: el sistema opera con el volumen que se le presente, sin
fallar ni truncar._

#### Scenario: Conjunto grande
- **WHEN** la región contiene un volumen alto de macetas
- **THEN** el sistema lo presenta completo, sin omisión silenciosa

#### Scenario: Techo técnico declarado
- **WHEN** se define el volumen con el que se verifica el sistema
- **THEN** ese techo queda declarado; lo que no se declara no se considera soportado

### Requirement: El conjunto se obtiene completo, independientemente de cómo llegue
El sistema MUST obtener el conjunto completo de macetas de la región, **sea cual sea la
forma en que la plataforma lo entregue** —integro, por partes o por páginas—. El conjunto
completo MUST ser alcanzable en todos los casos. El sistema MUST NOT depender de que la
plataforma proporcione una capacidad de separación que hoy no está declarada.

_Derivado de SYR-28, D-08. Verificable: con volumen alto, el conjunto completo sigue
alcanzable, y la presentación se fracciona si no cabe de una vez._

#### Scenario: La plataforma entrega el conjunto íntegro
- **WHEN** la plataforma entrega todas las macetas de la región en una sola respuesta
- **THEN** el sistema las procesa todas y el conjunto completo es alcanzable

#### Scenario: La plataforma entrega el conjunto por partes
- **WHEN** la plataforma entrega las macetas por partes
- **THEN** el sistema las acumula hasta agotar el conjunto, y la totalidad queda alcanzable

#### Scenario: El conjunto no cabe en una sola vista
- **WHEN** el conjunto excede lo que puede presentarse en una sola vista
- **THEN** el sistema lo presenta por fracciones, sin truncar el conjunto

### Requirement: Filtrado del conjunto
El sistema MUST permitir filtrar el conjunto por los criterios que la plataforma exponga.
La capacidad de filtrado MUST NOT depender de un volumen acotado.

_Derivado de SYR-29. Verificable: el filtrado opera sobre el conjunto recibido, con
independencia de su tamaño._

#### Scenario: Filtrado por criterio disponible
- **WHEN** la persona aplica un criterio de filtrado expuesto por la plataforma
- **THEN** el conjunto presentado se corresponde con ese criterio

### Requirement: Presentación de la información con su antigüedad
El sistema MUST indicar la antigüedad de la información presentada cuando la plataforma la
proporcione.

_Derivado de SYR-22, OB-6. Verificable: la información presentada indica cuándo
corresponde._

#### Scenario: Información con fecha conocida
- **WHEN** la plataforma proporciona la fecha de un dato
- **THEN** el sistema la presenta junto al dato

#### Scenario: Dato antiguo presentado como actual
- **WHEN** un dato tiene antigüedad conocida y el sistema no la muestra
- **THEN** el requisito no se cumple: la persona no puede saber que el dato es antiguo
