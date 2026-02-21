package com.example.practica_db_room.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "paralelo_materia",
    primaryKeys = ["paraleloId", "materiaId"],
    foreignKeys = [
        ForeignKey(
            entity = ParaleloEntity::class,
            parentColumns = ["id"],
            childColumns = ["paraleloId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["paraleloId"]), Index(value = ["materiaId"])]
)
data class ParaleloMateriaEntity(
    val paraleloId: Long,
    val materiaId: Long
)
