package com.example.practica_db_room.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class EstudianteConAsistencia(
    @Embedded val estudiante: EstudianteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "estudianteId"
    )
    val asistencias: List<AsistenciaEntity>
)
