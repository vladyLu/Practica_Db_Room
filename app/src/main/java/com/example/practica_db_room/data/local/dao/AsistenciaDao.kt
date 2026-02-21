package com.example.practica_db_room.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.domain.model.ReporteMateriaParalelo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface AsistenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAsistencia(asistencia: AsistenciaEntity)

    @Update
    suspend fun actualizarAsistencia(asistencia: AsistenciaEntity)

    @Query("SELECT * FROM asistencias WHERE estudianteId = :estudianteId ORDER BY fecha DESC")
    fun obtenerAsistenciasPorEstudiante(estudianteId: Long): Flow<List<AsistenciaEntity>>

    @Query("SELECT * FROM asistencias WHERE materiaId = :materiaId ORDER BY fecha DESC")
    fun obtenerAsistenciasPorMateria(materiaId: Long): Flow<List<AsistenciaEntity>>

    @Query("SELECT * FROM asistencias WHERE fecha BETWEEN :inicio AND :fin ORDER BY fecha ASC")
    fun obtenerAsistenciasPorRango(inicio: LocalDate, fin: LocalDate): Flow<List<AsistenciaEntity>>

    @Query("""
    SELECT * FROM asistencias WHERE materiaId = :materiaId AND fecha = :fecha ORDER BY estudianteId ASC""")
    fun obtenerAsistenciasPorMateriaYFecha(
        materiaId: Long,
        fecha: LocalDate
    ): Flow<List<AsistenciaEntity>>

    @Query("""SELECT * FROM asistencias WHERE estudianteId = :estudianteId AND materiaId = :materiaId AND fecha = :fecha LIMIT 1""")
    suspend fun obtenerAsistenciaDelDia(
        estudianteId: Long,
        materiaId: Long,
        fecha: LocalDate
    ): AsistenciaEntity?

    @Query("""SELECT * FROM asistencias WHERE estudianteId = :estudianteId AND materiaId = :materiaId AND fecha BETWEEN :inicio AND :fin ORDER BY fecha DESC
""")
    fun obtenerAsistenciasPorEstudianteMateriaYRango(
        estudianteId: Long,
        materiaId: Long,
        inicio: LocalDate,
        fin: LocalDate
    ): Flow<List<AsistenciaEntity>>

    @Query("""
    SELECT 
        e.id AS estudianteId,
        e.nombre AS nombre,
        e.apellido AS apellido,
        a.estado AS estado,
        a.fecha AS fecha
    FROM asistencias a
    INNER JOIN estudiantes e ON e.id = a.estudianteId
    WHERE e.paraleloId = :paraleloId
      AND a.materiaId = :materiaId
      AND a.fecha BETWEEN :inicio AND :fin
    ORDER BY e.apellido, e.nombre, a.fecha ASC
""")
    fun obtenerReportePorMateriaYParalelo(
        paraleloId: Long,
        materiaId: Long,
        inicio: LocalDate,
        fin: LocalDate
    ): Flow<List<ReporteMateriaParalelo>>

}