package com.example.practica_db_room.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "asistencias",
    foreignKeys = [
        ForeignKey(
            entity = EstudianteEntity::class,
            parentColumns = ["id"],
            childColumns = ["estudianteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity= MateriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["materiaId"],
            onDelete = ForeignKey.CASCADE

        )
    ],
    indices = [Index(value = ["estudianteId"]), Index(value = ["materiaId"]), Index(value = ["fecha"])]
    )
data class AsistenciaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val estudianteId: Long,
    val materiaId: Long,
    val fecha: LocalDate,
    val estado: String
)
