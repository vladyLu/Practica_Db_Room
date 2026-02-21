package com.example.practica_db_room.domain.repository

import com.example.practica_db_room.domain.model.Curso
import kotlinx.coroutines.flow.Flow

interface CursoRepository {
    fun listarCursos(): Flow<List<Curso>>
    suspend fun isertarCurso(curso: Curso)
    suspend fun actualizarCurso(curso: Curso)
    suspend fun eliminarCurso(curso: Curso)
    fun obtenerCursoPorId(cursoId: Long): Flow<Curso>
}