package com.example.practica_db_room.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes",
    foreignKeys = [
        ForeignKey(
            entity = ParaleloEntity::class,
            parentColumns = ["id"],
            childColumns = ["paraleloId"],
            onDelete = ForeignKey.CASCADE

        )
    ],
    indices = [Index(value=["paraleloId","nombre"], unique = false)]
)
data class EstudianteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val nombre: String,
    val apellido: String,
    val paraleloId: Long

)