# `escalabilidad-y-filtrado`

Cubre SYR-27, SYR-28, SYR-29, SYR-30.

> **El volumen no se pregunta, se absorbe.** `SyRS.md` §3.9 (E-026) retiró los cinco
> parámetros que v0.3 pedía: el volumen de macetas por región **no es un dato que el
> interesado deba proporcionar**, es un parámetro de ingeniería. La respuesta correcta a
> «no sé cuántas macetas habrá» no es *pregúntame el número*: es **el diseño no depende de
> conocerlo**.
>
> **SYR-27 no es una promesa de infinitud.** Todo sistema tiene un techo técnico real. Lo
> que se exige es que el techo **no obligue a cambiar de diseño**, y que el techo existente
> **se mida y se declare** en el plan de pruebas. Un requisito de escalabilidad que no dice
> dónde está el límite no dice nada (**R-009**).

## ADDED Requirements

### Requirement: El conjunto opera sin un límite superior conocido de antemano

El sistema SHALL obtener y presentar el conjunto de una región sin requerir un límite
superior conocido de antemano. El volumen con el que se ejercita la verificación lo fija el
plan de pruebas (ISO 29119) y se declara allí; no es un requisito pendiente (**DD-08**).

#### Scenario: El sistema opera con el volumen que se le presente

- **GIVEN** una región cuyo conjunto tiene un volumen alto
- **WHEN** el sistema obtiene y presenta ese conjunto
- **THEN** no falla por no existir un volumen declarado
- **AND** no trunca el conjunto sin declararlo

#### Scenario: El techo se declara, no se supone

- **GIVEN** un requisito de escalabilidad sin volumen fijado
- **WHEN** se planifica su verificación
- **THEN** el volumen con el que se ejercita queda declarado en el plan de pruebas
- **AND** no se pregunta al interesado

### Requirement: La obtención y la presentación se fraccionan

El sistema SHALL fraccionar la obtención y la presentación del conjunto cuando su volumen
lo requiera, en lugar de asumir que cabe íntegro. Con fraccionamiento activo, el conjunto
**completo** sigue siendo alcanzable.

#### Scenario: Con volumen alto, la obtención se fracciona

- **GIVEN** una región cuyo conjunto excede lo que cabe en una sola obtención
- **WHEN** el sistema obtiene el conjunto
- **THEN** lo obtiene por fracciones
- **AND** el conjunto completo sigue siendo alcanzable

#### Scenario: El fraccionamiento no trunca el conjunto

- **GIVEN** un conjunto obtenido por fracciones
- **WHEN** la persona recorre el listado
- **THEN** alcanza la totalidad del conjunto
- **AND** ninguna maceta queda inaccesible por el fraccionamiento

### Requirement: El conjunto se filtra por los criterios de la plataforma

El sistema SHALL filtrar el conjunto por los criterios que la plataforma exponga, sin que
la capacidad de filtrado dependa de un volumen acotado. Los criterios concretos son
decisión de diseño; este requisito establece que el filtrado opera sobre el conjunto
recibido, con independencia de su tamaño.

#### Scenario: El filtrado opera sobre el conjunto recibido

- **GIVEN** un conjunto de macetas recibido con los criterios que la plataforma expone
- **WHEN** la persona aplica un criterio de filtrado
- **THEN** el filtrado opera sobre el conjunto recibido
- **AND** su resultado no depende del tamaño del conjunto

#### Scenario: El filtrado no queda acotado por un volumen supuesto

- **GIVEN** un conjunto de volumen alto
- **WHEN** la persona filtra
- **THEN** el filtrado opera igual que con un conjunto pequeño
- **AND** no degrada por haber superado un volumen presumido

### Requirement: Conjunto parcial y conjunto completo se distinguen

El sistema SHALL distinguir el **conjunto parcial** del **conjunto completo**. Con
fraccionamiento activo, la persona debe poder saber que está viendo una parte y no el
total.

Este requisito se apoya en SYR-21: presentar una parte como si fuera el todo convierte un
dato incompleto en un dato falso.

#### Scenario: Con fraccionamiento activo, la parcialidad es visible

- **GIVEN** un conjunto presentado por fracciones
- **WHEN** la persona observa el listado
- **THEN** puede saber que está viendo una parte
- **AND** sabe que el conjunto no está completo

#### Scenario: El conjunto completo se declara como tal

- **GIVEN** un conjunto obtenido íntegro
- **WHEN** la persona observa el listado
- **THEN** sabe que ve el conjunto completo
- **AND** no se le induce a pensar que hay más fuera de lo presentado

#### Scenario: La parcialidad no se presenta como totalidad

- **GIVEN** un conjunto presentado parcialmente
- **WHEN** la persona observa el listado
- **THEN** la presentación no afirma que ese sea el total
- **AND** no confunde «parte» con «todo»
