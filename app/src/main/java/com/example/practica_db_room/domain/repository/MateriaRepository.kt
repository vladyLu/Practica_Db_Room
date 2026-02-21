package com.example.practica_db_room.domain.repository

import com.example.practica_db_room.domain.model.Materia
import kotlinx.coroutines.flow.Flow

interface MateriaRepository {
    fun obtenerMaterias(): Flow<List<Materia>>
    fun obtenerMateriasPorParalelo(paraleloId: Long): Flow<List<Materia>>
    suspend fun insertarMateria(materia: Materia)
    suspend fun actualizarMateria(materia: Materia)
    suspend fun eliminarMateria(materia: Materia)
    suspend fun asignarMateriaAParalelo(paraleloId: Long, materiaId: Long)
    suspend fun quitarMateriaDeParalelo(paraleloId: Long, materiaId: Long)
    fun obtenerMateriaPorId(materiaId: Long): Flow<Materia>
}