package com.materanet

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Raíz común de la app.
 *
 * Decide la pantalla inicial leyendo la aceptación persistida. El valor se lee una sola vez
 * al arrancar: girar el dispositivo no vuelve a leerlo, y por eso la pantalla sobrevive a la
 * rotación en el mismo estado en que estaba.
 */
@Composable
fun App(modifier: Modifier = Modifier) {
    MaterialTheme(colorScheme = TemaOscuro) {
        var aceptada by remember { mutableStateOf(AceptoLaBienvenida()) }

        if (aceptada) {
            PantallaPrincipal(modifier = modifier)
        } else {
            PantallaDeBienvenida(
                onContinuar = {
                    RegistrarAceptoLaBienvenida()
                    aceptada = true
                },
                modifier = modifier,
            )
        }
    }
}

private val TemaOscuro = darkColorScheme()
