package com.example.practica_db_room.presentacion.materia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_db_room.domain.model.Materia
import com.example.practica_db_room.domain.usecase.materia.MateriaUseCase
import com.example.practica_db_room.presentacion.model.MateriaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class MateriaViewModel @Inject constructor(
    private val useCase: MateriaUseCase
): ViewModel() {
    private val _materiaSeleccionada = MutableStateFlow<Materia?>(null)
    val materiaSeleccionada = _materiaSeleccionada.asStateFlow()

    val materias = useCase.obtener().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _paraleloSeleccionado= MutableStateFlow<Long?>(null)
    fun setParaleloSeleccionado(id: Long){_paraleloSeleccionado.value=id}

    val materiasUi: StateFlow<List<MateriaUi>> = combine(
        materias,

        _paraleloSeleccionado.flatMapLatest { pid->
            if (pid ==null) emptyFlow<List<Materia>>()
            else useCase.obtenerPorParalelo(pid)
        }
    ){todas, asignadas->
        val asigandasIds=asignadas.map{it.id}.toSet()
        todas.map { m->
            MateriaUi(
                id=m.id,
                nombre=m.nombre,
                asignada=asigandasIds.contains(m.id)
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleAsignacion(materiaId: Long){
        val pid=_paraleloSeleccionado.value ?: return
        viewModelScope.launch {
            val estaAsignada=materiasUi.value.any{it.id==materiaId && it.asignada}
            if (estaAsignada) quitar(pid, materiaId)
            else asignar(pid, materiaId)
        }

    }

    fun obtenerMateriasPorParalelo(paraleloId: Long): Flow<List<Materia>>{
        return useCase.obtenerPorParalelo(paraleloId)
    }

    fun insertar(nombre: String) = viewModelScope.launch {
        useCase.insertar(Materia(nombre = nombre))
    }

    fun actualizar(materia: Materia) = viewModelScope.launch {
        useCase.actualizar(materia)
    }

    fun eliminar(materia: Materia) = viewModelScope.launch {
        useCase.eliminar(materia)
    }

    fun asignar(paraleloId: Long, materiaId: Long) = viewModelScope.launch {
        useCase.asignar(paraleloId, materiaId)
    }

    fun quitar(paraleloId: Long, materiaId: Long) = viewModelScope.launch {
        useCase.quitar(paraleloId, materiaId)
    }

    fun cargarMateriaPorId(materiaId: Long) {
        viewModelScope.launch {
            useCase.obtenerMateriaPorId(materiaId)
                .collect { materia ->
                    _materiaSeleccionada.value = materia
                }
        }
    }
}