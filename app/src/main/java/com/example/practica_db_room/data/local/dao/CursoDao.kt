package com.example.practica_db_room.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.practica_db_room.data.local.entities.CursoConParalelos
import com.example.practica_db_room.data.local.entities.CursoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCurso(curso: CursoEntity)

    @Update
    suspend fun actualizarCurso(curso: CursoEntity)

    @Delete
    suspend fun eliminarCurso(curso: CursoEntity)

    @Query("SELECT * FROM cursos ORDER BY nombre ASC")
    fun obtenerCursos(): Flow<List<CursoEntity>>

    @Query("SELECT * FROM cursos WHERE id = :cursoId LIMIT 1")
    fun obtenerCursoPorIdSimpre(cursoId: Long): Flow<CursoEntity>

}