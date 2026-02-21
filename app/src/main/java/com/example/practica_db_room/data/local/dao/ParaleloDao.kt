package com.example.practica_db_room.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.practica_db_room.data.local.entities.ParaleloConEstudiante
import com.example.practica_db_room.data.local.entities.ParaleloConMateria
import com.example.practica_db_room.data.local.entities.ParaleloEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParaleloDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarParalelo(paralelo: ParaleloEntity)

    @Update
    suspend fun actualizarParalelo(paralelo: ParaleloEntity)

    @Delete
    suspend fun eliminarParalelo(paralelo: ParaleloEntity)

    @Query("SELECT * FROM paralelos WHERE cursoId=:cursoId ORDER BY nombre ASC")
    fun obtenerParalelosPorCursos(cursoId: Long): Flow<List<ParaleloEntity>>

    @Transaction
    @Query("SELECT *FROM paralelos WHERE id=:paraleloId")
    fun obtenerParalelosConEstudiantes(paraleloId: Long): Flow<ParaleloConEstudiante?>

    @Transaction
    @Query("SELECT *FROM paralelos WHERE id=:paraleloId")
    fun obtenerParalelosConMaterias(paraleloId: Long): Flow<ParaleloConMateria?>

    @Query("SELECT * FROM paralelos WHERE id = :paraleloId LIMIT 1")
    fun obtenerParaleloPorId(paraleloId: Long): Flow<ParaleloEntity?>
}