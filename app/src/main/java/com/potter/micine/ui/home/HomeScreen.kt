package com.potter.micine.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

data class PeliculaListado(
    val titulo: String,
    val genero: String,
    val estado: String,
    val calificacion: Int,
    val id: Int = 0
)

@Composable
fun HomeScreen(
    peliculas: List<PeliculaListado>,
    onEditar: (PeliculaListado) -> Unit = {},
    onEliminar: (PeliculaListado) -> Unit = {}
) {
    var filtro by remember { mutableStateOf("Todas") }

    val peliculasFiltradas = when (filtro) {
        "Vistas" -> peliculas.filter { it.estado == "Vista" }
        "Pendientes" -> peliculas.filter { it.estado == "Pendiente" }
        else -> peliculas
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi Cine",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Mis películas",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todas", "Vistas", "Pendientes").forEach { opcion ->
                FilterChip(
                    selected = filtro == opcion,
                    onClick = { filtro = opcion },
                    label = { Text(opcion) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (peliculasFiltradas.isEmpty()) {
            Text("No hay películas para mostrar")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(peliculasFiltradas) { pelicula ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = pelicula.titulo,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text("Género: ${pelicula.genero}")
                                Text("Estado: ${pelicula.estado}")

                                if (pelicula.estado == "Vista") {
                                    Text(
                                        "Calificación: ${
                                            "★".repeat(
                                                pelicula.calificacion.coerceIn(0, 5)
                                            )
                                        }"
                                    )
                                }
                            }

                            Row {
                                IconButton(
                                    onClick = { onEditar(pelicula) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ${pelicula.titulo}"
                                    )
                                }

                                IconButton(
                                    onClick = { onEliminar(pelicula) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar ${pelicula.titulo}",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        peliculas = listOf(
            PeliculaListado("Titanic", "Romance", "Vista", 5),
            PeliculaListado("Avatar", "Ciencia ficción", "Pendiente", 0),
            PeliculaListado("Intensamente", "Animación", "Vista", 4),
            PeliculaListado("Interestelar", "Ciencia ficción", "Pendiente", 0)
        )
    )
}


@Composable
fun HomeScreenConViewModel(
    viewModel: HomeViewModel
) {
    val peliculas by viewModel.peliculas.collectAsState()

    var peliculaAEliminar by remember {
        mutableStateOf<PeliculaListado?>(null)
    }

    HomeScreen(
        peliculas = peliculas,
        onEliminar = { pelicula ->
            peliculaAEliminar = pelicula
        }
    )

    peliculaAEliminar?.let { pelicula ->
        AlertDialog(
            onDismissRequest = {
                peliculaAEliminar = null
            },
            title = {
                Text("Eliminar película")
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas eliminar " +
                            "\"${pelicula.titulo}\"?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eliminarPelicula(pelicula.id)
                        peliculaAEliminar = null
                    }
                ) {
                    Text(
                        "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        peliculaAEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

