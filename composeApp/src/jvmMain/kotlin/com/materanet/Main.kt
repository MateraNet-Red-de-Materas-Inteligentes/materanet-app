package com.materanet

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        // D-02 — La ventana se abre a tamaño de teléfono angosto, no a pantalla completa:
        // así la comprobación de escritorio se parece a la del dispositivo y el requisito
        // de adaptación se puede mirar en los dos casos.
        state = rememberWindowState(size = DpSize(420.dp, 860.dp)),
        title = "MateraNet",
    ) {
        App()
    }
}
