package com.materanet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

/**
 * D-06 — La orientación no se fija: sin `android:screenOrientation` ni
 * `configChanges` que la restrinjan, la Activity sigue la rotación del dispositivo.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InicializarAlmacen(this)
        setContent {
            App()
        }
    }
}
