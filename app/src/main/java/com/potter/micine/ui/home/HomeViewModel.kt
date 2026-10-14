
package com.potter.micine.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _peliculas = MutableStateFlow<List<PeliculaListado>>(
        emptyList()
    )

    val peliculas: StateFlow<List<PeliculaListado>> =
        _peliculas.asStateFlow()
}
