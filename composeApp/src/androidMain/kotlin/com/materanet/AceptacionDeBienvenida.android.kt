package com.materanet

import android.content.Context
import android.content.SharedPreferences

/**
 * D-03 — `actual` para Android: preferencias del sistema.
 *
 * El `Context` se inyecta desde la `Activity` antes de que se use el almacén.
 */
private lateinit var preferencias: SharedPreferences

internal fun InicializarAlmacen(context: Context) {
    preferencias = context.applicationContext.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE)
}

private const val ARCHIVO = "materanet"
private const val CLAVE = "bienvenida_aceptada"

actual fun AceptoLaBienvenida(): Boolean = preferencias.getBoolean(CLAVE, false)

actual fun RegistrarAceptoLaBienvenida() {
    preferencias.edit().putBoolean(CLAVE, true).apply()
}
