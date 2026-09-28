# Spec Delta

## Purpose

Permite que la persona que opera el sistema sea reconocida antes de habilitarse
cualquier capacidad protegida, y que las credenciales de la plataforma nunca queden
expuestas en el dispositivo.

## ADDED Requirements

### Requirement: Identidad establecida antes de la capacidad protegida
El sistema MUST establecer la identidad de quien opera ANTES de habilitar cualquier
capacidad de listado. Ninguna capacidad protegida MUST operar sin identidad establecida.

_Derivado de SYR-01, SYR-17. Verificable: sin identidad establecida, ninguna capacidad
protegida responde._

#### Scenario: Consulta con identidad válida
- **WHEN** una persona con identidad establecida consulta el listado de su región
- **THEN** el sistema responde con el conjunto de macetas de esa región

#### Scenario: Consulta sin identidad
- **WHEN** se intenta una capacidad de listado sin que haya identidad establecida
- **THEN** el sistema no devuelve el conjunto y comunica que es necesario identificarse

### Requirement: Credenciales de la plataforma ausentes del cliente
El sistema MUST NOT exponer credenciales de la plataforma en el cliente. El cliente
distribuido MUST NOT contener secretos de la plataforma.

_Derivado de SYR-18, SYR-20. Verificable: el paquete entregado al usuario no contiene
secretos de la plataforma._

#### Scenario: Inspección del cliente
- **WHEN** se inspecciona el artefacto que el usuario instala
- **THEN** no contiene credenciales ni secretos de la plataforma

#### Scenario: Credenciales no recuperables del dispositivo
- **WHEN** se inspecciona el almacenamiento del dispositivo después de un uso normal
- **THEN** las credenciales de la plataforma no aparecen en forma recuperable por terceros

### Requirement: Identidad atribuida a una cuenta
El sistema MUST asociar la identidad establecida a una cuenta, de modo que la
información presentada para la región sea atribuible a quien la consulta.

_Derivado de SYR-01. Verificable: existe un usuario identificado y atribuido a una cuenta._

#### Scenario: Autoría de la consulta
- **WHEN** se consulta el conjunto de una región
- **THEN** la consulta queda atribuida a la cuenta que la originó
