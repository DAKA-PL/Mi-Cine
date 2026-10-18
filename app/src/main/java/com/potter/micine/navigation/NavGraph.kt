
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

// RUTAS DE LA APLICACIÓN
object Rutas {
    const val INICIO = "inicio"
    const val AGREGAR = "agregar"
    const val AJUSTES = "ajustes"
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

                HomeScreen(
                    peliculas = peliculas
                )
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

            // AJUSTES
            composable(Rutas.AJUSTES) {
                ajustesScreen()
            }
        }
    }
}
