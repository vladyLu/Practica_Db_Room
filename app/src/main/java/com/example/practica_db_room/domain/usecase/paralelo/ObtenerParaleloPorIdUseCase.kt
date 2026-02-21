package com.example.practica_db_room.domain.usecase.paralelo

import com.example.practica_db_room.domain.repository.ParaleloRepository
import javax.inject.Inject

class ObtenerParaleloPorIdUseCase @Inject constructor(
    private val repository: ParaleloRepository
) {
    operator fun invoke(paraleloId: Long) =
        repository.obtenerParaleloPorId(paraleloId)
}