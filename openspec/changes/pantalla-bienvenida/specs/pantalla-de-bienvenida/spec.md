# Pantalla de bienvenida

## ADDED Requirements

### Requirement: La pantalla se muestra una sola vez

La app DEBE (SHALL) mostrar la pantalla de bienvenida mientras el usuario no la haya
aceptado. Una vez aceptada, la app NO DEBE (SHALL NOT) volver a mostrarla, ni en la misma
sesión ni después de cerrar y reabrir la app. La aceptación DEBE persistir entre
ejecuciones de la app.

#### Scenario: Primera apertura

- **GIVEN** una app nunca abierta en este dispositivo
- **WHEN** el usuario la abre
- **THEN** la pantalla de bienvenida es lo único visible

#### Scenario: Apertura posterior a aceptar

- **GIVEN** un usuario que ya pulsó Continuar en una apertura anterior
- **WHEN** cierra la app por completo y la vuelve a abrir
- **THEN** la pantalla de bienvenida no aparece
- **AND** la app muestra la pantalla principal

#### Scenario: Aceptación tras reiniciar el dispositivo

- **GIVEN** un usuario que ya aceptó la pantalla de bienvenida
- **WHEN** el dispositivo se reinicia y el usuario abre la app otra vez
- **THEN** la pantalla de bienvenida no aparece

#### Scenario: Aceptación en cada plataforma

- **GIVEN** la app instalada en Android, en iOS o como programa de escritorio
- **WHEN** el usuario acepta la pantalla y la reabre
- **THEN** la pantalla no vuelve a mostrarse en ninguno de los tres destinos

### Requirement: La pantalla muestra el contenido de MateraNet

La pantalla de bienvenida DEBE (SHALL) mostrar, sobre una imagen de fondo, el logotipo de
MateraNet y el texto `MateraNet`.

#### Scenario: Contenido visible

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario la mira
- **THEN** el logotipo de MateraNet está visible sobre la imagen de fondo
- **AND** el texto `MateraNet` está visible

#### Scenario: Texto exacto

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario lee el nombre de la app
- **THEN** el texto mostrado es `MateraNet`, sin sufijos ni subtítulos

### Requirement: La pantalla ofrece un botón Continuar

La pantalla de bienvenida DEBE (SHALL) incluir un control accionable con el texto
`Continuar`. Ese control DEBE ser la única acción de la pantalla.

#### Scenario: El botón está disponible

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario la mira
- **THEN** encuentra un control con el texto `Continuar`
- **AND** no encuentra ninguna otra acción en la pantalla

#### Scenario: El nombre del control

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario busca la acción disponible
- **THEN** el único control accionable es el que dice `Continuar`

### Requirement: Aceptar la pantalla lleva a la pantalla principal

Cuando el usuario pulsa `Continuar`, la app DEBE (SHALL) dejar de mostrar la pantalla de
bienvenida y mostrar la pantalla principal.

#### Scenario: Navegación

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario pulsa `Continuar`
- **THEN** la pantalla de bienvenida deja de estar visible
- **AND** la pantalla principal queda visible

#### Scenario: Sin aceptar no hay navegación

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario pulsa en cualquier lugar que no sea el botón `Continuar`
- **THEN** la app no cambia de pantalla
- **AND** la pantalla de bienvenida sigue visible

### Requirement: La pantalla se presenta sin deformaciones

La pantalla de bienvenida DEBE (SHALL) presentar su contenido sin deformarlo. La imagen de
fondo DEBE mantener su proporción y cubrir el área visible, recortando el excedente. El
logotipo NO DEBE (SHALL NOT) estirarse. El texto DEBE permanecer legible sobre la imagen de
fondo, y el contenido NO DEBE (SHALL NOT) solaparse con el botón `Continuar`.

#### Scenario: Fondo en pantallas anchas

- **GIVEN** el destino JVM en una ventana ancha
- **WHEN** se muestra la pantalla de bienvenida
- **THEN** la imagen de fondo cubre toda el área visible
- **AND** el rostro de la imagen no aparece estirado

#### Scenario: Logotipo proporcional

- **GIVEN** la pantalla de bienvenida visible en cualquier tamaño de pantalla
- **WHEN** el usuario compara el logotipo con su versión original
- **THEN** el logotipo mantiene su proporción en horizontal y en vertical

#### Scenario: Legibilidad sobre el fondo

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario lee el texto `MateraNet`
- **THEN** el texto se distingue del fondo y se lee sin esfuerzo

#### Scenario: Contenido y botón sin solaparse

- **GIVEN** la pantalla de bienvenida visible
- **WHEN** el usuario mira la pantalla
- **THEN** el logotipo y el texto no se superponen al botón `Continuar`
- **AND** el botón no queda tapado por la imagen de fondo

### Requirement: La app admite orientación vertical y horizontal

La app NO DEBE (SHALL NOT) fijar la orientación de la pantalla. DEBE (SHALL) seguir la
rotación del dispositivo, tanto en Android como en iOS. Al cambiar de orientación, la
pantalla de bienvenida DEBE permanecer visible y en el mismo estado en que estaba.

#### Scenario: Rotación a horizontal

- **GIVEN** la pantalla de bienvenida visible en vertical
- **WHEN** el usuario gira el dispositivo a horizontal
- **THEN** la pantalla de bienvenida sigue visible
- **AND** no vuelve al principio de la app ni se reinicia

#### Scenario: Rotación de vuelta a vertical

- **GIVEN** la pantalla de bienvenida visible en horizontal
- **WHEN** el usuario gira el dispositivo de vuelta a vertical
- **THEN** la pantalla de bienvenida sigue visible

#### Scenario: Arrancar ya en horizontal

- **GIVEN** un dispositivo en orientación horizontal
- **WHEN** el usuario abre la app por primera vez
- **THEN** la pantalla de bienvenida aparece en horizontal

#### Scenario: Girar sin haber aceptado

- **GIVEN** la pantalla de bienvenida visible y sin aceptar
- **WHEN** el usuario gira el dispositivo
- **THEN** la pantalla sigue sin aceptar y el botón `Continuar` sigue disponible

### Requirement: La pantalla se adapta al tamaño disponible

La pantalla de bienvenida DEBE (SHALL) ser visible completa, sin contenido cortado, en
cualquier relación de aspecto y en ambas orientaciones. El contenido DEBE desplazarse
verticalmente si no cabe, y el botón `Continuar` DEBE permanecer alcanzable.

#### Scenario: Teléfono angosto en vertical

- **GIVEN** un teléfono en orientación vertical
- **WHEN** se muestra la pantalla de bienvenida
- **THEN** el logotipo, el texto y el botón son visibles
- **AND** ninguna parte del contenido queda cortada

#### Scenario: Teléfono en horizontal

- **GIVEN** un teléfono en orientación horizontal
- **WHEN** se muestra la pantalla de bienvenida
- **THEN** el logotipo, el texto y el botón son visibles
- **AND** el botón no queda bajo la muesca ni bajo los bordes del sistema

#### Scenario: Pantalla ancha de escritorio

- **GIVEN** el destino JVM en una ventana ancha
- **WHEN** se muestra la pantalla de bienvenida
- **THEN** el contenido es visible y proporcionado

#### Scenario: Contenido que no cabe

- **GIVEN** una ventana lo bastante pequeña como para que el contenido no entre completo
- **WHEN** se muestra la pantalla de bienvenida
- **THEN** el usuario puede desplazarse para alcanzarlo todo
- **AND** el botón `Continuar` sigue siendo alcanzable
