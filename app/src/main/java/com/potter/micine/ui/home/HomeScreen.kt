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
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class PeliculaListado(
    val titulo: String,
    val genero: String,
    val estado: String,
    val calificacion: Int
)

@Composable
fun HomeScreen(
    peliculas: List<PeliculaListado>
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
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = pelicula.titulo,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text("Género: ${pelicula.genero}")
                            Text("Estado: ${pelicula.estado}")

                            if (pelicula.estado == "Vista") {
                                Text(
                                    "Calificación: ${"★".repeat(pelicula.calificacion)}"
                                )
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