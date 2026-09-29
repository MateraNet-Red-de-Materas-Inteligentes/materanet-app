# Tareas — `implementar-sistema-desde-sysr`

> **Alcance de este change.** Este change establece la **línea base SRS** a partir de los 33
> requisitos de sistema de `SyRS.md`. **No implementa el sistema.** Las rebanadas
> verticales de implementación son changes siguientes, conforme a E-017
> (`SyRS.md` §6.2). Las tareas de abajo cierran la línea base, eliminan dependencias
> bloqueantes y dejan declarada la secuencia de rebanadas.
>
> Ninguna tarea de este documento escribe código de producción.

## 1. Dependencias bloqueantes

- [ ] 1.1 **Confirmar SA-04** (`Q-030`): verificar si la plataforma expone autenticación
  que emita token de acceso de vida corta. Es la pregunta bloqueante nº1 del `design.md` y
  la única cuyo resultado adverso invalida ADR-0002 y deja SYR-01, SYR-12, SYR-17 y SYR-19
  sin soporte. **La bitácora registra DD-05 como *«delegado a la plataforma»* (E-027), pero
  eso no confirma que la capacidad exista**: `BRS.md` §8.2 SA-01 sigue sin incluirla.
  **Hecho cuando** la respuesta está registrada por escrito y ADR-0002 pasa a `accepted` o
  se emite un ADR sustituyente.

- [ ] 1.2 **Determinar la interfaz de sesión y de configuración remota** (`Q-031`) que
  expone la plataforma: puntos de entrada, forma del token, mecanismo de renovación,
  formato del documento de configuración. Bloqueante para DD-05 y DD-07. **Hecho cuando**
  existe una descripción utilizable, o se declara que la plataforma aún no la expone y
  DD-05/DD-07 pasan a esperar.

- [ ] 1.3 **Confirmar el supuesto SA-01** (telemetría y comandos de actuadores), del que
  depende el hecho de que el proyecto tenga objeto. **Hecho cuando** está confirmado o se
  escala.

## 2. Recuperación documental

- [x] 2.1 **`BITÁCORA.md` recuperado** del remoto. Tenía 1324 líneas con `E-001…E-028`,
  `Q-001…Q-031`, `DA-01/DA-02` y `SA-01`. El repositorio local estaba por detrás: le
  faltaba el documento y el change `slice-1-listado-regional`. Reconciliado sobre el
  historial remoto, sin borrados (E-029).

- [x] 2.2 **DA-01 y DA-02 contrastados.** Sin discrepancias en sí mismos. **Hallazgo
  colateral:** la bitácora registra DD-02 (Expo/React Native) y DD-06 (región desde la
  cuenta) resueltas en `slice-1`, lo que **contradice** las decisiones tomadas en este
  change. Registrado como reversión explícita en E-031 y E-032, con sus alternativas
  descartadas y sus costos asumidos.

- [x] 2.3 **Decisiones de este change registradas** en `BITÁCORA.md` como E-029…E-036:
  corrección de E-029, migración a `intent-driven` (E-030), DD-02 (E-031), DD-06 (E-032),
  DD-07 (E-033), DD-05 y SA-04 (E-034), supersesión de `slice-1` (E-035) y corrección de
  `config.yaml` (E-036). Actualizados §0 Identificación, §5 (`Q-006` revertida, `Q-030` y
  `Q-031` nuevas) y la fecha de cabecera.

## 3. Base del proyecto

- [ ] 3.1 **Inicializar el control de versiones** en el repositorio. Hoy no es un repositorio
  git, de modo que no hay historial ni trazabilidad de decisiones. **Hecho cuando** `git
  status` funciona y `AGENTS.md` está versionado.

- [ ] 3.2 **Crear el esqueleto del proyecto Kotlin Multiplatform** conforme a ADR-0001, con
  el dominio en `commonMain` y la frontera `expect`/`actual` limitada a almacenamiento
  seguro y ubicación. **Hecho cuando** compila para Android e iOS con la frontera en esos
  dos puntos y en ningún otro.

- [ ] 3.3 **Establecer la disciplina de tests del dominio en `commonMain`** exigida por
  ADR-0001, ejecutables sin referencia a ninguna plataforma. **Hecho cuando** la suite pasa
  sin emulador ni dispositivo.

## 4. Declaraciones que DD-08 exige

- [ ] 4.1 **Declarar la versión mínima de plataforma soportada** (DD-08, SYR-23, SYR-33). No
  es una pregunta al interesado: es una decisión de ingeniería que se declara. **Hecho
  cuando** el valor está declarado en el sistema y es verificable.

- [ ] 4.2 **Declarar el comportamiento ante condiciones no soportadas** (SYR-33). **Hecho
  cuando** cada condición no soportada tiene un comportamiento declarado y comprobable.

- [ ] 4.3 **Fijar y declarar el volumen con el que se ejercita SYR-27** (DD-08, R-009) en el
  plan de pruebas, según ISO 29119. Un requisito de escalabilidad sin techo medido se
  cumple siempre y no dice nada. **Hecho cuando** el volumen está declarado, no supuesto.

- [ ] 4.4 **Fijar el valor por defecto de la regla de acceso** (R-008, Q-026) como dato de
  entrada. No requiere desarrollo: ADR-0004 permite ajustarlo sin release. **Hecho
  cuando** el valor está decidido y registrado.

## 5. Secuencia de rebanadas verticales

> Cada rebanada es un change de OpenSpec propio (E-017). Esta sección define la
> secuencia; **no** es una lista de tareas de implementación.

- [ ] 5.1 **Definir la rebanada 1 — camino mínimo a un listado real**: identidad (sesión),
  resolución de región por GPS, obtención del conjunto y presentación con los atributos de
  SYR-05 a SYR-09. Cubre SR-01…SR-06 y es el camino más corto a un incremento observable.
- [ ] 5.2 **Definir la rebanada 2 — fiabilidad y frescura**: `resultadoConsulta` de tres
  estados (ADR-0005), distinción de fallo y vacío (SYR-21) y antigüedad (SYR-22). Se
  coloca pronto porque afecta a cada consumidor posterior.
- [ ] 5.3 **Definir la rebanada 3 — escalabilidad y filtrado**: fraccionamiento, filtrado y
  distinción parcial/completo (SYR-27…SYR-30), con el volumen declarado en 4.3.
- [ ] 5.4 **Definir la rebanada 4 — atribución y configurabilidad**: macetas propias
  (SYR-13), configuración remota y ajuste independiente (SYR-31, SYR-32).
- [ ] 5.5 **Registrar cada rebanada como change de OpenSpec** con su propio
  `/opsx-propose`, respetando que la implementación no se mezcla en este change.
- [ ] 5.6 **Confirmar el orden con el propietario del proyecto**, dado el criterio de
  `BRS.md` §8.1 (desarrollo unipersonal) y la regla de que la misión precede al comercio
  (RN-01). La secuencia propuesta prioriza el camino a OB-3 y OB-6.

## 6. Verificación de la línea base

- [ ] 6.1 **Verificar la cobertura completa y disjunta de los 33 SYR** en los siete specs:
  cada SYR aparece exactamente una vez y ninguno queda sin cubrir. **Hecho cuando** el
  recuento da 33 sin solapamientos.

- [ ] 6.2 **Verificar que ninguna especificación introduce tecnología, query, tipo ni
  formato** (SYR-15, nota de redacción E-015), y que ninguna resuelve por su cuenta una
  decisión diferida (DD-01, DD-03, DD-04, DD-08). **Hecho cuando** los siete specs son
  agnósticos de implementación.

- [ ] 6.3 **Ejecutar `openspec validate implementar-sistema-desde-sysr --type change
  --strict`** y corregir los hallazgos. **Hecho cuando** la validación pasa sin errores.

- [ ] 6.4 **Archivar la línea base** con `/opsx-archive` una vez confirmadas 1.1, 4.1 y
  4.3, de modo que los specs pasen a `openspec/specs/` y sirvan de contrato a las
  rebanadas de 5.1 a 5.4.
