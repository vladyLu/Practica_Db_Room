package com.example.practica_db_room.di

import com.example.practica_db_room.data.repository.ParaleloRepositoryImpl
import com.example.practica_db_room.domain.repository.ParaleloRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class ParaleloRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindParaleloRepository(
        impl: ParaleloRepositoryImpl
    ): ParaleloRepository
}