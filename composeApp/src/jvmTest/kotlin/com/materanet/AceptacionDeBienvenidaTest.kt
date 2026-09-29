package com.materanet

import java.util.prefs.Preferences
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * D-03 — El almacén de preferencias de escritorio guarda la aceptación y se lee de vuelta.
 *
 * Es la única verificación automática de la rebanada, y cubre lo único que hay de lógica:
 * que la aceptación quede guardada. El resto de la pantalla se comprueba mirándola, que es
 * lo que hacen las tareas 6.2 a 6.5.
 *
 * Cada prueba empieza borrando la preferencia, porque el almacén sobrevive entre
 * ejecuciones: si no, la segunda vez que corren la app ya estaría "aceptada" y la primera
 * afirmación no significaría nada.
 */
class AceptacionDeBienvenidaTest {

    private val preferenciasDePrueba = PreferencesDeEscritorio()

    @BeforeTest
    fun borrarLaPreferencia() {
        preferenciasDePrueba.borrar()
    }

    @Test
    fun laAceptacionSeGuardaYSeLeeDeVuelta() {
        assertEquals(
            false,
            AceptoLaBienvenida(),
            "arranca sin haber aceptado",
        )

        RegistrarAceptoLaBienvenida()

        assertEquals(
            true,
            AceptoLaBienvenida(),
            "luego de aceptar, la app ya no vuelve a preguntar",
        )
    }

    @Test
    fun laAceptacionQuedaEnDisco() {
        RegistrarAceptoLaBienvenida()

        assertEquals(
            true,
            preferenciasDePrueba.leerDelDisco(),
            "el valor está persistido, no solo en memoria",
        )
    }

    @Test
    fun lasPruebasNoTocanLasPreferenciasRealesDeLaApp() {
        // El nodo real es el que lee la ventana de escritorio. Si las pruebas escribieran
        // ahí, después de correrlas la pantalla de bienvenida no volvería a aparecer nunca.
        val nodoReal = Preferences.userRoot().node(NODO_REAL_DE_LA_APP)
        val antes = nodoReal.getBoolean(CLAVE, false)

        RegistrarAceptoLaBienvenida()

        assertEquals(
            antes,
            nodoReal.getBoolean(CLAVE, false),
            "las pruebas escriben en su propio nodo, no en el de la app",
        )
        assertEquals(
            true,
            AceptoLaBienvenida(),
            "mientras corren, la app lee del nodo de pruebas",
        )
    }

    private companion object {
        const val NODO_REAL_DE_LA_APP = "/com/materanet"
        const val CLAVE = "bienvenida_aceptada"
    }
}
