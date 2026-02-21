package com.example.practica_db_room.domain.repository

import com.example.practica_db_room.data.local.dao.AsistenciaDao
import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.domain.model.Asistencia
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AsistenciaRepostory {
    suspend fun registrarAsistencia(asistencia: Asistencia)
    fun obtenerPorEstudiante(estudanteId: Long): Flow<List<Asistencia>>
    fun obtenerPorMateria(materiaId: Long): Flow<List<Asistencia>>
    fun obtenerPorRango(inicio: LocalDate, fin: LocalDate): Flow<List<Asistencia>>
    fun obtenerPorMateriaYFecha(materiaId: Long, fecha: LocalDate): Flow<List<Asistencia>>

    fun obtenerAsistenciasPorEstudianteMateriaYRango(estudianteId: Long, materiaId: Long, inicio: LocalDate, fin: LocalDate): Flow<List<AsistenciaEntity>>
}