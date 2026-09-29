# Tareas — `andamiaje-kmp`

> **Alcance de este change.** Construye el proyecto Kotlin Multiplatform con su frontera
> hacia la plataforma y el entorno que permite verificarla. **No implementa ninguna de las
> 33 SYR**: `commonMain` lleva el vocabulario que el contrato necesita para hablarse y
> ningún comportamiento propio (ADR-0008 es el ADR del andamiaje; D-05 en `design.md`).
> La primera rebanada, que es la que le da uso, se propone aparte como
> `rebanada-1-listado-regional`.
>
> **Este change sí escribe código.** A diferencia de `implementar-sistema-desde-sysr`, que
> es puro documento, aquí se compila y se prueba. Es lo que lo distingue y lo que obliga a
> que su puerta sea ejecutable y no una promesa.
>
> **Tarea previa a todo lo demás:** el SDK de Android. Sin él, la configuración de Kotlin
> Multiplatform es todo-o-nada y **falla también el objetivo de JVM** (ADR-0008).

## 1. Entorno de compilación

- [ ] 1.1 **Instalar el SDK de Android** y dejarlo apuntando desde la configuración del
  proyecto. Motivo: en KMP la configuración es todo-o-nada, y declarar `androidTarget()`
  sin SDK impide compilar **también** el objetivo de JVM, que es el único verificable aquí.
  **Hecho cuando** la configuración de Gradle resuelve `androidTarget()` sin error y
  `ANDROID_HOME` —o `sdk.dir`— está declarado en el repositorio, no solo en la máquina.
- [ ] 1.2 **Confirmar la toolchain base**: JDK 21 y wrapper de Gradle descargando la versión
  fijada. **Hecho cuando** `./gradlew --version` responde sin intervención manual.

## 2. Esqueleto del proyecto

- [ ] 2.1 **Crear la estructura Gradle de Kotlin Multiplatform** con los tres objetivos
  declarados —`jvm`, `androidTarget`, `iosTarget`— y el código compartido en `commonMain`.
  **Hecho cuando** los tres objetivos aparecen en `./gradlew targets` y el proyecto
  configura sin error. **Nota:** `iosTarget` se declara pero **no compila en Linux**
  (ADR-0008); su declaración existe para que el fallo aparezca en la primera rebanada.
- [ ] 2.2 **Declarar el vocabulario del dominio como tipos, sin comportamiento** (D-05):
  maceta, región, sesión, conjunto, y el resultado de consulta de tres estados de ADR-0005.
  **Hecho cuando** los tipos existen y compilan, y ningún tipo implementa reglas de negocio.
- [ ] 2.3 **Establecer la frontera `expect`/`actual` en exactamente dos puntos**
  —almacenamiento seguro y ubicación— con sus `actual` por objetivo. **Hecho cuando** no
  hay `expect`/`actual` en ningún otro lugar, y un barrido del repositorio lo confirma.

## 3. El contrato con la plataforma

- [ ] 3.1 **Declarar el contrato en `commonMain`** con las cinco capacidades de ADR-0006,
  exprésadas en el vocabulario del cliente. **Hecho cuando** el contrato expone las cinco y
  **ninguna capacidad de región**, que ADR-0003 volvió innecesaria.
- [ ] 3.2 **Modelar el conjunto como admisible en tres formas** —entero, por partes, por
  páginas— sin suponer que cabe. **Hecho cuando** el tipo no tiene un tamaño máximo y
  ninguna ruta de obtención asume separación previa.
- [ ] 3.3 **Declarar fecha de los datos y pertenencia a la cuenta como opcionales.**
  **Hecho cuando** su ausencia está representada en el tipo y el sistema no puede afirmar que
  un dato es actual ni que una maceta es de la cuenta cuando no se sabe.

## 4. El doble de prueba

- [ ] 4.1 **Implementar el doble de prueba** de las cinco capacidades en `commonTest` y en
  un conjunto de fuentes que solo entre en compilaciones de depuración. **Hecho cuando**
  implementa el contrato completo y **no está disponible para una compilación de
  distribución**.
- [ ] 4.2 **Hacerlo controlable**: que el doble pueda fallar cuando se le pida, y también
  devolver conjunto vacío y conjunto entero por separado. **Hecho cuando** una prueba puede
  provocar cada uno de los tres resultados de ADR-0005 a voluntad, sin esperar un fallo real
  de la plataforma.
- [ ] 4.3 **Separar por composición, no por bandera** (ADR-0007): un punto de composición de
  depuración que pasa el doble y uno de distribución que pasa el adaptador real. **Hecho
  cuando** no existe ninguna condición sobre tipo de compilación en el código, y **la
  compilación de distribución falla** mientras el adaptador real no exista.
- [ ] 4.4 **Escribir las pruebas de contrato** sobre el doble, incluida la del fallo a
  voluntad. **Hecho cuando** la prueba que provoca `NoConsultable` pasa y comprueba que
  nunca se obtiene `Vacio` por la misma causa.

## 5. Puerta del change

- [ ] 5.1 **Ejecutar la suite del dominio sin emulador ni dispositivo** —el criterio de
  confirmación de ADR-0001, y la razón del objetivo de JVM. **Hecho cuando** las pruebas
  pasan con `./gradlew jvmTest` y sin ningún componente de plataforma móvil instalado.
- [ ] 5.2 **Verificar que una compilación de distribución no lleva el doble.** **Hecho
  cuando** el artefacto de distribución se inspecciona y no contiene la implementación de
  prueba.
- [ ] 5.3 **Validar el change con OpenSpec** antes de archivar:
  `openspec validate andamiaje-kmp --type change --strict`. **Hecho cuando** responde
  `is valid`.

## 6. Registro

- [ ] 6.1 **Registrar en `BITÁCORA.md`** la creación de este change, las decisiones
  tomadas en el grill —dónde vive el doble, qué ocurre con la identidad si `SA-04` resulta
  falsa, qué se declara en `commonMain`, qué capacidades se declaran— y el coste aceptado de
  instalar el SDK de Android. **Hecho cuando** las entradas están en la bitácora y §0
  Identificación refleja el nuevo change activo.
- [x] 6.2 **Enlazar `§3-bis` de `implementar-sistema-desde-sysr`** con el nombre de este
  change, que hasta ahora era una referencia sin destino. **Hecho al crear este change**:
  §3-bis pasa a nombrar `andamiaje-kmp` y declara que este change satisface lo que allí se
  trasladó.

## Dependencias que este change no resuelve

Se registran aquí para que no se confundan con trabajo pendiente de este change:

- `Q-025` — la forma real del API. Bloquea el **adaptador real**, no este change.
- `Q-030` / `SA-04` — si la plataforma expone autenticación. Bloquea `adr/0002`, que sigue
  en estatus `proposed`. Si resulta falsa, hay que emitir un ADR que lo superseda.
- **iOS no se compila nunca en este entorno.** No es una tarea pendiente: exige macOS.
