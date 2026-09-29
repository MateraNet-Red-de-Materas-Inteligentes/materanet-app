package com.materanet

import platform.Foundation.NSUserDefaults
import platform.UIKit.UIViewController

/**
 * D-03 — `actual` para iOS: `NSUserDefaults`.
 */
actual fun AceptoLaBienvenida(): Boolean =
    NSUserDefaults.standardUserDefaults.boolForKey(CLAVE)

actual fun RegistrarAceptoLaBienvenida() {
    NSUserDefaults.standardUserDefaults.setObject(true, forKey = CLAVE)
}

private const val CLAVE = "bienvenida_aceptada"

fun PantallaDeBienvenidaController(): UIViewController = MainViewControllerKt.MainViewController()
