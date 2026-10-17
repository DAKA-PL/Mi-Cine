package com.potter.micine.ui.form

data class FormPeliculaUIState(
    val titulo: String = "",
    val generoId: Long = 0L,
    val generosDisponibles: List<String> = listOf(
        "Acción",
        "Animada",
        "Comedia",
        "Drama",
        "Ciencia Ficción"
    ),
    val esVista: Boolean = false,
    val calificacion: Int = 0,
    val nuevoGeneroNombre: String = "",
    val mostrarDialogoNuevoGenero: Boolean = false,
    val estaGuardado: Boolean = false,
    val guardando: Boolean = false,
    val error: String? = null
)