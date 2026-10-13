
package com.potter.micine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.potter.micine.ui.settings.AjustesScreen
import com.potter.micine.ui.theme.MICINETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MICINETheme {
                // Primer avance de Mi Perfil / Ajustes
                // La navegación se integrará posteriormente
                AjustesScreen()
            }
        }
    }
}
