package com.example.practica_db_room.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.practica_db_room.data.local.entities.EstudianteConAsistencia
import com.example.practica_db_room.data.local.entities.EstudianteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EstudianteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstudiante(estudiante: EstudianteEntity)

    @Update
    suspend fun actualizarEstudiante(estudiante: EstudianteEntity)

    @Delete
    suspend fun eliminarEstudiante(estudiante: EstudianteEntity)

    @Query("SELECT * FROM estudiantes WHERE paraleloId= :paraleloId ORDER BY apellido, nombre ASC")
    fun obtenerEstudiantePorParalelo(paraleloId: Long): Flow<List<EstudianteEntity>>

    @Query("SELECT * FROM estudiantes WHERE nombre LIKE '%' || :nombre ||'%' ")
    fun buscarEstudiante(nombre: String): Flow<List<EstudianteEntity>>

    @Transaction
    @Query("SELECT  * FROM estudiantes WHERE id= :id")
    fun obtenerEstudianteConAsistencia(id: Long): Flow<EstudianteConAsistencia>

    @Query("SELECT nombre || ' ' || apellido FROM estudiantes WHERE id = :id")
    suspend fun obtenerNombreCompletoPorId(id: Long): String?
}