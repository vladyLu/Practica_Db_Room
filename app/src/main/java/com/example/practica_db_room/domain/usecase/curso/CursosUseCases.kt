package com.example.practica_db_room.domain.usecase.curso

import com.example.practica_db_room.domain.usecase.paralelo.ObtenerCursoPorId
import javax.inject.Inject

data class CursosUseCases @Inject constructor(
    val listarCursos: ListarCursoUsecase,
    val insertarCurso: InsertarCursoUseCase,
    val eliminarCurso: EliminarCursoUseCase,
    val actualizarCurso: ActualizarCursoUseCase,
)
