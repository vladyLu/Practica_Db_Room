package com.example.practica_db_room.domain.usecase.asistencia

import com.example.practica_db_room.data.local.dao.AsistenciaDao
import com.example.practica_db_room.domain.model.ReporteMateriaParalelo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ObtenerReportePorMateriaYParaleloUseCase @Inject constructor(
    private val asistenciaDao: AsistenciaDao
) {
    operator fun invoke(
        paraleloId: Long,
        materiaId: Long,
        inicio: LocalDate,
        fin: LocalDate
    ): Flow<List<ReporteMateriaParalelo>> {
        return asistenciaDao.obtenerReportePorMateriaYParalelo(
            paraleloId, materiaId, inicio, fin
        )
    }
}