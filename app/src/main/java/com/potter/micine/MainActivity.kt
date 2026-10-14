
package com.potter.micine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.potter.micine.ui.settings.AjustesScreen
import com.potter.micine.ui.theme.MICINETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            // Segundo avance: modo oscuro
            var modoOscuro by rememberSaveable {
                mutableStateOf(false)
            }

            // Tercer avance: selector de idiomas
            var idioma by rememberSaveable {
                mutableStateOf("Español")
            }

            MICINETheme(darkTheme = modoOscuro) {
                AjustesScreen(
                    modoOscuro = modoOscuro,
                    onModoOscuroChange = { modoOscuro = it },
                    idioma = idioma,
                    onIdiomaChange = { idioma = it }
                )
            }
        }
    }
}
