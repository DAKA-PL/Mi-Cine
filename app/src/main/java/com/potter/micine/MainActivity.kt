
package com.potter.micine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.potter.micine.ui.settings.AjustesScreen
import com.potter.micine.ui.theme.MICINETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Guardar preferencias en el dispositivo
        val preferencias = getSharedPreferences(
            "MiCinePreferencias",
            MODE_PRIVATE
        )

        setContent {

            // Modo Oscuro
            var modoOscuro by rememberSaveable {
                mutableStateOf(
                    preferencias.getBoolean("modoOscuro", false)
                )
            }

            // Idioma
            var idioma by rememberSaveable {
                mutableStateOf(
                    preferencias.getString("idioma", "Español")
                        ?: "Español"
                )
            }

            // Notificaciones y Alertas
            var notificaciones by rememberSaveable {
                mutableStateOf(
                    preferencias.getBoolean("notificaciones", false)
                )
            }

            MICINETheme(darkTheme = modoOscuro) {

                AjustesScreen(

                    modoOscuro = modoOscuro,

                    onModoOscuroChange = { activado ->
                        modoOscuro = activado

                        preferencias.edit()
                            .putBoolean("modoOscuro", activado)
                            .apply()
                    },

                    idioma = idioma,

                    onIdiomaChange = { nuevoIdioma ->
                        idioma = nuevoIdioma

                        preferencias.edit()
                            .putString("idioma", nuevoIdioma)
                            .apply()
                    },

                    // NUEVA FUNCIÓN DE NOTIFICACIONES
                    notificaciones = notificaciones,

                    onNotificacionesChange = { activado ->
                        notificaciones = activado

                        preferencias.edit()
                            .putBoolean("notificaciones", activado)
                            .apply()
                    }
                )
            }
        }
    }
}
