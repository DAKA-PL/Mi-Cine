package com.potter.micine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PeliculaEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MiCineDatabase : RoomDatabase() {

    abstract fun peliculaDao(): PeliculaDao

}
