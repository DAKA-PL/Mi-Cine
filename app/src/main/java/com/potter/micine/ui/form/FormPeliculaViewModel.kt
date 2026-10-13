package com.potter.micine.ui.form

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormPeliculaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FormPeliculaUIState())

    val uiState: StateFlow<FormPeliculaUIState> =
        _uiState.asStateFlow()

    fun actualizarTitulo(valor: String) {
        _uiState.update {
            it.copy(titulo = valor, error = null)
        }
    }

    fun actualizarGenero(id: Long) {
        _uiState.update {
            it.copy(generoId = id, error = null)
        }
    }


    fun actualizarEstado(vista: Boolean) {
        _uiState.update { estado ->
            estado.copy(
                esVista = vista,
                calificacion = if (vista) {
                    estado.calificacion
                } else {
                    0
                },
                error = null
            )
        }
    }




    fun actualizarCalificacion(valor: Int) {
        _uiState.update { estado ->
            val nuevaCalificacion = if (!estado.esVista) {
                0
            } else if (estado.calificacion == valor) {
                0
            } else {
                valor.coerceIn(1, 5)
            }

            estado.copy(
                calificacion = nuevaCalificacion,
                error = null
            )
        }
    }



    fun actualizarNuevoGenero(valor: String) {
        _uiState.update {
            it.copy(nuevoGeneroNombre = valor)
        }
    }

    fun mostrarDialogoGenero(mostrar: Boolean) {
        _uiState.update {
            it.copy(mostrarDialogoNuevoGenero = mostrar)
        }
    }

    fun agregarGenero() {
        val estado = _uiState.value
        val nombre = estado.nuevoGeneroNombre.trim()

        if (nombre.isBlank()) {
            _uiState.update {
                it.copy(error = "Escribe el nombre del género.")
            }
            return
        }

        val indiceExistente =
            estado.generosDisponibles.indexOfFirst {
                it.equals(nombre, ignoreCase = true)
            }

        if (indiceExistente >= 0) {
            _uiState.update {
                it.copy(
                    generoId = indiceExistente + 1L,
                    nuevoGeneroNombre = "",
                    mostrarDialogoNuevoGenero = false,
                    error = null
                )
            }
        } else {
            val nuevosGeneros =
                estado.generosDisponibles + nombre

            _uiState.update {
                it.copy(
                    generosDisponibles = nuevosGeneros,
                    generoId = nuevosGeneros.size.toLong(),
                    nuevoGeneroNombre = "",
                    mostrarDialogoNuevoGenero = false,
                    error = null
                )
            }
        }
    }


    fun validarFormulario(): Boolean {
        val estado = _uiState.value

        val mensaje = when {
            estado.titulo.isBlank() ->
                "Debes escribir el título de la película."

            estado.generoId !in
                    1L..estado.generosDisponibles.size.toLong() ->
                "Debes seleccionar un género."

            estado.esVista && estado.calificacion !in 1..5 ->
                "Debes calificar la película de 1 a 5 estrellas."

            else -> null
        }

        _uiState.update {
            it.copy(error = mensaje)
        }

        return mensaje == null
    }
}