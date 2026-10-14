
package com.potter.micine.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.potter.micine.ui.theme.MICINETheme

@Composable
fun AjustesScreen(
    modoOscuro: Boolean,
    onModoOscuroChange: (Boolean) -> Unit,
    idioma: String,
    onIdiomaChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    // Permite identificar el idioma seleccionado
    val esIngles = idioma == "English"

    fun traducir(espanol: String, ingles: String): String {
        return if (esIngles) ingles else espanol
    }

    // Controla el menú desplegable de idiomas
    var mostrarIdiomas by remember {
        mutableStateOf(false)
    }

    Scaffold(modifier = modifier) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 22.dp,
                    vertical = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Text(
                text = "Mi Cine",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = traducir(
                    "♙  Mi Perfil",
                    "♙  My Profile"
                ),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider()

            Text(
                text = traducir("Preferencias", "Preferences"),
                style = MaterialTheme.typography.titleLarge
            )

            // Segundo avance: modo oscuro
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = traducir("Modo Oscuro", "Dark Mode"),
                    fontSize = 17.sp
                )

                Switch(
                    checked = modoOscuro,
                    onCheckedChange = onModoOscuroChange
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = traducir("General", "General"),
                style = MaterialTheme.typography.titleLarge
            )

            // Tercer avance: selector de idiomas
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = traducir("Idioma", "Language"),
                    fontSize = 17.sp
                )

                Box {

                    OutlinedButton(
                        onClick = { mostrarIdiomas = true }
                    ) {
                        Text(idioma)
                        Text(" ▾")
                    }

                    DropdownMenu(
                        expanded = mostrarIdiomas,
                        onDismissRequest = {
                            mostrarIdiomas = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = { Text("Español") },
                            onClick = {
                                onIdiomaChange("Español")
                                mostrarIdiomas = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                onIdiomaChange("English")
                                mostrarIdiomas = false
                            }
                        )
                    }
                }
            }

            AjustePendiente(
                traducir(
                    "Notificaciones y Alertas",
                    "Notifications and Alerts"
                ),
                traducir("Próximamente", "Coming Soon")
            )

            AjustePendiente(
                traducir("Privacidad", "Privacy"),
                traducir("Próximamente", "Coming Soon")
            )

            AjustePendiente(
                traducir("Borrar Historial", "Clear History"),
                traducir("Próximamente", "Coming Soon")
            )

            HorizontalDivider()

            Text(
                text = traducir(
                    "Modo oscuro y selector de idiomas disponibles. Las demás funciones se implementarán posteriormente.",
                    "Dark mode and language selector are available. Other features will be implemented later."
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AjustePendiente(
    titulo: String,
    mensaje: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = titulo,
            modifier = Modifier.weight(1f),
            fontSize = 17.sp
        )

        Text(
            text = mensaje,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AjustesPreview() {

    MICINETheme {
        AjustesScreen(
            modoOscuro = false,
            onModoOscuroChange = {},
            idioma = "Español",
            onIdiomaChange = {}
        )
    }
}
