package com.example.practica_db_room.domain.usecase.materia

import com.example.practica_db_room.domain.repository.MateriaRepository
import javax.inject.Inject

class QuitarMateriaDeParaleloUseCase @Inject constructor(
    private val repository: MateriaRepository
) {
    suspend operator fun invoke(paraleloId: Long, materiaId: Long)=repository.quitarMateriaDeParalelo(paraleloId, materiaId)
}