package com.example.practica_db_room.data.repository

import com.example.practica_db_room.data.local.dao.EstudianteDao
import javax.inject.Inject

class EstudianteRepository @Inject constructor(
    private val dao: EstudianteDao
) {
    suspend fun obtenerNombrePorId(id: Long): String {
        return dao.obtenerNombreCompletoPorId(id) ?: "Estudiante desconocido"
    }
}