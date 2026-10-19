package com.potter.micine.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.potter.micine.data.repository.PeliculaRepository
import kotlinx.coroutines.launch

// ESTADO DE LA PANTALLA DE AJUSTES
data class AjustesUiState(
    val modoOscuro: Boolean = false,
    val idioma: String = "Español",
    val notificaciones: Boolean = false
)

@HiltViewModel
class AjustesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val peliculaRepository: PeliculaRepository
) : ViewModel() {

    fun borrarHistorial() {
        viewModelScope.launch {
            try {
                val eliminadas = peliculaRepository.borrarHistorial()

                android.util.Log.d(
                    "MI_CINE",
                    "Películas eliminadas: $eliminadas"
                )

            } catch (e: Exception) {
                android.util.Log.e(
                    "MI_CINE",
                    "Error al borrar el historial",
                    e
                )
            }
        }
    }

    // PREFERENCIAS GUARDADAS
    private val preferencias = context.getSharedPreferences(
        "MiCinePreferencias",
        Context.MODE_PRIVATE
    )

    // ESTADO INICIAL
    private val _uiState = MutableStateFlow(
        AjustesUiState(
            modoOscuro = preferencias.getBoolean(
                "modoOscuro", false
            ),
            idioma = preferencias.getString(
                "idioma", "Español"
            ) ?: "Español",
            notificaciones = preferencias.getBoolean(
                "notificaciones", false
            )
        )
    )

    val uiState: StateFlow<AjustesUiState> =
        _uiState.asStateFlow()

    // CAMBIAR MODO OSCURO
    fun cambiarModoOscuro(activado: Boolean) {
        _uiState.update {
            it.copy(modoOscuro = activado)
        }

        preferencias.edit()
            .putBoolean("modoOscuro", activado)
            .apply()
    }

    // CAMBIAR IDIOMA
    fun cambiarIdioma(nuevoIdioma: String) {
        _uiState.update {
            it.copy(idioma = nuevoIdioma)
        }

        preferencias.edit()
            .putString("idioma", nuevoIdioma)
            .apply()
    }

    // CAMBIAR NOTIFICACIONES
    fun cambiarNotificaciones(activado: Boolean) {
        _uiState.update {
            it.copy(notificaciones = activado)
        }

        preferencias.edit()
            .putBoolean("notificaciones", activado)
            .apply()
    }
}
