package com.example.practica_db_room.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.practica_db_room.components.Converters
import com.example.practica_db_room.data.local.dao.AsistenciaDao
import com.example.practica_db_room.data.local.dao.CursoDao
import com.example.practica_db_room.data.local.dao.EstudianteDao
import com.example.practica_db_room.data.local.dao.MateriaDao
import com.example.practica_db_room.data.local.dao.ParaleloDao
import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.data.local.entities.CursoEntity
import com.example.practica_db_room.data.local.entities.EstudianteEntity
import com.example.practica_db_room.data.local.entities.MateriaEntity
import com.example.practica_db_room.data.local.entities.ParaleloEntity
import com.example.practica_db_room.data.local.entities.ParaleloMateriaEntity

@Database(entities = [
    CursoEntity::class,
    ParaleloEntity::class,
    EstudianteEntity::class,
    MateriaEntity::class,
    ParaleloMateriaEntity::class,
    AsistenciaEntity::class], version = 3, exportSchema = false)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cursoDao(): CursoDao
    abstract fun paraleloDao(): ParaleloDao
    abstract fun estudianteDao(): EstudianteDao
    abstract fun materiaDao(): MateriaDao
    abstract fun asistenciaDao(): AsistenciaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "mi_app_database.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}