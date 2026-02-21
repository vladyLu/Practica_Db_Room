package com.example.practica_db_room.data.mappers

import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.domain.model.Asistencia

fun List<AsistenciaEntity>.toDomain() = map {
    Asistencia(
        id = it.id,
        fecha = it.fecha.toString(),
        estado = it.estado,
        estudianteId = it.estudianteId,
        materiaId = it.materiaId
    )
}