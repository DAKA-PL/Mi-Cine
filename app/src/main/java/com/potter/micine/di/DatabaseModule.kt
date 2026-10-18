package com.potter.micine.di

import android.content.Context
import androidx.room.Room
import com.potter.micine.data.local.MiCineDatabase
import com.potter.micine.data.local.PeliculaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MiCineDatabase {

        return Room.databaseBuilder(
            context,
            MiCineDatabase::class.java,
            "mi_cine_database"
        ).build()
    }

    @Provides
    fun providePeliculaDao(
        database: MiCineDatabase
    ): PeliculaDao {

        return database.peliculaDao()
    }
}