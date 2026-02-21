package com.example.practica_db_room.presentacion.estudiante

import com.example.practica_db_room.domain.model.Estudiante

data class EstudianteState(
    val estudiantes: List<Estudiante> = emptyList(),
    val busqueda: List<Estudiante> = emptyList(),
    val estudianteEditar: Estudiante? = null,
    val openSheet: Boolean = false
)
