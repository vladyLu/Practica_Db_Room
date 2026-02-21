package com.example.practica_db_room.presentacion.asistencia

data class EstadisticasAsistencia(
    val presentes: Int = 0,
    val faltas: Int = 0,
    val licencias: Int = 0,
    val retrasos: Int = 0,

    val porcentajePresente: Float = 0f,
    val porcentajeFalta: Float = 0f,
    val porcentajeLicencia: Float = 0f,
    val porcentajeRetraso: Float = 0f
)
