package com.example.practica_db_room.di

import android.content.Context
import androidx.room.Room
import com.example.practica_db_room.data.local.dao.CursoDao
import com.example.practica_db_room.data.local.dao.ParaleloDao
import com.example.practica_db_room.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "mi_app_database.db"
        ).build()
    }

    @Provides @Singleton fun provideCursoDao(db: AppDatabase)=db.cursoDao()
    @Provides @Singleton fun provideParaleloDao(db: AppDatabase)=db.paraleloDao()
    @Provides @Singleton fun provideMateriaDao(db: AppDatabase)=db.materiaDao()
    @Provides @Singleton fun provideEstudianteDao(db: AppDatabase)=db.estudianteDao()

    @Provides @Singleton fun provideAsistenciaDao(db: AppDatabase)=db.asistenciaDao()
}