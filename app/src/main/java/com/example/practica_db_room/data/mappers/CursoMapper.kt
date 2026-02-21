package com.example.practica_db_room.data.mappers

import com.example.practica_db_room.data.local.entities.CursoEntity
import com.example.practica_db_room.domain.model.Curso

fun CursoEntity.toDomain(): Curso= Curso(
    id=id,
    nombre = nombre
)

fun Curso.toEntity(): CursoEntity= CursoEntity(
    id=id,
    nombre = nombre
)