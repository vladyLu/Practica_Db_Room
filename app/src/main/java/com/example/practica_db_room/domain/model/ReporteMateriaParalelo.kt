package com.example.practica_db_room.domain.model

import java.time.LocalDate

data class ReporteMateriaParalelo(
    val estudianteId: Long,
    val nombre: String,
    val apellido: String,
    val estado: String,
    val fecha: LocalDate
)
