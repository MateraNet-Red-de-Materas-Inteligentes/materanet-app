# Spec Delta

## Purpose

Permite que la jardinería conozca los atributos de cada maceta del listado —qué planta es,
cómo están sus sensores, en qué estado está, dónde está y qué se ha dicho de ella—
recibidos de la plataforma y nunca calculados por el cliente.

## ADDED Requirements

### Requirement: Tipo de planta por maceta
El sistema MUST obtener y presentar el tipo de planta de cada maceta del conjunto, y
atribuirlo a la maceta correspondiente.

_Derivado de SYR-05. Verificable: cada maceta tiene un tipo de planta atribuible._

#### Scenario: Tipo atribuible
- **WHEN** se presenta una maceta del conjunto
- **THEN** su tipo de planta aparece asociado a ella y a ninguna otra

### Requirement: Estado de sensores por maceta
El sistema MUST obtener y presentar el estado de los sensores de cada maceta del conjunto, y
atribuirlo a la maceta correspondiente.

_Derivado de SYR-06. Verificable: cada maceta tiene un estado de sensores atribuible._

#### Scenario: Sensores atribuibles
- **WHEN** se presenta una maceta del conjunto
- **THEN** su estado de sensores aparece asociado a ella

### Requirement: Estado de la planta recibido, no calculado
El sistema MUST obtener y presentar el estado de la planta de cada maceta. El sistema MUST
NOT determinar el estado de una planta por sí mismo: MUST NOT derivarlo de los sensores ni
de ningún otro dato disponible en el cliente.

_Derivado de SYR-07, SYR-R02, DA-01. Verificable: el estado está presente y el sistema no
lo derivó localmente._

#### Scenario: Estado recibido de la plataforma
- **WHEN** la plataforma proporciona el estado de una planta
- **THEN** el sistema lo presenta tal cual, sin transformarlo

#### Scenario: Estado no derivable localmente
- **WHEN** el sistema dispone de datos de sensores y no del estado de la planta
- **THEN** no calcula ni infiere un estado de planta a partir de los sensores

### Requirement: Ubicación por maceta
El sistema MUST obtener y presentar la ubicación de cada maceta del conjunto, y atribuirla
a la maceta correspondiente.

_Derivado de SYR-08. Verificable: cada maceta tiene una ubicación atribuible._

#### Scenario: Ubicación atribuible
- **WHEN** se presenta una maceta del conjunto
- **THEN** su ubicación aparece asociada a ella

### Requirement: Comentarios obtenidos por maceta
El sistema MUST obtener y presentar los comentarios obtenidos por cada maceta del conjunto,
y atribuirlos a la maceta correspondiente.

_Derivado de SYR-09. Verificable: cada maceta tiene sus comentarios atribuibles._

#### Scenario: Comentarios atribuibles
- **WHEN** se presenta una maceta del conjunto
- **THEN** los comentarios obtenidos para ella aparecen asociados a ella

### Requirement: La ubicación del dispositivo no se confunde con la ubicación de la maceta
Cuando el sistema use la capacidad de ubicación del dispositivo para resolver la región,
MUST distinguirla de la ubicación de la maceta, que es un dato provisto por la plataforma.
La ubicación de una maceta MUST NOT provenir del dispositivo del usuario.

_Derivado de SYR-24, SYR-08, DD-06. Verificable: la ubicación mostrada de una maceta es la
que entrega la plataforma._

#### Scenario: Región resuelta por ubicación del dispositivo
- **WHEN** la región se resolvió usando la ubicación del dispositivo
- **THEN** esa ubicación se usa solo para resolver la región, y no se presenta como la
  ubicación de una maceta

#### Scenario: Ubicación de maceta provista por la plataforma
- **WHEN** se presenta la ubicación de una maceta
- **THEN** es la que entregó la plataforma, no la del dispositivo
