
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
    notificaciones: Boolean,
    onNotificacionesChange: (Boolean) -> Unit,
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

            // TÍTULO DE LA APLICACIÓN
            Text(
                text = "Mi Cine",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // MI PERFIL
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

            // SEGUNDO AVANCE: MODO OSCURO
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

            // TERCER AVANCE: SELECTOR DE IDIOMAS
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

            // SEXTO AVANCE: NOTIFICACIONES Y ALERTAS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = traducir(
                        "Notificaciones y Alertas",
                        "Notifications and Alerts"
                    ),
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp
                )

                Switch(
                    checked = notificaciones,
                    onCheckedChange = onNotificacionesChange
                )
            }

            // Mensaje sobre las notificaciones
            Text(
                text = traducir(
                    "Preferencia guardada. Los avisos automáticos estarán disponibles próximamente.",
                    "Preference saved. Automatic notifications will be available soon."
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // QUINTO AVANCE: PRIVACIDAD
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

            // BORRAR HISTORIAL: PENDIENTE
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
                    "Modo oscuro, idiomas y preferencia de notificaciones disponibles. Las demás funciones se implementarán posteriormente.",
                    "Dark mode, languages and notification preferences are available. Other features will be implemented later."
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
                        "Mi Cine permite organizar películas vistas y pendientes. " +
                                "La información se almacenará localmente mediante Room " +
                                "cuando se integre la base de datos. " +
                                "Actualmente no se realiza sincronización en la nube.",

                        "Mi Cine allows you to organize watched and pending movies. " +
                                "The information will be stored locally using Room " +
                                "when the database is integrated. " +
                                "Cloud synchronization is not currently available."
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
            onIdiomaChange = {},
            notificaciones = false,
            onNotificacionesChange = {}
        )
    }
}
