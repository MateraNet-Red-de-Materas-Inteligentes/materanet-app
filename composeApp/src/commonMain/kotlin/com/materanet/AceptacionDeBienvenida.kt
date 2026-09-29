package com.materanet

/**
 * D-03 — La frontera aparece para persistir la aceptación.
 *
 * Guardar un booleano es la primera cosa que no se resuelve en `commonMain`: cada
 * plataforma tiene su almacén de preferencias. `actual` va a las preferencias del sistema
 * en cada destino.
 */

/** `true` si el usuario ya pulsó `Continuar` en la pantalla de bienvenida. */
expect fun AceptoLaBienvenida(): Boolean

/** Registra que el usuario pulsó `Continuar`. Debe sobrevivir al cierre de la app. */
expect fun RegistrarAceptoLaBienvenida()
