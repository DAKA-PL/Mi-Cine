package com.potter.micine.ui.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FormPeliculaRoute(
    onGuardar: (FormPeliculaUIState) -> Unit,
    onCancelar: () -> Unit,
    formViewModel: FormPeliculaViewModel = viewModel()
) {
    val uiState by formViewModel.uiState.collectAsStateWithLifecycle()

    FormPeliculaContent(
        uiState = uiState,
        onTituloChange = formViewModel::actualizarTitulo,
        onGeneroChange = formViewModel::actualizarGenero,
        onEstadoChange = formViewModel::actualizarEstado,
        onCalificacionChange = formViewModel::actualizarCalificacion,
        onNuevoGeneroChange = formViewModel::actualizarNuevoGenero,
        onMostrarDialogoGenero = formViewModel::mostrarDialogoGenero,
        onAgregarGenero = formViewModel::agregarGenero,
        onGuardar = {
            if (formViewModel.validarFormulario()) {
                onGuardar(formViewModel.uiState.value)
            }
        },
        onCancelar = onCancelar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPeliculaContent(
    uiState: FormPeliculaUIState,
    onTituloChange: (String) -> Unit,
    onGeneroChange: (Long) -> Unit,
    onEstadoChange: (Boolean) -> Unit,
    onCalificacionChange: (Int) -> Unit,
    onNuevoGeneroChange: (String) -> Unit,
    onMostrarDialogoGenero: (Boolean) -> Unit,
    onAgregarGenero: () -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Mi Cine",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Título:",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = uiState.titulo,
            onValueChange = onTituloChange,
            placeholder = { Text("Nombre de la película") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(
            text = "Género:",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = uiState.generosDisponibles.getOrNull(
                        (uiState.generoId - 1L).toInt()
                    ) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandido
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        )
                )

                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    uiState.generosDisponibles.forEachIndexed { index, genero ->
                        DropdownMenuItem(
                            text = { Text(genero) },
                            onClick = {
                                onGeneroChange(index + 1L)
                                expandido = false
                            }
                        )
                    }
                }
            }


            OutlinedButton(
                onClick = { onMostrarDialogoGenero(true) }
            ) {
                Text("Agregar")
            }
        }

        Text(
            text = "Estado:",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !uiState.esVista,
                    onClick = { onEstadoChange(false) }
                )
                Text("Pendiente")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = uiState.esVista,
                    onClick = { onEstadoChange(true) }
                )
                Text("Vista")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            (1..5).forEach { estrella ->
                Text(
                    text = if (estrella <= uiState.calificacion) "★" else "☆",
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (estrella <= uiState.calificacion) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier
                        .clickable(enabled = uiState.esVista) {
                            onCalificacionChange(estrella)
                        }
                        .padding(horizontal = 5.dp)
                )
            }
        }

        if (!uiState.esVista) {
            Text(
                text = "Marca la película como vista para calificarla.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        uiState.error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onGuardar,
            enabled = !uiState.guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Película")
        }

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }

    if (uiState.mostrarDialogoNuevoGenero) {
        AlertDialog(
            onDismissRequest = {
                onMostrarDialogoGenero(false)
            },
            title = { Text("Agregar género") },
            text = {
                OutlinedTextField(
                    value = uiState.nuevoGeneroNombre,
                    onValueChange = onNuevoGeneroChange,
                    label = { Text("Nombre del género") }
                )
            },
            confirmButton = {
                TextButton(onClick = onAgregarGenero) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onMostrarDialogoGenero(false)
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}