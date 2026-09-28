# StRS — Stakeholder Requirements Specification
## IntPlant Network — Necesidades de la jardinería

| | |
|---|---|
| **Documento** | StRS — Requisitos de las partes interesadas |
| **Nivel** | 2 de 7 — Stakeholder Requirements |
| **Estándar** | ISO/IEC/IEEE 29148 |
| **Versión** | 0.1 — conjunto base de requisitos |
| **Fecha** | 2026-09-28 |
| **Deriva de** | `BRS.md` v0.1 (aprobado) |
| **Registro de decisiones** | `BITÁCORA.md` — entrada E-018, E-019 |
| **Próximo nivel** | SyRS |

> **Directriz vigente (E-018.m):** documentos lean, conjunto base de requisitos,
> crecimiento por iteración. **Las iteraciones futuras solo agregan requisitos a este
> mismo documento**, con entrada en la tabla de control de cambios. No se crean
> documentos nuevos por iteración.

---

## 1. Sujeto de este documento

**La jardinería** — el cuidador de plantas que compró una maceta inteligente y forma
parte de la red urbana.

Este documento describe **qué necesita la persona**, no cómo se lo proporciona ni quién
lo proporciona. La procedencia de los datos y las reglas de acceso son decisiones de
sistema (nivel SyRS), registradas en `BITÁCORA.md` como DA-01 y DA-02.

---

## 2. Necesidad

La jardinería quiere **conocer las macetas de su región**: qué plantas hay, cómo están
y qué se ha dicho de ellas.

El reconocimiento es **territorial, no social**. La unidad de descubrimiento es la
**región**, no la relación interpersonal: no hay amigos, seguidores ni feed. La red es
un registro compartido de plantas dentro de un área geográfica (E-018).

---

## 3. Requisitos base

| ID | Requisito | Tipo |
|---|---|---|
| **SR-01** | La jardinería debe poder conocer el listado de macetas existentes en una región | Conocimiento |
| **SR-02** | La jardinería debe poder conocer el tipo de planta de cada maceta del listado | Conocimiento |
| **SR-03** | La jardinería debe poder conocer el estado de los sensores de cada maceta del listado | Conocimiento |
| **SR-04** | La jardinería debe poder conocer el estado de la planta de cada maceta del listado | Conocimiento |
| **SR-05** | La jardinería debe poder conocer la ubicación de cada maceta del listado | Conocimiento |
| **SR-06** | La jardinería debe poder conocer los comentarios obtenidos por cada maceta del listado | Conocimiento |

**Nota de nivel (E-019):** estos requisitos dicen **qué** conoce la jardinería. No
especifican de dónde vienen los datos, quién los calcula, ni quién tiene permiso de
verlos. Esas decisiones se toman en SyRS.

---

## 4. Alcance de esta iteración

### 4.1 Incluido

- Conocimiento del conjunto de macetas de una región y de sus atributos (SR-01…SR-06)

### 4.2 No incluido en esta iteración

| Fuera | Motivo | Dónde se trata |
|---|---|---|
| Estructura de la API y origen de los datos | Decisión de sistema | SyRS (DA-01) |
| Reglas de acceso al listado | Regla de sistema | SyRS (DA-02) |
| Control de actuadores (riego, iluminación) | Requisito de las macetas propias, no del listado regional | Iteración posterior |
| Historia y alertas | Requisito de las macetas propias | Iteración posterior |
| Interacción social: publicar, comentar, seguir | No declarado como necesidad base | Iteración posterior |
| Estructura, stack, arquitectura | Decisión de diseño | ARQ / SRS |

---

## 5. Riesgos heredados del BRS

| ID | Riesgo | Impacto en estos requisitos |
|---|---|---|
| **R-005** | **Ampliación de alcance.** E-003 describía las macetas *propias* del usuario; el StRS pide las macetas *de una región*, que incluyen las de otros. No es reformulación, es alcance nuevo | SR-01…SR-06 sustituyen a los "tableros de control" de E-003 en el alcance de esta iteración |
| **R-007** | **La misión queda débilmente servida.** SR-01…SR-06 atienden OB-3 y parcialmente OB-6, pero apenas tocan OB-5 (bienestar socioemocional) y OB-7 (pertenencia ciudadana), que RN-01 coloca por encima de todo lo comercial | **Deuda explícita de la iteración 1.** Requiere atención en las iteraciones siguientes |
| **R-008** | El listado de una región con ubicación es un conjunto de datos personales. "Cualquier usuario del sistema" puede no ser compatible con la normativa aplicable | Resolver en SyRS junto con DA-02 |

---

## 6. Observación de consistencia con el BRS

El BRS establece en RN-01 que la misión prevalece sobre lo comercial, y en OB-5/OB-7
identifica el bienestar socioemocional y el sentido de pertenencia como objetivos de
**primer nivel**.

El conjunto base SR-01…SR-06 describe un **registro compartido de plantas por
territorio**. Es un comienzo legítimo y verificable, y sirve bien a OB-3, pero es
esencialmente un mapa de plantas: **no constituye por sí solo una red social ni
materializa OB-5 u OB-7.**

Esto **no invalida** esta iteración. Queda registrado como deuda consciente: la jerarquía
de objetivos del BRS se cumple de forma completa en iteraciones posteriores, no en esta.

---

## 7. Trazabilidad

### 7.1 Hacia el BRS

| Requisito | Objetivo de negocio que sirve | Regla / riesgo relacionado |
|---|---|---|
| SR-01 listado de macetas de la región | **OB-3** efecto de red | RS-01, RS-02, RN-02 |
| SR-02 tipo de planta | OB-3, OB-6 | RN-03 |
| SR-03 estado de los sensores | **OB-6** responsabilidad en el cuidado | RN-03, R-004 |
| SR-04 estado de la planta | **OB-6** | R-006 → DA-01 |
| SR-05 ubicación | OB-3, OB-7 | R-008 |
| SR-06 comentarios obtenidos | OB-7 comunidad de práctica | — |

### 7.2 Hacia adelante

```
   StRS (este documento)
     -> SyRS   requisitos de sistema: capacidades, reglas de acceso,
                estructura de la API
     -> SRS    requisitos de software   -> openspec/specs/
     -> ARQ    arquitectura (ISO 42010, un solo sistema)
     -> COD    implementación
     -> TEST   verificación (ISO 29119)
```

### 7.3 Decisiones derivadas, de niveles inferiores

Registradas aquí solo para trazabilidad. **No son requisitos de este documento.**

| ID | Decisión | Nivel real | Bitácora |
|---|---|---|---|
| **DA-01** | El frontend obtiene todos sus datos de la API. Estructura a definir más adelante. El frontend no calcula el estado de la planta | SyRS / SRS | E-019 |
| **DA-02** | El listado de una región es visible para cualquier usuario del sistema | SyRS (regla de acceso) | E-019, R-008 |

---

## 8. Control de cambios

| Versión | Fecha | Cambio | Origen |
|---|---|---|---|
| 0.1 | 2026-09-28 | Conjunto base de requisitos. Emitido y aprobado | E-018, E-019 |

**Iteraciones futuras:** agregan requisitos a §3 y actualizan esta tabla. No se crean
documentos nuevos.
