package com.example.practica_db_room.domain.usecase.paralelo

import com.example.practica_db_room.domain.model.Paralelo
import com.example.practica_db_room.domain.repository.ParaleloRepository
import javax.inject.Inject

class EliminarParaleloUseCase @Inject constructor(
    private val repository: ParaleloRepository
) {
    suspend operator fun invoke(paralelo: Paralelo)=
        repository.eliminarParalelo(paralelo)
}