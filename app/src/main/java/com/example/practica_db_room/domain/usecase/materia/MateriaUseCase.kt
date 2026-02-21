package com.example.practica_db_room.domain.usecase.materia

import javax.inject.Inject

data class MateriaUseCase @Inject constructor(
    val insertar: InsertarMateriaUseCase,
    val actualizar: ActualizarMateriaUseCase,
    val eliminar: EliminarMateriaUseCase,
    val obtener: ObtenerMateriasUseCase,
    val obtenerPorParalelo: ObtenerMateriasPorParaleloUseCase,
    val asignar: AsiganarMateriaParaleloUseCase,
    val quitar: QuitarMateriaDeParaleloUseCase,
    val obtenerMateriaPorId: ObtenerMateriaPorIdUseCase
)
