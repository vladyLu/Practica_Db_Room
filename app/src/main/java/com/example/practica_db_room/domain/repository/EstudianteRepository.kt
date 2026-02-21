package com.example.practica_db_room.domain.repository

import com.example.practica_db_room.domain.model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {
    fun obtenerEstudiantesPorParalelo(paraleloId: Long): Flow<List<Estudiante>>
    fun buscarEstudiante(nombre: String): Flow<List<Estudiante>>
    suspend fun insertarEstudiante(estudiante: Estudiante)
    suspend fun actualizarEstudiante(estudiante: Estudiante)
    suspend fun eliminarEstudiante(estudiante: Estudiante)
}