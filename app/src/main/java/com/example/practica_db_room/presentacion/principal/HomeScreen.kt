package com.example.practica_db_room.presentacion.principal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practica_db_room.components.ParaleloSheetType
import com.example.practica_db_room.components.SheetType
import com.example.practica_db_room.domain.model.Asistencia
import com.example.practica_db_room.domain.model.Curso
import com.example.practica_db_room.domain.model.Paralelo
import com.example.practica_db_room.navigation.BottomNavItem
import com.example.practica_db_room.presentacion.components.BottomBar
import com.example.practica_db_room.presentacion.components.EditarSheet
import com.example.practica_db_room.presentacion.components.OpcionesShett
import com.example.practica_db_room.presentacion.informacion.InfoScreen
import com.example.practica_db_room.presentacion.materia.MateriaViewModel
import com.example.practica_db_room.presentacion.materia.MateriasScreen
import com.example.practica_db_room.presentacion.model.MateriaUi
import com.example.practica_db_room.presentacion.paralelo.OpcionesParaleloSheet
import com.example.practica_db_room.presentacion.paralelo.ParaleloCard
import com.example.practica_db_room.presentacion.paralelo.ParaleloViewModel
import com.example.practica_db_room.presentacion.perfil.PerfilScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: CursoViewModel = hiltViewModel(),
    openAddCursoSheet: Boolean,
    onSheetOpened: () -> Unit,
    onAregarEstudiante:(Long)-> Unit,
    onAbrirAsistencia: (materiaId: Long, paraleloId: Long)-> Unit

) {
    val paraleloVieModel: ParaleloViewModel = hiltViewModel()
    val paralelos by paraleloVieModel.paralelos.collectAsState()
    val paraleloSeleccionado by paraleloVieModel.paraleloSeleccionado.collectAsState()
    var paraleloSheet by remember { mutableStateOf(ParaleloSheetType.NONE) }

    val cursos by viewModel.cursos.collectAsState()
    val cursoSeleccionado by viewModel.cursoSeleccionado.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var sheetActual by remember { mutableStateOf(SheetType.NONE) }
    val asignacionVm: MateriaViewModel=hiltViewModel()

    LaunchedEffect(openAddCursoSheet) {
        if (openAddCursoSheet) {
            sheetActual = SheetType.AGREGAR
            onSheetOpened()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cursos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            //.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = cursos,
                key = { it.id ?: it.hashCode() }
            ) { curso ->
                CursoCard(
                    curso = curso,
                    isSelect = cursoSeleccionado?.id == curso.id,
                    onClick = {
                        viewModel.seleccionarCurso(curso)
                        paraleloVieModel.cargarParalelos(curso.id)

                    },
                    onLongClick = {
                        viewModel.seleccionarCurso(curso)
                        sheetActual = SheetType.OPCIONES
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(
                items = paralelos,
                key = { it.id ?: it.hashCode() }
            ) { paralelo ->
                val materiasPorParalelo by asignacionVm.obtenerMateriasPorParalelo(paralelo.id)
                    .collectAsState(initial = emptyList())
                ParaleloCard(
                    titulo = "${cursoSeleccionado?.nombre.orEmpty()} ${paralelo.nombre}",
                    materias = materiasPorParalelo,
                    onClick = {},
                    onLongClick = {
                        paraleloVieModel.seleccionarParalelo(paralelo)
                        paraleloSheet = ParaleloSheetType.OPCIONES
                    },
                    onChipClick={materiaId->
                        paraleloVieModel.seleccionarParalelo(paralelo)
                        onAbrirAsistencia(materiaId, paralelo.id)
                    }
                )
            }
        }
    }

    when (sheetActual) {
        SheetType.OPCIONES -> {
            ModalBottomSheet(
                onDismissRequest = { sheetActual = SheetType.NONE },
                sheetState = sheetState
            ) {
                OpcionesShett(
                    onEditar = { sheetActual = SheetType.EDITAR },
                    onEliminar = {
                        cursoSeleccionado?.let { viewModel.eliminarCurso(it) }
                        sheetActual = SheetType.NONE
                    },
                    onAgregarParalelo = { sheetActual = SheetType.AGREGAR_PARALELO },
                    onCancelar = { sheetActual = SheetType.NONE }
                )
            }
        }

        SheetType.EDITAR -> {
            ModalBottomSheet(
                onDismissRequest = { sheetActual = SheetType.NONE },
                sheetState = sheetState
            ) {
                EditarSheet(
                    title = "Editar curso",
                    initialValue = cursoSeleccionado!!.nombre,
                    label = "Nombre del curso",
                    onSave = { nuevoNombre ->
                        viewModel.actualizarCurso(
                            cursoSeleccionado!!.copy(nombre = nuevoNombre)
                        )
                        sheetActual = SheetType.NONE
                    },
                    onCancel = { sheetActual = SheetType.NONE }
                )
            }
        }

        SheetType.AGREGAR -> {
            ModalBottomSheet(
                onDismissRequest = { sheetActual = SheetType.NONE },
                sheetState = sheetState
            ) {
                EditarSheet(
                    title = "Agregar curso",
                    label = "Nombre del curso",
                    onSave = { nombre ->
                        viewModel.agregarCurso(nombre)
                        sheetActual = SheetType.NONE
                    },
                    onCancel = { sheetActual = SheetType.NONE }
                )
            }
        }

        SheetType.AGREGAR_PARALELO -> {

            ModalBottomSheet(
                onDismissRequest = { sheetActual = SheetType.NONE },
                sheetState = sheetState
            ) {
                EditarSheet(
                    title = "Agregar paralelo",
                    label = "Nombre del paralelo",
                    onSave = { nombre ->
                        val nuevo = Paralelo(
                            nombre = nombre,
                            cursoId = cursoSeleccionado!!.id!!
                        )
                        paraleloVieModel.insertarParalelo(nuevo)
                        sheetActual = SheetType.NONE

                    },
                    onCancel = { sheetActual = SheetType.NONE }
                )
            }
        }

        else -> Unit
    }
    when (paraleloSheet) {
        ParaleloSheetType.OPCIONES -> {
            ModalBottomSheet(
                onDismissRequest = { paraleloSheet = ParaleloSheetType.NONE },
                sheetState = sheetState
            ) {
                OpcionesParaleloSheet(
                    onEditar = { paraleloSheet = ParaleloSheetType.EDITAR },
                    onEliminar = {
                        paraleloSeleccionado?.let { paraleloVieModel.eliminarParalelo(it) }
                        paraleloSheet = ParaleloSheetType.NONE
                    },
                    onAgregarMaterias = {
                        paraleloSheet = ParaleloSheetType.AGREGAR_MATERIA
                    },
                    onAgregarEstudiantes = {
                        paraleloSheet= ParaleloSheetType.NONE
                        paraleloSeleccionado?.id?.let { paraleloId->
                            onAregarEstudiante(paraleloId)
                        }
                    },
                    onCancelar = { paraleloSheet = ParaleloSheetType.NONE }
                )
            }
        }

        ParaleloSheetType.EDITAR -> {
            ModalBottomSheet(
                onDismissRequest = {paraleloSheet= ParaleloSheetType.NONE},
                sheetState = sheetState
            ) {
                EditarSheet(
                    title = "Editar paralelo",
                    initialValue = paraleloSeleccionado!!.nombre,
                    label = "nombre del paralelo",
                    onSave = {nuevoNombre->
                        paraleloVieModel.actualizarParalelo(
                            paraleloSeleccionado!!.copy(nombre = nuevoNombre)
                        )
                        paraleloSheet= ParaleloSheetType.NONE
                    },
                    onCancel = {paraleloSheet= ParaleloSheetType.NONE}
                )
            }
        }

        ParaleloSheetType.AGREGAR_MATERIA -> {

            LaunchedEffect(paraleloSeleccionado) {
                paraleloSeleccionado?.id?.let { asignacionVm.setParaleloSeleccionado(it) }
            }
            val materiaUi by asignacionVm.materiasUi.collectAsState()
            ModalBottomSheet(
                onDismissRequest = {paraleloSheet= ParaleloSheetType.NONE},
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Asignar materias a ${cursoSeleccionado?.nombre} ${paraleloSeleccionado?.nombre ?:""}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(materiaUi, key = {it.id}){m->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable{asignacionVm.toggleAsignacion(m.id)}
                                    .padding(horizontal = 8.dp, vertical = 4.dp)

                            ) {
                                Checkbox(
                                    checked = m.asignada,
                                    onCheckedChange = {asignacionVm.toggleAsignacion(m.id)}
                                )
                                Spacer(Modifier.width(2.dp))
                                Text(m.nombre)
                            }

                        }
                    }
                }
            }
        }

        else -> Unit
    }
}