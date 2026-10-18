package com.potter.micine.data.repository

import kotlinx.coroutines.flow.Flow
import com.potter.micine.data.model.Pelicula

interface PeliculaRepository {

    fun obtenerTodas(): Flow<List<Pelicula>>

    fun obtenerVistas(): Flow<List<Pelicula>>

    fun obtenerPendientes(): Flow<List<Pelicula>>

    fun obtenerPorId(id: Int): Flow<Pelicula?>

    suspend fun insertar(pelicula: Pelicula): Long

    suspend fun actualizar(pelicula: Pelicula): Int

    suspend fun eliminar(pelicula: Pelicula): Int
}
