package com.example.practica_db_room.components

import com.example.practica_db_room.data.local.entities.AsistenciaEntity
import com.example.practica_db_room.domain.model.ReporteMateriaParalelo

data class ReporteAsistenciaEstado(
    val asistencias: List<AsistenciaEntity> = emptyList(),
    val asistenciasReporteParalelo: List<ReporteMateriaParalelo> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
    val nombreEstudiante: String = ""
)
