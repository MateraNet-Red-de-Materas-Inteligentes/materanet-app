---
status: "accepted"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# El contrato con la plataforma lo declara el cliente

## Context and Problem Statement

`SyRS.md` §5 difirió **DD-01** —el esquema de la API de la plataforma— y la forma real sigue
sin conocerse (`Q-025`). La plataforma es caja negra (`SyRS.md` SYR-R01), y el cliente
necesita hoy cinco capacidades de ella para operar.

E-027 intentó resolver DD-01 declarando que el sistema pide *seis* capacidades a la
plataforma, entre ellas fraccionamiento. E-028 lo corrigió: exigir una capacidad que otro
sistema no tiene convierte la incertidumbre en dependencia, y *"un requisito que depende de
otro sistema, y que ese sistema no tiene, es un riesgo disfrazado de especificación"*. El
principio reusable que quedó asentado: **adaptarse siempre es posible; exigir no**.

Consecuencia añadida por E-031 y E-032: DD-02 pasó a Kotlin Multiplatform y DD-06 resolvió
la región por el GPS del dispositivo, con lo que la capacidad *determinar la región de la
cuenta* dejó de ser necesaria. E-034 volvió a plantear DD-05 con estatus `proposed`, porque
`SA-04` —si la plataforma expone autenticación— no está confirmada (`BRS.md` §8.2 describe la
capacidad de la plataforma como «telemetría y comandos de actuadores», sin mencionar
autenticación).

## Decision Drivers

- No crear dependencias que la plataforma no puede satisfacer
- Que el cliente sea verificable sin depender de que la plataforma responda
- Evitar que cada rebanada reinterprete qué necesita de la plataforma
- Reconciliar DD-01 con lo ya decidido en ADR-0001, ADR-0003 y ADR-0004

## Considered Options

- Declarar el contrato como lista de capacidades que la plataforma *debería* exponer
- Mantener DD-01 abierta hasta conocer el API real, y no construir nada antes
- Declarar el contrato mínimo desde el cliente, y no exigir ninguno

## Decision Outcome

Chosen option: **declarar el contrato mínimo desde el cliente, y no exigir ninguno**, porque
es la única de las tres que permite construir y verificar sin bloquear, y porque no crea
ningún compromiso para otro equipo.

El contrato comprises **cinco capacidades**: obtener sesión de plataforma; obtener el
conjunto de macetas de una región con sus atributos; filtros aplicables al conjunto; fecha
de los datos cuando la plataforma la tenga; e indicación de qué macetas son de la cuenta.

**La capacidad *determinar la región de la cuenta* se retira**, porque ADR-0003 resuelve la
región por el dispositivo. Pedirla sería pedir menos de lo necesario.

**Lo que se le pide a la plataforma: nada.** Ni fraccionamiento, ni tamaño máximo, ni campos
concretos, ni tipos, ni forma de respuesta. El conjunto se obtiene **entero, por partes o
por páginas**, sin suponer que cabe. La fecha y la pertenencia a la cuenta son
**opcionales**: si la plataforma no las da, el sistema declara que las desconoce en lugar de
afirmar que un dato es actual.

El contrato se expresa **en el vocabulario del cliente**. La forma real del API sigue siendo
`Q-025`; el adaptador real se escribe después, contra lo que exista. Un cambio en la
plataforma queda confinado al adaptador y no alcanza al dominio.

### Consequences

- Good, because la primera rebanada se puede construir y verificar sin esperar a la
  plataforma, y sin gravar a otro equipo con un requisito nuevo.
- Good, because convierte dos incógnitas externas —`SA-04` y `Q-025`— en una costura local, y
  el riesgo de `SA-04` queda confinado a un archivo.
- Good, because fija por escrito las cinco capacidades, de modo que la tarea 1.x del SRS
  sabe qué observar al consultar la plataforma, en vez de explorar sin guion.
- Bad, because el contrato se escribe **antes** de conocer la plataforma real, y puede
  necesitar ajuste. Se mitiga por diseño: el contrato está escrito para el peor caso, así que
  el ajuste es del adaptador.
- Bad, because declarar «fecha de los datos» como opcional puede leerse como que
  SYR-22 se cumple sin ella. No es así: se cumple **declarando la antigüedad desconocida**,
  nunca presentando el dato como actual.
- Follow-up, because `SA-04` sigue abierta (`Q-030`): si resulta falsa, ADR-0002 debe ser
  supersedido por un ADR que describa la identidad sin sesión de plataforma.

## Pros and Cons of the Options

### Declarar el contrato como lista de capacidades que la plataforma debería exponer

- Good, because documenta de forma explícita lo que el sistema necesita.
- Bad, because convierte cada capacidad desconocida en dependencia externa, que es
  exactamente el defecto que E-028 corrigió.
- Bad, because con `SA-04` sin confirmar habría convertido una incógnita en bloqueo de
  proyecto.

### Mantener DD-01 abierta hasta conocer el API real

- Good, because evita escribir un contrato que luego haya que ajustar.
- Bad, because no permite construir nada, y deja el SRS completo sin primer incremento
  construible.
- Bad, porque la forma del API no depende solo del proyecto: puede no estar disponible
  durante todo el proyecto.

### Declarar el contrato mínimo desde el cliente, y no exigir ninguno

- Good, because desbloquea la construcción y la verificación sin crear dependencias.
- Good, because hace explícito lo que el cliente necesita, que es información útil para el
  equipo de la plataforma aunque no sea un requisito.
- Bad, because el contrato puede quedar desalineado con la plataforma real; la mitigación
  es que el ajuste cae en el adaptador.

## More Information

**Reversibilidad.** Alta. El contrato son tipos e interfaces en `commonMain`; ajustar su
forma a la plataforma es cambiar firmas, no reescribir el dominio. La decisión más difícil de
revertir no es esta, sino `Q-030`.

**Punto de reevaluación.** Cuando `Q-025` se responda y se conozca la forma real del API, el
contrato debe contrastarse contra ella. Si una capacidad resultara inexistente en la
plataforma, se registra como dato y se revisa esta decisión —no se convierte en exigencia.

**Relacionado.** ADR-0001 (frontera `commonMain`), ADR-0002 (identidad, estatus `proposed`),
ADR-0003 (región por dispositivo), ADR-0005 (fallo y vacío como estados distintos).
