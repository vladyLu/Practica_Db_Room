package com.example.practica_db_room.data.repository

import com.example.practica_db_room.data.local.dao.MateriaDao
import com.example.practica_db_room.data.local.entities.ParaleloMateriaEntity
import com.example.practica_db_room.data.mappers.toDomain
import com.example.practica_db_room.data.mappers.toEntity
import com.example.practica_db_room.domain.model.Materia
import com.example.practica_db_room.domain.repository.MateriaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MateriaRepositoryImpl @Inject constructor(
    private val dao: MateriaDao
): MateriaRepository {
    override fun obtenerMaterias(): Flow<List<Materia>> =
        dao.obtenerMaterias().map { list -> list.map { it.toDomain()} }

    override fun obtenerMateriasPorParalelo(paraleloId: Long): Flow<List<Materia>> =
        dao.obtenerMateriasPorParalelo(paraleloId).map { list->
            list.map { it.toDomain() }
        }

    override suspend fun insertarMateria(materia: Materia) {
        dao.insertarMateria(materia.toEntity())
    }

    override suspend fun actualizarMateria(materia: Materia) {
        dao.actualizarMateria(materia.toEntity())
    }

    override suspend fun eliminarMateria(materia: Materia) {
        dao.eliminarMateria(materia.toEntity())
    }

    override suspend fun asignarMateriaAParalelo(paraleloId: Long, materiaId: Long) {
        dao.asignarMateriaParalelo(
            ParaleloMateriaEntity(paraleloId, materiaId)
        )
    }

    override suspend fun quitarMateriaDeParalelo(paraleloId: Long, materiaId: Long) {
        dao.quitarMateriaDeParalelo(paraleloId, materiaId)
    }

    override fun obtenerMateriaPorId(materiaId: Long): Flow<Materia> =
        dao.obtenerMateriaPorId(materiaId)
            .filterNotNull()
            .map { it.toDomain() }

}