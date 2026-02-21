package com.example.practica_db_room.domain.usecase.materia

import com.example.practica_db_room.domain.model.Materia
import com.example.practica_db_room.domain.repository.MateriaRepository
import javax.inject.Inject

class EliminarMateriaUseCase @Inject constructor(
    private val repository: MateriaRepository
) {
    suspend operator fun invoke(materia: Materia)=repository.eliminarMateria(materia)
}