# Design

## Context

Ver `proposal.md` para la motivación. Este documento cubre el **cómo** de la primera
rebanada, y tiene que resolver tres decisiones que `SyRS.md` había enviado a este nivel y
que ya no pueden diferirse más: **DD-01** (esquema de la API), **DD-05** (mecanismo de
identidad) y **DD-06** (criterio de resolución de región).

Restricciones heredadas que condicionan el diseño:

- El frontend **no calcula** valores de dominio. El estado de la planta se recibe (SYR-R02).
- El frontend **no conoce** el estado de desarrollo de las macetas (SYR-R03).
- El cliente **no contiene** secretos de la plataforma (SYR-18, SYR-20).
- Todo dato proviene de la plataforma. No hay base de datos propia (SYR-R01).

Los 33 requisitos de sistema de `SyRS.md` v0.4 se detallan en los cuatro archivos de
`specs/`. Este documento no los repite.

## Goals / Non-Goals

**Goals:**

- Descartar **SA-01** temprano: comprobar con una consulta real que la plataforma sirve el
  conjunto de macetas por región. Si no lo sirve, el proyecto se detiene aquí y no después.
- Dejar una base de verificación (ISO 29119) sobre la que las siguientes rebanadas
  engrosen sin rehacer.
- Satisfacer SYR-27…SYR-30 **por construcción**, no por lucky guess: el diseño no debe
  contener un techo de volumen.
- Resolver DD-01, DD-05 y DD-06 con la menor cantidad de supuestos inventados.

**Non-Goals (nivel de diseño, no de alcance):**

- Cualquier mecanismo de presentación concreto más allá de lo que SYR-10 y SYR-26 exigen.
- Persistencia local de datos de dominio. Nada de lo obtenido se guarda.
- Notificaciones, alarmas, sincronización en segundo plano: nada de esto se necesita para
  esta rebanada.
- Cualquier estructura de la plataforma que no el sistema requiera.

## Decisions

### D-01 — Expo / React Native, no PWA

**Resuelve DD-02.** El criterio diferido en E-008 era *"si la app debe operar con el
teléfono bloqueado o en segundo plano"*. **En esta rebanada, no.** El conjunto de macetas
de una región se consulta y se presenta mientras la persona está mirando la pantalla. No
hay tarea diferida, no hay notificación, no hay sincronización programada.

Un PWA resolvería la rebanada y sería más barato de distribuir. Se elige nativo de todos
modos por una razón que no es de esta rebanada: **E-003 ya contempla alertas e historial**,
que sí exigen operación en segundo plano, y RN-01 coloca el cuidado de la planta (OB-6)
por encima de la comodidad comercial. Cambiar de plataforma después costaría más que
elegirla ahora. Decidir PWA hoy sería optimize-lo-que-viene.

**Alternativa considerada:** PWA con service worker. Descartada por lo anterior. Si en una
iteración posterior se confirmara que las alertas **no** son parte del producto, la
decisión es revisable.

### D-02 — La plataforma expone una capacidad de listado; el cliente define el contrato mínimo

**Resuelve DD-01.** El sistema requiere exactamente esto de la plataforma, y nada más:

| Capacidad | Requisitos que la necesitan |
|---|---|
| Establecer identidad de una cuenta | SYR-01, SYR-17 |
| Determinar la región de la cuenta | SYR-02 |
| Obtener el conjunto de macetas de una región, con sus atributos | SYR-04…09, SYR-27…30 |
| Filtros aplicables al conjunto | SYR-29 |
| Fecha de los datos, cuando la plataforma la tenga | SYR-22 |
| Indicación de qué macetas son de la cuenta | SYR-13 |

**Lo que se le pide a la plataforma: nada.** Ni fraccionamiento, ni un tamaño máximo, ni
ninguna capacidad que hoy no exista. **El sistema no puede asumir que el conjunto cabe, y
por lo tanto no puede depender de que la plataforma lo separe**: tiene que funcionar con
lo que llegue. Ver D-08.

**Lo que NO se le pide:** campos concretos, tipos, forma de la respuesta. El cliente
consume lo que la plataforma publique; el contrato se ajusta a lo existente.

**Dato de verifibilidad:** el grupo 1 de `tasks.md` registra la respuesta cruda de la
plataforma. Con eso se sabe qué capacidades existen hoy, **sin exigir ninguna**.

### D-03 — La región se resuelve desde la cuenta, no desde el GPS del dispositivo

**Resuelve DD-06 y desactiva SYR-24.** Tres criterios, en orden:

1. **Correctitud del acceso.** La región determina **qué se puede ver** (SYR-12, R-008).
   Resolverla por GPS significa que la ubicación del teléfono decide la visibilidad de
   datos personales. Eso es un control más débil que la pertenencia a la cuenta, y con
   SYR-31 la regla de acceso es ajustable pero **la región no debería ser**.
2. **La maceta ya tiene dueña.** La maceta inteligente se compra y vincula a una cuenta
   (E-009). La pertenencia territorial del producto ya existe en el negocio: la maceta
   está en un territorio. Pedirle a la persona que lo demuestre con el teléfono es
   redundante.
3. **SYR-24 se desactiva.** Al no usar GPS, el requisito no aplica, y el fragmento de
   `specs/atributos-maceta` sobre no-confusión queda como **red de seguridad**: si más
   adelante se usa ubicación, la especificación ya impide que se confunda con la
   ubicación de la maceta.

**Consecuencia asumida:** la persona no ve macetas de una región mientras no tenga
macetas ahí. Es el comportamiento correcto para un modelo de red territorial, y es
justo lo que RN-03 ya exige para el conjunto vacío.

**Alternativa considerada:** resolver por GPS. Descartada por el criterio 1.

### D-04 — La identidad se delega a la plataforma; el cliente no la implementa

**Resuelve DD-05.** SYR-01 declara la **capacidad** de establecer identidad, no el
mecanismo. El diseño delega: la plataforma autentica, el cliente opera bajo esa identidad.

Esto **no contradice SYR-18 ni SYR-20.** El cliente no contiene secretos: usa el flujo que
la plataforma publica. Y si ese flujo entrega un token, el token vive en almacenamiento
seguro del sistema operativo, no en el código.

**Lo que esta decisión NO difiere:** qué es una sesión, cuánto dura, si se renueva. Eso
depende de la plataforma y **no se especifica acá**. Se registra como dependencia, no como
requisito pendiente.

**Consecuencia de seguridad que sí se asume:** si la sesión expira en uso, el sistema
debe distinguir "no autorizado" de "fallo de plataforma" — es la misma distinción que
SYR-21 exige para el conjunto.

### D-05 — Sin caché de datos de dominio

El sistema no persiste datos obtenidos de la plataforma. Cada consulta va a la plataforma.

**Por qué:** SYR-22 exige indicar la antigüedad de la información. Un dato cacheado en
el cliente tiene una antigüedad que el cliente no puede verificar, y presentarlo sin
decirlo contradice SYR-22 y SYR-21. Además, SYR-R01 deja al cliente sin base de datos
propia: el sistema no tiene dónde persistir.

**Alternativa considerada:** cachear para trabajar con mala red. Descartada porque
introduce un estado que SYR-21 y SYR-22 obligarían a distinguir, y esa distinción ya es
un requisito. **DP-05 (condiciones de red) queda por tanto respondido: sin caché, el
sistema depende de la red, y eso se acepta explícitamente.**

### D-06 — La regla de acceso es un parámetro, con valor por defecto igual a DA-02

SYR-31 exige que la regla sea ajustable sin modificar código. Esto se cumple con un
único parámetro, cuyo valor inicial es "cualquier usuario del sistema ve el listado de su
región" (DA-02).

**Límite declarado:** endurecer la regla es cambiar un valor. **Cambiar la regla no es
gratis en la plataforma** — si la restricción requiere una comprobación que la plataforma
no hace, hay que pedirla. La configurableidad del cliente no crea capacidades del
servidor. **Esto es lo que R-008 sigue siendo:** no un requisito ausente, una
dependencia de otro sistema.

### D-07 — Techo de volumen declarado, no supuesto

SYR-27 no requiere conocer el volumen de antemano. R-009 exige que el techo **se mida y se
declare**. El diseño no pone un número: lo pone **el plan de pruebas**, cuando sepa qué
está midiendo.

**Lo que el diseño sí garantiza:** ninguna parte del cliente asume que el conjunto cabe.
Ver D-08.

### D-08 — El cliente se adapta al conjunto que llegue, sea entero o por partes

**Corrige D-02 (E-028).** El sistema trata el conjunto recibido como un flujo, no como un
valor que se espera completo:

| Si la plataforma entrega | El sistema |
|---|---|
| El conjunto entero | Lo procesa y lo presenta. Fracciona **la presentación** si hace falta |
| El conjunto por partes | Las acumula y sigue pidiendo hasta agotarlo |
| El conjunto por páginas, con indicador de fin | Las consume y sabe cuándo terminó |

**En los tres casos** se cumple lo mismo: ninguna parte del cliente contiene un techo de
volumen, y el conjunto completo es alcanzable (SYR-28).

**Por qué esta decisión y no exigir fraccionamiento a la plataforma:** exigir una
capacidad que hoy no existe es una dependencia sobre otro sistema, y una dependencia
puede frenar el proyecto. **Adaptarse es siempre posible; exigir no.** El diseño se hace
para el peor caso —que es que la plataforma entregue todo junto— porque en ese caso igual
funciona.

**Lo que se pierde:** con el conjunto entero, la primera pantalla espera a que llegue
todo. Es una disadvanntage aceptada a cambio de no bloquear el proyecto. Si el volumen
resultara grande, la optimización es agregar fraccionamiento **en la plataforma**, y en
ese momento se tendrá la medición real que hoy no existe (D-07).

## Risks / Trade-offs

**[SA-01: la plataforma no sirve el conjunto por región]** → Es la dependencia más grave y
la primera tarea la comprueba con una consulta real, antes de construir interfaz. Si
falla, el proyecto se detiene aquí. **Es el riesgo que justifica esta rebanada.**

**[El conjunto llega entero y es grande]** → D-08 lo maneja: la presentación se
fracciona aunque la obtención no. La primera pantalla puede esperar más de lo desirable.
Mitigación: se mide (D-07) y, si molesta, se pide fraccionamiento a la plataforma **con
datos en mano**, no como exigencia previa.

**[R-008: el valor por defecto de la regla no es el correcto]** → Mitigación: D-06 deja el
ajuste como un parámetro, sin desarrollo. Endurecerlo requiere cambio en la plataforma, y
eso **no** se puede resolver desde este repositorio. Riesgo transferido, no eliminado.

**[D-01:Expo era la decisión equivocada]** → Si las alertas de E-003 se caen, PWA habría
sido más barato. Mitigación: la decisión se revisó **explícitamente** contra el criterio
de E-008 y se favorizó nativo por RN-01, no por conveniencia técnica. Si el alcance de
alertas se cae, la decisión es revisable y el cambio está documentado aquí.

**[Sin caché, la experiencia con mala red es peor]** → D-05 lo acepta explícitamente. Es un
compromiso a favor de la corrección (SYR-21, SYR-22) sobre la comodidad. Si más adelante
la usabilidad con red pobre resulta crítica, **cambiarlo es un cambio de diseño, no un
parche**, porque el estado cacheado introduce distinciones que los requisitos ya exigen.

**[R-007: la misión sigue sin materializarse]** → No es riesgo de esta rebanada: es deuda
ya declarada en el StRS. Un listado de plantas en el territorio no es una red social. La
rebanada no empeora R-007, pero tampoco lo salda.

## Migration Plan

No aplica: no hay software previo, no hay datos previos, no hay usuarios previos. La
primera versión se despliega sin migración.

**Reversión:** eliminar el despliegue. No hay estado que revertir.

## Open Questions

**Ninguna de las tres puede invalidar el diseño.** Las tres son datos de entrada sobre
sistemas externos, y D-08, D-06 y D-07 ya están diseñadas para no depender de ellas.

**O-01 — ¿La plataforma expone fraccionamiento del conjunto de macetas?**
*Diferible, y no la resuelve el usuario.* Es una pregunta factual sobre un sistema
externo, y **no bloquea nada**: D-08 funciona con y sin fraccionamiento. Se responde al
registrar la respuesta cruda en la tarea 1.3, porque **de esa respuesta sale la medición
real** que hoy no existe. **Cambiada en E-028:** en la versión anterior esta pregunta
bloqueaba el diseño y le exigía una capacidad a otro sistema. Ya no.

**O-02 — ¿Qué valor por defecto toma la regla de acceso en la jurisdicción del cluster?**
*Diferible a antes de F1.* No cambia el diseño —D-06 ya lo hace parámetro— ni los
requisitos. Solo fija el valor inicial de un parámetro que ya existe. **Cierra Q-026.**

**O-03 — ¿Qué versión mínima de plataforma declara el sistema?**
*Diferible a la declaración de soporte.* SYR-23 y SYR-33 exigen que el alcance **esté
declarado**, no que se declare en este documento. El valor se fija cuando se elija la
tecnología de distribución. **Cierra DD-08.**
