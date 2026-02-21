package com.example.practica_db_room.domain.usecase.estudiante

import javax.inject.Inject

data class EstudianteUseCase @Inject constructor(
    val insertar: InsertarEstudianteUseCase,
    val actualizar: ActualizarEstudianteUseCase,
    val eliminar: EliminarEstudianteUseCase,
    val obtenerPorParalelo: ObtenerEstudiantePorParaleloUseCase,
    val buscar: BuscarEstudianteUseCase
)
