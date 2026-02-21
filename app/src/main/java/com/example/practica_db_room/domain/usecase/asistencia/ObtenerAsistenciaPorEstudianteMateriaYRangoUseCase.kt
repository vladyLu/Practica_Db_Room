package com.example.practica_db_room.domain.usecase.asistencia

import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ObtenerAsistenciaPorEstudianteMateriaYRangoUseCase @Inject constructor(
    private val asistenciaRepository: AsistenciaRepostory
) {
    operator fun invoke(estudianteId: Long, materiaId: Long, inicio: LocalDate, fin: LocalDate
    ): Flow<List<AsistenciaEntity>> {
        return asistenciaRepository.obtenerAsistenciasPorEstudianteMateriaYRango(
            estudianteId,
            materiaId,
            inicio,
            fin
        )
    }
}