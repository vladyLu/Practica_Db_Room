package com.example.practica_db_room.domain.usecase.estudiante

import com.example.practica_db_room.domain.model.Estudiante
import com.example.practica_db_room.domain.repository.EstudianteRepository
import javax.inject.Inject

class ActualizarEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante) =
        repository.actualizarEstudiante(estudiante)
}