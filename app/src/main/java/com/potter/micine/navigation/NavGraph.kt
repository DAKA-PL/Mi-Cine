package com.potter.micine.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.*
import com.potter.micine.ui.home.HomeScreen
import com.potter.micine.ui.home.HomeViewModel
import com.potter.micine.ui.form.FormPeliculaRoute
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.potter.micine.ui.home.PeliculaListado
import androidx.navigation.NavType
import androidx.navigation.navArgument

// RUTAS DE LA APLICACIÓN
object Rutas {
    const val INICIO = "inicio"
    const val AGREGAR = "agregar"
    const val EDITAR = "editar/{peliculaId}"
    const val AJUSTES = "ajustes"

    fun editarPelicula(id: Int): String {
        return "editar/$id"
    }
}

@Composable
fun MiCineNavGraph(
    ajustesScreen: @Composable () -> Unit
) {

    val navController = rememberNavController()

    val entradaActual by navController
        .currentBackStackEntryAsState()

    val rutaActual = entradaActual?.destination?.route

    val colores = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colores.background,

        bottomBar = {

            NavigationBar(
                containerColor = colores.surfaceContainer
            ) {

                listOf(
                    Triple(Rutas.INICIO, "Inicio", "⌂"),
                    Triple(Rutas.AGREGAR, "Agregar", "+"),
                    Triple(Rutas.AJUSTES, "Ajustes", "⚙")
                ).forEach { (ruta, nombre, icono) ->

                    NavigationBarItem(
                        selected = rutaActual == ruta,

                        onClick = {
                            navController.navigate(ruta) {
                                popUpTo(Rutas.INICIO) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },

                        icon = {
                            Text(icono)
                        },

                        label = {
                            Text(nombre)
                        },

                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                colores.onPrimaryContainer,

                            selectedTextColor =
                                colores.primary,

                            indicatorColor =
                                colores.primaryContainer,

                            unselectedIconColor =
                                colores.onSurfaceVariant,

                            unselectedTextColor =
                                colores.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { paddingInterno ->

        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO,
            modifier = Modifier.padding(paddingInterno)
        ) {


            // INICIO
            composable(Rutas.INICIO) {

                val viewModel: HomeViewModel = hiltViewModel()

                val peliculas by viewModel.peliculas
                    .collectAsStateWithLifecycle()

                // Película seleccionada para eliminar
                var peliculaAEliminar by remember {
                    mutableStateOf<PeliculaListado?>(null)
                }

                HomeScreen(
                    peliculas = peliculas,

                    onEditar = { pelicula ->
                        navController.navigate(
                            Rutas.editarPelicula(pelicula.id)
                        )
                    },

                    onEliminar = { pelicula ->
                        peliculaAEliminar = pelicula
                    }
                )

                // Ventana de confirmación
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

            // AGREGAR
            composable(Rutas.AGREGAR) {

                FormPeliculaRoute(
                    onGuardado = {
                        navController.popBackStack(
                            Rutas.INICIO,
                            false
                        )
                    },

                    onCancelar = {
                        navController.popBackStack(
                            Rutas.INICIO,
                            false
                        )
                    }
                )
            }


            // EDITAR PELÍCULA
            composable(
                route = Rutas.EDITAR,
                arguments = listOf(
                    navArgument("peliculaId") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->

                val peliculaId = entrada.arguments
                    ?.getInt("peliculaId") ?: -1

                FormPeliculaRoute(
                    peliculaId = peliculaId,

                    onGuardado = {
                        navController.popBackStack()
                    },

                    onCancelar = {
                        navController.popBackStack()
                    }
                )
            }

            // AJUSTES
            composable(Rutas.AJUSTES) {
                ajustesScreen()
            }
        }
    }
}
