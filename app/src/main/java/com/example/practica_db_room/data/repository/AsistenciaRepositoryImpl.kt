package com.example.practica_db_room.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.practica_db_room.data.local.dao.AsistenciaDao
import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.data.mappers.toDomain
import com.example.practica_db_room.domain.model.Asistencia
import com.example.practica_db_room.domain.repository.AsistenciaRepostory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class AsistenciaRepositoryImpl @Inject constructor(
    private val asistenciaDao: AsistenciaDao
): AsistenciaRepostory {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun registrarAsistencia(asistencia: Asistencia) {
        val fecha= LocalDate.parse(asistencia.fecha)
        val registrada=asistenciaDao.obtenerAsistenciaDelDia(
            asistencia.estudianteId,
            asistencia.materiaId,
            fecha
        )

        if (registrada==null){
            asistenciaDao.insertarAsistencia(
                AsistenciaEntity(
                    estudianteId = asistencia.estudianteId,
                    materiaId = asistencia.materiaId,
                    fecha = fecha,
                    estado = asistencia.estado
                )
            )
        }else{
            asistenciaDao.actualizarAsistencia(
                registrada.copy(
                    estado = asistencia.estado
                )
            )
        }
    }

    override fun obtenerPorEstudiante(estudanteId: Long): Flow<List<Asistencia>> =
        asistenciaDao.obtenerAsistenciasPorEstudiante(estudanteId).map { it.toDomain() }


    override fun obtenerPorMateria(materiaId: Long): Flow<List<Asistencia>> =
        asistenciaDao.obtenerAsistenciasPorMateria(materiaId).map { it.toDomain() }

    override fun obtenerPorRango(
        inicio: LocalDate,
        fin: LocalDate
    ): Flow<List<Asistencia>> =
        asistenciaDao.obtenerAsistenciasPorRango(inicio, fin).map{it.toDomain()}

    override fun obtenerPorMateriaYFecha(
        materiaId: Long,
        fecha: LocalDate
    ): Flow<List<Asistencia>> =
        asistenciaDao.obtenerAsistenciasPorMateriaYFecha(materiaId, fecha)
            .map { it.toDomain() }

    override fun obtenerAsistenciasPorEstudianteMateriaYRango(
        estudianteId: Long,
        materiaId: Long,
        inicio: LocalDate,
        fin: LocalDate
    ): Flow<List<AsistenciaEntity>> {
        return asistenciaDao.obtenerAsistenciasPorEstudianteMateriaYRango(
            estudianteId,
            materiaId,
            inicio,
            fin
        )
    }
}