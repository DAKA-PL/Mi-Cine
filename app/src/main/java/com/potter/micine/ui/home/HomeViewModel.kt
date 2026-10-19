
package com.potter.micine.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potter.micine.data.repository.PeliculaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val peliculaRepository: PeliculaRepository
) : ViewModel() {

    // Obtener las películas desde el repositorio
    val peliculas: StateFlow<List<PeliculaListado>> =
        peliculaRepository.obtenerTodas()
            .map { lista ->
                lista.map { pelicula ->
                    PeliculaListado(
                        titulo = pelicula.titulo,
                        genero = pelicula.genero,
                        estado = if (pelicula.vista) {
                            "Vista"
                        } else {
                            "Pendiente"
                        },
                        calificacion = pelicula.calificacion ?: 0,
                        id = pelicula.id
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Eliminar una película por su identificador
    fun eliminarPelicula(id: Int) {
        if (id <= 0) return

        viewModelScope.launch {
            try {
                val pelicula = peliculaRepository
                    .obtenerPorId(id)
                    .first()

                if (pelicula != null) {
                    peliculaRepository.eliminar(pelicula)
                }

            } catch (e: Exception) {
                Log.e(
                    "MI_CINE",
                    "Error al eliminar película",
                    e
                )
            }
        }
    }
}
