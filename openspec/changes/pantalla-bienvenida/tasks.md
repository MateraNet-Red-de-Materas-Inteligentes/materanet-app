# Tasks

## 1. Entradas

- [x] 1.1 `logo_materanet.png` (756 × 1046) y `fondo_bienvenida.png` (1672 × 941), en
      `composeApp/src/commonMain/composeResources/drawable/`
- [x] 1.2 SDK de Android en `~/Android/sdk` (`platforms/android-35`, `build-tools/35.0.0`)
- [x] 1.3 La pantalla principal se llama `PantallaPrincipal` y está vacía a propósito

## 2. Esqueleto del proyecto

- [x] 2.1 Proyecto Gradle con KMP y los tres targets declarados (D-01, D-02)
- [x] 2.2 Compose Multiplatform, plugin de escritorio, `compose.resources` con
      `packageOfResClass = com.materanet.resources`
- [x] 2.3 `:composeApp:assemble` compila los targets habilitados

## 3. Persistencia de la aceptación

- [x] 3.1 `expect` en `commonMain` para leer y registrar la aceptación (D-03)
- [x] 3.2 `actual` con `SharedPreferences` (Android), `NSUserDefaults` (iOS) y
      `java.util.prefs` (escritorio)
- [x] 3.3 `jvmTest` comprueba que la aceptación se guarda y queda en disco, y que las
      pruebas no tocan las preferencias reales de la app

## 4. La pantalla

- [x] 4.1 Logo y fondo con `compose.resources` (D-04)
- [x] 4.2 Fondo, logo y texto `MateraNet`
- [x] 4.3 `ContentScale.Crop` en el fondo, `ContentScale.Fit` en el logo (D-05)
- [x] 4.4 Botón `Continuar` como única acción
- [x] 4.5 `Continuar` registra la aceptación y muestra `PantallaPrincipal`
- [x] 4.6 Contenido desplazable con el botón fuera de la zona desplazable
- [x] 4.7 `safeDrawing` respetado en ambas orientaciones (D-06)

## 5. Puntos de entrada por plataforma

- [x] 5.1 `MainActivity` sin orientación fija, con `configChanges` de rotación (D-06)
- [x] 5.2 Ventana de escritorio a 420 × 860 dp (D-02)
- [x] 5.3 `MainViewController` para SwiftUI
- [x] 5.4 `App()` decide la pantalla inicial según la aceptación persistida

## 6. Verificación

- [x] 6.1 `:composeApp:jvmTest` pasa: la aceptación se guarda y persiste
- [x] 6.2 Abrir la ventana de escritorio y mirar la pantalla
- [x] 6.3 Cerrar y reabrir: la pantalla no reaparece
- [x] 6.4 El fondo no se deforma: `Crop` lo recorta y el logo va con `Fit` en caja fija (D-05)
- [ ] 6.5 Arrancar en emulador Android, girar a horizontal, y repetir las comprobaciones
- [x] 6.6 iOS no verificado: los targets quedan deshabilitados en Linux, registrado en D-03

## Qué se comprobó y cómo

6.2 y 6.3 se hicieron mirando la ventana del escritorio, no con una captura. La ventana se
abre a 420 × 860 dp, como pidió D-02.

6.3 quedó respaldado por el estado, no por un ojo: al pulsar `Continuar`, la clave
`bienvenida_aceptada` apareció en `/com/materanet` de las preferencias de escritorio, y
sobrevivió a varios reinicios de la app hasta que se borró a propósito con
`reiniciar-bienvenida.sh`. Los tests cubren el mismo camino.

6.4 no necesita depender de una inspección: `ContentScale.Crop` sobre el fondo y
`ContentScale.Fit` dentro de una caja de tamaño fijo sobre el logo hacen que la deformación
sea imposible por construcción, en cualquier tamaño de ventana. La ventana se miró en su
tamaño angosto, que es el que va a un teléfono; la variante ancha no se miró.

## 6.5 sigue abierta

No hay emulador Android en esta máquina y no se instaló ninguno: `~/Android/sdk` tiene
`platforms`, `build-tools` y `platform-tools`, pero `emulator` y una imagen de sistema no.
Levantarla requiere ese download, que no estaba en ninguna tarea.

Lo que sí quedó comprobado de Android es que `assemble` compila el target y produce los AAR
de debug y release. Que la pantalla se vea bien en horizontal es otra cosa, y sigue sin verse.

## Disposición de los elementos

Reordenar logo, nombre y botón en la pantalla queda a criterio de quien la va a usar, y no
es una tarea de este cambio: el spec no fija una composición, fija que el fondo no se
deforme, que el texto se lea sobre el fondo y que nada se solape con el botón. Si ese
reordenamiento cambia alguno de esos tres comportamientos, hay que actualizar el spec antes.
