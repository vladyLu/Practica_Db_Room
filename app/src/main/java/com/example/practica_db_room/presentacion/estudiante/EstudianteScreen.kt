package com.example.practica_db_room.presentacion.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practica_db_room.components.EstudianteSheetType
import com.example.practica_db_room.components.MateriaSheetType
import com.example.practica_db_room.domain.model.Estudiante
import com.example.practica_db_room.presentacion.components.MateriaEstudianteCard
import com.example.practica_db_room.presentacion.components.OpcionesMateriaEtudianteSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteScreen(
    paraleloId: Long,
    openAddEstudianteSheet: Boolean,
    onSheetOpened: () -> Unit,
    viewModel: EstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Control exclusivo de UI
    var sheetType by remember { mutableStateOf(EstudianteSheetType.NONE) }
    var estudianteSeleccionado by remember { mutableStateOf<Estudiante?>(null) }

    LaunchedEffect(paraleloId) {
        viewModel.cargarPorParalelo(paraleloId)
    }

    LaunchedEffect(openAddEstudianteSheet) {
        if (openAddEstudianteSheet) {
            viewModel.abrirSheetAgregar()
            onSheetOpened()
        }
    }

    val estudiantes = state.estudiantes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Estudiantes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            items(
                items = estudiantes,
                key = { it.id ?: it.hashCode() }
            ) { estudiante ->
                MateriaEstudianteCard(
                    titulo = "${estudiante.nombre} ${estudiante.apellido}",
                    onClick = {},
                    onLongClick = {
                        estudianteSeleccionado = estudiante
                        sheetType = EstudianteSheetType.OPCIONES
                    }
                )
            }
        }
    }

    if (sheetType == EstudianteSheetType.OPCIONES && estudianteSeleccionado != null) {
        ModalBottomSheet(
            onDismissRequest = { sheetType = EstudianteSheetType.NONE }
        ) {
            OpcionesMateriaEtudianteSheet(
                titulo = "Opciones de estudiante",
                onEditar = {
                    sheetType = EstudianteSheetType.NONE
                    viewModel.abrirSheetEditar(estudianteSeleccionado!!)
                },
                onEliminar = {
                    viewModel.eliminar(estudianteSeleccionado!!)
                    sheetType = EstudianteSheetType.NONE
                },
                onCancelar = {
                    sheetType = EstudianteSheetType.NONE
                }
            )
        }
    }

    if (state.openSheet) {
        AddEditEstudianteSheet(
            estudiante = state.estudianteEditar,
            onDismiss = { viewModel.cerrarSheet() },
            onSave = { nombre, apellido ->
                if (state.estudianteEditar == null) {
                    viewModel.insertar(
                        Estudiante(
                            nombre = nombre,
                            apellido = apellido,
                            paraleloId = paraleloId
                        )
                    )
                } else {
                    viewModel.actualizar(
                        state.estudianteEditar!!.copy(
                            nombre = nombre,
                            apellido = apellido
                        )
                    )
                }
            },
            onCancel ={sheetType= EstudianteSheetType.NONE}
        )
    }
}