package com.example.practica_db_room.data.repository

import com.example.practica_db_room.data.local.dao.EstudianteDao
import com.example.practica_db_room.data.mappers.toDomain
import com.example.practica_db_room.data.mappers.toEntity
import com.example.practica_db_room.domain.model.Estudiante
import com.example.practica_db_room.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstudianteRepositoryImpl @Inject constructor(
    private val dao: EstudianteDao
): EstudianteRepository {
    override fun obtenerEstudiantesPorParalelo(paraleloId: Long): Flow<List<Estudiante>> {
        return dao.obtenerEstudiantePorParalelo(paraleloId)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun buscarEstudiante(nombre: String): Flow<List<Estudiante>> {
        return dao.buscarEstudiante(nombre)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertarEstudiante(estudiante: Estudiante) {
        dao.insertarEstudiante(estudiante.toEntity())
    }

    override suspend fun actualizarEstudiante(estudiante: Estudiante) {
        dao.actualizarEstudiante(estudiante.toEntity())
    }

    override suspend fun eliminarEstudiante(estudiante: Estudiante) {
        dao.eliminarEstudiante(estudiante.toEntity())
    }
}