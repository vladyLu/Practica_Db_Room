package com.example.practica_db_room.domain.usecase.asistencia

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.practica_db_room.domain.model.Asistencia
import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ObtenerAsistenciaDeHoyPorMateriaUseCase @Inject constructor(
    private val repository: AsistenciaRepostory
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(materiaId: Long): Flow<List<Asistencia>> {
        val hoy = LocalDate.now()
        return repository.obtenerPorMateriaYFecha(materiaId, hoy)
    }
}