
package com.potter.micine.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potter.micine.data.local.PeliculaDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val peliculaDao: PeliculaDao
) : ViewModel() {

    val peliculas: StateFlow<List<PeliculaListado>> =
        peliculaDao.obtenerTodas()
            .map { entidades ->
                entidades.map { pelicula ->
                    PeliculaListado(
                        titulo = pelicula.titulo,
                        genero = pelicula.genero,
                        estado = if (pelicula.vista) "Vista" else "Pendiente",
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

    fun eliminarPelicula(id: Int) {
        if (id <= 0) return

        viewModelScope.launch {
            val pelicula = peliculaDao.obtenerPorId(id).first()

            if (pelicula != null) {
                peliculaDao.eliminar(pelicula)
            }
        }
    }
}
