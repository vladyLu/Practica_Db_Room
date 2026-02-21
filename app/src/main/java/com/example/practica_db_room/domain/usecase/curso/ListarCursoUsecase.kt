package com.example.practica_db_room.domain.usecase.curso

import com.example.practica_db_room.domain.model.Curso
import com.example.practica_db_room.domain.repository.CursoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListarCursoUsecase @Inject constructor(
    private val repository: CursoRepository
) {
    operator fun invoke(): Flow<List<Curso>>{
        return repository.listarCursos()
    }
}