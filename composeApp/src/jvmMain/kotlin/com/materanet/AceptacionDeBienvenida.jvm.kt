package com.materanet

import java.util.prefs.Preferences

/**
 * D-03 — `actual` para escritorio: `java.util.prefs`.
 *
 * El nodo se puede desviar con la propiedad de sistema `materanet.prefs.nodo`. Existe por
 * un motivo concreto: las pruebas corren en la misma máquina y en el mismo almacén, así que
 * si escribieran en el nodo real dejarían la pantalla de bienvenida marcada como aceptada
 * para siempre. Con la propiedad, las pruebas usan un nodo aparte y no tocan el estado de
 * quien está usando la app.
 */
private fun preferencias(): Preferences =
    Preferences.userRoot().node(System.getProperty(PROPIEDAD_NODO) ?: NODO)

actual fun AceptoLaBienvenida(): Boolean =
    preferencias().getBoolean(CLAVE, false)

actual fun RegistrarAceptoLaBienvenida() {
    preferencias().putBoolean(CLAVE, true)
    preferencias().flush()
}

/** Propiedad de sistema que permite apuntar a otro nodo. Ver [preferencias]. */
const val PROPIEDAD_NODO = "materanet.prefs.nodo"

private const val NODO = "/com/materanet"
private const val CLAVE = "bienvenida_aceptada"
