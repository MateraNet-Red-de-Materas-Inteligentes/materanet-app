package com.materanet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Imagen de fondo a pantalla completa.
 *
 * D-05 — `ContentScale.Crop` cubre el área visible y recorta el excedente, en vez de
 * estirar. Por eso el original tiene que ser de alta resolución: se recorta bastante, sobre
 * todo en vertical, donde queda solo una franja del medio.
 */
@Composable
fun FondoDePantalla(
    recurso: DrawableResource,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(recurso),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}
