package com.example.practica_db_room.domain.model

data class Asistencia(
    val id: Long=0L,
    val fecha : String,
    val estado: String,
    val estudianteId: Long,
    val materiaId: Long
)
