package com.example.practica_db_room.domain.usecase.curso

import com.example.practica_db_room.domain.model.CursoConCantidadParalelos
import com.example.practica_db_room.domain.repository.CursoRepository
import com.example.practica_db_room.domain.repository.ParaleloRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObtenerCursosConCantidadParalelos @Inject constructor(
    private val cursoRepository: CursoRepository,
    private val paraleloRepository: ParaleloRepository
    ) {
    operator fun invoke(): Flow<List<CursoConCantidadParalelos>> {
        return cursoRepository.listarCursos()
            .flatMapLatest { cursos ->

                combine(
                    cursos.map { curso ->
                        paraleloRepository
                            .obtenerParalelosPorCurso(curso.id!!)
                            .map { paralelos ->
                                CursoConCantidadParalelos(
                                    curso = curso,
                                    cantidadParalelos = paralelos.size
                                )
                            }
                    }
                ) { it.toList() }

            }
    }
}