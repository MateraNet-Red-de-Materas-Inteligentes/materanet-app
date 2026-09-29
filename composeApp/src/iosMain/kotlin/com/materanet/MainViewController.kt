package com.materanet

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

/**
 * Punto de entrada para SwiftUI.
 *
 * D-06 — Las orientaciones soportadas no se declaran aquí sino en el `Info.plist` del
 * proyecto iOS, que es donde iOS las lee.
 */
fun MainViewController(): UIViewController = ComposeUIViewController { App() }
