package com.example.practica_db_room.domain.usecase.asistencia

import com.example.practica_db_room.domain.model.Asistencia
import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import javax.inject.Inject

class RegistrarAsistenciaUseCase @Inject constructor(
    private val repostory: AsistenciaRepostory
) {
    suspend operator fun invoke(asistencia: Asistencia){
        repostory.registrarAsistencia(asistencia)
    }
}