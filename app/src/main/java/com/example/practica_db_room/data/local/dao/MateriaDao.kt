package com.example.practica_db_room.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.practica_db_room.data.local.entities.MateriaEntity
import com.example.practica_db_room.data.local.entities.ParaleloEntity
import com.example.practica_db_room.data.local.entities.ParaleloMateriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MateriaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMateria(materia: MateriaEntity)

    @Update
    suspend  fun actualizarMateria(materia: MateriaEntity)

    @Delete
    suspend fun eliminarMateria(materia: MateriaEntity)

    @Query("SELECT * FROM materias ORDER BY nombre ASC")
    fun obtenerMaterias(): Flow<List<MateriaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun asignarMateriaParalelo(paraleloMateria: ParaleloMateriaEntity)

    @Query("DELETE FROM paralelo_materia WHERE paraleloId =:paraleloId AND materiaId=:materiaId")
    suspend fun quitarMateriaDeParalelo(paraleloId: Long, materiaId: Long)

    @Query("""
        SELECT m.* FROM materias m
        INNER JOIN paralelo_materia pm ON m.id = pm.materiaId
        WHERE pm.paraleloId = :paraleloId
    """)
    fun obtenerMateriasPorParalelo(paraleloId: Long): Flow<List<MateriaEntity>>

    @Query("""
        SELECT p.* FROM paralelos p
        INNER JOIN paralelo_materia pm ON p.id = pm.paraleloId
        WHERE pm.materiaId = :materiaId
    """)
    fun obtenerParalelosPorMateria(materiaId: Long): Flow<List<ParaleloEntity>>

    @Query("SELECT * FROM materias WHERE id = :materiaId LIMIT 1")
    fun obtenerMateriaPorId(materiaId: Long): Flow<MateriaEntity?>
}