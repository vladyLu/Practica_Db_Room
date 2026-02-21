package com.example.practica_db_room.domain.usecase.estudiante

import com.example.practica_db_room.domain.model.Estudiante
import com.example.practica_db_room.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerEstudiantePorParaleloUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(paraleloId: Long): Flow<List<Estudiante>> =
        repository.obtenerEstudiantesPorParalelo(paraleloId)
}