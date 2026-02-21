package com.example.practica_db_room.domain.repository

import com.example.practica_db_room.domain.model.Paralelo
import kotlinx.coroutines.flow.Flow

interface ParaleloRepository {
    suspend fun  insertarParalelo(paralelo: Paralelo)
    suspend fun actualizarParalelo(paralelo: Paralelo)
    suspend fun eliminarParalelo(paralelo: Paralelo)
    fun obtenerParalelosPorCurso(cursoId: Long): Flow<List<Paralelo>>
    fun obtenerParaleloPorId(paraleloId: Long): Flow<Paralelo>
}