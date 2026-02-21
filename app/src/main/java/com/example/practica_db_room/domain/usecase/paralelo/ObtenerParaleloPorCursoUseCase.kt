package com.example.practica_db_room.domain.usecase.paralelo

import com.example.practica_db_room.domain.repository.ParaleloRepository
import javax.inject.Inject

class ObtenerParaleloPorCursoUseCase @Inject constructor(
    private val repository: ParaleloRepository
) {
    operator fun invoke(cursoId: Long)=
        repository.obtenerParalelosPorCurso(cursoId)
}