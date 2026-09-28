# SyRS — System Requirements Specification
## IntPlant Network — Frontend móvil

| | |
|---|---|
| **Documento** | SyRS — Requisitos de sistema |
| **Nivel** | 3 de 7 — System Requirements |
| **Estándar** | ISO/IEC/IEEE 29148 |
| **Versión** | 0.4 — 33 requisitos, sin secciones bloqueadas |
| **Fecha** | 2026-09-28 |
| **Deriva de** | `StRS.md` v0.1 (aprobado) |
| **Registro de decisiones** | `BITÁCORA.md` — E-016, E-019 |
| **Próximo nivel** | SRS → `openspec/specs/` |

> **Directriz vigente (E-018.m):** documentos lean, conjunto base de requisitos,
> crecimiento por iteración. Las iteraciones futuras agregan requisitos a este mismo
> documento.

---

## 1. Sujeto de este documento

**El sistema de este proyecto es únicamente el frontend móvil.** Todo lo demás —backend,
plataforma de gestión, hardware de las macetas— es **caja negra abstraída tras la API
GraphQL** (E-016).

Consecuencia: este documento describe lo que debe hacer **un solo sistema**. No hay
asignación de responsabilidades entre sistemas porque no hay varios sistemas que
repartir.

**Identificador del sistema:** `FRONTEND` (frontend móvil de IntPlant Network).

---

## 2. Restricciones del sistema

Restricciones heredadas que condicionan los requisitos de sistema:

| ID | Restricción | Origen |
|---|---|---|
| **SYR-R01** | El sistema obtiene la totalidad de sus datos de la API. No dispone de fuente alternativa | DA-01 (E-019) |
| **SYR-R02** | El sistema **no calcula** valores de dominio. En particular, no determina el estado de una planta: lo recibe | DA-01 (E-019) |
| **SYR-R03** | El sistema no conoce ni representa el estado de desarrollo del hardware | E-014 |
| **SYR-R04** | El sistema opera bajo la regla de la plataforma de que el listado de una región es visible para cualquier usuario del sistema | DA-02 (E-019) — **ver R-008** |

**SYR-R01 y SYR-R02** materializan el principio de caja negra y responsabilidad única
(E-015, E-016). Un requisito de sistema se verifica contra una **capacidad de la API**,
nunca contra una implementación propia.

---

## 3. Requisitos de sistema

Un solo requisito de interesado se apoya en **muchos** requisitos de sistema. SR-01 no
es un requisito: es un propósito, y_SYR-03 a SYR-09 existen porque cumplirlo implica
resolver antes las condiciones en que es posible.

### 3.1 Condiciones previas para que la necesidad sea satisfacible

Ningún requisito de conocimiento es aplicable hasta que estas condiciones se cumplen.

| ID | Requisito | Por qué es condición previa | Verificable por |
|---|---|---|---|
| **SYR-01** | El sistema debe ser capaz de **establecer la identidad** de quien opera, antes de habilitar cualquier capacidad de listado | SYR-12 dice "cualquier usuario del sistema": sin identidad no hay usuario del sistema. **SYR-03 a SYR-09 son inoperantes sin SYR-01** | Existe un usuario identificado y atribuido a una cuenta |
| **SYR-02** | El sistema debe ser capaz de **determinar a qué región** corresponde la consulta | SR-01 dice "una región" sin definir cuál. Sin resolución de región no hay conjunto que listar | La región queda determinada y es consultable |
| **SYR-03** | El sistema debe ser capaz de **distinguir** las macetas unas de otras en el conjunto | SR-02…SR-06 son atributos **de cada maceta**. Sin identidad de maceta, los atributos no se pueden atribuir a nadie | Cada elemento del conjunto es distinguible |

### 3.2 Obtención de la información

| ID | Requisito | Deriva de | Verificable por |
|---|---|---|---|
| **SYR-04** | El sistema debe obtener de la plataforma el **conjunto de macetas** asociadas a una región | SR-01 | El conjunto recibido corresponde a la región resuelta (SYR-02) |
| **SYR-05** | El sistema debe obtener el **tipo de planta** de cada maceta del conjunto | SR-02 | Cada maceta tiene un tipo de planta atribuible (SYR-03) |
| **SYR-06** | El sistema debe obtener el **estado de los sensores** de cada maceta | SR-03 | Cada maceta tiene un estado de sensores atribuible |
| **SYR-07** | El sistema debe obtener el **estado de la planta** de cada maceta. **Lo recibe; no lo calcula** (SYR-R02) | SR-04 | Cada maceta tiene un estado de planta. Verificar que el sistema **no lo derivó localmente** |
| **SYR-08** | El sistema debe obtener la **ubicación** de cada maceta | SR-05 | Cada maceta tiene una ubicación atribuible |
| **SYR-09** | El sistema debe obtener los **comentarios obtenidos** por cada maceta | SR-06 | Cada maceta tiene sus comentarios atribuibles |

### 3.3 Alcanzabilidad

| ID | Requisito | Fundamento | Verificable por |
|---|---|---|---|
| **SYR-10** | El sistema debe ser capaz de **presentar a la jardinería** el conjunto de macetas de la región con los atributos de SYR-05 a SYR-09 **disponibles para su consulta** | SYR-04 a SYR-09 establecen que el sistema obtiene. SYR-10 establece que la persona puede consultarlo, que es el propósito de SR-01…SR-06 | La jardinería consulta el conjunto y sus atributos |
| **SYR-11** | El sistema debe operar **sin degradar** cuando el conjunto de una región está vacío, sin tratar la ausencia de macetas como un fallo | **RN-03**: en un lanzamiento por cluster, el conjunto vacío es el **estado inicial de la base**, no una excepción | Con cero macetas en la región, el sistema opera normalmente |

### 3.4 Reglas de acceso

| ID | Requisito | Estado | Verificable por |
|---|---|---|---|
| **SYR-12** | El sistema debe operar bajo una **regla de acceso vigente y ajustable** (SYR-19, SYR-31), cuya redacción inicial es la establecida por la plataforma: el listado de una región es visible para cualquier usuario del sistema | **Vigente. Solo el valor por defecto depende de R-008 / Q-026** | Con un usuario habilitado, el listado es accesible conforme a la regla vigente |

### 3.5 Atribución de la información al destinatario

| ID | Requisito | Fundamento | Verificable por |
|---|---|---|---|
| **SYR-13** | El sistema debe ser capaz de **distinguir las macetas propias** de las demás dentro del conjunto consultado | Sin esta distinción no es posible el tabler personal (capacidad 3 de E-003) y no se puede uphold el duty de cuidado sobre lo propio (OB-6) | Las macetas propias son identificables dentro del conjunto |

**Nota de redacción (E-015):** los requisitos se verifican contra **capacidades de la
interfaz**, no contra una implementación. Ninguno nombra tecnología, query, tipo ni
formato. La estructura concreta se define en el nivel SRS.

### 3.6 Análisis de la derivación (v0.2) — reemplazado por §3.14

> **⚠ Esta sección se conserva como registro histórico y está desactualizada a propósito.**
> Describe la derivación de v0.2 (13 requisitos). El análisis vigente, con los 33
> requisitos de v0.4, está en **§3.14**. No usarla para trazar.

El hallazgo de fondo de v0.2 sigue siendo válido: **SR-01 no es un requisito, es un
propósito.** Un mapeo 1:1 habría producido seis requisitos que asumirían identidad
resuelta, región resuelta, macetas distinguibles y propietario conocido, cuando nada de
eso está garantizado por el StRS. Los otros cinco requisitos de v0.2 —SYR-01, SYR-02,
SYR-03, SYR-11, SYR-12— no derivan de ningún SR porque son **condiciones de
posibilidad**: el StRS describe lo que la persona quiere, no lo que tiene que existir
para que sea posible.

**Lo que v0.4 añadió a este hallazgo:** que un cociente bajo no siempre es un defecto.
13 = 6 × 2 ya era un cociente suspiciousamente alto, y 33 = 6 × 5,5 no es mejor: el
crecimiento vino de **categorías vacías**, no de descomponer mejor el mismo propósito.
Descomponer SR-01 no iba a producir un requisito de seguridad. Ese error de método se
registró en E-023.

### 3.7 Requisitos de interfaz

**Corrección de DD-01 (E-023):** se diferenció la API al SRS porque un requisito de
sistema se verifica contra una capacidad, no contra la forma. Correcto para queries,
campos y tipos. **Incorrecto para el requisito de interfaz mismo**, que sí es de sistema:
que el sistema se comunique con la plataforma por la interfaz publicada es una
propiedad del sistema, verificable sin conocer la estructura.

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-14** | El sistema shall ser capaz de comunicarse con la plataforma **exclusivamente a través de la interfaz publicada por la plataforma** | SYR-R01 | El sistema no requiere otro canal para obtener datos |
| **SYR-15** | El sistema shall ser capaz de operar **sin requerir conocimiento del estado interno de la plataforma ni de las macetas** | E-016, caja negra | El sistema funciona sin información sobre implementación ajena |
| **SYR-16** | El sistema shall ser capaz de operar contra **la interfaz de plataforma correspondiente al entorno** en que se ejecuta | Verificación por entorno | El sistema opera contra la interfaz de su entorno |

**Lo que sigue diferido (DD-01 reformulado):** queries, campos, tipos, paginación y
esquema. Van al SRS. Lo que **no** se difiere es la existencia del requisito de
interfaz, que es de este nivel.

### 3.8 Requisitos de seguridad

Derivados de RN-04, R-008 y SYR-01. **No inventan mecanismos**: el mecanismo es diseño.

**Reformulados en E-026:** la respuesta del usuario a Q-026 fue *"todo debe ser
configurable"*. Eso no elimina el requisito, lo **convierte**: SYR-19 ya no dice "proteger
conforme a la norma" — dice "proteger conforme a una **regla ajustable**". Lo que queda
abierto no es *si* hay una regla, sino **cuál es su valor por defecto**, y eso sí puede
diferirse (R-008).

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-17** | El sistema shall poder establecer la identidad del usuario **antes** de habilitar cualquier capacidad protegida | SYR-01, DA-02 | Sin identidad establecida, ninguna capacidad protegida opera |
| **SYR-18** | El sistema **no shall** exponer credenciales de la plataforma en el cliente | DA-01, caja negra | El cliente no contiene secretos de la plataforma |
| **SYR-19** | El sistema shall proteger la información de la región conforme a una **regla de acceso vigente y ajustable** (SYR-31), cuya redacción inicial es la de DA-02 y **puede endurecerse sin nuevo desarrollo** | R-008, Q-026 → **E-026** | La regla se puede restringir sin modificar el sistema, y el acceso se ajusta a la regla vigente |
| **SYR-20** | El sistema **no shall** almacenar ni transmitir credenciales de la plataforma de forma recuperable por terceros | SYR-18 | Las credenciales no son recuperables del dispositivo |

### 3.9 Requisitos de desempeño, escalabilidad y capacidad

> **⚠ Sección reformulada en E-026.** v0.3 la dejaba vacía y pedía al usuario el volumen
> de macetas por región. **Ese planteamiento era incorrecto.** El volumen no es un dato
> que el interesado deba proporcionar: es un parámetro de ingeniería. La respuesta
> correcta a *"no sé cuántas macetas habrá"* no es *pregúntame el número* — es **el diseño
> no depende de conocerlo**.
>
> Por eso estos requisitos **no contienen un número de volumen**, y aun así son
> falsables: se verifican en el punto donde el volumen deja de importar, no en un
> umbral supuesto.

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-27** | El sistema shall obtener y presentar el conjunto de una región **sin requerir un límite superior conocido de antemano** | E-026, RS-01 | El sistema opera con el volumen que se le presente, sin fallar ni truncar |
| **SYR-28** | El sistema shall **fraccionar** la obtención y la presentación del conjunto cuando su volumen lo requiera, en lugar de asumir que cabe íntegro | E-026, SYR-10 | Con volumen alto, el sistema obtiene y presenta por fracciones, y el conjunto **completo** sigue siendo alcanzable |
| **SYR-29** | El sistema shall **filtrar** el conjunto por los criterios que la plataforma exponga, sin que la capacidad de filtrado dependa de un volumen acotado | E-026, SR-01 | El filtrado opera sobre el conjunto recibido, con independencia de su tamaño |
| **SYR-30** | El sistema shall distinguir **conjunto parcial** de **conjunto completo** | SYR-21, SYR-28 | Con fraccionamiento activo, la persona puede saber que está viendo una parte y no el total |

**⚠ SYR-27 no es una promesa de infinitud, y no se escribe como si lo fuera.** Todo
sistema tiene un techo técnico real. Lo que se exige aquí es que el techo **no obligue a
cambiar de diseño**, y que el techo existente **se mida y se declare** en el plan de
pruebas. Un requisito de escalabilidad que no dice dónde está el límite no dice nada.

**Alcance de la verificación.** El volumen concreto con el que se ejercitan SYR-27 a
SYR-30 lo fija el **plan de pruebas** (ISO 29119) al diseñarse, y se declara ahí.
**No es un requisito pendiente: es una decisión de ingeniería de quien desarrolla, y por
eso no se pregunta a nivel de sistema.**

**Eliminados los cinco parámetros que v0.3 pedía.** DP-03 se apoyaba en una suposición
inventada y se retiró en E-024. DP-01, DP-02, DP-04 y DP-05 se retiraron en **E-026**,
porque trataban el volumen como un dato de negocio cuando es una decisión de ingeniería.
**El volumen se absorbe como requisito de escalabilidad, no como pregunta.**

### 3.10 Requisitos de disponibilidad y tolerancia a fallos

Estos sí se derivan de decisiones ya tomadas.

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-21** | El sistema shall distinguir **la imposibilidad de obtener datos** de la **ausencia de macetas** en la región | R-001, SYR-11 | Ante fallo de la plataforma, el sistema no presenta el fallo como "no hay macetas" |
| **SYR-22** | El sistema shall indicar la **antigüedad de la información** presentada cuando la plataforma la proporcione | OB-6, RN-01.b | La información presentada indica cuándo corresponde |

**SYR-21 es un requisito de seguridad funcional, no de usabilidad.** Si la plataforma
falla y el sistema muestra el conjunto vacío, la jardinería ve *"no hay macetas en tu
región"* cuando la verdad es *"no se pudo consultar"*. Convierte un fallo en un dato
falso. Con un lanzamiento por cluster y macetas que cambian de estado, esa distinción
miente sobre la realidad del barrio.

**SYR-22 se deriva de OB-6.** Una lectura de humedad de hace tres horas, presentada como
actual, puede llevar a una decisión de cuidado equivocada. Es el mismo principio que
RN-01.b aplica a los actuadores, aplicado a la información.

### 3.11 Requisitos de recursos y hardware

> **⚠ Distinción obligatoria (E-023):** "hardware" significa aquí **el dispositivo del
> usuario**, no la maceta inteligente. El hardware de las macetas está explícitamente
> fuera: *"al frontend no le corresponde el estado de desarrollo de las macetas"*
> (E-014, SYR-R03). Confundir ambos es un error de nivel.

> **⚠ Sección completada en E-026.** v0.3 la declaraba pendiente por Q-028. **Eso también
> era una pregunta mal dirigida:** la versión mínima soportada es una **decisión de
> ingeniería** — se declara, no se consulta. Se consultaba porque no se había cerrado la
> pregunta de *quién decide*, y la respuesta correcta es: quien desarrolla.

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-23** | El sistema shall operar en **todos los dispositivos que se declaren como objetivo**, y **declarar explícitamente** esa versión mínima en lugar de aceptarlos en silencio | E-026, SYR-31 | El alcance soportado está declarado; lo no declarado no se considera soportado |
| **SYR-24** | El sistema shall hacer uso de la **capacidad de ubicación del dispositivo** si la resolución de región (DD-06) la requiere | **CONDICIONADO** a DD-06 | Si DD-06 requiere ubicación, el sistema la usa |

**SYR-23 no nombra una versión, y eso es deliberado.** Nombrarla aquí sería una decisión
de diseño (§3.11 es sistema, y la versión mínima pertenece a la declaración de soporte
del SRS/ARQ). Lo que el nivel de sistema exige es que **el alcance esté declarado**: un
sistema que no declara su mínimo no sabe si soporta lo que la gente tiene en la mano.

**SYR-24 queda condicionado a propósito:** si la región se resuelve por pertenencia o
selección, el sistema **no** necesita GPS del dispositivo, y afirmar lo contrario sería
un requisito falso. La capacidad de ubicación del teléfono y la ubicación de la maceta
(SR-05) son cosas distintas: la primera es del dispositivo, la segunda es un dato que
entrega la plataforma.

### 3.12 Requisitos de usabilidad

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-25** | El sistema shall poder ser operado por la jardinería **sin instrucción previa** | OB-5 | La persona alcanza el listado sin documentación |
| **SYR-26** | El sistema shall presentar el conjunto de la región de forma **navegable**, sin requerir que la persona construya la consulta | SR-01 | La persona obtiene la información sin escalar filtros |

**Nota de disciplina (RN-01):** estos requisitos se verifican contra usabilidad, y la
misión es el objetivo de primer nivel. Un requisito de usabilidad que sirva a OB-2
—fricción intencional hacia la compra— **es inválido por RN-01**, aunque sea más
eficiente comercialmente.

### 3.13 Requisitos de mantenibilidad y configurabilidad

> **Origen:** instrucción explícita del usuario en E-026 — *"todo debe ser configurable,
> el sistema debe ser escalable y fácil de mantener"*. ISO 25010 los clasifica en
> **Maintainability**. En ISO 29148 son **requisitos de sistema**, no de diseño: el
> sistema debe ser mantenible, y eso es verificable desde fuera.

| ID | Requisito | Derivación | Verificable por |
|---|---|---|---|
| **SYR-31** | El sistema shall permitir **ajustar la regla de acceso** a la región (SYR-12, SYR-19) **sin requerir modificación de su código** | E-026, R-008 | La regla puede endurecerse sin un nuevo ciclo de desarrollo |
| **SYR-32** | Las decisiones de comportamiento del sistema —**regla de acceso, resolución de región, criterios de filtrado**— shall ser **separadas entre sí y ajustables de forma independiente**, no agrupadas en un único control | E-026 | Cambiar una decisión no altera las otras dos |
| **SYR-33** | El sistema shall **declarar explícitamente** su alcance de soporte (versión mínima de plataforma) y su comportamiento ante condiciones no soportadas | SYR-23, E-026 | El alcance está declarado y es verificable |

**⚠ Límite de "configurable" — por qué esto no se escribe "todo debe ser configurable".**
Un requisito de configurabilidad sin **enumerar qué se configura** es vacío: no falla
nada, no se puede probar, y en la práctica significa "lo que haga falta". Por eso
SYR-31 y SYR-32 nombran los puntos de ajuste concretos.

**Lo que estos requisitos no dicen:** el *mecanismo* de configuración — archivo, variable
de entorno, servidor de flags— es diseño (DD-07). Y **añadir un parámetro no crea un
requisito**: un parámetro nuevo que nadie pidió no está cubierto por SYR-31.

### 3.14 Análisis de la derivación (actualizado)

```
   SR-01 "conocer el listado de macetas de una region"
     |
     +-> SYR-02  que region?              <- la pregunta queda sin respuesta
     +-> SYR-04  obtener el conjunto
     +-> SYR-03  distinguir las macetas   <- sin esto los atributos no tienen dueno
     +-> SYR-10  poder consultarlo
     +-> SYR-11  poder estar vacio        <- RN-03
     +-> SYR-12  quien puede verlo        <- DA-02, condicionado a R-008
     +-> SYR-13  cuales son las mias
     |
   SR-02 "tipo de planta"  -> SYR-05 (y SYR-03)
   SR-03 "sensores"        -> SYR-06
   SR-04 "estado planta"   -> SYR-07 (recibido, no calculado)
   SR-05 "ubicacion"       -> SYR-08
   SR-06 "comentarios"     -> SYR-09

   + condiciones de posibilidad, derivadas de reglas heredadas:
   SYR-01  identidad ................ DA-02, SYR-17
   SYR-14  interfaz ................ SYR-R01
   SYR-15  sin conocimiento interno  E-016
   SYR-18  sin credenciales en cliente DA-01
   SYR-21  distinguir fallo de vacio  R-001
   SYR-22  antiguedad de la informacion OB-6

   + atributos no funcionales exigidos por el usuario (E-026):
   SYR-23  declarar alcance de soporte ..... §3.11
   SYR-25  operable sin instruccion ....... §3.12  OB-5
   SYR-26  navegable sin escalar filtros .. §3.12
   SYR-27  sin limite superior conocido ... §3.9   escalabilidad
   SYR-28  fraccionar el conjunto ......... §3.9
   SYR-29  filtrar el conjunto ............ §3.9
   SYR-30  parcial != completo ............ §3.9   SYR-21
   SYR-31  regla de acceso ajustable ...... §3.13  R-008
   SYR-32  decisiones separadas ........... §3.13
   SYR-33  alcance declarado .............. §3.13  SYR-23

   + identidad ya existia como SYR-01 (E-024, cierra Q-029).
     "solo usuarios registrados pueden consultar" NO es una necesidad
     nueva: es la condicion que SYR-01 ya exigia.

   6 requisitos de interesado  ->  33 requisitos de sistema
```

**⚠ El cociente es la señal de la omisión.** Un mapa 1:1 dio seis requisitos, la primera
corrección dio trece, la corrección por categoría dio veintiséis, y la exigencia de
escalabilidad y mantenibilidad da **treinta y tres**. El crecimiento no es arbitrario:
cada categoría vacía de ISO 29148 contiene requisitos que no se ven mirando los
funcionales, porque responden a otros criterios.

**Q-029 cerrada (E-024).** *"Consultar usuarios registrados"* **no es una necesidad
nueva** y nunca fue un requisito faltante: es la condición que **SYR-01 ya exigía**. Sin
identidad establecida no hay "usuario del sistema", y SYR-12 dice "cualquier usuario del
sistema". La conclusión correcta era *"hay un SYR y falta un SR"*, no *"no hay SR, luego
no hay SYR"*. **Nada entra por el StRS en esta iteración.**

Detalle complementario de **§3.3** (alcanzabilidad), **§3.4** (acceso) y **§3.5**
(atribución).

SYR-10 no especifica la forma de la presentación —pantalla, mapa, listado— porque esa
decisión es de diseño (SRS/ARQ), no de sistema.

**⚠ SYR-12 sigue condicionado a R-008, pero ya no bloquea nada.** El listado de una
región con ubicación constituye un conjunto de datos personales. RN-04 establece que las
obligaciones de protección aplican desde la primera cohorte. La regla "cualquier usuario
del sistema" **puede no ser compatible** con la normativa del cluster (Q-026).

**Lo que cambió en E-026:** la respuesta del usuario fue *"todo debe ser configurable"*.
Eso **no cierra** la cuestión legal, pero la **desacopla del sistema**: con SYR-31 la
regla puede endurecerse sin tocar código. Lo que queda abierto es **el valor por
defecto**, y eso es un dato de entrada, no un requisito ausente.

**Si la conclusión fuera restrictiva**, SYR-12 y SYR-19 cambiarían de redacción —nada
más—, y **SR-01 no se vería afectado**: la necesidad de la
jardinería no depende de la regla de acceso (E-019).

---

## 4. Riesgos y puertas de calidad

| ID | Riesgo | Puerta de calidad ISO 25010 | Requisito afectado |
|---|---|---|---|
| **R-008** | El **valor por defecto** de la regla de acceso puede ser incompatible con la normativa del cluster. **Mitigado en E-026:** la regla es ajustable (SYR-31), así querestrictir no requiere desarrollo. Queda abierto solo el valor inicial | **Security** | SYR-12, SYR-19, SYR-31 |
| **R-009** | Escalabilidad declarativa sin techo declarado: un requisito de escalabilidad sin límite medido se cumple siempre y no dice nada | **Scalability** | SYR-27 (techo a medir y declarar en el plan de pruebas) |
| **R-001** | El conjunto de una región puede estar vacío en el arranque en frío | **Usability** | **SYR-11**, RN-03 |
| **R-005** | El alcance de esta iteración sustituye a los "tableros de control" de E-003 | — | SYR-13 |
| **R-006** | "Estado de la planta" es un juicio, no un dato | **Correctness** | **SYR-07** — verificable comprobando que el sistema **no lo derivó localmente** |
| **R-007** | La misión (OB-5, OB-7) no se materializa en esta iteración | — | Deuda declarada |

**Nota sobre R-001 y RN-03:** en un lanzamiento por cluster, el conjunto de una región
puede no tener macetas. El sistema debe poder operar en ese estado sin fallar, y sin
tratar la ausencia como un error. Es **SYR-11**, un requisito de usabilidad heredado de
RN-03, no una excepción.

**Nota sobre R-004 (seguridad funcional en actuadores):** los requisitos de esta
iteración **no incluyen control de actuadores**. Ese riesgo permanece abierto y se
atenderá cuando el control de actuadores entre en alcance. Ver `StRS.md` §4.2.

---

## 5. Elementos de diseño explícitamente diferidos

Decisiones que **no** se toman en este nivel y que fueron consultadas en este ciclo:

| ID | Decisión diferida | Nivel destino | Motivo |
|---|---|---|---|
| **DD-01** | Esquema concreto de la API: queries, campos, tipos, paginación | **SRS** | Diferido por el usuario en E-019: *"ya veremos la estructura más adelante"*. **Reformulado en E-023:** el *requisito* de interfaz (SYR-14…16) es de este nivel; solo el *esquema* se difiere |
| **DD-02** | Tecnología de la interfaz: Expo/React Native, PWA, otra | **ARQ** | Decisión de arquitectura. El criterio diferido (E-008) es si el sistema debe operar con el teléfono bloqueado o en segundo plano |
| **DD-03** | Forma de presentación del listado: pantalla, mapa, ambos | **SRS / ARQ** | Es decisión de diseño, no de sistema |
| **DD-04** | Mecanismo de obtención de datos: una consulta o múltiples | **SRS / ARQ** | Optimización, no requisito de sistema |
| **DD-05** | Cómo se establece y se mantiene la identidad (SYR-01) | **SRS** | SYR-01 declara la **capacidad** de establecer identidad; el mecanismo es diseño |
| **DD-06** | Cómo se resuelve la región (SYR-02): por ubicación, por selección, por pertenencia | **SRS / ARQ** | SYR-02 declara que la región **se determina**; el criterio es decisión de negocio y diseño |
| **DD-07** | Mecanismo de configuración de SYR-31 y SYR-32: archivo, variable de entorno, servidor de flags, build | **SRS / ARQ** | Añadido en E-026. Los requisitos de mantenibilidad declaran **qué** se ajusta y que sea separable; el **cómo** es diseño |
| **DD-08** | Versión mínima de plataforma soportada y volumen con el que se verifica SYR-27 | **ARQ / PLAN DE PRUEBAS** | Añadido en E-026. SYR-23 y SYR-27 exigen que el alcance **sea declarado**, no que se declare aquí. Es decisión de ingeniería, no un requisito abierto |

**DD-04** se registra porque es tentador resolverlo en este nivel: "consultar el listado
en una sola llamada" parece un requisito de sistema. No lo es. Es una decisión de
implementación, y su definición correcta es *"el sistema debe obtener la información
necesaria de forma consistente"*, sin fijar el mecanismo.

---

## 6. Trazabilidad

### 6.1 StRS → SyRS — matriz de derivación

La relación es **muchos a uno**, no uno a uno. Cada SR se apoya en varios SYR.

| StRS | Requisitos de sistema que lo satisfacen | Verificación declarada |
|---|---|---|
| **SR-01** listado de macetas de la región | **SYR-02** (qué región), **SYR-03** (distinguir macetas), **SYR-04** (obtener conjunto), **SYR-10** (consultarlo), **SYR-11** (conjunto vacío), **SYR-13** (cuáles son propias) | El conjunto recibido corresponde a la región resuelta; cada elemento es distinguible; la jardinería puede consultarlo |
| **SR-02** tipo de planta | SYR-05 (+ SYR-03) | Cada maceta tiene un tipo atribuible |
| **SR-03** estado de los sensores | SYR-06 (+ SYR-03) | Cada maceta tiene estado atribuible |
| **SR-04** estado de la planta | **SYR-07** (+ SYR-03) | Estado presente; el sistema **no lo derivó localmente** |
| **SR-05** ubicación | SYR-08 (+ SYR-03) | Cada maceta tiene ubicación atribuible |
| **SR-06** comentarios obtenidos | SYR-09 (+ SYR-03) | Cada maceta tiene comentarios atribuibles |
| *(los seis: que la necesidad sea satisfacible)* | **SYR-01** (identidad) | Existe identidad establecida y atribuida a un usuario |
| *(ninguno — regla de plataforma)* | **SYR-12** (acceso), **SYR-19** (regla ajustable) | **Valor por defecto condicionado a R-008** |
| *(ninguno — instruido por el usuario, E-026)* | **SYR-23** (alcance declarado), **SYR-25, SYR-26** (usabilidad), **SYR-27…SYR-30** (escalabilidad), **SYR-31…SYR-33** (mantenibilidad) | Atributos no funcionales exigidos explícitamente |

**Cobertura:** los 6 SR están cubiertos, y **ningún SYR queda sin origen declarado**.
Reparto **disjunto** — la suma da exactamente 33, sin solapamientos:

| # | Origen | SYR | n |
|---|---|---|---|
| 1 | **Derivados de un SR** | 04, 05, 06, 07, 08, 09, 13 | 7 |
| 2 | **Condiciones de posibilidad** (RN-03, DA-02) | 01, 02, 03, 10, 11, 12 | 6 |
| 3 | **Interfaz y caja negra** (E-015, E-016) | 14, 15, 16, 18, 20 | 5 |
| 4 | **Seguridad** (RN-04, SYR-01) | 17, 19 | 2 |
| 5 | **Disponibilidad** (R-001, OB-6) | 21, 22 | 2 |
| 6 | **Usabilidad** (OB-5, RN-01) | 25, 26 | 2 |
| 7 | **Escalabilidad** (E-026, RS-01) | 27, 28, 29, 30 | 4 |
| 8 | **Alcance y mantenibilidad** (E-026) | 23, 24, 31, 32, 33 | 5 |
| | | **Total** | **33** |

**Nota sobre SYR-24:** se cuenta en el grupo 8 aunque esté condicionado a DD-06. Ocupa
lugar en el documento porque su *condición* es de sistema; lo que se difiere es si la
condición se cumple, no si el requisito existe. Si DD-06 se resuelve sin ubicación, SYR-24
se retira y el total baja a 32.

**Nota sobre los grupos 6, 7 y 8:** ninguno proviene de un SR, y **eso está bien**. Un
atributo no funcional no necesita que un interesado lo pida para ser requisito: SYR-25
existe porque el sistema debe poder usarse sin instrucción previa, no porque alguien lo
solicitara. La pregunta que sí importa —y que el §1 impide saltarse— es si introducir
un requisito de este tipo sirve a la misión. Ver §6.3.

**⚠ Trazabilidad incompleta declarada:** la capacidad 3 de E-003 ("tableros de control
con estado de planta") está representada acá solo por **SYR-13** (distinguir las macetas
propias). El tabler en sí —presentar el estado de las macetas propias de forma continua—
**no tiene requisito de sistema en esta iteración**, porque no existe un SR que lo pida.
Ver `StRS.md` §4.2 y la nota de consistencia de abajo.

### 6.2 Hacia adelante

```
   SyRS (este documento)
     -> SRS    requisitos de software   -> openspec/specs/
     -> ARQ    arquitectura (ISO 42010, un solo sistema)
     -> COD    implementación
     -> TEST   verificación (ISO 29119)
```

A partir del nivel SRS, el trabajo se organiza en **rebanadas verticales** manejadas
como changes de OpenSpec (E-017).

### 6.3 Hacia atrás, hacia el BRS

| SyRS | Objetivo de negocio | Regla / restricción heredada |
|---|---|---|
| SYR-01 (identidad) | OB-6 (custodio responsable) | DA-02, SYR-R04 |
| SYR-02, SYR-04, SYR-08 | OB-3, OB-7 | RS-01, RS-02, RN-02 |
| SYR-03 (distinguir) | OB-6 | — |
| SYR-05, SYR-06, SYR-07 | **OB-6** | RN-03, RN-01.b, R-006 |
| SYR-09 | OB-7 | — |
| SYR-10 (consultar) | OB-3 | RN-03 |
| SYR-11 (conjunto vacío) | OB-7, OB-3 | **RN-03**, R-001 |
| SYR-12 (acceso) | OB-6, OB-3 | **RN-04**, R-008 |
| SYR-13 (macetas propias) | **OB-6** | R-005 |
| SYR-19 (regla ajustable) | OB-6 | **RN-04**, R-008 |
| SYR-21, SYR-22 (fallo ≠ vacío; antigüedad) | **OB-6** | RN-01.b |
| SYR-25, SYR-26 (usabilidad) | **OB-5, OB-7** | **RN-01** |
| SYR-27…SYR-30 (escalabilidad) | OB-3 | RS-01, R-009 |
| SYR-31…SYR-33 (mantenibilidad) | OB-3 | E-026 |

**⚠ RN-01 aplicado a la mantenibilidad.** Un sistema mantenible y configurable
facilita la evolución. Facilitar OB-1 o OB-2 mediante fricción intencional **no** es un
requisito de mantenibilidad: es un objetivo comercial disfrazado de atributo técnico, y
RN-01 lo subordina. **SYR-31…SYR-33 existen para que el sistema sea sostenible, no para
que venda más.**

---

## 7. Control de cambios

| Versión | Fecha | Cambio | Origen |
|---|---|---|---|
| 0.1 | 2026-09-28 | Emitido para revisión. Derivación **1:1** — insuficiente | E-016, E-019, E-020 |
| 0.2 | 2026-09-28 | **Derivación completa: 8 → 13 requisitos.** Se descompone SR-01 en sus condiciones de posibilidad; se incorpora SYR-13 | E-022 |
| 0.3 | 2026-09-28 | **Clasificación por categoría ISO 29148: 13 → 26 requisitos.** Se agregan interfaz (§3.7), seguridad (§3.8), disponibilidad (§3.10), hardware (§3.11), usabilidad (§3.12). §3.9 desempeño queda vacía, sin datos. DD-01 se reformula: el requisito de interfaz no se difiere | E-023 |
| 0.4 | 2026-09-28 | **26 → 33 requisitos. §3.9 deja de estar bloqueada y §3.11 se completa.** El volumen de macetas **no se pregunta**: se absorbe como requisito de escalabilidad (SYR-27…30). Se agrega mantenibilidad y configurabilidad (SYR-31…33) por instrucción explícita del usuario. SYR-19 pasa de "conforme a la norma" a "conforme a una **regla ajustable**". R-008 baja de bloqueante a **valor por defecto pendiente**. Cierra Q-026, Q-027, Q-028 | **E-026** |

**Iteraciones futuras:** agregan requisitos a §3 y actualizan esta tabla.
