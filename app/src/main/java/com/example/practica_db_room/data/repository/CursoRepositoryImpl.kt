package com.example.practica_db_room.data.repository

import com.example.practica_db_room.data.local.dao.CursoDao
import com.example.practica_db_room.data.local.entities.CursoEntity
import com.example.practica_db_room.data.mappers.toDomain
import com.example.practica_db_room.data.mappers.toEntity
import com.example.practica_db_room.domain.model.Curso
import com.example.practica_db_room.domain.repository.CursoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CursoRepositoryImpl @Inject constructor(
    private val dao: CursoDao
): CursoRepository {
    override fun listarCursos(): Flow<List<Curso>> {
        return dao.obtenerCursos().map { entities ->
            entities.map { it.toDomain() }

        }
    }

    override suspend fun isertarCurso(curso: Curso) {
        dao.insertarCurso(curso.toEntity())
    }

    override suspend fun actualizarCurso(curso: Curso) {
        dao.actualizarCurso(curso.toEntity())
    }

    override suspend fun eliminarCurso(curso: Curso) {
        dao.eliminarCurso(curso.toEntity())
    }

    override fun obtenerCursoPorId(cursoId: Long): Flow<Curso> {
        return dao.obtenerCursoPorIdSimpre(cursoId)
            .filterNotNull()
            .map { it.toDomain() }
    }
}