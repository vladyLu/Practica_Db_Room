package com.example.practica_db_room.domain.usecase.paralelo

import javax.inject.Inject

data class ParalelosUseCase @Inject constructor(
    val insertarParalelo:InsertarParaleloUseCase,
    val actualizarParalelo:ActualizarParaleloUseCase,
    val eliminarParalelo:EliminarParaleloUseCase,
    val obtenerParaleloPorCurso:ObtenerParaleloPorCursoUseCase,
    val obtenerParaleloPorId: ObtenerParaleloPorIdUseCase,
    val obtenerCursoPorId: ObtenerCursoPorId

)
