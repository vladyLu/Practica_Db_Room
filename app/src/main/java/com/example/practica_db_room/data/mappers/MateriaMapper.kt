package com.example.practica_db_room.data.mappers

import com.example.practica_db_room.data.local.entities.MateriaEntity
import com.example.practica_db_room.domain.model.Materia

fun MateriaEntity.toDomain()= Materia(
    id=id,
    nombre = nombre
)

fun Materia.toEntity()= MateriaEntity(
    id=id,
    nombre = nombre
)