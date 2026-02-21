package com.example.practica_db_room.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ParaleloConEstudiante(
    @Embedded val parelelo: ParaleloEntity,
    @Relation(
        parentColumn = "id",
        entityColumn="paraleloId"
    )
    val estudiante: List<EstudianteEntity>
)
