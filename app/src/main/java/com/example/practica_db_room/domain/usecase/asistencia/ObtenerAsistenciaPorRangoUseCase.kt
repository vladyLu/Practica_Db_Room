package com.example.practica_db_room.domain.usecase.asistencia

import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import java.time.LocalDate
import javax.inject.Inject

class ObtenerAsistenciaPorRangoUseCase @Inject constructor(
    private val repository: AsistenciaRepostory
) {
    operator fun invoke(inicio: LocalDate, fin: LocalDate) =
        repository.obtenerPorRango(inicio, fin)
}
