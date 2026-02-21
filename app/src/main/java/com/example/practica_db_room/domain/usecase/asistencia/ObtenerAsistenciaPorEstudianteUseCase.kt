package com.example.practica_db_room.domain.usecase.asistencia

import androidx.room.Insert
import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import javax.inject.Inject

class ObtenerAsistenciaPorEstudianteUseCase @Inject constructor(
    private val repostory: AsistenciaRepostory
) {
    operator fun invoke(id: Long)=repostory.obtenerPorEstudiante(id)
}