package com.potter.micine.di

import com.potter.micine.data.repository.PeliculaRepository
import com.potter.micine.data.repository.PeliculaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPeliculaRepository(
        implementation: PeliculaRepositoryImpl
    ): PeliculaRepository
}
