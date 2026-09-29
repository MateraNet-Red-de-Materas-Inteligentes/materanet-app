package com.materanet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.materanet.resources.Res
import com.materanet.resources.fondo_bienvenida
import com.materanet.resources.logo_materanet
import org.jetbrains.compose.resources.painterResource

/**
 * Pantalla de bienvenida de MateraNet.
 *
 * `onContinuar` es la única acción de la pantalla. El botón vive fuera de la zona
 * desplazable, así que "siempre alcanzable" y "nada se solapa con el botón" se cumplen por
 * construcción y no por cálculo.
 *
 * D-05 — El logotipo se dibuja con `ContentScale.Fit` dentro de una caja de tamaño fijo, que
 * es lo que impide que se estire. La sombra del texto existe porque el fondo es una
 * fotografía y el nombre tiene que leerse sobre ella.
 *
 * D-06 — Una sola disposición vertical para las dos orientaciones, respetando
 * `safeDrawing`: en horizontal la muesca queda a un lado y el gesto de retroceso ocupa una
 * franja abajo.
 */
@Composable
fun PantallaDeBienvenida(
    onContinuar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        FondoDePantalla(recurso = Res.drawable.fondo_bienvenida)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo_materanet),
                    contentDescription = "MateraNet",
                    modifier = Modifier.size(width = 160.dp, height = 200.dp),
                    contentScale = ContentScale.Fit,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "MateraNet",
                    style = MaterialTheme.typography.displaySmall.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.75f),
                            offset = androidx.compose.ui.geometry.Offset(0f, 2f),
                            blurRadius = 10f,
                        ),
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                onClick = onContinuar,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                Text(text = "Continuar")
            }
        }
    }
}
