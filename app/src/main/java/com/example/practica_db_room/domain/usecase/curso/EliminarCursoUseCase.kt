package com.example.practica_db_room.domain.usecase.curso

import com.example.practica_db_room.domain.model.Curso
import com.example.practica_db_room.domain.repository.CursoRepository
import javax.inject.Inject

class EliminarCursoUseCase @Inject constructor(
    private val repository: CursoRepository
) {
    suspend operator fun invoke(curso: Curso){
        repository.eliminarCurso(curso)
    }
}