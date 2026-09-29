package com.materanet

import java.util.prefs.Preferences

/**
 * Nodo de preferencias que usan las pruebas.
 *
 * Vive en `jvmTest` y apunta a un nodo propio, separado del que usa la app. Así correr
 * `:composeApp:jvmTest` no marca la pantalla de bienvenida como aceptada en el build de
 * escritorio: sin esto, probar la persistencia dejaría la app en un estado en el que ya
 * no se ve la pantalla que se está probando.
 */
class PreferencesDeEscritorio {

    init {
        System.setProperty(PROPIEDAD_NODO, NODO_DE_PRUEBAS)
    }

    fun leerDelDisco(): Boolean = nodo().getBoolean(CLAVE, false)

    fun borrar() {
        nodo().remove(CLAVE)
        nodo().flush()
    }

    private fun nodo(): Preferences = Preferences.userRoot().node(NODO_DE_PRUEBAS)

    private companion object {
        const val NODO_DE_PRUEBAS = "/com/materanet/pruebas"
        const val CLAVE = "bienvenida_aceptada"
    }
}
