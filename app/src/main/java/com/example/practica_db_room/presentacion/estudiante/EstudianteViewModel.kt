package com.example.practica_db_room.presentacion.estudiante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.example.practica_db_room.domain.model.Estudiante
import com.example.practica_db_room.domain.usecase.estudiante.EstudianteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteViewModel @Inject constructor(
    private val useCase: EstudianteUseCase
): ViewModel() {
    private val _state = MutableStateFlow(EstudianteState())
    val state: StateFlow<EstudianteState> = _state.asStateFlow()

    fun cargarPorParalelo(paraleloId: Long) {
        viewModelScope.launch {
            useCase.obtenerPorParalelo(paraleloId).collectLatest { lista ->
                _state.update { it.copy(estudiantes = lista) }
            }
        }
    }

    fun buscar(nombre: String) {
        viewModelScope.launch {
            useCase.buscar(nombre).collect { lista ->
                _state.update { it.copy(busqueda = lista) }
            }
        }
    }

    fun insertar(estudiante: Estudiante) = viewModelScope.launch {
        useCase.insertar(estudiante)
    }

    fun actualizar(estudiante: Estudiante) = viewModelScope.launch {
        useCase.actualizar(estudiante)
    }

    fun eliminar(estudiante: Estudiante) = viewModelScope.launch {
        useCase.eliminar(estudiante)
    }
    fun abrirSheetAgregar() {
        _state.update {
            it.copy(
                estudianteEditar = null,
                openSheet = true
            )
        }
    }

    fun abrirSheetEditar(estudiante: Estudiante) {
        _state.update {
            it.copy(
                estudianteEditar = estudiante,
                openSheet = true
            )
        }
    }

    fun cerrarSheet() {
        _state.update {
            it.copy(
                estudianteEditar = null,
                openSheet = false
            )
        }
    }
}