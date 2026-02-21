package com.example.practica_db_room.domain.usecase.asistencia

import javax.inject.Inject

class AsistenciaUseCase @Inject constructor(
    val obtenerAsistenciaPorEstudianteUseCase: ObtenerAsistenciaPorEstudianteUseCase,
    val obtenerAsistenciaPorMateriaUseCase: ObtenerAsistenciaPorMateriaUseCase,
    val obtenerAsistenciaPorRangoUseCase: ObtenerAsistenciaPorRangoUseCase,
    val registrarAsistenciaUseCase: RegistrarAsistenciaUseCase,
    val obtenerAsistenciaDeHoyPorMateria: ObtenerAsistenciaDeHoyPorMateriaUseCase,
    val ObtenerAsistenciaPorEstudianteMateriaYRango: ObtenerAsistenciaPorEstudianteMateriaYRangoUseCase,
    val obtenerReportePorMateriaYParalelo: ObtenerReportePorMateriaYParaleloUseCase
) {
}