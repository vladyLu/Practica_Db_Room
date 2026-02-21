package com.example.practica_db_room.domain.usecase.paralelo

import com.example.practica_db_room.domain.repository.CursoRepository
import javax.inject.Inject

class ObtenerCursoPorId @Inject constructor(
    private val repository: CursoRepository
) {
    operator fun invoke(cursoId: Long) =
        repository.obtenerCursoPorId(cursoId)
}