# Tasks

## 1. Descartar SA-01 antes de construir nada

**Esta sección va primera a propósito.** Si la plataforma no sirve el conjunto de macetas
por región, el proyecto no tiene objeto (SA-01) y todo lo demás es trabajo perdido. No se
escribe una línea de interfaz antes de resolver esto.

- [ ] 1.1 Obtener acceso a la plataforma y documentar su interfaz publicada: qué
      operaciones expone, cómo se establece la identidad, y si expone el conjunto de
      macetas de una región
- [ ] 1.2 Ejecutar una consulta real y **registrar la respuesta cruda**: un conjunto con
      al menos una maceta, con sus atributos. Guardarla como referencia de prueba
- [ ] 1.3 Anotar, **sin exigir nada**, cómo entrega el conjunto la plataforma: entero, por
      partes, o por páginas. Esto es O-01, y **no bloquea nada** — D-08 funciona con las
      tres formas. Se registra porque de acá sale la medición real de volumen
- [ ] 1.4 Confirmar qué atributos están realmente disponibles (tipo de planta, sensores,
      estado de planta, ubicación, comentarios) y **cuáles faltan**
- [ ] 1.5 **Punto de decisión.** Si **falta el conjunto de macetas por región** (SA-01), o
      falta algún atributo, **parar acá** y revisar `design.md`. Si la plataforma sirve el
      conjunto con sus atributos, **continuar**: lo que falte se ajusta en el cliente
- [ ] 1.6 Registrar el volumen observado en la respuesta cruda. Es el primer dato real de
      volumen del proyecto, y alimenta D-07 y R-009

## 2. Base del proyecto

- [ ] 2.1 Inicializar el proyecto con Expo / React Native, según D-01
- [ ] 2.2 Declarar la versión mínima de plataforma soportada (SYR-23, SYR-33, O-03)
- [ ] 2.3 Configurar el acceso a la interfaz de la plataforma **por entorno** (SYR-16),
      de modo que el mismo sistema opere contra la interfaz de su entorno sin cambios de
      comportamiento
- [ ] 2.4 Verificar que el artefacto que se distribuye **no contiene credenciales ni
      secretos de la plataforma** (SYR-18, SYR-20)

## 3. Identidad

- [ ] 3.1 Implementar el flujo de identidad delegando en la plataforma, según D-04
- [ ] 3.2 Comprobar que **ninguna capacidad de listado responde sin identidad
      establecida** (SYR-01, SYR-17)
- [ ] 3.3 Comprobar que la consulta queda atribuida a la cuenta que la originó (SYR-01)
- [ ] 3.4 Manejar el vencimiento de sesión **distinguiéndolo de un fallo de plataforma**
      (consecuencia de D-04, misma distinción que SYR-21)

## 4. Obtención del conjunto

- [ ] 4.1 Implementar la obtención del conjunto de macetas de la región resuelta,
      **adaptada a cómo lo entregue la plataforma** (entero, por partes o por páginas) y sin
      asumir un volumen máximo en ninguna parte del cliente (SYR-04, SYR-27, SYR-28, D-08)
- [ ] 4.2 Implementar la resolución de región desde la cuenta, según D-03. **No usar la
      ubicación del dispositivo** — SYR-24 queda desactivado
- [ ] 4.3 Obtener y atribuir los atributos de cada maceta: tipo de planta, sensores,
      estado de planta, ubicación, comentarios (SYR-05…SYR-09)
- [ ] 4.4 Comprobar que el estado de planta **se recibe y no se calcula**: ninguna lógica
      del cliente lo infiere a partir de los sensores (SYR-07, SYR-R02)
- [ ] 4.5 Implementar el filtrado por los criterios que la plataforma exponga (SYR-29)
- [ ] 4.6 Mostrar la antigüedad de la información cuando la plataforma la proporcione
      (SYR-22). Si 1.5 determinó que no existe, **parar y revisar**

## 5. Presentación

- [ ] 5.1 Presentar el conjunto de forma navegable, sin que la persona construya la
      consulta (SYR-10, SYR-26)
- [ ] 5.2 Presentar cada maceta de forma distinguible (SYR-03)
- [ ] 5.3 Distinguir las macetas propias de las demás (SYR-13)
- [ ] 5.4 **Presentar la región resuelta de forma visible y revisable** (SYR-02)
- [ ] 5.5 Presentar la ubicación de la maceta **como dato de la plataforma**, nunca como
      la del dispositivo (SYR-24)

## 6. Los tres estados que RN-03 hace obligatorios

Esta sección es la que distingue un sistema correcto de uno que miente. Cada estado es un
escenario de prueba, no una mejora de interfaz.

- [ ] 6.1 **Conjunto vacío:** operar normalmente, sin tratar la ausencia como fallo
      (SYR-11)
- [ ] 6.2 **Fallo de obtención:** comunicar que no se pudo consultar, y **no** que la
      región está vacía (SYR-21)
- [ ] 6.3 **Conjunto parcial:** comunicar que lo presentado es una parte, no el total
      (SYR-30)
- [ ] 6.4 Comprobar que los tres estados son **distinguibles entre sí** desde la
      perspectiva de la persona

## 7. Reglas de acceso y alcance

- [ ] 7.1 Implementar la regla de acceso como parámetro, con el valor por defecto de
      DA-02, ajustable sin modificar código (SYR-12, SYR-19, SYR-31, D-06)
- [ ] 7.2 Implementar regla de acceso, resolución de región y criterios de filtrado como
      ajustes **independientes** (SYR-32)
- [ ] 7.3 Comprobar que endurecer la regla de acceso **no requiere modificar el código**
- [ ] 7.4 Declarar el comportamiento ante dispositivos fuera del alcance de soporte
      (SYR-23, SYR-33)

## 8. Verificación y cierre

- [ ] 8.1 Medir el techo de volumen real y **declararlo** (SYR-27, R-009, D-07). El
      número lo produce esta medición, no una suposición, y parte del volumen ya
      observado en la tarea 1.6
- [ ] 8.2 Verificar que no hay truncamiento silencioso del conjunto en el techo declarado
- [ ] 8.3 Ejecutar los escenarios de las cuatro especificaciones y registrar resultados
      (ISO 29119)
- [ ] 8.4 **Revisar R-002**: si la jerarquía de objetivos es comercialmente viable. La
      revisión corresponde al cierre de la rebanada 1, según lo definido en el BRS
- [ ] 8.5 Registrar en `BITÁCORA.md` la decisión sobre el valor por defecto de la regla
      de acceso (O-02, cierra Q-026) y la versión mínima declarada (O-03, cierra DD-08)
- [ ] 8.6 Archivar el change con `openspec archive` para promover las cuatro
      especificaciones a `openspec/specs/`
