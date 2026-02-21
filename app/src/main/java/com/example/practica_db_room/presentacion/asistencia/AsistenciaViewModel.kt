package com.example.practica_db_room.presentacion.asistencia

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_db_room.components.ReporteAsistenciaEstado
import com.example.practica_db_room.data.repository.EstudianteRepository
import com.example.practica_db_room.domain.model.Asistencia
import com.example.practica_db_room.domain.usecase.asistencia.AsistenciaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AsistenciaViewModel @Inject constructor(
    private val asistenciaUseCase: AsistenciaUseCase,
    private val estudianteRepo: EstudianteRepository
): ViewModel(){


    private val _materiaId = MutableStateFlow<Long?>(null)
    private val _estado = MutableStateFlow(ReporteAsistenciaEstado())
    val estado: StateFlow<ReporteAsistenciaEstado> = _estado

    @RequiresApi(Build.VERSION_CODES.O)
    val asistencias = _materiaId
        .filterNotNull()
        .flatMapLatest { materiaId ->
            asistenciaUseCase.obtenerAsistenciaDeHoyPorMateria(materiaId)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    val estadisticas = asistencias
        .map { lista -> calcularEstadisticas(lista) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, EstadisticasAsistencia())

    fun setMateriaId(id: Long) {
        _materiaId.value = id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registrarAsistencia(
        estudianteId: Long,
        materiaId: Long,
        estado: String
    ) {
        viewModelScope.launch {
            asistenciaUseCase.registrarAsistenciaUseCase(
                Asistencia(
                    estudianteId = estudianteId,
                    materiaId = materiaId,
                    fecha = LocalDate.now().toString(),
                    estado = estado
                )
            )
        }
    }

    private fun calcularEstadisticas(lista: List<Asistencia>): EstadisticasAsistencia {
        val total = lista.size.takeIf { it > 0 } ?: 1

        val presentes = lista.count { it.estado == "PRESENTE" }
        val faltas = lista.count { it.estado == "FALTA" }
        val licencias = lista.count { it.estado == "LICENCIA" }
        val retrasos = lista.count { it.estado == "RETRAZO" }

        return EstadisticasAsistencia(
            presentes = presentes,
            faltas = faltas,
            licencias = licencias,
            retrasos = retrasos,

            porcentajePresente = presentes * 100f / total,
            porcentajeFalta = faltas * 100f / total,
            porcentajeLicencia = licencias * 100f / total,
            porcentajeRetraso = retrasos * 100f / total
        )
    }

    fun cargarReporte(estudianteId: Long, materiaId: Long, inicio: LocalDate, fin: LocalDate
    ) {
        viewModelScope.launch {
            asistenciaUseCase.ObtenerAsistenciaPorEstudianteMateriaYRango(
                estudianteId,
                materiaId,
                inicio,
                fin
            )
                .onStart {
                    _estado.value = _estado.value.copy(
                        cargando = true,
                        error = null
                    )
                }
                .catch { e ->
                    _estado.value = _estado.value.copy(
                        error = e.message,
                        cargando = false
                    )
                }
                .collect { lista ->
                    _estado.value = _estado.value.copy(
                        asistencias = lista,
                        cargando = false,
                        error = null
                    )
                }
        }
    }

    fun cargarNombreEstudiante(estudianteId: Long) {
        viewModelScope.launch {
            val nombre = estudianteRepo.obtenerNombrePorId(estudianteId)
            _estado.update {
                it.copy(nombreEstudiante = nombre)
            }
        }
    }

    fun cargarReportePorMateriaYParalelo(
        paraleloId: Long,
        materiaId: Long,
        inicio: LocalDate,
        fin: LocalDate
    ) {
        viewModelScope.launch {
            asistenciaUseCase
                .obtenerReportePorMateriaYParalelo(paraleloId, materiaId, inicio, fin)
                .onStart {
                    _estado.value = _estado.value.copy(cargando = true, error = null)
                }
                .catch { e ->
                    _estado.value = _estado.value.copy(
                        cargando = false,
                        error = e.message
                    )
                }
                .collect { lista ->
                    _estado.value = _estado.value.copy(
                        asistenciasReporteParalelo = lista,
                        cargando = false,
                        error = null
                    )
                }
        }
    }
}