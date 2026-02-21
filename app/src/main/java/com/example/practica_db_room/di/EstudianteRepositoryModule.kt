package com.example.practica_db_room.di

import com.example.practica_db_room.data.repository.EstudianteRepositoryImpl
import com.example.practica_db_room.data.repository.MateriaRepositoryImpl
import com.example.practica_db_room.domain.repository.EstudianteRepository
import com.example.practica_db_room.domain.repository.MateriaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EstudianteRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        impl: EstudianteRepositoryImpl
    ): EstudianteRepository
}