---
status: "accepted"
date: 2026-09-29
decision-makers: "Paulo (propietario del producto)"
consulted: "—"
informed: "—"
---

# Kotlin Multiplatform como stack del frontend móvil

## Context and Problem Statement

`FRONTEND` es el único sistema del proyecto; el resto es caja negra tras la API
(`SyRS.md` SYR-R01, SYR-15; E-016). `SyRS.md` §5 difirió **DD-02** —la tecnología de la
interfaz— al nivel de arquitectura, con el criterio de si el sistema debe operar con el
teléfono bloqueado o en segundo plano (E-008).

Para esta iteración el criterio **no obliga**: la operación en segundo plano pertenece al
control de actuadores, que `SyRS.md` §4 mantiene fuera de alcance por el riesgo abierto
**R-004**. La elección queda por tanto abierta entre las alternativas que el propio SyRS
mencionaba —Expo/React Native, PWA, u otra— y el proyecto tiene restricciones que pesan:
desarrollo unipersonal a ~8 h/día (`BRS.md` §8.1), repositorio sin código, y un canal de
venta propio en e-commerce (**RS-03**).

## Decision Drivers

- Coste de arranque bajo para desarrollo unipersonal (~8 h/día, `BRS.md` §8.1)
- Presencia en tiendas como vía de servicio a OB-1 y OB-4
- Reutilización de código con la superficie web del e-commerce (**RS-03**)
- Posibilidad de notificaciones push,probables para alertas de cuidado de **OB-6**
- Compatibilidad con geolocalización del dispositivo (**SYR-24**, ver ADR-0003)

## Considered Options

- Kotlin Multiplatform
- Expo / React Native
- PWA
- Mantener DD-02 abierta y decidir en un change posterior

## Decision Outcome

Chosen option: **Kotlin Multiplatform**, porque el propietario del proyecto lo eligió
explícitamente entre las alternativas ofrecidas, y porque `SyRS.md` §5 admite «otra»
tecnología para DD-02.

Dentro de KMP se adopta compartir **dominio y configuración** en `commonMain`, y duplicar
la capa de plataforma (ubicación, almacenamiento seguro, red) mediante `expect`/`actual`.
La frontera `expect`/`actual` se mantiene en exactamente **dos** puntos: almacenamiento
seguro y ubicación.

### Consequences

- Good, because el dominio y los requisitos de plataforma son verificables en un único
  lugar, y el mismo código sirve a Android e iOS sin duplicar las reglas de negocio.
- Good, because satisface geolocalización y notificaciones push, que una PWA soporta peor en
  iOS.
- Bad, because es **la opción más pesada de las tres** en configuración de build
  (Gradle multiplataforma, `expect`/`actual`, ciclo de publicación en dos tiendas) frente
  a desarrollo unipersonal de ~8 h/día. Es el mayor coste conocido de esta decisión.
- Bad, because un segundo objetivo solo justifica KMP cuando existe un segundo
  consumidor. Android e iOS son el mismo consumidor en dos disinterested formas; la
  reutilización real llegaría con una tercera superficie (web, escritorio), que hoy no
  existe.
- Bad, because el coste se paga **antes** del primer incremento de valor: la rebanada
  vertical más simple no se beneficia de compartir nada.

### Confirmation

El cumplimiento se confirma por los tests del dominio en `commonMain` ejecutándose sin
referencia a ninguna plataforma, y por la ausencia de `expect`/`actual` fuera de los dos
puntos acordados.

## Pros and Cons of the Options

### Kotlin Multiplatform

- Good, because un único núcleo de dominio para Android e iOS.
- Good, because compila a nativo en ambos, con rendimiento y acceso a APIs de plataforma.
- Neutral, because la reutilización con el e-commerce (RS-03) es posible pero no está
  comprometida: exige `jsMain`, que no se asume.
- Bad, because build y ciclo de publicación más complejos que cualquier alternativa.
- Bad, because la ventaja se materializa solo con una tercera superficie.

### Expo / React Native

- Good, because `EAS Build` elimina la carga del toolchain nativo para un desarrollador
  único.
- Good, because reutilización real con una futura superficie web (RS-03).
- Bad, because se descartó por decisión explícita del propietario del proyecto.

### PWA

- Good, because la ruta más rápida a la primera rebanada y la de menor coste de
  despliegue: archivos estáticos, sin tienda ni firma.
- Good, because la geolocalización (SYR-24) funciona en ambos sistemas.
- Bad, because soporte más débil en iOS y sin notificaciones push fiables, lo que dificulta
  servir a OB-6 en iteraciones posteriores.
- Bad, because exige una base de código separada del e-commerce.
- Bad, because se descartó por decisión explícita del propietario del proyecto.

### Mantener DD-02 abierta

- Good, because evita decidir sin evidencia suficiente.
- Bad, because **SYR-32** exige que regla de acceso, resolución de región y criterios de
  filtrado sean ajustables de forma independiente, y eso es difícil de garantizar sin
  saber dónde vive la configuración en tiempo de ejecución.
- Bad, porque el diseño quedaría sin runtime y los cuatro niveles C4 quedarían sin
  fronteras de contenedor.

## More Information

**Reversibilidad.** Volver a un único objetivo es un cambio acotado mientras no exista un
segundo consumidor: se borra `expect`/`actual` y se elige una implementación. Esa
reversibilidad es la razón por la que esta decisión, pese a su coste, no es
irrecuperable.

**Punto de reevaluación.** Cuando aparezca una tercera superficie (web del e-commerce,
escritorio), o cuando el coste de build se perciba como bloqueante frente al avance de las
rebanadas verticales, esta decisión debe revisarse.

**Relacionado.** ADR-0002 (identidad), ADR-0003 (región), ADR-0004 (configuración).
