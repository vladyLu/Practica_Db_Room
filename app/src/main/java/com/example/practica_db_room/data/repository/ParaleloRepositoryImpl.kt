package com.example.practica_db_room.data.repository

import com.example.practica_db_room.data.local.dao.ParaleloDao
import com.example.practica_db_room.data.mappers.toDomain
import com.example.practica_db_room.data.mappers.toEntity
import com.example.practica_db_room.domain.model.Paralelo
import com.example.practica_db_room.domain.repository.ParaleloRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ParaleloRepositoryImpl @Inject constructor(
    private val dao: ParaleloDao
): ParaleloRepository {
    override suspend fun insertarParalelo(paralelo: Paralelo) {
        dao.insertarParalelo(paralelo.toEntity())
    }

    override suspend fun actualizarParalelo(paralelo: Paralelo) {
        dao.actualizarParalelo(paralelo.toEntity())
    }

    override suspend fun eliminarParalelo(paralelo: Paralelo) {
        dao.eliminarParalelo(paralelo.toEntity())
    }

    override fun obtenerParalelosPorCurso(cursoId: Long): Flow<List<Paralelo>> =
        dao.obtenerParalelosPorCursos(cursoId).map{lista->
            lista.map { it.toDomain() }

    }

    override fun obtenerParaleloPorId(paraleloId: Long): Flow<Paralelo> {
        return dao.obtenerParaleloPorId(paraleloId)
            .filterNotNull()
            .map { it.toDomain() }
    }


}