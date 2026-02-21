package com.example.practica_db_room.data.mappers

import com.example.practica_db_room.data.local.entities.ParaleloEntity
import com.example.practica_db_room.domain.model.Paralelo

fun ParaleloEntity.toDomain()= Paralelo(
    id=id,
    nombre=nombre,
    cursoId = cursoId
)

fun Paralelo.toEntity()= ParaleloEntity(
    id=id,
    nombre = nombre,
    cursoId = cursoId
)
