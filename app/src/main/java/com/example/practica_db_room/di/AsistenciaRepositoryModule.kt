package com.example.practica_db_room.di

import com.example.practica_db_room.data.repository.AsistenciaRepositoryImpl
import com.example.practica_db_room.data.repository.CursoRepositoryImpl
import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import com.example.practica_db_room.domain.repository.CursoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AsistenciaRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAsistenciaRepository(
        impl: AsistenciaRepositoryImpl
    ): AsistenciaRepostory
}