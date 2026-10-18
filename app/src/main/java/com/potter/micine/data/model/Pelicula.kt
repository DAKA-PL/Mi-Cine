package com.potter.micine.data.model

data class Pelicula(
    val id: Int = 0,
    val titulo: String,
    val genero: String,
    val vista: Boolean = false,
    val calificacion: Int? = null
)
