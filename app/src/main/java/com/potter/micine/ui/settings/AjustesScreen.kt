
package com.potter.micine.ui.settings

import androidx.compose.foundation.clickable
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

    // Identificar el idioma seleccionado
    val esIngles = idioma == "English"

    fun traducir(espanol: String, ingles: String): String {
        return if (esIngles) ingles else espanol
    }

    // Control del menú de idiomas
    var mostrarIdiomas by remember {
        mutableStateOf(false)
    }

    // Control de la ventana de privacidad
    var mostrarPrivacidad by remember {
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

            // Título de la aplicación
            Text(
                text = "Mi Cine",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // Encabezado Mi Perfil
            Text(
                text = traducir(
                    "♙  Mi Perfil",
                    "♙  My Profile"
                ),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider()

            // SECCIÓN PREFERENCIAS
            Text(
                text = traducir(
                    "Preferencias",
                    "Preferences"
                ),
                style = MaterialTheme.typography.titleLarge
            )

            // MODO OSCURO
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = traducir(
                        "Modo Oscuro",
                        "Dark Mode"
                    ),
                    fontSize = 17.sp
                )

                Switch(
                    checked = modoOscuro,
                    onCheckedChange = onModoOscuroChange
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // SECCIÓN GENERAL
            Text(
                text = "General",
                style = MaterialTheme.typography.titleLarge
            )

            // SELECTOR DE IDIOMAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = traducir(
                        "Idioma",
                        "Language"
                    ),
                    fontSize = 17.sp
                )

                Box {

                    OutlinedButton(
                        onClick = {
                            mostrarIdiomas = true
                        }
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
                            text = {
                                Text("Español")
                            },
                            onClick = {
                                onIdiomaChange("Español")
                                mostrarIdiomas = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("English")
                            },
                            onClick = {
                                onIdiomaChange("English")
                                mostrarIdiomas = false
                            }
                        )
                    }
                }
            }

            // NOTIFICACIONES
            AjustePendiente(
                titulo = traducir(
                    "Notificaciones y Alertas",
                    "Notifications and Alerts"
                ),
                mensaje = traducir(
                    "Próximamente",
                    "Coming Soon"
                )
            )

            // PRIVACIDAD - NUEVA OPCIÓN
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        mostrarPrivacidad = true
                    }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = traducir(
                        "Privacidad",
                        "Privacy"
                    ),
                    fontSize = 17.sp
                )

                Text(
                    text = "›",
                    fontSize = 24.sp
                )
            }

            // BORRAR HISTORIAL
            AjustePendiente(
                titulo = traducir(
                    "Borrar Historial",
                    "Clear History"
                ),
                mensaje = traducir(
                    "Próximamente",
                    "Coming Soon"
                )
            )

            HorizontalDivider()

            // MENSAJE INFORMATIVO
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

    // VENTANA DE PRIVACIDAD
    if (mostrarPrivacidad) {

        AlertDialog(
            onDismissRequest = {
                mostrarPrivacidad = false
            },
            title = {
                Text(
                    traducir(
                        "Privacidad",
                        "Privacy"
                    )
                )
            },
            text = {
                Text(
                    traducir(
                        "Las opciones de privacidad estarán disponibles próximamente.",
                        "Privacy options will be available soon."
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarPrivacidad = false
                    }
                ) {
                    Text(
                        traducir(
                            "Cerrar",
                            "Close"
                        )
                    )
                }
            }
        )
    }
}

// COMPONENTE PARA FUNCIONES PENDIENTES
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

// VISTA PREVIA
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
