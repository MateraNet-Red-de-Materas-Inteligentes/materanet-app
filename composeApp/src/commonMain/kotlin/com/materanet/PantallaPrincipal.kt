package com.materanet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Pantalla principal. En esta rebanada está vacía a propósito: no hay funcionalidades que
 * mostrar todavía. Existe para que `Continuar` tenga a dónde ir.
 */
@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize())
}
