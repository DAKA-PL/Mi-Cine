package com.potter.micine.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peliculas")
data class PeliculaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val titulo: String,

    val genero: String,

    val vista: Boolean = false,

    val calificacion: Int? = null
)
