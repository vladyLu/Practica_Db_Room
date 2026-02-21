package com.example.practica_db_room.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CursoConParalelos(
    @Embedded val curso: CursoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "cursoId"
    )
    val paralelos: List<ParaleloEntity>
)
