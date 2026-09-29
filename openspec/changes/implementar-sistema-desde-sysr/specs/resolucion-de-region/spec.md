# `resolucion-de-region`

Cubre SYR-02, SYR-24.

> **Decisión DD-06 cerrada: la región se resuelve por ubicación del dispositivo (GPS).**
> Consecuencia directa: **SYR-24 permanece en alcance** y el total de requisitos se
> mantiene en 33. Si DD-06 se hubiera resuelto sin ubicación, SYR-24 se retiraba y el
> total bajaba a 32 (`SyRS.md` §6.1).
>
> **Costos declarados y aceptados:** diálogo de permiso de ubicación, precisión del GPS
> como dependencia de arranque bajo `RN-03`, y un segundo vector de datos personales que
> se suma al ya señalado por `R-008`.
>
> **Distinción obligatoria (SYR-R03 / E-014):** la ubicación del **dispositivo** es un dato
> del teléfono que el sistema obtiene. La ubicación de la **maceta** (SYR-08) es un dato
> que la plataforma entrega. No son lo mismo.

## ADDED Requirements

### Requirement: La región queda determinada para la consulta

El sistema SHALL determinar a qué región corresponde la consulta antes de solicitar el
conjunto de macetas. `SR-01` dice «una región» sin definir cuál: sin resolución de
región no hay conjunto que listar, de modo que esto es **condición de posibilidad**, no un
detalle de implementación.

#### Scenario: La consulta resuelve su región

- **GIVEN** una persona con identidad establecida
- **WHEN** consulta el listado de macetas
- **THEN** el sistema ha determinado a qué región corresponde la consulta
- **AND** la región resuelta es consultable por la propia persona

#### Scenario: Sin región resuelta no se solicita el conjunto

- **GIVEN** una consulta en la que la región no ha podido determinarse
- **WHEN** el sistema intenta obtener el conjunto de macetas
- **THEN** no emite la solicitud como si correspondiera a una región
- **AND** declara que no pudo determinar la región

#### Scenario: El conjunto recibido corresponde a la región resuelta

- **GIVEN** una región determinada
- **WHEN** el sistema obtiene el conjunto de macetas
- **THEN** el conjunto recibido corresponde a esa región
- **AND** no mezcla macetas de regiones ajenas

### Requirement: La ubicación del dispositivo se usa para resolver la región

Cuando la resolución de región requiera la ubicación, el sistema SHALL hacer uso de la
capacidad de ubicación del dispositivo, y SHALL solicitar el permiso correspondiente al
usuario.

#### Scenario: El permiso se solicita antes de usar la ubicación

- **GIVEN** una resolución de región que requiere la ubicación del dispositivo
- **WHEN** el sistema va a resolverla
- **THEN** solicita el permiso de ubicación
- **AND** no obtiene la ubicación sin ese permiso

#### Scenario: La ubicación del dispositivo resuelve la región

- **GIVEN** el permiso de ubicación concedido
- **AND** una ubicación disponible
- **WHEN** el sistema resuelve la región
- **THEN** la región se determina a partir de la ubicación del dispositivo

### Requirement: La resolución de región se degrada de forma explícita

Cuando la ubicación no esté disponible —permiso denegado, ubicación desactivada, o
señal insuficiente— el sistema SHALL distinguir esa causa de la **ausencia de macetas** y
deberá declararla. La imposibilidad de resolver la región **no** se presenta como
conjunto vacío.

#### Scenario: Permiso denegado no produce conjunto vacío

- **GIVEN** una persona que ha denegado el permiso de ubicación
- **WHEN** intenta consultar el listado
- **THEN** el sistema declara que no pudo determinar la región
- **AND** no presenta el resultado como «no hay macetas en tu región»

#### Scenario: La ausencia de ubicación se distingue de la ausencia de macetas

- **GIVEN** una región cuya ubicación no ha podido determinarse
- **WHEN** el sistema no obtiene el conjunto
- **THEN** declara un problema de resolución, no un conjunto vacío

#### Scenario: La ubicación no disponible no degrada a un valor inventado

- **GIVEN** que la ubicación del dispositivo no está disponible
- **WHEN** el sistema intenta resolver la región
- **THEN** no adopta una región por defecto sin declarar
- **AND** no presenta un conjunto como si correspondiera a una región resuelta
