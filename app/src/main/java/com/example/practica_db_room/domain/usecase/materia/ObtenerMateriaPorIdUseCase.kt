package com.example.practica_db_room.domain.usecase.materia

import com.example.practica_db_room.domain.repository.MateriaRepository
import com.example.practica_db_room.domain.repository.ParaleloRepository
import javax.inject.Inject

class ObtenerMateriaPorIdUseCase @Inject constructor(
    private val repository: MateriaRepository
) {
    operator fun invoke(materiaId: Long) =
        repository.obtenerMateriaPorId(materiaId)
}