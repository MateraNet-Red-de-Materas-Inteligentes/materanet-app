# `portabilidad-y-verificacion`

Las propiedades que hacen que el andamiaje sea **comprobable** y no una promesa.

> **Por qué este spec existe.** `adr/0001` exige que el dominio viva en `commonMain` y que
> `expect`/`actual` esté limitada a dos puntos, pero una decisión no se verifica sola: hace
> falta un requisito que la compruebe. Además, el entorno de trabajo carece de SDK de
> Android y de toolchain de Apple, de modo que sin una vía de verificación explícita
> cualquier puerta de este change sería una promesa — el defecto que E-029 ya señaló en la
> cadena documental.
>
> **Lo que se verifica y lo que no.** En esta máquina se compila y se prueba el objetivo de
> JVM. Android se compila tras instalar su SDK. **iOS queda declarado y no compilable
> aquí**, porque compilarlo exige macOS. Esa limitación es del entorno, no del sistema, y
> por eso no se escribe como requisito.

## ADDED Requirements

### Requirement: El dominio se compila y se prueba sin emulador ni dispositivo

El sistema SHALL ejecutar la suite de pruebas del dominio en `commonMain` **sin emulador,
sin dispositivo y sin SDK de plataforma móvil**. El dominio SHALL NOT requerir una
plataforma concreta para ser probado.

#### Scenario: La suite del dominio corre sin emulador

- **GIVEN** el proyecto con sus objetivos declarados
- **WHEN** se ejecuta la suite de pruebas del dominio
- **THEN** corre sin emulador
- **AND** corre sin dispositivo conectado
- **AND** no requiere instalar componentes de ninguna plataforma móvil

#### Scenario: La suite corre en un objetivo verificable

- **GIVEN** el objetivo de verificación declarado
- **WHEN** se ejecutan las pruebas sobre él
- **THEN** las pruebas de `commonMain` se ejecutan y su resultado es comprobable

#### Scenario: Un fallo señala lógica de dominio

- **GIVEN** una prueba del dominio que falla
- **WHEN** se examina el fallo
- **THEN** se puede rastrear hasta lógica del dominio
- **AND** no requiere instrumentación de plataforma para reproducirse

#### Scenario: La suite no depende de la red

- **GIVEN** la suite de pruebas del dominio
- **WHEN** se ejecuta
- **THEN** no contacta a ninguna plataforma externa
- **AND** su resultado depende solo del código bajo prueba

### Requirement: La frontera con la plataforma está en exactamente dos puntos

El sistema SHALL limitar `expect`/`actual` a **dos puntos**: almacenamiento seguro y
ubicación. El sistema SHALL NOT introducir un tercer punto de frontera.

El contrato con la plataforma **no es un punto de frontera**: se expresa con tipos e
interfaces de `commonMain` que no requieren `expect`/`actual`, porque la biblioteca de
comunicación es en sí misma multiplataforma. Esto es lo que permite declarar el contrato sin
consumir ninguno de los dos puntos disponibles.

#### Scenario: La frontera se limita a dos puntos

- **GIVEN** el código del sistema
- **WHEN** se inspecciona el uso de `expect` y `actual`
- **THEN** aparece en el punto de almacenamiento seguro
- **AND** aparece en el punto de ubicación
- **AND** no aparece en ningún otro punto

#### Scenario: El contrato no consume un punto de frontera

- **GIVEN** el contrato con la plataforma
- **WHEN** se implementa sus tipos e interfaces
- **THEN** se expresa íntegramente en `commonMain`
- **AND** no requiere `expect`/`actual`

#### Scenario: Un tercer punto exige decisión explícita

- **GIVEN** la necesidad de un punto de frontera adicional
- **WHEN** se propone introducirlo
- **THEN** requiere una decisión arquitectónica registrada y su alternativa descartada
- **AND** no se introduce por conveniencia

### Requirement: El dominio no depende de ninguna plataforma concreta

El sistema SHALL implementar el dominio en una única fuente compartida por todos los
objetivos declarados. El sistema SHALL NOT duplicar lógica de dominio por plataforma.

#### Scenario: Una sola fuente para todos los objetivos

- **GIVEN** los objetivos declarados en el proyecto
- **WHEN** se compila el dominio para cada uno
- **THEN** todos consumen el mismo código de `commonMain`
- **AND** ninguno exige una copia del dominio

#### Scenario: La lógica de dominio no está duplicada

- **GIVEN** una regla de negocio
- **WHEN** se busca su implementación
- **THEN** existe una sola implementación
- **AND** no está replicada por plataforma

### Requirement: Una compilación de distribución no puede llevar el doble de prueba

El sistema SHALL impedir que una compilación de distribución incluya el doble de prueba de
la plataforma. El sistema SHALL NOT condicionar el comportamiento según el tipo de
compilación: la separación se logra por **composición**, no por banderas.

Esta es una propiedad del **artefacto entregado**, no del doble de prueba. Por eso es un
requisito y no tarea: una aplicación publicada que simule datos de plataforma sería un
fallo de seguridad funcional tanto como lo sería una que confunda un fallo con una región
sin macetas (SYR-21).

#### Scenario: La distribución referencia solo el adaptador real

- **GIVEN** una compilación de distribución
- **WHEN** se examina qué implementación de la plataforma usa
- **THEN** es el adaptador real
- **AND** el doble de prueba no forma parte del artefacto

#### Scenario: Sin adaptador real, la distribución no compila

- **GIVEN** que el adaptador real todavía no existe
- **WHEN** se construye la variante de distribución
- **THEN** la construcción falla
- **AND** no se produce un artefacto que carezca del adaptador

#### Scenario: No hay selección por tipo de compilación

- **GIVEN** el código que decide qué implementación de la plataforma se usa
- **WHEN** se busca una condición sobre depuración o tipo de compilación
- **THEN** no existe tal condición
- **AND** la elección se hace por composición, en el punto de composición
