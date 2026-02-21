package com.example.practica_db_room.domain.usecase.asistencia

import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import javax.inject.Inject

class ObtenerAsistenciaPorMateriaUseCase @Inject constructor(
    private val repository: AsistenciaRepostory
) {
    operator fun invoke(id: Long) = repository.obtenerPorMateria(id)
}