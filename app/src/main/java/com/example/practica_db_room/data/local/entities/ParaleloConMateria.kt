package com.example.practica_db_room.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ParaleloConMateria(
    @Embedded val paralelo: ParaleloEntity,
    @Relation(
        parentColumn = "id",
        entityColumn ="id",
        associateBy = Junction(
            value = ParaleloMateriaEntity::class,
            parentColumn = "paraleloId",
            entityColumn = "materiaId"

        )
    )
    val materias: List<MateriaEntity>
)
