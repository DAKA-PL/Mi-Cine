package com.potter.micine.data.repository

import com.potter.micine.data.local.PeliculaDao
import com.potter.micine.data.local.PeliculaEntity
import com.potter.micine.data.model.Pelicula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PeliculaRepositoryImpl(
    private val peliculaDao: PeliculaDao
) : PeliculaRepository {

    override fun obtenerTodas(): Flow<List<Pelicula>> {
        return peliculaDao.obtenerTodas().map { lista ->
            lista.map { it.toModel() }
        }
    }

    override fun obtenerVistas(): Flow<List<Pelicula>> {
        return peliculaDao.obtenerVistas().map { lista ->
            lista.map { it.toModel() }
        }
    }

    override fun obtenerPendientes(): Flow<List<Pelicula>> {
        return peliculaDao.obtenerPendientes().map { lista ->
            lista.map { it.toModel() }
        }
    }

    override fun obtenerPorId(id: Int): Flow<Pelicula?> {
        require(id > 0) {
            "El identificador debe ser mayor que cero"
        }

        return peliculaDao.obtenerPorId(id).map { entidad ->
            entidad?.toModel()
        }
    }

    override suspend fun insertar(pelicula: Pelicula): Long {
        validarPelicula(pelicula)
        return peliculaDao.insertar(pelicula.toEntity())
    }

    override suspend fun actualizar(pelicula: Pelicula): Int {
        require(pelicula.id > 0) {
            "La película debe tener un ID válido"
        }

        validarPelicula(pelicula)
        return peliculaDao.actualizar(pelicula.toEntity())
    }

    override suspend fun eliminar(pelicula: Pelicula): Int {
        require(pelicula.id > 0) {
            "La película debe tener un ID válido"
        }

        return peliculaDao.eliminar(pelicula.toEntity())
    }

    private fun validarPelicula(pelicula: Pelicula) {
        require(pelicula.titulo.isNotBlank()) {
            "El título no puede estar vacío"
        }

        require(pelicula.genero.isNotBlank()) {
            "El género no puede estar vacío"
        }

        require(pelicula.calificacion == null ||
                pelicula.calificacion in 1..5) {
            "La calificación debe estar entre 1 y 5"
        }

        require(pelicula.vista || pelicula.calificacion == null) {
            "No se puede calificar una película pendiente"
        }
    }

    private fun PeliculaEntity.toModel(): Pelicula {
        return Pelicula(
            id = id,
            titulo = titulo,
            genero = genero,
            vista = vista,
            calificacion = calificacion
        )
    }

    private fun Pelicula.toEntity(): PeliculaEntity {
        return PeliculaEntity(
            id = id,
            titulo = titulo.trim(),
            genero = genero.trim(),
            vista = vista,
            calificacion = calificacion
        )
    }
}
