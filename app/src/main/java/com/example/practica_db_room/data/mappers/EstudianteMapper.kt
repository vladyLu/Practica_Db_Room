package com.example.practica_db_room.data.mappers

import com.example.practica_db_room.data.local.entities.EstudianteEntity
import com.example.practica_db_room.domain.model.Estudiante

fun EstudianteEntity.toDomain() = Estudiante(
    id = id,
    nombre = nombre,
    apellido = apellido,
    paraleloId = paraleloId
)

fun Estudiante.toEntity() = EstudianteEntity(
    id = id,
    nombre = nombre,
    apellido = apellido,
    paraleloId = paraleloId
)