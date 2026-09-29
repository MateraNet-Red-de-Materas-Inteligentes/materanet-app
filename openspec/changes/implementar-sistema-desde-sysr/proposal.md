# Propuesta — `implementar-sistema-desde-sysr`

## Por qué

`SyRS.md` v0.4 (2026-09-28) cierra la derivación de requisitos y declara **33 requisitos de
sistema** para `FRONTEND`, pero la cadena de ISO 29148 se detiene ahí: no existe aún el
nivel **SRS**. `SyRS.md` §6.2 fija el destino — *"SRS requisitos de software →
`openspec/specs/`"* — y `E-017` establece que, a partir de ese nivel, el trabajo se
organiza en **rebanadas verticales manejadas como changes de OpenSpec**.

Sin un SRS no hay rebanadas: no hay contrato de comportamiento del que derivar tareas ni
un punto de referencia verificable frente al cual comprobar que el frontend cumple lo
que el sistema debe hacer. Este change establece esa línea base.

**Esta propuesta es únicamente planificación — no implementa código.** El repositorio no
contiene todavía fuente, `package.json` ni historial git: es el punto de partida
documental, no una migración.

## Qué cambia

Se traduce el nivel de sistema al nivel de software, y se cierran cuatro de las ocho
decisiones diferidas que `SyRS.md` §5 había remitido a este nivel.

**Se establece:**

- La línea base SRS: los 33 SYR como requisitos de software en forma Gherkin, repartidos
  en **7 capacidades** disjuntas cuya suma da exactamente 33.
- La arquitectura de referencia del frontend móvil, con límites de sistema que respeten
  la caja negra (SYR-R01, SYR-15).

**Decisiones diferidas que este change cierra** (`SyRS.md` §5):

| ID | Decisión | Resolución adoptada |
|---|---|---|
| **DD-02** | Tecnología de la interfaz | **Kotlin Multiplatform** (Android + iOS desde `commonMain`) |
| **DD-05** | Cómo se establece la identidad | **Sesión emitida por la plataforma**: token de acceso de vida corta en almacenamiento seguro (Keystore / Keychain). El cliente nunca posee credenciales de la plataforma (SYR-18, SYR-20) |
| **DD-06** | Cómo se resuelve la región | **Ubicación del dispositivo (GPS)**. Consecuencia registrada: **SYR-24 permanece en alcance** y el total se mantiene en 33 (§6.1) |
| **DD-07** | Mecanismo de configuración | **Configuración remota** recuperada en ejecución, con **valor por defecto seguro horneado en el build** como respaldo. Tres claves independientes, nunca un bloque único (SYR-32) |

**Decisiones que siguen diferidas** (por diseño, `SyRS.md` §5):

| ID | Sigue diferida | Motivo |
|---|---|---|
| **DD-01** | Esquema de la API: queries, campos, tipos, paginación | Depende de una plataforma que este proyecto no controla. El *requisito* de interfaz (SYR-14…16) sí es de este nivel y queda especificado; solo el esquema se difiere |
| **DD-03** | Forma de presentación: pantalla, mapa o ambos | Decisión de diseño, no de sistema (SYR-10 no especifica la forma) |
| **DD-04** | Obtención en una o varias consultas | Optimización, no requisito |
| **DD-08** | Versión mínima de plataforma y volumen de verificación de SYR-27 | SYR-23 y SYR-27 exigen que el alcance *sea declarado*, no que se declare aquí |

## Capacidades

### Capacidades nuevas

`openspec/specs/` está vacío, así que las siete son nuevas. El reparto es **disjunto** y
completo: `6 + 2 + 9 + 5 + 4 + 3 + 4 = 33`, sin solapamientos, en la misma disciplina de
reparto que aplica `SyRS.md` §6.1.

| Capacidad | SYR cubiertos | n |
|---|---|---|
| `identidad-y-control-de-acceso` | SYR-01, 12, 17, 18, 19, 20 | 6 |
| `resolucion-de-region` | SYR-02, 24 | 2 |
| `obtencion-de-datos` | SYR-04, 05, 06, 07, 08, 09, 14, 15, 16 | 9 |
| `presentacion-del-listado` | SYR-03, 10, 13, 25, 26 | 5 |
| `escalabilidad-y-filtrado` | SYR-27, 28, 29, 30 | 4 |
| `fiabilidad-y-frescura` | SYR-11, 21, 22 | 3 |
| `configurabilidad-y-mantenibilidad` | SYR-23, 31, 32, 33 | 4 |
| | **Total** | **33** |

- **`identidad-y-control-de-acceso`**: establecer la identidad antes de habilitar
  cualquier capacidad; operar bajo una regla de acceso vigente y ajustable; no exponer ni
  almacenar credenciales de la plataforma de forma recuperable.
- **`resolucion-de-region`**: determinar a qué región corresponde la consulta y usar la
  capacidad de ubicación del dispositivo cuando la resolución lo requiere.
- **`obtencion-de-datos`**: obtener el conjunto de macetas de la región y sus atributos
  (tipo de planta, sensores, estado de planta, ubicación, comentarios) exclusivamente a
  través de la interfaz publicada, sin calcular estado de planta y sin conocer estado
  interno.
- **`presentacion-del-listado`**: distinguir macetas, presentar el conjunto con sus
  atributos consultables, distinguir las macetas propias, y hacerlo navegable y operable
  sin instrucción previa.
- **`escalabilidad-y-filtrado`**: operar sin límite superior conocido de antemano,
  fraccionar la obtención y la presentación, filtrar por los criterios que la plataforma
  exponga, y distinguir conjunto parcial de conjunto completo.
- **`fiabilidad-y-frescura`**: operar sin degradar con conjunto vacío, distinguir la
  imposibilidad de obtener datos de la ausencia de macetas, e indicar la antigüedad de la
  información presentada.
- **`configurabilidad-y-mantenibilidad`**: ajustar la regla de acceso sin modificar
  código, mantener separadas y ajustables de forma independiente las decisiones de
  comportamiento, y declarar el alcance de soporte y el comportamiento ante condiciones no
  soportadas.

### Capacidades modificadas

Ninguna. `openspec/specs/` no contiene especificaciones previas, de modo que no hay
comportamiento existente que este change altere.

## Impacto

**Código.** Ninguno. El repositorio no tiene fuente, `package.json` ni control de
versiones; este change no crea ninguno. Los artefactos produced son exclusivamente
documentales.

**Artefactos OpenSpec.** Se crean siete especificaciones nuevas bajo
`openspec/specs/`, más `design.md`, `adr.md` y `tasks.md` en este change.

**Dependencias externas.** La plataforma sigue siendo caja negra (SYR-R01, SYR-15). Este
change **no modifica** la API ni la plataforma; consume la interfaz publicada.

### Riesgos aceptados y declarados

| Riesgo | Origen | Tratamiento en esta propuesta |
|---|---|---|
| **Permiso de ubicación en primer plano** | Decisión DD-06 | Aceptado. GPS elimina la fricción de selección, pero añade un diálogo de permiso y hace de la precisión del GPS una dependencia de arranque bajo **RN-03** (arranque en frío) |
| **Segundo vector de datos personales** | Decisión DD-06 | Aceptado. La ubicación del dispositivo se suma al conjunto ya señalado por **R-008**. While R-008 sigue abierto en su valor por defecto, la ubicación agrava la exposición |
| **KMP como coste de complejidad** | Decisión DD-02 | Aceptado. Gradle multiplataforma, `expect`/`actual` y un segundo objetivo iOS son carga real para desarrollo unipersonal de ~8 h/día (`BRS.md` §8.1). Se registra en el ADR con su reversibilidad |
| **Enforcement de SYR-19 no es posible en el cliente** | Análisis de diseño | La regla de acceso del cliente gobierna **presentación y refetch**, no seguridad. La protección efectiva debe residir en la plataforma. El spec no debe insinuar que el cliente es una frontera de seguridad |
| **R-009 — techo de escalabilidad sin medir** | `SyRS.md` §4 | SYR-27 no es una promesa de infinitud. El techo debe **medirse y declararse** en el plan de pruebas (DD-08) |

### Supuestos nuevos y declarados

| ID | Supuesto | Si resulta falso |
|---|---|---|
| **SA-04** | La plataforma emite sesiones: expone una capacidad de autenticación que devuelve un token de acceso de vida corta, y es la única autoridad sobre identidad y sobre la regla de acceso | **SYR-01, 12, 17, 19 quedan sin soporte.** No es recuperable desde el cliente: la caja negra (DA-01) impide que el frontend invente identidad, y un identificador de dispositivo no produce un «usuario del sistema» del que SYR-12 pueda hablar, ni un «propio» del que SYR-13 dependa |

> **Este supuesto no está respaldado por ningún documento de la cadena.**
> `BRS.md` §8.2 `SA-01` define la capacidad de plataforma como *"telemetría y comandos de
> actuadores"* — **la autenticación no figura en ella**. La decisión DD-05 queda
> construida sobre una capacidad de plataforma que ningún documento establecido. Es la
> dependencia externa más frágil de esta línea base y debe confirmarse antes de
> implementar.

### Lagunas declaradas en la cadena documental

- **`BITÁCORA.md` no existe.** `BRS.md`, `StRS.md` y `SyRS.md` la citan como registro de
  decisiones y remiten a entradas `E-001…E-026`, `Q-021/026/028/029` y `DA-01/DA-02` que
  no son legibles. **No se reconstruye aquí**: inventar historial de decisiones sería
  peor que declararlo ausente. Las decisiones tomadas en este change quedan registradas en
  su ADR, no en una bitácora que no se posee.
- **Trazabilidad incompleta declarada** (`SyRS.md` §6.1): el tabler de control de E-003
  solo está representado por SYR-13. El tabler en sí no tiene requisito de sistema en esta
  iteración, y esta propuesta no lo añade.

### Fuera de alcance

Control de actuadores (riego, iluminación) — riesgo **R-004** abierto. Historia y alertas.
Interacción social: publicar, comentar, seguir. Estructura de la API (DD-01). Los
"tableros de control" de E-003 salvo por SYR-13.
