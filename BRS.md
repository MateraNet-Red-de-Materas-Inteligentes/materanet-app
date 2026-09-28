# BRS — Business Requirements Specification
## IntPlant Network

| | |
|---|---|
| **Documento** | BRS — Requisitos de Negocio |
| **Producto** | IntPlant Network |
| **Artefacto asociado** | Frontend móvil — repositorio `IntPlantNetworkFrontend` |
| **Estándar** | ISO/IEC/IEEE 29148 |
| **Nivel** | 1 de 7 — Business Requirements |
| **Versión** | 0.2 |
| **Fecha** | 2026-09-28 |
| **Estado** | Aprobado, v0.2 |
| **Trazabilidad** | Este documento → StRS → SyRS → SRS → ARQ → código → pruebas |

> Registro de decisiones y evolución: `BITÁCORA.md` (entradas E-001 … E-016).
> Cada afirmación de este BRS referencia su entrada de bitácora de origen.

---

## 1. Propósito del documento

Establecer los **requerimientos de negocio** de IntPlant Network: la necesidad que lo
origina, los objetivos que persigue, las restricciones que lo acotan, y los riesgos que
lo ameazan.

Este documento **no** describe la solución. No nombra tecnologías, no asigna
responsabilidades a componentes, y no especifica cómo se realiza nada. Describe qué
resultado busca el negocio y bajo qué condiciones.

**Alcance del documento (decisión Q-021, opción A):** este BRS describe **IntPlant
Network como negocio**, no únicamente lo que entrega el repositorio frontend. El
frontend es uno de los medios para lograr los objetivos de negocio; el e-commerce, la
plataforma de gestión y el hardware son externos al proyecto pero forman parte del
negocio y por eso aparecen aquí.

**Lo que explícitamente NO contiene este documento:**

| No contiene | Por qué | Dónde vive |
|---|---|---|
| Stack tecnológico | Es solución, no necesidad | ARQ / SRS |
| Asignación de responsabilidades entre sistemas | El proyecto tiene **un solo sistema**; el resto es caja negra tras la API (E-016) | No aplica |
| Distribución de trabajo, plazos, equipo | Gestión de proyecto | ISO 12207 |
| Estado de desarrollo del hardware | No es responsabilidad del frontend (E-014) | Fuera del proyecto |
| Mecanismos de verificación de objetivos de misión | Son objetivos, no medios de producción | §7 (aprobado) |

---

## 2. Necesidad de negocio

IntPlant Network es una **plataforma comercial** cuyo producto es una **red social de
cuidadores de plantas**.

El modelo tiene dos pasos (E-009):

1. Las plantas se venden **dentro de macetas inteligentes**. Quien compra la maceta se
   convierte en *jardinería* (cuidador) y queda vinculado a esa maceta.
2. Sobre esa base de usuarios se construye una **red urbana de jardineros**, a la que
   se le proveen productos relacionados con plantas, información sobre cuidados, datos
   de la planta, y herramientas de red social.

**Lectura de negocio:** el ingreso no está en la maceta —que actúa como anzuelo de
adopción— sino en lo que la rodea: venta de productos, contenido de cuidado, y la
retención producida por la pertenencia a la red (E-009, H-1 … H-5).

**Necesidad que atiende:** el cuidador de plantas no tiene hoy una forma de saber si
sus plantas están bien sin atención constante, ni un lugar donde compartir y aprender
de su práctica.

**Hallazgo de alcance (E-009):** esta necesidad exige capacidades de red social —perfil,
conexiones, feed, contenido— que **no figuran** en la lista inicial de capacidades del
producto. Esa discrepancia se resolvió en el nivel de requisitos de software, no aquí
(Q-016, StRS).

---

## 3. Objetivos de negocio

### 3.1 Declaración

| ID | Objetivo | Familia |
|---|---|---|
| **OB-1** | Vender más macetas | Comercial |
| **OB-2** | Vender productos a la base instalada | Comercial |
| **OB-3** | Crear y densificar el efecto de red | Comercial / estructural |
| **OB-4** | Retener al cliente | Comercial |
| **OB-5** | Brindar un hobby como alternativa de bienestar socioemocional | Misión |
| **OB-6** | Promover la responsabilidad en el cuidado de seres vivos | Misión |
| **OB-7** | Formar una comunidad de práctica en jardinería urbana distribuida, para aumentar el sentido de pertenencia ciudadana | Misión |

### 3.2 Jerarquía: la misión precede al comercio (E-013)

**Decisión: jerarquía total. La misión es el objetivo de primer nivel; el comercial
es subordinado.**

```
   +------------------------------------------+
   |  1o  MISION    OB-5, OB-6, OB-7           |  <- prevalece todo conflicto
   +------------------------------------------+
   |  2o  COMERCIAL OB-1, OB-2, OB-3, OB-4    |  <- subordinado
   +------------------------------------------+
```

El comercial no se optimiza *además* de la misión, sino **al servicio** de ella.

### 3.3 Regla de decisión ante conflicto (E-013)

> **RN-01.** Ante conflicto entre un objetivo comercial y un objetivo de misión,
> prevalece el objetivo de misión. El negocio debe buscar una vía que no degrade la
> experiencia de cuidado.

Esta regla gobierna la resolución de conflictos en **todos los niveles inferiores** de
la cadena de requisitos. Un nivel inferior que la contradiga es inválido.

**Efectos derivados:**

- **RN-01.a** — El push comercial intrusivo (carrito abandonado, urgencia artificial)
  queda excluido. La presentación comercial es una decisión de negocio *sujeta a
  restricción*, no libre.
- **RN-01.b** — Un defecto de seguridad funcional en el control de actuadores
  contradice **OB-6** directamente. La seguridad funcional deja de ser un atributo de
  calidad opcional y pasa a ser **requisito de cumplimiento del objetivo de primer
  nivel**.

---

## 4. Restricciones de negocio (E-014)

| ID | Restricción | Implicación |
|---|---|---|
| **RS-01** | Distribución **restringida a clusters geográficos**. No es global desde el inicio | La red se construye por concentración territorial, no por cobertura |
| **RS-02** | Lanzamiento **por iteración**, con usuarios seleccionados, hasta alcanzar lanzamiento público | La plataforma debe soportar un acceso restringido por invitación |
| **RS-03** | Canal de venta: **e-commerce propio** | La relación comercial con el usuario es directa |

### 4.1 Consecuencias de RS-01 y RS-02

```
   +-----------+   +-----------+   +-----------+
   |  cluster  |-->|  cluster 2 |-->| publico   |
   |  cerrado  |   | selection |   |           |
   +-----------+   +-----------+   +-----------+
    invitados     invitados        cualquiera
                  por invitation     con maceta
```

- **RN-02.** La plataforma debe admitir un modelo de **cohorte invitada** desde su
  primera versión, no como adaptación posterior.
- **RN-03.** El estado *"usuario sin ninguna conexión social"* no es un caso excepcional
  sino el **estado inicial de toda la base de usuarios** en un lanzamiento por clusters.
  La plataforma debe ser viable en ese estado.
- **RN-04.** La restricción de mercado **no exime** de las obligaciones de protección
  de datos personales: estas aplican desde la primera cohorte, no al abrir al público.

---

## 5. Estrategia de lanzamiento (E-014)

| Fase | Descripción | Riesgo asociado |
|---|---|---|
| **F1** | Cluster cerrado. Cohorte invitada, primer cluster | R-001, R-002 |
| **F2** | Cluster adicional, selección de usuarios | R-001 |
| **F3** | Apertura al público general | — |

**Estructura de arranque en dos frentes:** la red social requiere masa crítica y la
maceta tiene su propio ciclo de producción y distribución. Un producto social que
arranca vacío no muestra su valor: el primer usuario abre la aplicación, no conoce a
nadie, y su única relación es con su propia maceta. **RN-03** existe para cubrir
exactamente este caso.

---

## 6. Riesgos de negocio (E-012, E-013, E-014)

| ID | Riesgo | Origen | Tratamiento |
|---|---|---|---|
| **R-001** | **Arranque en dos frentes.** La red necesita masa crítica; la maceta tiene ciclo lento. Riesgo de arranque en frío | E-009 / H-5 | RN-03 |
| **R-002** | **La jerarquía total puede ser inviable.** Si el negocio no logra vender sin Leverage comercial, el problema es de modelo de negocio, no de producto | E-013 | **Revisar al cierre de la 1ª y 2ª rebanada vertical, no al final del proyecto** |
| **R-003** | **Los objetivos de misión no son verificables.** OB-5 y OB-7 no admiten prueba directa; sin proxy acordado no hay trazabilidad hasta ISO 29119 | E-012 | **Mitigado** — proxies aprobados en §7, RN-05. La limitación del proxy se acepta declarada |
| **R-004** | **Sobreventa de la aplicación contra OB-6.** Un fallo de seguridad funcional en actuadores contradice el objetivo de primer nivel | E-005 / E-013 | RN-01.b |

---

## 7. Verificación de los objetivos de negocio

**Estado: aprobado (E-021, cierra Q-017).** Vigente desde la emisión de este documento.

Los objetivos OB-5 y OB-7 no son medibles directamente. Para permitir trazabilidad hasta
ISO 29119 se adoptan **proxies observables** como criterio de verificación:

| ID | Objetivo | No verificable directamente | Proxy de verificación |
|---|---|---|---|
| **OB-5** | Bienestar socioemocional | "el usuario se siente bien" | Duración de sesión voluntaria; uso **no** ligado a compra |
| **OB-6** | Responsabilidad en el cuidado | "el usuario es responsable" | Supervivencia de la planta a 90 días; ratio de alertas de cuidado atendidas vs. ignoradas |
| **OB-7** | Comunidad de práctica | "el usuario se siente parte" | Conexiones mantenidas a 30 días; contribución generada (no solo consumo); retorno tras 7 días de inactividad |

**RN-05 — Norma de interpretación de los proxies.** Un proxy no es el objetivo: sirve
para hacer discutible el éxito, no para sustituir la intención. Si el proxy se cumple y
el objetivo no, **se revisa el proxy; no se declara éxito**.

**Advertencia aceptada explícitamente al aprobar (E-021):** los proxies miden
comportamiento observable, no estado interno del usuario. En particular, "sesión
voluntaria" mide permanencia en la aplicación, no bienestar, y admite lecturas
contradictorias. Se adopta con esa limitación declarada, en lugar de declarar los
objetivos no verificables.

---

## 8. Restricciones de la actividad y supuestos de negocio

### 8.1 Fuera de este documento

Los siguientes datos son reales y condicionan el proyecto, pero **no son requisitos de
negocio** y no se registran aquí (E-014, E-016):

| Dato | Nivel correcto |
|---|---|
| Desarrollo unipersonal, ~8 h/día | Gestión de proyecto (ISO 12207). Confirma el método de rebanadas verticales |
| Al frontend no le corresponde el estado de desarrollo de las macetas | Principio de arquitectura / supuesto de sistema (SyRS) |
| El proyecto tiene un solo sistema; el resto es caja negra tras la API GraphQL | E-016. Restricción estructural, no requisito de negocio |

### 8.2 Supuestos

| ID | Supuesto | Si resulta falso |
|---|---|---|
| **SA-01** | Existe una capacidad de plataforma, accesible por API, que provee telemetría y comandos de actuadores | El proyecto no tiene objeto; se detiene aquí |
| **SA-02** | La API GraphQL es estable o versionada | Se agrega trabajo de versionado al SRS; riesgo de acoplamiento |
| **SA-03** | El usuario ya dispone de macetas compradas por el e-commerce propio | Si no, el alta desde cero entra en alcance |

---

## 9. Partes interesadas

Identificación mínima para estructurar el StRS. **No se desarrollan aquí sus
necesidades**: eso es el nivel siguiente.

| Interesado | Interés en el proyecto | Rol en la cadena |
|---|---|---|
| **Jardinería** (cuidador) | Cuidar su planta con criterio; pertenecer a su barrio | Usuario final. Sujeto del StRS |
| **Negocio de IntPlant Network** | Vender macetas y productos; sostener la red | Dueño de OB-1 … OB-4, y de OB-5 … OB-7 |
| **Marco legal / protección de datos** | Cumplimiento de la normativa de datos personales | Restricción de StRS y SyRS (Q-019, Q-020) |

**Pendiente para StRS (Q-016, Q-019, Q-020):** necesidades concretas de la jardinería
frente a la red social; y modelo de privacidad de las conexiones entre jardineros.

---

## 10. Glosario

| Término | Significado |
|---|---|
| **Jardinería** | Cuidador de planta. Usuario tipo de la red. Denominación del cliente en el modelo de negocio |
| **Maceta inteligente** | Unidad IoT física: sensores (humedad, temperatura, ubicación) y actuadores (riego, iluminación, calefacción). Unidad comercial y ancla de identidad del usuario |
| **Red urbana de jardineros** | Masa crítica de jardineros conectados por ubicación. Activo principal del negocio |
| **Cluster** | Concentración territorial inicial de la red, por RS-01 |
| **Cohorte invitada** | Conjunto de usuarios de acceso restringido en un cluster, por RS-02 |
| **Caja negra** | Sistema externo abstraído tras la API, cuya implementación no es responsabilidad de este proyecto (E-016) |
| **Rebanada vertical** | Unidad de trabajo: una capacidad de punta a punta, de requisitos a pruebas |
| **RN / RS / R / SA** | Regla de negocio / Restricción / Riesgo / Supuesto |
| **Proxy de verificación** | Criterio observable aprobado que sustituye a la medición directa de un objetivo no cuantificable (RN-05) |

---

## 11. Trazabilidad

### 11.1 Trazabilidad vertical (cadena ISO 29148)

```
   BRS (este documento)
     -> StRS   necesidades de la jardinería frente a la red social  [PENDIENTE]
     -> SyRS   requisitos de sistema
     -> SRS    requisitos de software   -> openspec/specs/
     -> ARQ    arquitectura (ISO 42010, un solo sistema)
     -> COD    implementación
     -> TEST   verificación (ISO 29119)
```

### 11.2 Trazabilidad interna de este documento

| Requisito / regla | Objetivo que sirve | Riesgos mitigados |
|---|---|---|
| RN-01 | OB-5, OB-6, OB-7 sobre OB-1…OB-4 | R-002 |
| RN-05 (§7) | Verificación de OB-5, OB-6, OB-7 | R-003 |
| RN-01.b | OB-6 | R-004 |
| RN-02, RN-03 | OB-3, OB-7 | R-001 |
| RN-04 | OB-6 | R-004 |
| RS-01, RS-02 | OB-3 | R-001 |

### 11.3 Trazabilidad hacia atrás (origen de cada decisión)

| Sección | Bitácora |
|---|---|
| §2 Necesidad | E-009 |
| §3 Objetivos | E-012 |
| §3.2 Jerarquía | E-013 |
| §3.3 Regla de decisión | E-013 |
| §4 Restricciones | E-014 |
| §5 Estrategia | E-014 |
| §6 Riesgos | E-005, E-012, E-013, E-014 |
| §7 Verificación | E-012 → **aprobado E-021** |
| §8 Fuera de documento | E-014, E-016 |
| §1 Alcance A | Q-021 (respuesta del usuario) |

---

## 12. Control de cambios

| Versión | Fecha | Cambio | Origen |
|---|---|---|---|
| 0.1 | 2026-09-28 | Emisión inicial | E-009 … E-016, Q-021 |
| 0.2 | 2026-09-28 | §7 aprobado: proxies de verificación de OB-5/OB-6/OB-7 adoptados; se agrega RN-05. R-003 pasa a mitigado | E-021 |

**Próximo paso del ciclo:** con este BRS cerrado, iniciar el **StRS** (Q-016, Q-019,
Q-020) para derivar las necesidades de la jardinería frente a la red social.
