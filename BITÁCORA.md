# BITÁCORA — IntPlantNetworkFrontend

> Registro cronológico de decisiones, con su fundamento, para que el proceso pueda
> reproducirse sin contexto previo. Última actualización: 2026-09-28.

---

## 0. Identificación

| Campo | Valor |
|---|---|
| **Producto** | IntPlant Network — ecosistema de macetas inteligentes IoT |
| **Artefacto** | Frontend **móvil** (este repositorio) |
| Ruta | `~/Projects/IntPlantNetworkFrontend` |
| Bitácora | `BITÁCORA.md` en la raíz del repositorio |
| Esquema OpenSpec | `spec-driven` |
| OpenSpec CLI | v1.13.1 |
| Idioma de trabajo | Español (esta bitácora y la conversación) |
| Estado | **Niveles 1, 2 y 3 cerrados. Nivel 4 (SRS) emitido: change `slice-1-listado-regional` completo y validado, pendiente de implementación** |
| Artefactos emitidos | `BRS.md` v0.2 (E-017, E-021) · `StRS.md` v0.1 (E-020) — aprobados. `SyRS.md` v0.4 (E-022…E-026) — aprobado. `openspec/changes/slice-1-listado-regional/` (E-027) — 4/4 artefactos, `validate --strict` OK |
| Directriz vigente | **Documentos lean; conjunto base de requisitos; crecimiento por iteración** (E-018.m) |
| Artefacto emitido | — |

---

## 1. Reglas de esta bitácora

- Una entrada por paso. Numeración `E-0xx`, correlativa, sin reutilizar.
- Cada entrada marca explícitamente su **estatus**: `DECIDIDO`, `PROPUESTO` u `ABIERTO`.
  Nunca se registra una idea como si estuviera decidida.
- Se registran también las **alternativas descartadas** y por qué, para no reabrir
  discusiones cerradas sin motivo nuevo.
- Las preguntas pendientes viven en §5, no se entierran en el texto de las entradas.
- Se escribe antes de actuar, no después: si algo cambió, se agrega una entrada nueva.

**Control de nivel, método y suposición (aprendido en E-010, E-011, E-014, E-016, E-019, E-022, E-023, E-024):**

> **1. Identificar el sujeto** de la pregunta. Persona → StRS. Negocio → BRS.
> Sistema → SyRS. Solución → SRS.
>
> **2. Comprobar el destino.** ¿Esta información va a aparecer *escrita* en el BRS?
> Si sirve para construir pero no para el documento, es de otro nivel.
> Preguntar "¿cuándo esto estará listo?", "¿quién lo programó?" o "¿con qué equipo
> cuento?" es construir, no BRS.
>
> **3. Filtro "¿necesita saberlo la persona?"** (añadido en E-019, para StRS).
> ¿Esta información cambia lo que la persona quiere obtener, o solo describe cómo se lo
> provee o quién lo provee? Si solo describe el **cómo** o el **quién**, no es StRS:
> es decisión de diseño y va a SyRS/SRS.
>
> **Regla mnemotécnica:** el StRS habla de **qué**, nunca de **de dónde** ni de
> **quién implementa**.
>
> **4. Control de método de derivación (añadido en E-022).** El cociente importa: si un
> requisito de un nivel produce exactamente un requisito del siguiente, es que el mapeo
> no descompuso nada. Pregunta de control: **¿qué condiciones tienen que ser ciertas
> para que este requisito sea siquiera aplicable?** Cada respuesta es un requisito del
> nivel siguiente. Un requisito de nivel superior es un **propósito**; los de nivel
> inferior son **capacidades que lo hacen posible**.
>
> **5. Control de condición de posibilidad (añadido en E-024).** Antes de crear un
> requisito de nivel superior, comprobar si el nivel inferior ya lo tiene como *condición
> de posibilidad*. **"No hay SR" significa "no hay SR *y* no hay SYR"**, nunca "no hay SR
> y por eso no hay SYR". La segunda lectura es un non-sequitur: la ausencia de un SR no
> implica la ausencia de un SYR.
>
> **6. Control de suposición (añadido en E-024).** No afirmar el comportamiento de algo
> que no se ha observado. Un requisito basado en una suposición inventada es peor que un
> requisito ausente, porque se verifica contra un número falso. Si el valor es un
> supuesto de negocio, se pregunta; si es una decisión de diseño, se difiere.
>
> **7. Control de atribución de causa (añadido en E-025).** No atribuir a una
> característica del contexto una consecuencia que no se ha derivado. *"Es unipersonal"*
> es un dato de recursos (E-014); **no implica por sí mismo menor capacidad técnica**. La
> ocupación del tiempo es una restricción de planificación, no una limitación de calidad
> por parte de quien desarrolla. Si existe una limitación real, se nombra su causa
> concreta en lugar de invocar el tamaño del equipo.
>
> **8. Control de ternas (añadido en E-026).** Ante un valor desconocido, clasificar en
> **tres** ramas, no dos:
> - **Supuesto de negocio** → se pregunta al interesado.
> - **Decisión de diseño** → no se pregunta; se difiere al nivel que le corresponde.
> - **Supuesto de ingeniería** → **no se pregunta ni se difiere: se convierte en requisito
>   de no dependencia.** *El diseño no puede depender de conocer el valor.*
>
> El control 6 (E-024) solo tenía las dos primeras ramas, y por eso la tercera —que es
> la habitual en este tipo de sistema— no tenía dónde caer. **Esa laguna produjo
> Q-027 y Q-028 enteras.**

---

## 2. Enfoque de trabajo

**Modalidad:** incremental y vertical, no cascada. El proceso **baja por niveles del
modelo en V**, revisando cada escalón antes de pasar al siguiente, hasta llegar a un
producto desplegado. No se busca producir un producto completo en una sola pasada.

**Pregunta por pregunta.** El levantamiento de requisitos se hace de una
pregunta por vez. Se registra la respuesta y se avanza; no se agrupan interrogantes.

**Origen de autoridad.** El usuario define alcance y método. Cuando el método OpenSpec
y el método ISO propuestos no coinciden, el método ISO manda y OpenSpec se adapta.

---

## 3. Mapa del proceso — V-model + OpenSpec

```
   +---------+   proposicion: por que existe este cambio
   |   BRS   |   <- nivel 1, punto de partida
   +----+----+
   +----+----+   stakeholders: a quien le importa, que necesita
   |   StRS  |   <- nivel 2
   +----+----+
   +----+----+   sistema: vision, contexto, vinculos externos
   |   SyRS  |   <- equivale al "proposal" de OpenSpec
   +----+----+
   +----+----+   software: requisitos "shall" verificables
   |   SRS   |   <- acumulado en openspec/specs/
   +----+----+
   +----+----+   arquitectura (ISO 42010): vistas, decisiones
   |   ARQ   |   <- equivale a "design.md"
   +----+----+
   +----+----+
   |   COD   |   <- /opsx-apply
   +----+----+
   +----+----+   pruebas (ISO 29119)
   |  TEST   |   <- /opsx-apply
   +---------+

   tasks.md ~ plan de gestion (no es un nivel del V-model)
```

**Reparto propuesto** — pendiente de confirmación:

| Nivel | Dónde vive |
|---|---|
| BRS, StRS | Documentos de proyecto en el repo, transversales a todo change |
| SyRS | `proposal.md` del change |
| SRS | `openspec/specs/` — se acumula con cada change |
| ARQ | `design.md` del change |
| COD, TEST | `/opsx-apply` + tasks de testing derivadas del SRS |
| Trazabilidad | Matriz que enlace cada requisito con su verificación |

**Razón:** OpenSpec cubre bien desde SyRS hacia abajo, pero no tiene artefacto nativo
para BRS ni StRS, y forzarlos dentro del modelo sería una deformación.

---

## 4. Bitácora de entradas

### E-001 — Creación del repositorio
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`
Proyecto nuevo y vacío en `~/Projects/IntPlantNetworkFrontend`.
*Nota:* directorios hermanos existentes fueron deliberadamente dejados fuera de
consideración (ver E-007).

### E-002 — Inicialización de OpenSpec
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`
`openspec init --tools opencode` → esquema `spec-driven`, 6 skills y 6 comandos en
`.opencode/`. Estado inicial: 0 changes, 0 specs.

### E-003 — Definición de alcance
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Dentro de alcance:** únicamente el frontend móvil, con estas capacidades:
1. Autenticación y gestión de cuenta
2. Registro y asociación de macetas a la cuenta
3. Tableros de control con estado de planta (GPS, humedad, temperatura)
4. Control de actuadores (riego, iluminación/calefacción)
5. Historial
6. Alertas

**Fuera de alcance (sistemas externos que se consumen, no se construyen):**
- Backend
- Plataforma de gestión
- Macetas IoT: sensores (GPS, humedad, temperatura) y actuadores (riego, iluminación)
- API GraphQL

*Consecuencia:* toda la integración ocurre por GraphQL externo. Ninguna decisión de
infraestructura de datos, persistencia o backend es de este proyecto.

### E-004 — Estándares aplicados y cadena de trazabilidad
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

| Estándar | Aplica a |
|---|---|
| ISO 29148 | Requisitos — BRS, StRS, SyRS, SRS |
| ISO 42010 | Arquitectura del frontend |
| ISO 12207 | Ciclo de vida |
| ISO 29119 | Pruebas |
| ISO 32675 | Operación |
| ISO 25010 / 2502x | Calidad del producto y su medición |

**Cadena obligatoria:** `BRS → StRS → SyRS → SRS → arquitectura → código → pruebas`

### E-005 — Advertencia de seguridad funcional en actuadores
**Fecha:** 2026-09-28 · **Estatus:** `ABIERTO` (insumo para el BRS/SyRS)

Los actuadores son la única parte del alcance donde un defecto de UI tiene
consecuencia física sobre el mundo real. Casos identificados:

- doble toque accidental sobre "regar ahora" → ahoga la planta
- el cliente optimistically confirma el comando y la red cae antes del ACK → la UI
  muestra un éxito que no ocurrió
- la app vuelve del segundo plano con estado obsoleto y aplica un comando sobre un
  contexto desactualizado

Esto no es un detalle de UX. Se traduce en atributos de calidad de ISO 25010
(*Safety*, *Reliability*) y en requisitos de diseño concretos: confirmación en
acciones irreversibles, idempotencia de la mutation, rollback de UI optimista y
estado de "dato obsoleto" explícito en lugar de simular frescura.

### E-006 — Método de trabajo: incremental y vertical
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`
Definición del proyecto: arrancar en BRS, bajar por niveles **revisando cada paso**,
hasta un producto desplegado. No crear un producto completo en una sola pasada.
Criterio declarado: "empezar haciendo las cosas bien".

### E-007 — Las plataformas anteriores del usuario no aplican
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`
Los proyectos previos del usuario fueron descartados explícitamente como base de
decisión sobre stack y herramientas.

**Alcance del descarte:** solo el stack y las herramientas. La **cadena ISO** (§3)
no se descarta: fue declarada como parte del encargo.

**Consecuencia:** el stack tecnológico se determina exclusivamente a partir de los
requisitos, no por analogía con trabajo previo. No se vuelve a invocar el historial.

### E-008 — Pregunta de plataforma móvil, aplazada
**Fecha:** 2026-09-28 · **Estatus:** `ABIERTO` (reasignada de nivel)

Nativo (Expo/React Native) frente a web móvil / PWA. Es una decisión de
**arquitectura**, no de negocio: pertenece a los niveles ARQ/COD, no al BRS.

**Motivo del aplazamiento:** se estaba consultando demasiado temprano en la cadena.

**Criterio de decisión diferido:** la bifurcación la resuelve un único hecho
funcional — si la app debe operar con el teléfono bloqueado o en segundo plano
(ver Q-004, Q-005). Background tasks y push fiable solo existen de forma robusta en
nativo.

### E-009 — Modelo de negocio: red social de cuidadores
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — respuesta a Q-001

**Negocio:** plataforma comercial cuyo producto es una **red social de cuidadores de
plantas**.

**Modelo de dos pasos:**
1. Las plantas se venden **dentro de macetas inteligentes**. Quien la compra se
   convierte en *jardinería* (cuidador) y queda vinculado a esa maceta.
2. Sobre esa base se construye una **red urbana de jardineros**, a la que se le
   proveen: productos relacionados con plantas, información de cuidados, datos de la
   planta, y herramientas de red social.

**Lectura de negocio:** el ingreso no está en la maceta (es el anzuelo de adopción),
sino en lo que rodea a la maceta: e-commerce de productos, contenido de cuidado,
y la retención por pertenencia a la red social.

**Consecuencia estructural:** el eje del producto es **social**, con la maceta como
ancla física. El dato de un solo usuario no es el producto; el valor está en la
relación entre usuarios y en la retención de la red.

**Vocabulario de dominio introducido:** *jardinería* (cuidador), *red urbana de
jardineros*, *maceta inteligente* como unidad comercial y física.

**⚠ Hallazgo — discrepancia de alcance (ver E-010):** el modelo de negocio exige
capacidades de red social (perfil, conexiones, feed, contenido) que **no figuran** en
la lista de capacidades de E-003. O E-003 estaba incompleta, o la red social es un
sistema externo. Requiere aclaración antes de cerrar el SyRS.

### E-010 — Corrección de nivel: E-009 seagate de nivel (matiz)
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Error cometido:** se preguntó *"¿la red social la construimos nosotros o es sistema
externo?"* como si fuera una pregunta de nivel BRS. No lo es.

**Por qué el usuario tiene razón:** esa pregunta es de **repartición de funciones
entre sistemas** (qué construyo, qué consumo). Eso pertenece a los niveles de sistema
y arquitectura, no al nivel de negocio. Es un error clásico de ISO 29148: **filtrar
soluciones hacia arriba** y escribirlos como si fueran necesidades.

**Distinción correcta (parcial, corregida en E-011):**

| Pregunta | Nivel correcto |
|---|---|
| ¿Los jardineros necesitan conectarse entre sí? | **StRS** — no BRS, ver E-011 |
| ¿Quién implementa esa conexión, nosotros o otro sistema? | **SyRS / ARQ** — es asignación de función |

*Nota: la entrada E-009 conserva su hallazgo de discrepancia de alcance. Las
preguntas asociadas se re-numeran en §5.*

**Regla derivada (sigue vigente):** al escribir el BRS, todo sistema se nombra por su
capacidad, nunca por quién lo construye. La asignación se decide más abajo y puede
cambiar sin invalidar el BRS.

### E-011 — Corrección de nivel: necesidades de usuario NO son necesidades de negocio
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Error cometido:** se presentó *"el jardinero necesita descubrir otros jardineros
cercanos"* como una pregunta de nivel **BRS**. No lo es. Es una **necesidad de
interesado** y pertenece al **StRS**.

**El criterio de nivel, en una línea:**

| Nivel | Pregunta que responde |
|---|---|
| **BRS** | ¿Por qué **el negocio** necesita esto? ¿Qué resultado de negocio se busca? |
| **StRS** | ¿Qué necesita **el usuario/parte interesada** del sistema? |
| **SyRS** | ¿Qué debe hacer **el sistema** para satisfycer esos requisitos? |

**Diagnóstico:** la causa es confundir el *sujeto* de la oración. *"El jardinero
necesita X"* es StRS por construcción, sin importar cuán importante sea X para el
negocio. El BRS habla del negocio como sujeto: *"el negocio necesita aumentar
retención"*, *"el negocio necesita que la red alcance masa crítica"*.

**Regla derivada (control de nivel, para todo el levantamiento):**

> Antes de registrar una pregunta, identificar quién es el sujeto.
> Si el sujeto es una persona, es StRS. Si el sujeto es el negocio, es BRS.
> Si el sujeto es el sistema, es SyRS. Si el sujeto es la solución, es SRS.

**Consecuencia sobre el arranque:** el BRS no se puede levantar preguntando al usuario
qué quiere la app. Se levanta preguntando **qué resultado de negocio** persigue el
proyecto. La sesión de descubrimiento de StRS (qué necesita el jardinero) es una
sesión **posterior y separada**, aunque se apoye en el BRS.

**Corrección al mapa (§3):** el BRS no arranca de la visión del producto, sino de la
**visión del negocio**. El producto es una hipótesis del negocio, no su punto de partida.

### E-012 — Objetivos de negocio: siete, en dos familias
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — respuesta parcial a Q-015

**Objetivos declarados:**

| # | Objetivo | Familia |
|---|---|---|
| OB-1 | Vender más macetas | Comercial |
| OB-2 | Vender productos a la base instalada | Comercial |
| OB-3 | Crear y densificar el efecto de red | Comercial / estructural |
| OB-4 | Retener al cliente | Comercial |
| OB-5 | Brindar un hobby como alternativa de **bienestar socioemocional** | Misión |
| OB-6 | Promover la **responsabilidad en el cuidado de seres vivos** | Misión |
| OB-7 | Formar una **comunidad de práctica** en jardinería urbana distribuida, para aumentar el **sentido de pertenencia ciudadana** | Misión |

OB-1 a OB-4 fueron propuestos por el asistente; OB-5 a OB-7 por el usuario.

**Lectura:** el proyecto no es solo comercial. OB-5 a OB-7 son objetivos de misión y
constituyen el diferencial del producto: no compiten con OB-1..OB-4, los justifican.
Un producto que solo vendiera macetas no sería esto.

**Consecuencias que ya se pueden derivar:**

1. **OB-6 es una restricción dura, no un deseo.** Vinculada con E-005 (riesgo de
   actuadores): si la app permite ahogar una planta por un defecto de UI, el producto
   incumple su propia misión. La seguridad funcional pasa a ser requisito de
   cumplimiento, no solo de calidad.
2. **OB-5 y OB-7 son los objetivos no verificables.** "Bienestar socioemocional" y
   "sentido de pertenencia ciudadana" no admiten prueba directa bajo ISO 29119. Ver
   Q-017: hay que encontrar proxies observables o el objetivo no será trazable.
3. **El éxito del negocio puede contradecir el de la misión.** Ver Q-018: una app
   optimizada para OB-2 (vender) puede producir OB-5 (hobby) mediante presión
   comercial. Falta criterio de desempate.

### E-013 — Relación entre objetivos: jerarquía total
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — respuesta a Q-018

**Decisión: la misión precede al comercio. Jerarquía total.**

Orden de prioridad declarado por el usuario:

```
   +--------------------------------------+
   |  1o  MISION   (OB-5, OB-6, OB-7)     |  <- gana todo conflicto
   +--------------------------------------+
   |  2o  COMERCIAL (OB-1, OB-2, OB-3, OB-4) |
   +--------------------------------------+
```

**Consecuencia estructural:** el comercial es un objetivo **subordinado**, no
paralelo. No se optimiza OB-1..OB-4 *además* de la misión, sino **al servicio** de ella.
Cada vez que un objetivo comercial colisione con uno de misión, **gana la misión**.

**Regla de decisión derivada (aplicará a todo el proyecto subsiguiente):**

> Ante conflicto entre un objetivo comercial y uno de misión, prevalece el de misión.
> El negocio debe buscar una vía que no degrade la experiencia de cuidado.

**Efecto sobre Q-012 (monetización):** "productos relacionados con las plantas" se
proveen, pero su presentación es una decisión de negocio sujeta a restricción. No
procede el push comercial intrusivo.

**Efecto sobre E-005 (actuadores):** se refuerza. Un fallo de seguridad funcional
contradice OB-6 directamente. En este proyecto no es solo un defecto de calidad: es
un incumplimiento del objetivo de primer nivel.

**⚠ Coste que hay que reconocer explícitamente (riesgo de proyecto):** la jerarquía
total implica que el éxito comercial debe lograrse **sin** Leverage de la presión
comercial. Si eso resulta inviable, el proyecto tiene un problema de modelo de negocio,
no de producto. Este riesgo queda registrado como R-002 en §5.1 y debe revisarse en
la primera y segunda rebanada vertical, no al final.

### E-014 — Restricciones del negocio: respuesta parcial, con reclasificación
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Declarado por el usuario:**

| Restricción | Valor | Nivel correcto |
|---|---|---|
| Distribución geográfica | **Restringida a clusters geográficos**. No global desde el inicio | **BRS** — límite de mercado |
| Lanzamiento | **Por iteración**, con usuarios seleccionados, hasta alcanzar lanzamiento público | **BRS** — estrategia de alcance de mercado |
| Canal de venta | **E-commerce propio** | **BRS** — decisión de modelo de negocio |
| Equipo | Desarrollo **unipersonal**, ~8 h/día | **No BRS** → ISO 12207 / gestión |
| Estado del hardware | Al frontend **no le corresponde** saberlo | **No BRS** → supuesto de sistema (SyRS) |
| *(sin ítem — ver texto de E-014)* | — | — |

**Observación del usuario:** *"varias de estas preguntas no forman parte del BRS"*.
Correcto, y seadina a E-010 y E-011: **el error se repite en la misma dirección.**

**Patrón de error (tercera ocurrencia, E-010, E-011, E-014):** el asistente tiende a
preguntar *"¿qué necesitamos saber para construir esto?"* cuando el nivel exige
*"¿qué necesita saber el documento?"*. Las preguntas de construcción son legítimas —
pero no en BRS.

**Regla de control añadida a §1:**

> Antes de formular una pregunta, comprobar: **¿esta información va a aparecer escrita
> en el BRS?** Si sirve para construir pero no para el documento, es de otro nivel.

**Aceptadas pero NO registradas en el BRS:**

1. **Unipersonal / 8 h día** → confirma la decisión de rebanadas verticales (E-006):
   no hay capacidad para una cascada de requisitos largos. Refuerza el método.
2. **Segregación de responsabilidades** (al frontend no le corresponde conocer el
   estado de desarrollo de las macetas) → principio de arquitectura, no de negocio.
   Se captura en el SyRS/ARQ.
3. **Lanzamiento por iteración con usuarios seleccionados** → esto **sí** es BRS, y es
   una consecuencia fuerte: el BRS debe describir una plataforma que arranca en un
   grupo reducido y se abre. Alimenta directamente R-001.

**⚠ Corolario de "clusters geográficos":** la restricción de mercado y la de datos
personales son cosas distintas. Que el lanzamiento sea por clusters no elimina las
obligaciones de privacidad que aplican desde la primera cohorte (Q-019, Q-020).

### E-015 — Desarrollo orientado a interfaces y responsabilidad única
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` (nivel: ARQ / SRS)

**Observación del usuario:** *"me preocupa que usted no parezca entender lo que es
desarrollo orientado a interfaces, responsabilidad única, etc."*

**Corrección del usuario (2 de 3 puntos aceptados, 1 rechazado):**

- Punto 1 — **aceptado.** El BRS no asigna responsabilidades a componentes; describe
  capacidades de negocio.
- Punto 2 — **RECHAZADO.** *"Está mal: este proyecto es solo de un frontend. Todo lo
  demás es una caja negra que se abstrae en una API."* Ver E-016.
- Punto 3 — **sin calificar por el usuario.**

**Registro de la posición original del asistente (E-015, superada en parte por E-016):**

Estos principios son la razón por la que existen tres decisiones ya tomadas en este
proceso. No son una etiqueta, son el fundamento de la clasificación:

| Principio | Dónde secoldó visible en este proyecto | Decisión que produce |
|---|---|---|
| **Responsabilidad única** (por componente) | "Al frontend no le corresponde el estado de las macetas" (E-014) | El frontend no modela la maceta: la consume |
| **Caja negra tras una API** | *"Todo lo demás es una caja negra"* (E-016) | El único sistema del proyecto es el frontend |
| **Orientado a interfaces** (no a implementación) | "Toda mención a un sistema se nombra por capacidad, nunca por quién lo construye" (E-010) | El BRS nombra capacidades; la asignación va al SyRS |
| **Dependencia de la capa superior hacia la inferior** | "Toda integración ocurre por GraphQL externo" (E-003) | El frontend depende de una abstracción estable, no del hardware |

**Consecuencia para la cadena de requisitos:** el BRS **no asigna responsabilidades a
componentes**. Eso es la vista de arquitectura (ISO 42010). El BRS nombra *capacidades
que el negocio necesita*, punto.

**Consecuencia práctica para el SRS (nivel de software):** cada requisito software se
verifica contra una **interfaz** (un tipo de dato, un query, un contrato), nunca
contra una implementación. La pregunta de control antes de escribir un requisito:

> ¿Este requisito se puede violar sin tocar una implementación concreta?
> Si sí, es un requisito de interfaz. Si no, es una decisión de diseño, y no va al SRS.

**Pendiente de confirmación (el usuario puede corregir):** si esta lectura es correcta,
el principio queda capturado en §3 (reparto de niveles) y no en el BRS.

### E-016 — Corrección: el proyecto tiene un solo sistema
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Error cometido (punto 2 de E-015):** se afirmó que *"la vista de arquitectura es el
lugar donde vive la asignación de responsabilidades entre frontend, backend, plataforma
y hardware"*. Es falso en este proyecto.

**Corrección del usuario:** *"este proyecto es solo de un frontend, todo lo demás es una
caja negra que se abstrae en una API"*.

**Implicación correcta:** no hay asignación de responsabilidades que hacer, porque **no
hay varios sistemas que repartir**. La pregunta "quién implementa qué" no tiene objeto:
todo lo externo ya está abstraído detrás de la API GraphQL (E-003).

```
   lo que el asistente asumio (mal)     lo que es (bien)
   -------------------------------       --------------------
   +-- frontend --+                     +-- frontend --+
   |              |                     |              |  <- unico sistema
   +--------------+                     +--------------+
   | backend      |  <- repartir        |   API        |  <- caja negra,
   +--------------+     responsabilidades|  (interfaz)  |     no se reparte
   | plataforma   |                                        |
   +--------------+                                        |
   | hardware     |                                        |
   +--------------+                                        |

   ISO 42010 describe aqui UNA sola unidad, no un sistema
   de sistemas. No hay vista de despliegue que repartir.
```

**Consecuencia sobre la cadena de requisitos:**

1. **ISO 42010 se aplica a un solo sistema.** Su utilidad es describir vistas del
   *frontend* (módulos, capas, flujo de datos hacia la API), no coordinar sistemas
   externos. Nada de stakeholder-driven views con terceros.
2. **La "caja negra" es un supuesto estructural del BRS**, no un requisito. El BRS
   establece que el negocio depende de una capacidad de plataforma; el detalle de qué
   la implementa no aparece, y **no debe** aparecer.
3. **El nombre "IntPlantNetworkFrontend" no es incidental.** El sufijo `Frontend` no
   describe una porción optional: **delimita el sistema**. El proyecto es el cliente de
   una plataforma, no la plataforma.

**⚠ Nota de proceso (cuarta vez, E-010, E-011, E-014, E-016):** el asistente tiende a
preguntar cosas de niveles inferiores a BRS. En este proyecto eso es especialmente
tentador porque el alcance técnico está bien definido y es más fácil de preguntar que
la necesidad de negocio. **Regla: hasta cerrar el BRS, toda pregunta debe tener sujeto
= el negocio.** Ver control de nivel en §1.

### E-017 — BRS emitido y aprobado
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

`BRS.md` v0.1 emitido. **Aprobado por el usuario** ("sí, continuar"). Nivel 1 del
V-model cerrado.

**Contenido:** 12 secciones ISO 29148. Necesidad, 7 objetivos, jerarquía, restricciones,
estrategia de lanzamiento, riesgos, verificación pendiente, exclusiones, interesados,
glosario, trazabilidad, control de cambios.

**Reglas de negocio derivadas (operativas, no descriptivas):**

| ID | Regla | Origen |
|---|---|---|
| **RN-01** | Ante conflicto comercial vs. misión, prevalece la misión | E-013 |
| **RN-01.b** | Un fallo de seguridad funcional contradice OB-6 → pasa a ser requisito de cumplimiento, no de calidad | E-013 / E-005 |
| **RN-02** | Admite cohorte invitada desde la v1 | RS-02 |
| **RN-03** | El usuario sin conexiones es el estado inicial de la base, no una excepción | RS-02 / R-001 |
| **RN-04** | El lanzamiento por cluster no exime de privacidad: aplica desde la primera cohorte | RS-01 |

**Supuestos declarados:** SA-01 (existe capacidad de plataforma con telemetría y
comandos — si falla, el proyecto no tiene objeto), SA-02 (API estable o versionada),
SA-03 (el usuario ya dispone de maceta del e-commerce propio).

**Pendiente arrastrado:** §7 proxies de verificación — propuesta no aprobada (Q-017).

**Decisión de método:** el BRS se emitió como documento **de proyecto** en la raíz,
conforme a §3 (reparto de niveles). No se usó un change de OpenSpec para BRS ni StRS:
son transversales. A partir de **SyRS** cada rebanada vertical sí se maneja como
change de OpenSpec.

### E-018 — StRS: necesidad base de la jardinería
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — respuesta a Q-016

**Directriz del usuario (E-018.m):** *"no vamos a crear un documento complejo, vamos a
escoger unos requisitos base y en futuros ciclos (iteraciones) iremos creciendo"*.
Aplica a StRS y a los niveles inferiores: **documentos lean, un conjunto base de
requisitos, crecimiento por iteración.**

**Necesidad declarada.** La jardinería necesita conocer, de las macetas de una región:
SR-01 el listado de macetas; SR-02 el tipo de planta; SR-03 el estado de los sensores;
SR-04 el estado de la planta; SR-05 la ubicación; SR-06 los comentarios que ha obtenido.

**Lectura: la red es geográfica, no social.** La unidad de descubrimiento es la
**región**, no la relación interpersonal. No hay amigos, ni seguidores, ni feed. Coherente
con RS-01 (clusters) y OB-7 (jardinería urbana distribuida). Esto **reduce** el alcance
social respecto de lo supuesto en E-009: la red es un registro compartido de plantas,
no un grafo de relaciones.

**⚠ Ampliación de alcance respecto de E-003.** E-003 definía *"tableros de control con
estado de plantas"* — las macetas **propias** del usuario. El StRS define el listado de
macetas **de una región**, que incluye macetas de **otros**. No es el mismo requisito y
no es una reformulación: es un alcance nuevo. Registrado como R-005.

**⚠ El estado de la planta es un juicio, no un dato (R-006).** "Estado de la planta"
no es un valor crudo como la humedad: es una valoración. Si el frontend la calculara,
violaría la responsabilidad única (E-015) y reintroduciría lógica de dominio en el
cliente. Debe provenir de la API, o ser declarada por la jardinería. Q-023.

**Hallazgo: la misión queda débilmente servida.** SR-01…SR-06 atienden **OB-3** (efecto
de red) y parcialmente **OB-6** (responsabilidad: ver el estado de otras plantas enseña).
Pero apenas tocan **OB-5** (bienestar socioemocional) y **OB-7** (pertenencia), que
RN-01 coloca **por encima** de todo lo comercial. R-007: el conjunto base no cumple la
jerarquía de objetivos. No bloquea la iteración 1, pero deja una deuda explícita.

**Q-016 queda cerrada.** Se abren Q-023 (origen del estado de la planta), Q-024 (quién
ve el listado de una región) y se endurece Q-019.

### E-019 — Quinta corrección de nivel: origen de datos y reglas de visibilidad
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — cierra Q-023, Q-024

**Respuestas del usuario** (a dos preguntas que no debían haberse hecho en StRS):

1. *"Para el frontend todo lo entrega la API, ya veremos la estructura más adelante."*
2. *"El listado lo ve cualquier usuario del sistema, pero esto no es requerimiento de
   interesado."*

**Ambas respuestas cierran la pregunta y confirman el error de nivel.** Quinta vez que
ocurre (E-010, E-011, E-014, E-016, E-019).

| Pregunta formulada | Sujeto real | Nivel real |
|---|---|---|
| ¿De dónde viene el estado de la planta? (Q-023) | El sistema | **SyRS / SRS** |
| ¿Quién puede ver el listado? (Q-024) | El sistema | **SyRS** (regla de acceso) |

**Diagnóstico:** la necesidad de la jardinería es *independiente del origen*. Que el
estado de la planta lo calcule la API o la plataforma no cambia lo que la jardinería
necesita: conocerlo. **La procedencia del dato no es un requerimiento de interesado.**
Ncavorable, en ISO 29148 el StRS describe la necesidad, no su implementación.

Lo mismo con la visibilidad: *"cualquier usuario del sistema lo ve"* es una **regla de
acceso**, es decir una condición del sistema sobre la capacidad, no una necesidad de la
persona. El StRS dice *"la jardinería quiere conocer las macetas de su región"*; quién
tiene permiso de verlo es otra capa.

**Nueva causa raíz, distinta de las anteriores:** las cuatro primeras ocurrencias
preguntaron por *niveles equivocados* (negocio donde iba persona, sistema donde iba
negocio). Esta es distinta: **se convirtió una decisión de diseño en una
pregunta de requerimientos.** Se preguntó "de dónde viene el dato" y "¿quién tiene
permiso", que son preguntas de la capa de sistema, y se tradujo al StRS. El StRS no
pregunta *cómo* ni *quién*; se responde *qué necesita la persona*.

**Regla añadida a §1 (control de nivel, v2):**

> **Filtro de "¿necesita saberlo la persona?"**
> Pregunta: *¿esta información cambia lo que la persona quiere obtener, o solo describe
> cómo se loProvee?*
> - Si la **cambia** → StRS (o el nivel del sujeto).
> - Si solo describe **cómo se loProvee** → no es StRS. Va a SyRS/SRS.
> - Si solo describe **quién loProvee** → no es StRS. Va a SyRS.
>
> Regla mnemotécnica: **el StRS habla de *qué*, nunca de *de dónde* ni de *quién
> implementa*.**

**Datos capturados para niveles inferiores (no son StRS):**

- **DA-01** — El frontend obtiene todos sus datos de la API. La estructura sedefinirá más
  adelante (SyRS/SRS). Resuelve R-006 en la práctica: el frontend no calcula el estado
  de la planta.
- **DA-02** — El listado de una región es visible para cualquier usuario del sistema
  (SyRS, regla de acceso). **⚠ Queda tensionado contra RN-04** (obligaciones de datos
  personales desde la primera cohorte, E-014). Se registra como **R-008** para
  resolver en SyRS: un listado de macetas de una región con ubicación es un conjunto de
  datos personales, y "cualquier usuario del sistema" puede no ser compatible con la
  normativa aplicable en el cluster de lanzamiento.

**⚠ Quinta corrección de nivel — E-019. Filtro "¿necesita saberlo la persona?":**

> El StRS responde **qué** necesita la persona. Nunca **de dónde** viene el dato ni
> **quién** lo provee. Si la respuesta no cambia lo que la persona quiere obtener, es
> una decisión de diseño y no va al StRS.

Causa raíz distinta de las cuatro anteriores: aquí no se preguntó en el nivel
equivocado, se **convirtió una decisión de diseño en una pregunta de requerimientos**.

### E-020 — StRS emitido
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

`StRS.md` v0.1 emitido y aprobado. **Nivel 2 del V-model cerrado.**

**Contenido:** 8 secciones lean. Necesidad, 6 requisitos base (SR-01…SR-06), alcance de
la iteración, riesgos heredados, observación de consistencia con el BRS, trazabilidad,
control de cambios.

**Nomenclatura confirmada:** SR-01…SR-06 (Stakeholder Requirement). Convención de IDs
por nivel: `BR-` negocio, `SR-` interesado, `SYR-` sistema, `SWR-` software.

**Convención de iteración confirmada:** las iteraciones futuras **agregan requisitos a
este mismo documento** con entrada en el control de cambios. No se crean documentos
nuevos por iteración.

**Estado de la cadena:**

| Nivel | Artefacto | Estado |
|---|---|---|
| BRS | `BRS.md` v0.1 | Cerrado, aprobado (E-017) |
| StRS | `StRS.md` v0.1 | **Cerrado, aprobado (E-020)** |
| SyRS | — | No iniciado |
| SRS | — | No iniciado. Destino: `openspec/specs/` |
| ARQ | — | No iniciado |
| COD | — | No iniciado |
| TEST | — | No iniciado |

**Riesgos vigentes al cerrar el StRS:** R-001, R-002, R-004, R-005, R-007,
R-008 (resolver en SyRS). R-003 mitigado (E-021). R-006 resuelto en la práctica por
DA-01.

**Pendiente para SyRS:** Q-025 (estructura de la API), Q-024/DA-02 (regla de acceso
contrastada con la normativa, R-008), y la derivación de SR-01…SR-06 a capacidades de
sistema.

### E-021 — Aprobación de los proxies de verificación (Q-017)
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

Los proxies de verificación de OB-5, OB-6 y OB-7 **quedan aprobados** por el usuario.
Cierra Q-017, que estaba pendiente desde E-012.

**Cambios aplicados:**
- `BRS.md` §7 pasa de "PENDIENTE DE APROBACIÓN" a aprobado. Versión del documento
  0.1 → **0.2**.
- Se agrega **RN-05**: norma de interpretación de los proxies. Si el proxy se cumple y
  el objetivo no, se revisa el proxy; no se declara éxito.
- Se registra la **advertencia aceptada**: los proxies miden comportamiento observable,
  no estado interno. "Sesión voluntaria" mide permanencia en la app, no bienestar.
  Se adopta con la limitación declarada, en lugar de declarar los objetivos no
  verificables. Esta es la razón por la que la alternativa elegida es un compromise y
  no una solución limpia.
- **R-003 pasa a mitigado.** La trazabilidad hasta ISO 29119 ahora cubre los siete
  objetivos de negocio.
- Actualizadas §1 (tabla de exclusiones), §11.2 (trazabilidad interna, nueva fila RN-05),
  §11.3 (origen), §10 (glosario: "proxy de verificación") y §12 (control de cambios).

### E-022 — Error de método: derivar no es mapear
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Error cometido:** la primera versión del `SyRS.md` (v0.1) hizo una derivación **1:1**
del StRS: cada SR produjo exactamente un SYR. Seis SR → seis SYR.

**Por qué está mal.** El lado izquierdo del V-model significa que **cada nivel
descompone** el anterior. Un requisito de interesado es un **propósito**; no se
implementa, se apoya en muchas capacidades. Derivar no es mapear.

**Diagnóstico adicional (el asistente primero creyó que el error era de alcance).**
La primera hipótesis fue que faltaban las capacidades 1, 2, 4, 5 y 6 de E-003
(auth, registro, actuadores, historial, alertas). El usuario corrigió: **no, es una
rebanada, un solo SR — pero ese SR exige muchos SYR.** El error no era de alcance sino
de granularidad de derivación.

**Corrección aplicada.** `SyRS.md` v0.2: 13 requisitos, agrupados por función.

| Grupo | Requisitos | Función |
|---|---|---|
| §3.1 Condiciones previas | SYR-01, SYR-02, SYR-03 | Sin estas, los demás son inoperantes |
| §3.2 Obtención | SYR-04 … SYR-09 | Los seis atributos de SR-01…SR-06 |
| §3.3 Alcanzabilidad | SYR-10, SYR-11 | Que la persona pueda consultarlo, incluso vacío |
| §3.4 Acceso | SYR-12 | Condicionado a R-008 |
| §3.5 Atribución | SYR-13 | Distinguir macetas propias |

**Cinco requisitos que un mapeo 1:1 habría omitido o hecho implícitos:**

| ID | Condición | Origen |
|---|---|---|
| SYR-01 | Identidad del usuario. **SYR-12 dice "cualquier usuario del sistema" — sin identidad no hay usuario del sistema** | DA-02 |
| SYR-02 | Determinación de la región. "Una región" sin definir cuál | SR-01 |
| SYR-03 | Distinción entre macetas. Sin identidad de maceta, los atributos no se atribuyen a nadie | SR-02…06 |
| SYR-11 | Conjunto vacío viable. RN-03: es el estado inicial de la base | RN-03 / R-001 |
| SYR-13 | Distinguir macetas propias | OB-6 / R-005 |

**Regla añadida a §1 (control de método, v3):**

> **El cociente de derivación importa.** Si un requisito de un nivel produce
> exactamente un requisito del nivel siguiente, es que el mapeo no descompuso nada.
> Pregunta de control: *¿qué condiciones tienen que ser ciertas para que este requisito
> sea siquiera aplicable?* Cada respuesta es un requisito del nivel siguiente.
>
> Un requisito de nivel superior es un **propósito**; los de nivel inferior son
> **capacidades que lo hacen posible**.

**Trazabilidad incompleta declarada (no corregida en esta iteración):** la capacidad 3
de E-003 ("tableros de control") solo tiene SYR-13. El tabler en sí no tiene requisito de
sistema porque no hay SR que lo pida. Queda declarado en §6.1 del SyRS y debe resolverse
al ampliar el StRS en iteraciones futuras.

### E-023 — Los requisitos de sistema se agrupan por categoría, no son una lista
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Error cometido (tercero, y el más grave de los tres).** `SyRS.md` v0.2 tenía 13
requisitos, **todos funcionales**. El usuario señaló que faltaban usuarios registrados,
seguridad, desempeño y hardware.

**Diagnóstico: el documento no tenía estructura de categorías.** ISO 29148 agrupa los
requisitos de sistema en categorías, y cada una contiene requisitos que **no son
visibles mirando los funcionales**, porque responden a otro criterio. No era un problema
de cantidad: era que cinco categorías enteras no existían.

| v0.2 tenía | v0.3 agrega |
|---|---|
| §3.1 Condiciones previas | **§3.7 Interfaz** — SYR-14, 15, 16 |
| §3.2 Obtención | **§3.8 Seguridad** — SYR-17 … SYR-20 |
| §3.3 Alcanzabilidad | **§3.9 Desempeño** — **VACÍA, sin datos** |
| §3.4 Acceso | **§3.10 Disponibilidad** — SYR-21, SYR-22 |
| §3.5 Atribución | **§3.11 Hardware** — SYR-23 (pendiente), SYR-24 (condicionado) |
| | **§3.12 Usabilidad** — SYR-25, SYR-26 |

**13 → 26 requisitos.**

**Autocorrección: DD-01 estaba mal planteada.** Se diferenció toda la API al SRS con el
razonamiento de que *"un requisito de sistema se verifica contra una capacidad, no contra
la forma"*. El razonamiento era correcto y la aplicación, no: se diferenció el
**esquema** (correcto) y también el **requisito de interfaz** (incorrecto). Que el
sistema se comunique con la plataforma por la interfaz publicada es una propiedad del
sistema, verificable sin conocer la estructura. **DD-01 se reformula:** el esquema se
difiere al SRS; la existencia del requisito de interfaz es de este nivel.

**Distinción obligatoria: "hardware" tiene dos sentidos** (SYR-23, nota de §3.11):

| Hardware | Estado |
|---|---|
| **Dispositivo del usuario** (teléfono) | **En alcance.** SYR-23, SYR-24 |
| **Maceta inteligente** | **Fuera.** E-014: *"al frontend no le corresponde"* (SYR-R03) |

Confundirlos es un error de nivel. También se distinguió la **capacidad de ubicación del
teléfono** (del dispositivo, SYR-24) de la **ubicación de la maceta** (dato de la
plataforma, SR-05). Son cosas distintas.

> **⚠ Este párrafo queda REVERSADO por E-024.** Se afirmó que "consultar usuarios
> registrados" era una necesidad de interesado nueva porque ningún SR la pedía. **Falso:**
> SYR-01 ya la exigía, y SYR-12 dice "cualquier usuario del sistema". La conclusión
> correcta era *"hay un SYR y falta un SR"*, no *"no hay SR, luego no hay SYR"*.
> **Nada entró por el StRS.** Ver el control 5 de §1.

**§3.9 quedaba vacía en esta versión.** Un requisito de desempeño sin valor medible no
es un requisito: es una aspiración. Se listaron los parámetros faltantes (DP-01…DP-05)
con la pregunta que cada uno responde.

> **⚠ Y E-026 revierte también este enfoque.** La observación *"un requisito de desempeño
> sin cifra no es falsable"* **sigue siendo cierta**, pero la conclusión *"hay que
> preguntarle el número al interesado"* era falsa: el volumen es un **supuesto de
> ingeniería**, no de negocio. Los cinco DP se eliminaron y se convirtieron en
> **SYR-27…SYR-30**. Ver el control 8 de §1.

**Sobre seguridad — no se inventaron valores.** SYR-17…SYR-20 se derivan de decisiones
existentes (RN-04, R-008, SYR-01, DA-01). No se fijaron algoritmos, longitudes de clave ni
tiempos de sesión: esos son diseño y medida, no requisitos. Se dijo explícitamente en el
documento que **no se inventan mecanismos**.

**Derivados notables de esta corrección:**

| ID | Requisito | Por qué no era visible antes |
|---|---|---|
| **SYR-21** | Distinguir la imposibilidad de obtener datos de la ausencia de macetas | Un fallo de plataforma mostrado como conjunto vacío es un **dato falso**, no un error de usabilidad. Seguridad funcional |
| **SYR-22** | Indicar la antigüedad de la información | Una lectura de humedad de hace 3 h presentada como actual lleva a una decisión de cuidado equivocada. Mismo principio que RN-01.b |
| **SYR-24** | Uso condicional de la ubicación del dispositivo | **Condicionado a propósito:** si la región se resuelve por pertenencia o selección, el sistema no necesita GPS, y afirmarlo sería un requisito falso |

### E-024 — Cuarta corrección: identidad es condición de posibilidad, no necesidad nueva
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — cierra Q-029

**Respuesta del usuario:** *"consultar usuarios registrados… claro que sí se pide, pues
solo los usuarios registrados pueden consultar"*.

**Error cometido:** en E-023 se registraron "consultar usuarios registrados" como
**necesidad de interesado nueva** (Q-029), con la explicación de que *"no hay ningún SR
que lo pida, y por eso no hay SYR"*. Es falso: **SYR-01 lo pide**, y SYR-01 es
precisamente el requisito de identidad.

**Diagnóstico: se aplicó el razonamiento correcto al objeto equivocado.** La regla de
E-022 — *"si no hay SR, no hay SYR"* — es válida, pero se usó para concluir que faltaba
un SR, cuando lo correcto era concluir que **falta un SR y hay un SYR**. Son dos hechos
distintos, y confundirlos produjo una conclusión falsa.

**Corrección de nivel (sétima ocurrencia).** Identificar si algo es condición de
posibilidad o necesidad. La identidad del usuario **no es un deseo de la jardinería**: la
jardinería no *quiere* identificarse, el sistema lo necesita para que SYR-12
("cualquier usuario del sistema") tenga sujeto. Es exactamente la categoría que E-022
estableció — **capacidades que hacen posible un propósito** — y en esa categoría viven
SYR-01, SYR-02, SYR-03, SYR-11, SYR-12, SYR-13.

**Regla añadida a §1 (control v4):**

> Antes de crear un requisito de nivel superior, comprobar si el nivel inferior ya lo
> tiene como **condición de posibilidad**. *"No hay SR"* significa *"no hay SR **y** no
> hay SYR"*, nunca *"no hay SR y por eso no hay SYR"*. La segunda lectura es un
> non-sequitur.

**Cuarta corrección, del mismo tipo, sobre §3.9:** el usuario respondió a dos parámetros con
"de dónde saca que…" y "macetas por región se gestiona con buenas prácticas". Dos
respuestas:

1. **DP-03 (comentarios sin límite) — error propio, no del usuario.** No hay ninguna
   fuente. Se *supuso* un crecimiento ilimitado y se escribió como hecho. **Se
   elimina DP-03.** No se puede afirmar el comportamiento de algo que nunca se observó.
   Un requisito basado en una suposición inventada es peor que un requisito ausente,
   porque se verifica contra un número falso.
2. **DP-01 / DP-04 (volumen) — matiz del asistente, aceptado como decisión de diseño.**
   El usuario sostiene que el volumen se gestiona con buenas prácticas, y es cierto en el
   nivel de diseño. Pero un requisito de desempeño **no puede declararse sin un valor
   medible**: sin cifra, "correcto" no es falsable, y un requisito no falsable no se
   puede probar. La cifra no la inventa el asistente — es un supuesto de negocio (la
   densidad de macetas por región en cada fase), y pertenece al usuario. El diseño lo
   resuelve después.
   **Nota: no es una objeción al usuario, es una distinción entre "decisión de diseño"
   y "supuesto de negocio".** El volumen no es una decisión de diseño: es un hecho del
   mundo que el diseño debe enfrentar.

   > **⚠ ESTE PÁRRAFO FUE CORREGIDO EN E-026 Y NO ES VIGENTE.** El razonamiento
   > "un requisito de desempeño necesita un valor medible" sigue siendo cierto, pero la
   > conclusión *"por eso hay que preguntarle el número al interesado"* **era falsa**.
   > El número no era un supuesto de negocio: era un **supuesto de ingeniería**, y la
   > respuesta correcta no era preguntar sino **convertirlo en requisito de no
   > dependencia** (SYR-27…SYR-30). El usuario lo objectó en E-026 con *"eso se resuelve con
   > filtros"*. Se conserva el párrafo original por registro; **la versión vigente es
   > E-026.**

### E-025 — Atribución de causa: "unipersonal" no implica menor calidad
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Error cometido (quinta corrección de nivel, segunda de atribución de causa).** El
asistente afirmó: *"con unipersonal, 'todas las buenas prácticas' produce sistemas con la
mitad de los controles de conexión que un sistema con mil macetas necesita"*.

**Respuesta del usuario:** *"¿De dónde saca eso? El hecho que un desarrollo sea
unipersonal no tiene porque comprometer la calidad. Para eso se supone que las crearon a
ustedes."*

**El asistente no tiene fuente. La afirmación era infundada.** Se presentó como
consecuencia deducida cuando era una generalización sobre la capacidad de una persona,
sin ningún fundamento. Peor: es del tipo de suposición que uno mismo evita en los
requisitos (control 6) y quePractica sin darse cuenta en el razonamiento.

**Regla añadida a §1 (control 7):** no atribuir a una característica del contexto una
consecuencia no derivada. "Unipersonal" es un dato de recursos, no un factor de calidad.
La ocupación del tiempo afecta **planificación y secuenciación**, no la calidad de lo
entregado. Si hay una restricción real, se nombra la causa concreta (tiempo disponible,
alcance comprometido) en vez de invocar el tamaño del equipo.

**Consecuencia sobre E-014.** El dato "desarrollo unipersonal, ~8 h/día" sigue siendo
válido y útil, pero **su único uso legítimo es calendario**: dimensión de rebanadas verticales,
orden de prioridad, y revision de R-002 en la 1ª y 2ª iteración. No se usa para
degradar expectativas técnicas.

**Lo que NO cambió:** la necesidad de un valor medible para los requisitos de desempeño
sigue en pie, y por una razón que no tiene que ver con el equipo. Un requisito de
desempeño sin cifra **no es falsable**, y por lo tanto no es verificable bajo ISO
29119. Eso es una propiedad de la verificación, no una limitación de quien desarrolla.

**Nota sobre la redacción anterior de esta entrada:** contenía un error tipográfico y una
frase truncada (el usuario "respondió a" aparecía con caracteres corruptos), corregidos en E-025.

### E-026 — Sexta corrección: no pedir al interesado lo que es decisión de ingeniería
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO` — cierra Q-026, Q-027 y Q-028

**Error cometido (sexta corrección de nivel, tercera de atribución).** El asistente dejó
`SyRS.md` §3.9 **vacía** y la anunció como bloqueada, pidiendo al usuario el volumen de
macetas por región, el tiempo de respuesta y el crecimiento F1→F3. Y dejó §3.11
**pendiente** pidiendo los dispositivos objetivo.

**Respuesta del usuario:**
> *"Eso depende de muchas cosas, y pues eso se resuelve con filtros. ¿Por qué hace
> preguntas de cosas que se supone que un buen desarrollador ya sabe cómo resolver?"*

**El usuario tiene razón, en los dos casos.** El volumen de datos y la versión mínima
soportada **son decisiones de ingeniería**. Preguntarlos al interesado:

1. **Devuelve una decisión al nivel que no le corresponde.** El nivel de sistema declara
   *que* el sistema debe escalar; *cómo* escala —paginación, fraccionamiento,
   virtualización de listas, filtros— es diseño, y le corresponde a quien desarrolla.
2. **Es un no-sequiturApplied al revés.** En E-024 el error fue concluir *"no hay SR →
   no hay SYR"*. Acá es: *"no sé el volumen → el requisito no se puede escribir"*.
   Falso en los dos casos: un requisito de escalabilidad **no necesita conocer el
   volumen para existir**, necesita no **depender** de él.
3. **Es exactamente el patrón que el propio asistente objetó en E-025** (atribuir
   consecuencias no derivadas) y en E-024 (afirmar suposiciones como hechos). Un
   supuesto de que *"el volumen es un dato de negocio"* es una suposición, y las
   suposiciones no se escriben como preguntas al usuario.

**Reformulación de §3.9 — el volumen se absorbe como requisito, no como pregunta:**

| v0.3 (preguntaba) | v0.4 (declara) |
|---|---|
| DP-01 volumen de macetas por región | **SYR-27** sin límite superior conocido de antemano |
| DP-02 tiempo de respuesta | **SYR-28** fraccionar el conjunto cuando el volumen lo requiera |
| DP-03 volumen de comentarios *(eliminado en E-024)* | **SYR-29** filtrar por los criterios que la plataforma exponga |
| DP-04 crecimiento F1→F3 | **SYR-30** distinguir conjunto parcial de conjunto completo |
| DP-05 condiciones de red | *(cubierto por SYR-21: fallo ≠ vacío)* |

**Los cinco parámetros desaparecen. 26 → 33 requisitos.**

**El techo sigue siendo real, y no se esconde.** SYR-27 **no es una promesa de
infinitud**: todo sistema tiene un límite técnico. Lo que se exige es que el techo no
oblige a cambiar de diseño, y que **se mida y se declare** en el plan de pruebas. Se
registró como **R-009**: un requisito de escalabilidad sin techo medido se cumple
siempre y no dice nada.

**Secuencia que faltaba en el control 6 (E-024).** Ese control decía: *si el valor es un
supuesto de negocio, se pregunta; si es decisión de diseño, se difiere.* **Faltaba la
tercera rama**, y es la queettered se aplicaba en este caso:

> **8. Control de Ternas (añadido en E-026).** Ante un valor desconocido, clasificar en
> **tres** ramas, no dos:
> - **Supuesto de negocio** → se pregunta al interesado.
> - **Decisión de diseño** → no se pregunta; **se difiere al nivel que le corresponde.**
> - **Supuesto de ingeniería** → **no se pregunta y no se difiere: se convierte en
>   requisito de no dependencia.** *El diseño no puede depender de conocer el valor.*

**Q-028 reabierta y cerrada de otra forma.** La versión mínima de plataforma también es
decisión de ingeniería. SYR-23 ya **no la nombra**: exige que el alcance de soporte
**esté declarado**. Lo que la documentación miente no es tener un mínimo —todos lo
tienen— sino **no declarar cuál** (DD-08).

**Q-026 — el usuario responde: "todo debe ser configurable".** La respuesta **no cierra
la cuestión legal, pero la desacopla del sistema**, y eso es lo que importa aquí:

| | v0.3 | v0.4 |
|---|---|---|
| **SYR-19** | *"...conforme a la regla de acceso vigente"* (R-008 bloqueante) | *"...conforme a una **regla de acceso vigente y ajustable**, cuya redacción inicial es la de DA-02 y puede endurecerse sin nuevo desarrollo"* |
| **Riesgo R-008** | Bloqueante: no se puede escribir el requisito | **Valor por defecto pendiente**, no requisito ausente |
| **Nuevo** | — | **SYR-31** la regla se ajusta **sin modificar código** |

**Por qué esto es una respuesta y no un evasión:** el riesgo ya no es *"el sistema
violará la norma"* sino *"el valor por defecto inicial no es el correcto"*. El primero exige
rediseño; el segundo, una configuración. Es la diferencia entre un riesgo de
arquitectura y un parámetro de entrada.

**Sección nueva: §3.13 mantenibilidad y configurabilidad (SYR-31…33).** El usuario dijo
*"todo debe ser configurable, el sistema debe ser escalable y fácil de mantener"*. Las
tres son **atributos de calidad ISO 25010** y en ISO 29148 son **requisitos de sistema**,
no de diseño. Se aceptaron las tres, con un límite declarado:

> **"Configurable" sin enumerar QUÉ se configura es un requisito vacío.** No falla nada,
> no se puede probar, y en la práctica significa "lo que haga falta". Por eso SYR-31 y
> SYR-31 nombran los puntos de ajuste concretos: regla de acceso, resolución de región,
> criterios de filtrado. El **mecanismo** de configuración es diseño (DD-07).

**RN-01 aplicado a la mantenibilidad (riesgo nuevo, control de nivel).** Un sistema
configurable y mantenible **facilita la evolución**. Facilitar OB-1 u OB-2 mediante
fricción intencional **no es un requisito de mantenibilidad**: es un objetivo comercial
disfrazado de atributo técnico, y RN-01 lo subordina. Se dejó escrito en §8.3 porque la
tentación de leer "configurable" como "adaptable a lo que venda" es real y aparecería
dentro de dos iteraciones.

**Q-029 cerrada definitivamente (E-024).** *"Consultar usuarios registrados"* no es una
necesidad nueva: es la condición que **SYR-01 ya exigía**. Sin identidad no hay "usuario
del sistema", y SYR-12 dice "cualquier usuario del sistema". **Nada entra por el StRS.**

**Estado tras E-026:** `SyRS.md` v0.4, **33 requisitos, ninguna sección bloqueada por
datos.** Abiertas: Q-026 (solo el valor por defecto de la regla), DD-06, DD-07, DD-08.

### E-027 — Nivel 4 (SRS) emitido como change de OpenSpec: `slice-1-listado-regional`
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Artefactos emitidos** en `openspec/changes/slice-1-listado-regional/`:

| Artefacto | Contenido |
|---|---|
| `proposal.md` | Por qué, qué cambia, 4 capacidades, impacto |
| `specs/identidad/spec.md` | 3 requisitos, 5 escenarios |
| `specs/listado-regional/spec.md` | 13 requisitos, 20 escenarios |
| `specs/atributos-maceta/spec.md` | 6 requisitos, 8 escenarios |
| `specs/reglas-y-alcance/spec.md` | 7 requisitos, 10 escenarios |
| `design.md` | D-01…D-07, riesgos, 3 preguntas abiertas |
| `tasks.md` | 8 grupos, 45 tareas |

`openspec validate --strict` → **válido**. 4/4 artefactos completos.

**Trazabilidad verificada por script:** los **33 de 33 SYR** de `SyRS.md` v0.4 aparecen
como requisito de software. Ninguno sin cubrir.

#### 29 SWR, no 33: por qué el cociente no es 1:1

Los 33 SYR se expresan como **29 requisitos de software** porque cuatro fusionan dos SYR
cuando forman un único comportamiento verificable:

| Fusión | Requisito de software | SYR |
|---|---|---|
| 1 | Identidad establecida antes de la capacidad protegida | SYR-01 + SYR-17 |
| 2 | Credenciales de la plataforma ausentes del cliente | SYR-18 + SYR-20 |
| 3 | Regla de acceso a la región vigente y ajustable | SYR-12 + SYR-19 |
| 4 | Alcance de soporte declarado | SYR-23 + SYR-33 |

**Control 4 (E-022) aplicado en la dirección contraria:** el control dice que un
cociente 1:1 indica que el mapeo no descompuso. Funciona igual al bajar: un cociente
mayor que 1 **también** requiere justificación, y la justificación es la fusión. Lo que
no se admite es fusión **sin trazabilidad** — por eso cada SWR declara de qué SYR deriva.

#### DD-01, DD-05, DD-06 resueltos: ya no podían diferirse

El `SyRS.md` los había enviado al SRS con la fórmula *"nivel destino: SRS"*. **Este es el
SRS.** Diferirlos otra vez los habría convertido en diferidos para siempre, que es la forma
elegante de no decidir nada. Se resolvieron en `design.md`:

| ID | Resolución | Decisión |
|---|---|---|
| **DD-01** | Esquema de la API | El sistema pide **seis capacidades** a la plataforma, no un esquema. La forma la define lo que la plataforma ya publica |
| **DD-02** | Expo vs PWA | **Expo / React Native** — ver abajo, es la excepción importante |
| **DD-05** | Mecanismo de identidad | **Delegado a la plataforma.** El cliente no la implementa; usa el flujo publicado |
| **DD-06** | Criterio de resolución de región | **Desde la cuenta, no desde el GPS.** Desactiva SYR-24 |

**D-01 (Expo) es la excepción y merece explicación, porque el criterio diferido en E-008
—no usar segundo plano— sí se cumple en esta rebanada.** El listado se consulta mientras
la persona mira la pantalla: no hay tarea diferida ni notificación. Un PWA resolvería esta
rebanada y sería más barato. Se eligió nativo por una razón **de esta rebanada y de las
que vienen**: E-003 ya contempla alertas e historial, que sí exigen segundo plano, y
RN-01 coloca el cuidado de la planta (OB-6) por encima de la comodidad comercial. Si el
alcance de alertas se cae, la decisión es revisable y está documentada.

**D-03 (región desde la cuenta) desactiva SYR-24, y por eso el conteo de SYR no baja a
32.** El fragmento de `specs/atributos-maceta` sobre no-confusión entre la ubicación del
teléfono y la de la maceta **se mantiene como red de seguridad**: si más adelante se usa
ubicación, la especificación ya impide la confusión. Retirarlo habría sido tirar
información útil para cuadrar un número.

#### D-05 (sin caché) responde DP-05, que llevaba abierto desde E-023

DP-05 preguntaba por las condiciones de red. **Queda respondida por decisión de diseño:**
sin caché de datos de dominio, el sistema depende de la red, y eso se acepta
explícitamente. El motivo es que un dato cacheado en el cliente tiene una antigüedad que
el cliente no puede verificar, y presentarlo sin decirla contradice SYR-21 y SYR-22. Es
un compromiso a favor de la corrección sobre la comodidad. **Cambiarlo después es un
cambio de diseño, no un parche**, porque el estado cacheado introduce distinciones que los
requisitos ya exigen.

#### R-008 no se eliminó: se transfirió

D-06 deja la regla de acceso como parámetro, pero el diseño deja escrito el límite:
**endurecer la regla es cambiar un valor en el cliente; cumplirla requiere que la
plataforma haga la comprobación.** La configurableabilidad del cliente **no crea
capacidades del servidor**. R-008 pasa de *"no se puede escribir el requisito"* a
*"depende de otro sistema"*. Es una mejora real, y no es una solución.

#### La primera tarea no es escribir código

El grupo 1 de `tasks.md` es una **comprobación de SA-01**: consultar la plataforma real,
registrar la respuesta cruda, y determinar qué atributos existen y cuáles no. Termina en
un **punto de decisión explícito**: si falta el fraccionamiento, o falta un atributo, o
la plataforma no entrega fecha, **se para ahí y se revisa el diseño**.

El motivo es SA-01: si la plataforma no sirve el conjunto por región, el proyecto no tiene
objeto. Descubrirlo después de construir la interfaz es la forma más cara de descubrirlo.

#### Tres preguntas abiertas, y ninguna cambia el diseño

| ID | Pregunta | Estado |
|---|---|---|
| **O-01** | ¿La plataforma expone fraccionamiento? | **No diferible.** Bloquea la tarea 1.2. Es una pregunta factual sobre un sistema externo |
| **O-02** | ¿Valor por defecto de la regla en la jurisdicción del cluster? | **Diferible a antes de F1.** No cambia diseño ni requisitos. Cierra Q-026 |
| **O-03** | ¿Versión mínima de plataforma? | **Diferible.** SYR-23 exige que el alcance esté declarado, no que se declare acá. Cierra DD-08 |

**Las tres son datos de entrada, no decisiones de diseño**, y por eso van como preguntas
abiertas y no como tareas de implementación. **O-01 es la única que puede invalidar el
diseño**, y por eso quedó en el grupo 1.

### E-028 — Novena corrección: un requisito que otro sistema puede no cumplir
**Fecha:** 2026-09-28 · **Estatus:** `DECIDIDO`

**Origen de la corrección:** el usuario preguntó *"¿La plataforma expone fraccionamiento?
¿Qué significa?"*. La pregunta no pedía una definición: **señalaba que el requisito no
tenía por qué existir tal como estaba escrita.**

**Error cometido (novena corrección de nivel, cuarta de atribución).** En D-02 (E-027) se
escribió: *"Lo que se le pide a la plataforma: **fraccionamiento**"*, y se dejó asentado que
era *"el único punto donde esta rebanada depende de un cambio en otro sistema"*. Además se
puso como **punto de bloqueo**: O-01 preguntaba si la plataforma lo tenía, y la tarea 1.2 decía
que si no, se paraba todo y se revisaba el diseño.

**Tres defectos en un solo requisito:**

1. **El usuario no lo pidió.** En E-026 dijo *"eso se resuelve con **filtros**"*. Filtrar
   es pedir menos cosas; fraccionar es pedir las mismas en tandas. Son distintos, y el
   asistente cambió uno por otro **sin declarar la sustitución**.
2. **Convertía una posibilidad en dependencia.** La plataforma no expone fraccionamiento
   porque nadie se lo pidió. El asistente lo convirtió en un requisito que otro equipo
   tendría que cumplir — y en un **bloqueo del proyecto**. **Un requisito que depende de
   otro sistema, y que ese sistema no tiene, es un riesgo disfrazado de especificación.**
3. **Se apoyaba en una preocupación ya descartada.** Justificaba el fraccionamiento el
   volumen desconocido — pero en E-026 el propio asistente había borrado las preguntas de
   volumen por ser decisión de ingeniería. **Se invocó una preocupação para la que ya se
   había declarado no tener datos.**

**Corrección — D-08: el cliente se adapta, la plataforma no se toca.**

| | v0 (E-027) | v0.1 (E-028) |
|---|---|---|
| **Requisito** | La plataforma debe exponer fraccionamiento | El sistema obtiene el conjunto completo **sea cual sea la forma en que llegue** |
| **Dependencia** | Bloqueante, sobre otro sistema | **Ninguna** |
| **Si la plataforma no lo tiene** | Se para el proyecto | **Funciona igual** |

**El principio que se aplicó, y que es el reutilizable:**

> **Adaptarse siempre es posible; exigir no.** El diseño se hace para el peor caso
> —que la plataforma entregue todo junto— porque en ese caso también funciona. Pedir una
> capacidad nueva a otro sistema es una dependencia que puede frenar el proyecto entero.

**Consecuencia asumida y escrita:** con el conjunto entero, la primera pantalla espera a
que llegue todo. Es una desventaja real, aceptada a cambio de no bloquear. Si el volumen
resultara grande, la optimización es agregar fraccionamiento en la plataforma **después**,
y para entonces se tendrá la medición real que hoy no existe (D-07).

**Efecto secundario útil:** al no exigir fraccionamiento, O-01 **dejó de ser una pregunta
para el usuario** y pasó a ser un dato que se registra al observar la respuesta cruda
(tarea 1.3). **Las tres preguntas abiertas de E-027 ahora son, las tres, datos de entrada
que no bloquean el diseño.** Ninguna puede invalidarlo.

**Ojo con la tentación inversa, que también es un error:** convertir *"no sé si la
plataforma lo tiene"* en *"lo voy a necesitar"*. La incertidumbre no es un requisito. Es
una razón para **adaptarse**, no para **exigir**.

**Cambio en la spec:** el requisito *"Fraccionamiento del conjunto"* pasó a *"El conjunto
se obtiene completo, independientemente de cómo llegue"*, con tres escenarios —entero, por
partes, por páginas— en lugar de uno.

### 5.1 Riesgos de proyecto

| ID | Riesgo | Origen | Estado |
|---|---|---|---|
| R-001 | **Arranque en dos fronts.** La red social requiere masa crítica y la maceta tiene su propio ciclo lento. El primer usuario abre la app sin red y no ve su valor | H-5 | Vigente |
| R-002 | **La jerarquía total puede ser inviable.** Si el negocio no logra vender sin leverage comercial, el problema es de modelo de negocio, no de producto. **Revisar en la 1ª y 2ª rebanada, no al final** | E-013 | Vigente |
| R-003 | ~~Objetivos de misión no verificables~~ | E-012 / E-021 | **MITIGADO** — proxies aprobados (BRS §7, RN-05) |
| R-004 | **Sobreventas de la app contra OB-6.** Un fallo de seguridad funcional en actuadores contradice el objetivo de primer nivel | E-005 / E-013 | Vigente |
| R-005 | **Ampliación de alcance en StRS.** E-003 describía las macetas *propias*; el StRS pide el listado de macetas *de una región*, que incluye las de otros. No es reformulación, es alcance nuevo | E-003 / E-018 | Vigente |
| R-006 | **"Estado de la planta" es un juicio, no un dato.** Si el frontend lo calculara, violaría la responsabilidad única | E-018 | **Resuelto en la práctica** por DA-01: la API lo entrega |
| R-007 | **La misión queda débilmente servida.** SR-01…SR-06 atienden OB-3 y parte de OB-6, pero apenas tocan OB-5 y OB-7, que RN-01 coloca por encima de todo lo comercial. Deuda explícita de la iteración 1 | E-018 / E-013 | Vigente |
| R-008 | **DA-02 vs. RN-04.** El listado de macetas de una región —con ubicación— es un conjunto de datos personales. Que lo vea "cualquier usuario del sistema" puede no ser compatible con la normativa aplicable en el cluster de lanzamiento | E-019 / E-014 | **MITIGADA en E-026.** La regla es **ajustable sin código** (SYR-31), así que restrictir no requiere desarrollo. **Queda abierto solo el valor por defecto** |
| R-009 | **Escalabilidad declarativa sin techo declarado.** Un requisito de escalabilidad sin límite medido se cumple siempre y no dice nada. SYR-27 exige que el techo real **se mida y se declare** en el plan de pruebas | E-026 | **NUEVA — atender en el plan de pruebas** |

---

## 5. Preguntas abiertas

Prioridad = bloquea el nivel actual. Se responden de a una.

| ID | Pregunta | Nivel | Estado |
|---|---|---|---|
| ~~Q-001~~ | ~~¿Qué necesidad o problema concreto resuelve este producto, y a quién le duele hoy?~~ | BRS | **RESPONDIDA → E-009** |
| ~~Q-015~~ | ~~¿Qué resultado de negocio busca IntPlant Network?~~ | BRS | **RESPONDIDA → E-012, E-013** |
| ~~Q-021~~ | ~~¿El BRS describe el negocio completo (A) o solo lo que entrega este repo (B)?~~ | BRS | **RESPONDIDA → A. BRS.md emitido** |
| ~~Q-023~~ | ~~¿"Estado de la planta": lo provee la API o lo declara la jardinería?~~ | ~~StRS~~ → **SyRS/SRS** | **RESPONDIDA → E-019 (DA-01)** |
| ~~Q-024~~ | ~~¿Quién ve el listado de una región?~~ | ~~StRS~~ → **SyRS** | **RESPONDIDA → E-019 (DA-02), genera R-008** |
| ~~Q-019~~ | ~~¿Las conexiones entre jardineros son públicas o privadas?~~ | ~~StRS~~ | **DESCARTADA** — el modelo es territorio, no social (E-018) |
| **Q-025** | **¿Cuál es la estructura de la API?** Deriva directa de los seis SR. Bloqueante del nivel 3 | SyRS | **ABIERTA** |
| **Q-026** | **Valor por defecto de la regla de acceso.** La compatibilidad con la normativa ya **no bloquea el sistema**: con SYR-31 la regla es ajustable sin código (E-026). Queda solo decidir el valor inicial | SyRS → **datos de entrada** | **PARCIALMENTE RESUELTA → E-026**. Diferible a antes de F1 |
| ~~Q-027~~ | ~~Volumen de macetas por región y crecimiento~~ | — | **ELIMINADA → E-026**. Era decisión de ingeniería. El volumen se absorbe en SYR-27…30 como requisito de escalabilidad; el volumen de verificación lo fija el plan de pruebas |
| ~~Q-028~~ | ~~Dispositivos objetivo~~ | — | **ELIMINADA → E-026**. Versión mínima soportada es decisión de ingeniería. SYR-23 exige que el alcance **esté declarado** (DD-08) |
| ~~Q-029~~ | ~~"Consultar usuarios registrados"~~ | StRS | **RESPONDIDA → E-024**. No es necesidad nueva: es la condición que **SYR-01 ya exigía**. Nada entra por el StRS |
| Q-022 | ¿El contenido es solo de plantas, o también hay vida personal del usuario? Una red de cuidados admite ambos; la privacidad difiere | StRS | Abierta |
| **Q-018** | **¿Las familias comercial y de misión están en relación de medio-fin, o son co-iguales? Define cómo se resuelven los conflictos (feed vs. checkout)** | BRS | **ABIERTA — bloqueante** |
| **Q-019** | **Costumbre de privacidad en red social: ¿las conexiones entre jardineros son públicas, privadas, o por defecto privadas con opción de descubrir?** Impacta el modelo de datos y el StRS entero | BRS / StRS | **ABIERTA — bloqueante para §4** |
| ~~Q-017~~ | ~~¿Cómo se verifica OB-5 y OB-7?~~ | BRS | **RESPONDIDA → E-021. Aprobada.** |
| ~~Q-011b~~ | ~~¿Qué necesita el jardinero de la red social?~~ | ~~BRS~~ → **StRS** | **RECLASIFICADA → Q-016** (ver E-011) |
| ~~Q-016~~ | ~~¿Qué necesita la jardinería de la red social?~~ | StRS | **RESPONDIDA → E-018** |
| **Q-012** | **¿Qué monetization concretiza "productos relacionados"? ¿Marketplace, catálogo de la marca, recomendación?** | BRS | **ABIERTA** |
| Q-002 | ¿Qué significa "Network" en el producto? Parcialmente contestado (red social, no multi-organización). Falta confirmar si existe dimensión institucional además de la social | BRS | Abierta |
| Q-003 | ¿La meta es certificación ISO formal, o disciplina de trazabilidad interna? Cambia el rigor exigido | Transversal | Abierta |
| Q-004 | ¿La app debe operar con el teléfono bloqueado / en segundo plano? | ARQ | Abierta (reasignada) |
| Q-005 | ¿Quién emite el push: el backend o el frontend? | SyRS/SRS | Abierta |
| ~~Q-006~~ | ~~¿Qué representa el GPS de una maceta?~~ | SyRS | **RESPONDIDA en D-03 (E-027).** La ubicación de la maceta es un dato de la plataforma. **El GPS del dispositivo se descarta** para resolver la región, y **SYR-24 queda desactivado** |
| Q-007 | ¿El frontend *crea* el registro de maceta o solo *asocia* una ya registrada? Afecta el modelo de estados | SRS | Abierta |
| Q-008 | ¿Hay superficie de administración en la app móvil? Nota: es **crítica** si hay moderación de red social | StRS | Abierta |
| Q-009 | ¿Idioma de la UI: español, inglés, ambos? "Internacional" sugiere multilingüe | SRS | Abierta |
| Q-010 | Confirmar título y alcance exacto de ISO 32675 antes de asignarle derivados | Transversal | Abierta |
| Q-013 | ¿Hay moderación de contenido? Una red social con fotos de usuarios lo exige en la mayoría de jurisdicciones | StRS | Abierta |
| ~~Q-014~~ | ~~¿La maceta se vende en e-commerce propio o en retail de terceros?~~ | BRS | **RESPONDIDA → E-014** (e-commerce propio) |

### 5.2 Proxies de verificación — APROBADOS (Q-017)

**Estado: aprobado por el usuario (E-021).** Vigente. Texto normativo en `BRS.md` §7.

| Objetivo | No medible directamente | Proxy aprobado |
|---|---|---|
| OB-5 bienestar socioemocional | "el usuario se siente bien" | Duración de sesión voluntaria; uso **no** ligado a compra |
| OB-6 responsabilidad en el cuidado | "el usuario es responsable" | Supervivencia de la planta a 90 días; ratio de alertas atendidas vs. ignoradas |
| OB-7 comunidad / pertenencia | "el usuario se siente parte" | Conexiones mantenidas a 30 días; contribución generada; retorno tras 7 días de inactividad |

**RN-05 (nuevo, en BRS §7).** Un proxy no es el objetivo. Si el proxy se cumple y el
objetivo no, **se revisa el proxy; no se declara éxito.**

**Limitación aceptada explícitamente al aprobar:** los proxies miden comportamiento
observable, no estado interno. "Sesión voluntaria" mide permanencia en la app, no
bienestar. Se adopta con la limitación declarada, en vez de declarar los objetivos no
verificables.

**Consecuencia:** R-003 pasa de vigente a **mitigado**. La trazabilidad ISO 29119 llega
ahora a los siete objetivos.

---

## 6. Glosario

| Término | Significado en este proyecto |
|---|---|
| **BRS** | Business Requirements Specification — requerimientos de negocio |
| **StRS** | Stakeholder Requirements Specification — requerimientos de partes interesadas |
| **SyRS** | System Requirements Specification — requerimientos de sistema |
| **SRS** | Software Requirements Specification — requerimientos de software |
| **Maceta** | Unidad IoT física: sensores + actuadores. Unidad comercial y ancla de identidad del usuario |
| **Jardinería** | Cuidador de planta. Usuario tipo de la red. Denominación del cliente, en el modelo de negocio |
| **StRS** | Stakeholder Requirements Specification — requerimientos de la persona usuaria (nivel 2) |
| **Red urbana de jardineros** | Masa crítica de jardineros conectados por ubicación. Es el activo principal del negocio |
| **Change** (OpenSpec) | Rebanada vertical de una capacidad, de punta a punta |

---

## 6.1 Hipótesis de monetización (a validar)

Derivadas de E-009, **no son requisitos**. Se listan para poder criticarlas:

| Hipótesis | Lectura |
|---|---|
| H-1 | La maceta es el anzuelo de adquisición, no la fuente de ingreso. Se vende como puerta de entrada a la red |
| H-2 | El ingreso es e-commerce recurrente de productos (sustrato,utrientos, semillas) |
| H-3 | La retención la genera la pertenencia social: el usuario se queda por la red, no por la maceta |
| H-4 | Los datos de la planta son contenido con valor propio, no solo servicio |
| H-5 | Si la red no alcanza masa crítica, la plataforma no tiene valor |

H-5 es el riesgo de negocio más serio: **dos fronts de arranque simultáneos.** La app
tiene que ser útil y tolerable para el primer usuario, cuando la red todavía no
existe. Un producto social que empieza vacío no muestra su valor.

---

## 7. Procedimiento de arranque (reproducible)

Desde cero, en este repositorio:

```bash
mkdir -p ~/Projects/IntPlantNetworkFrontend && cd ~/Projects/IntPlantNetworkFrontend
openspec init --tools opencode        # esquema spec-driven
```

Luego: responder las preguntas de §5 de a una, en orden descendente por nivel del
V-model, registrando cada paso como entrada en §4 antes de avanzar al siguiente.
