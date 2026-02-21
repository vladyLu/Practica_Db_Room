package com.example.practica_db_room.domain.model

data class Estudiante(
    val id: Long=0L,
    val nombre: String,
    val apellido: String,
    val paraleloId: Long
)
