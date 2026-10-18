package com.potter.micine.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculaDao {

    // Obtener todas las películas
    @Query("SELECT * FROM peliculas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<PeliculaEntity>>

    // Obtener únicamente películas vistas
    @Query("SELECT * FROM peliculas WHERE vista = 1 ORDER BY id DESC")
    fun obtenerVistas(): Flow<List<PeliculaEntity>>

    // Obtener únicamente películas pendientes
    @Query("SELECT * FROM peliculas WHERE vista = 0 ORDER BY id DESC")
    fun obtenerPendientes(): Flow<List<PeliculaEntity>>

    // Buscar una película por su identificador
    @Query("SELECT * FROM peliculas WHERE id = :id LIMIT 1")
    fun obtenerPorId(id: Int): Flow<PeliculaEntity?>

    // Registrar una película nueva
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(pelicula: PeliculaEntity): Long

    // Modificar una película existente
    @Update
    suspend fun actualizar(pelicula: PeliculaEntity): Int

    // Eliminar una película
    @Delete
    suspend fun eliminar(pelicula: PeliculaEntity): Int
}
