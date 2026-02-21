package com.example.practica_db_room.presentacion.paralelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_db_room.domain.model.Curso
import com.example.practica_db_room.domain.model.Paralelo
import com.example.practica_db_room.domain.usecase.paralelo.ParalelosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParaleloViewModel @Inject constructor(
    private val useCase: ParalelosUseCase
) : ViewModel(){
    private val _paralelos= MutableStateFlow<List<Paralelo>>(emptyList())
    val paralelos=_paralelos.asStateFlow()
    private val _paraleloSeleccionado = MutableStateFlow<Paralelo?>(null)
    val paraleloSeleccionado = _paraleloSeleccionado.asStateFlow()

    private val _cursoSeleccionado = MutableStateFlow<Curso?>(null)
    val cursoSeleccionado = _cursoSeleccionado.asStateFlow()

    private fun normalizar(texto: String): String =
        texto.lowercase()
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u")
            .replace("ñ", "n")

    fun cargarParalelos(cursoId: Long) {
        viewModelScope.launch {
            useCase.obtenerParaleloPorCurso(cursoId)
                .collect { lista ->
                    _paralelos.value = lista.sortedBy { normalizar(it.nombre) }
                }
        }
    }

    fun cargarParaleloPorId(paraleloId: Long) {
        viewModelScope.launch {
            useCase.obtenerParaleloPorId(paraleloId).collect { paralelo ->
                _paraleloSeleccionado.value = paralelo
            }
        }
    }

    fun cargarCursoDelParalelo() {
        viewModelScope.launch {
            paraleloSeleccionado.value?.let { paralelo ->
                useCase.obtenerCursoPorId(paralelo.cursoId).collect { curso ->
                    _cursoSeleccionado.value = curso
                }
            }
        }
    }


    fun insertarParalelo(paralelo: Paralelo){
        viewModelScope.launch {
            useCase.insertarParalelo(paralelo)
        }
    }

    fun actualizarParalelo(paralelo: Paralelo){
        viewModelScope.launch {
            useCase.actualizarParalelo(paralelo)
        }
    }

    fun eliminarParalelo(paralelo: Paralelo){
        viewModelScope.launch {
            useCase.eliminarParalelo(paralelo)
        }
    }

    fun seleccionarParalelo(paralelo: Paralelo) {
        _paraleloSeleccionado.value = paralelo
    }
}