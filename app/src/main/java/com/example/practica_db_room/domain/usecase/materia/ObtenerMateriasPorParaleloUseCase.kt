package com.example.practica_db_room.domain.usecase.materia

import com.example.practica_db_room.domain.model.Materia
import com.example.practica_db_room.domain.repository.MateriaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerMateriasPorParaleloUseCase @Inject constructor(
    private val repository: MateriaRepository
) {
    operator fun invoke(paraleloId: Long): Flow<List<Materia>>{
        return repository.obtenerMateriasPorParalelo(paraleloId)
    }
}