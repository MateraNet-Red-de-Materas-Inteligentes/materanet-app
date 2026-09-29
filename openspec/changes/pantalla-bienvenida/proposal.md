# Proposal

## Why

Un usuario que abre la app por primera vez necesita saber qué es y que hay una sola vez para
verlo. La pantalla de bienvenida cumple las dos cosas: presenta MateraNet y, una vez
aceptada, no vuelve a interrumpir.

## What Changes

- La app muestra una pantalla de bienvenida en la primera apertura, con el logo de MateraNet
  sobre una imagen de fondo y el nombre de la app.
- La pantalla tiene un botón `Continuar`. Al pulsarlo, la app pasa a la pantalla principal.
- La app recuerda que la pantalla ya se aceptó y no vuelve a mostrarla, ni tras cerrar la
  app ni tras reiniciar el dispositivo.
- La app funciona en vertical y en horizontal, y sigue la rotación del dispositivo.
- Se declara Kotlin Multiplatform con Android, iOS y JVM, y se instala el SDK de Android
  para poder compilar el destino de Android.

## Capabilities

### New Capabilities

- `pantalla-de-bienvenida`: la pantalla de presentación de MateraNet y la regla de que se
  muestra una sola vez.

### Modified Capabilities

Ninguna. No hay specs previos en el repositorio.

## Impact

- Proyecto Gradle nuevo: primer código de la aplicación.
- Dependencias: Kotlin, Compose Multiplatform, AGP.
- **Faltan los recursos gráficos**: `logo_materanet.png` y `fondo_bienvenida.png`, en
  `src/commonMain/composeResources/drawable/`. La pantalla no se puede construir sin ellos.
- Persistencia: se usa el almacén de preferencias del sistema en cada plataforma, a través
  de la primera frontera `expect`/`actual` del proyecto.
- Requiere el SDK de Android para compilar `androidTarget`.
- iOS no se compila en este entorno: exige macOS con Xcode. El código es compartido.
- Sin backend ni red: la app es local.
