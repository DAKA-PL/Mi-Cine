
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
    onBorrarHistorial: () -> Unit = {},
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

    // Control de la ventana Borrar Historial
    var mostrarBorrarHistorial by remember {
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

            // NOTIFICACIONES Y ALERTAS
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

            // SECCIÓN BORRAR HISTORIAL
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        mostrarBorrarHistorial = true
                    }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = traducir(
                        "Borrar Historial",
                        "Clear History"
                    ),
                    fontSize = 17.sp
                )

                Text(
                    text = "›",
                    fontSize = 24.sp
                )
            }
        }
    }

    // VENTANA DE CONFIRMACIÓN PARA BORRAR HISTORIAL
    if (mostrarBorrarHistorial) {

        AlertDialog(
            onDismissRequest = {
                mostrarBorrarHistorial = false
            },

            title = {
                Text(
                    traducir(
                        "Borrar Historial",
                        "Clear History"
                    )
                )
            },

            text = {
                Text(
                    traducir(
                        "¿Estás seguro de que deseas eliminar todo el historial de películas? " +
                                "Esta acción no se puede deshacer.",

                        "Are you sure you want to delete all movie history? " +
                                "This action cannot be undone."
                    )
                )
            },

            // BOTÓN ELIMINAR
            confirmButton = {
                TextButton(
                    onClick = {

                        // Preparado para conectar con Room
                        onBorrarHistorial()

                        // Cerrar ventana
                        mostrarBorrarHistorial = false
                    }
                ) {
                    Text(
                        traducir(
                            "Eliminar",
                            "Delete"
                        )
                    )
                }
            },

            // BOTÓN CANCELAR
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarBorrarHistorial = false
                    }
                ) {
                    Text(
                        traducir(
                            "Cancelar",
                            "Cancel"
                        )
                    )
                }
            }
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
            onNotificacionesChange = {},
            onBorrarHistorial = {}
        )
    }
}
