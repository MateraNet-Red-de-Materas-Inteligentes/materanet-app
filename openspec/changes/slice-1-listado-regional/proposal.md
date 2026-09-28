# Proposal

## Why

El proyecto no tiene ningún software. La plataforma (API, gestión, macetas) es externa y
está detrás de una caja negra; el frontend es lo único que se construye acá. Hoy no hay
**ni un solo requisito de software escrito**, y sin él no hay nada que implementar,
probar ni archivar.

La primera rebanada vertical ataca el supuesto más frágil del proyecto entero: **que la
plataforma sirva efectivamente el conjunto de macetas de una región** (SA-01). Si eso no
es cierto, el proyecto no tiene objeto y todo lo demás esNullable. La forma más barata de
saberlo es construir el camino completo más corto: entrar, ver la región, ver las macetas.

No es una rebanada de funcionalidad. Es la rebanada que **descarta el riesgo de SA-01** y
establece el esqueleto de verificación (ISO 29119) sobre el que crecerán las demás.

## What Changes

- Se crea el primer conjunto de **requisitos de software** del proyecto: 33 requisitos
  SWR derivados de los 33 requisitos de sistema de `SyRS.md` v0.4, organizados en 4
  capacidades.
- Se define el **contrato de interfaz** con la plataforma: qué capacidades debe exponer la
  API para que el frontend pueda satisfacer SYR-01…SYR-13. La estructura concreta (queries,
  campos, tipos, paginación) se fija en `design.md`, que es donde vive DD-01.
- Se resuelven **tres decisiones diferidas** que el SyRS había enviado a este nivel y que
  ya no pueden diferirse más: DD-01 (esquema de la API), DD-05 (mecanismo de identidad) y
  DD-06 (criterio de resolución de región).
- Se declara explícitamente **qué no está en la rebanada**: control de actuadores,
  alertas, publicación, comentarios propios, y todo lo que E-003 llamaba "tableros de
  control" (R-005).
- No hay cambios de comportamiento sobre software existente: **es la primera rebanada**.
  No existen capacidades modificadas.

## Capabilities

### New Capabilities

- `identidad`: establecer la identidad de quien opera antes de habilitar cualquier
  capacidad protegida, y proteger las credenciales de la plataforma en el cliente.
  Cubre SYR-01, SYR-17, SYR-18, SYR-20.
- `listado-regional`: determinar la región, obtener el conjunto de macetas, presentarlo,
  y sostenerlo en los tres estados que RN-03 hace obligatorios —conjunto vacío, fallo de
  obtención, conjunto parcial—. Incluye la distinción de macetas propias y la escalabilidad
  del conjunto. Cubre SYR-02, 03, 04, 10, 11, 13, 21, 25, 26, 27, 28, 29, 30.
- `atributos-maceta`: obtener y presentar los atributos de cada maceta —tipo de planta,
  estado de sensores, estado de planta, ubicación, comentarios obtenidos— e indicar la
  antigüedad de la información. Cubre SYR-05, 06, 07, 08, 09, 22.
- `reglas-y-alcance`: la regla de acceso a la región como parámetro ajustable, el contrato
  de interfaz con la plataforma, y la declaración explícita del alcance de soporte.
  Cubre SYR-12, 14, 15, 16, 19, 23, 24, 31, 32, 33.

**Cobertura:** las cuatro capacidades cubren **33 de 33 requisitos de sistema** de
`SyRS.md` v0.4. Ninguno queda sin requisito de software.

Los 33 SYR se expresan como **29 requisitos de software** porque varios fusionan dos SYR
cuando forman un único comportamiento verificable. La fusión no pierde trazabilidad: cada
requisito de software declara de qué SYR deriva.

| Fusión | Requisito de software | SYR |
|---|---|---|
| 1 | Identidad establecida antes de la capacidad protegida | SYR-01 + SYR-17 |
| 2 | Credenciales de la plataforma ausentes del cliente | SYR-18 + SYR-20 |
| 3 | Regla de acceso a la región vigente y ajustable | SYR-12 + SYR-19 |
| 4 | Alcance de soporte declarado | SYR-23 + SYR-33 |
| — | Los otros 25 SWR | 1 SYR cada uno |

**Sobre SYR-24** (capacidad de ubicación del dispositivo): aparece en
`atributos-maceta` solo como **requisito de no confusedión** — la ubicación del teléfono
nunca se presenta como la ubicación de una maceta. **La obligación de usar el GPS del
dispositivo sigue condicionada a DD-06** y no se activa en esta rebanada. Si DD-06 se
resuelve sin ubicación, ese fragmento se retira y el total baja a 28.

### Modified Capabilities

Ninguna. `openspec/specs/` está vacío: no hay software previo cuyo comportamiento cambie.

## Impact

**Sistemas afectados:** ninguno fuera de este repositorio. La plataforma es una caja
negra (E-016) y no se modifica.

**Dependencia crítica:** la plataforma debe exponer un conjunto de macetas por región con
los atributos de SR-02…SR-06. **Si no lo expone, esta rebanada no es implementable** y el
proyecto se detiene en SA-01. La forma más barata de comprobarlo es una consulta real
contra la plataforma, y esa comprobación va **antes** de construir interfaz.

**Datos personales:** el listado de una región con ubicación es un conjunto de datos
personales (R-008). La regla de acceso se implementa como **parámetro ajustable** (SYR-31)
con el valor por defecto de DA-02, para que endurecerla no requiera desarrollo. El valor por
defecto definitivo queda pendiente de confirmar la jurisdicción del cluster.

**Restricciones heredadas que condicionan el diseño:** el frontend **no calcula** el estado
de una planta, lo recibe (SYR-R02, DA-01); **no expone credenciales** de la plataforma en
el cliente (SYR-18, SYR-20); **no conoce** el estado de desarrollo de las macetas
(SYR-R03).

**Riesgo que esta rebanada no retira:** R-007. La misión (OB-5, OB-7) sigue sin
materializarse: un listado de plantas en el territorio no es todavía una red social. La
deuda es explícita y ya estaba declarada en el StRS; esta rebanada no la empeora, pero no
la salda.
