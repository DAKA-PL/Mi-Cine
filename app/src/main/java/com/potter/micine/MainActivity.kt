

package com.potter.micine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.potter.micine.ui.home.HomeScreenConViewModel
import com.potter.micine.ui.home.HomeViewModel
import com.potter.micine.ui.settings.AjustesScreen
import com.potter.micine.ui.theme.MICINETheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val preferencias = getSharedPreferences(
            "MiCinePreferencias",
            MODE_PRIVATE
        )

        setContent {
            var pantallaActual by rememberSaveable {
                mutableStateOf("home")
            }

            var modoOscuro by rememberSaveable {
                mutableStateOf(
                    preferencias.getBoolean("modoOscuro", false)
                )
            }

            var idioma by rememberSaveable {
                mutableStateOf(
                    preferencias.getString("idioma", "Español")
                        ?: "Español"
                )
            }

            var notificaciones by rememberSaveable {
                mutableStateOf(
                    preferencias.getBoolean("notificaciones", false)
                )
            }

            MICINETheme(darkTheme = modoOscuro) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            pantallaActual =
                                if (pantallaActual == "home") {
                                    "ajustes"
                                } else {
                                    "home"
                                }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            if (pantallaActual == "home") {
                                "Ir a ajustes"
                            } else {
                                "Volver a inicio"
                            }
                        )
                    }

                    when (pantallaActual) {
                        "home" -> {
                            HomeScreenConViewModel(
                                viewModel = homeViewModel
                            )
                        }

                        "ajustes" -> {
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
        }
    }
}
