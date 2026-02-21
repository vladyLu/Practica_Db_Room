package com.example.practica_db_room.presentacion.principal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica_db_room.data.local.entities.CursoEntity
import com.example.practica_db_room.domain.model.Curso
import com.example.practica_db_room.domain.usecase.curso.CursosUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CursoViewModel @Inject constructor(
    private val useCases: CursosUseCases
) : ViewModel(){
    private val _cursos= MutableStateFlow<List<Curso>>(emptyList())
    val cursos: StateFlow<List<Curso>> = _cursos.asStateFlow()

    private val _cursoSeleccionado = MutableStateFlow<Curso?>(null)
    val cursoSeleccionado = _cursoSeleccionado.asStateFlow()
    init {
        viewModelScope. launch {
            useCases.listarCursos().collect { _cursos.value=it }
        }
    }
    fun agregarCurso(nombre: String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.insertarCurso(Curso(nombre = nombre))
        }
    }

    fun eliminarCurso(curso: Curso){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.eliminarCurso(curso)
        }
    }

    fun actualizarCurso(curso: Curso){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.actualizarCurso(curso)
        }
    }

    fun seleccionarCurso(curso: Curso) {
        _cursoSeleccionado.value = curso
    }
}