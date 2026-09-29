# Design

## Decisiones

### D-01 — Compose Multiplatform en `commonMain`

La pantalla se escribe una vez en `commonMain` con Compose Multiplatform. Los tres
destinos la renderizan sin duplicarla.

La alternativa era una UI por plataforma —Vistas en Android, SwiftUI en iOS— que obliga a
mantener la misma pantalla tres veces. Es lo contrario de lo que justifica declarar tres
destinos.

### D-02 — `jvm()` se declara para poder verificar en esta máquina

Sin un destino de escritorio, comprobar la pantalla exige un emulador. `jvm()` permite
abrirla y mirarla donde se trabaja.

La ventana se abre a tamaño de teléfono angosto, no a pantalla completa: así la
comprobación de escritorio se parece a la del dispositivo.

### D-03 — La frontera aparece para persistir la aceptación

Guardar un booleano es la primera cosa que no se puede resolver en `commonMain`: cada
plataforma tiene su almacén de preferencias. Se declara la frontera mínima:

```kotlin
// commonMain
expect fun AceptoLaBienvenida(): Boolean
expect fun RegistrarAceptoLaBienvenida()
```

`actual` va a las preferencias del sistema en cada destino: `SharedPreferences` en Android,
`NSUserDefaults` en iOS, `java.util.prefs` en escritorio.

Se descartaron dos alternativas: **SQLDelight**, que es una base de datos para un solo
booleano; y **un archivo propio en el directorio de datos**, que en cada plataforma tiene
otra ruta y otras permisos.

El requisito dice "la aceptación persiste entre ejecuciones" y no "usar
`SharedPreferences`": esa es la frontera entre qué se necesita y cómo se resuelve.

### D-04 — Las imágenes se resuelven como recursos compartidos

El logo y la imagen de fondo se incluyen con `compose.resources`, que los expone igual en
los tres destinos desde un solo lugar en `commonMain`. La alternativa —un recurso por
plataforma en `res/`, `Assets.xcassets` y el classpath— obliga a mantener tres copias que
pueden dejar de coincidir.

Van en `src/commonMain/composeResources/drawable/`, en minúsculas y con guiones
underscore, porque de ahí el plugin genera los accesores. Los dos van en **PNG**, y por eso
importa la resolución de origen: el logo se muestra desde un teléfono angosto hasta una
ventana de escritorio ancha, y un raster chico se ve suave en pantalla grande. Se pide el
logo con 1024 px de ancho o más, y el fondo con 2000 px del lado corto, porque el fondo se
recorta y un original chico se ve borroso.

Si el logo viene exportado a varias densidades, se dejan en `drawable-hdpi/`,
`drawable-xhdpi/` y `drawable-xxhdpi/`, y Android toma la que corresponde a cada pantalla.
Para iOS y escritorio se usa el archivo más grande.

### D-05 — «Profesional» se traduce a propiedades comprobables

«Estilo profesional» no es un requisito: no se puede verificar si dos personas lo pueden
leer distinto. En la spec quedó desglosado en cuatro propiedades que sí se comprueban a ojo
—el fondo mantiene su proporción, el logo no se estira, el texto se lee sobre el fondo, y
nada se solapa con el botón— más la adaptación a tamaños. Si la primera versión no se ve
como debe, se ajusta la implementación: el requisito no se reescribe para que pase.

### D-06 — La orientación se declara por plataforma, no se bloquea

La app sigue la rotación del dispositivo. Eso no se resuelve en `commonMain`: Android
fijaría la orientación salvo que la `Activity` lo indique, y en iOS las orientaciones
soportadas se declaran en el plist. Ambas declaraciones van en el código nativo mínimo de
cada destino.

El contenido usa una sola disposición vertical en las dos orientaciones. No se agrega una
disposición en columnas para horizontal: ningún requisito lo pide, y la versión vertical
recortada ya se ve bien en una pantalla ancha.

El fondo se recorta con `ContentScale.Crop` en ambas orientaciones, y la pantalla respeta
las áreas seguras del sistema —en horizontal la muesca queda a un lado en iOS y el gesto
de retroceso ocupa una franja abajo en Android—, porque el botón `Continuar` no puede
quedar debajo de ninguna de las dos.

## Alcance de esta rebanada

Al pulsar `Continuar` la app muestra la **pantalla principal**, que en esta rebanada está
vacía: no hay otras funcionalidades que mostrar todavía. Se deja el punto de entrada
listo para que la siguiente rebanada la desarrolle.
