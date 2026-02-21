package com.example.practica_db_room.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "paralelos",
    foreignKeys = [
        ForeignKey(
            entity = CursoEntity::class,
            parentColumns = ["id"],
            childColumns = ["cursoId"],
            onDelete = ForeignKey.CASCADE
        )
    ], indices = [Index(value=["cursoId", "nombre"], unique=true)]
)
data class ParaleloEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0L,
    val nombre: String,
    val cursoId: Long
)
