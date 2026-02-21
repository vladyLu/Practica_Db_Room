package com.example.practica_db_room.presentacion.materia

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practica_db_room.components.MateriaSheetType
import com.example.practica_db_room.domain.model.Materia
import com.example.practica_db_room.presentacion.components.EditarSheet
import com.example.practica_db_room.presentacion.components.MateriaEstudianteCard
import com.example.practica_db_room.presentacion.components.OpcionesMateriaEtudianteSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriasScreen(
    viewModel: MateriaViewModel= hiltViewModel(),
    openAddMateriaSheet: Boolean,
    onSheetOpened: ()-> Unit
){
    val materias by viewModel.materias.collectAsState()
    var sheetType by remember { mutableStateOf(MateriaSheetType.NONE) }
    var materiaSeleccionada by remember { mutableStateOf<Materia?>(null) }

    if (openAddMateriaSheet){
        sheetType= MateriaSheetType.AGREGAR
        onSheetOpened()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("Materias",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        LazyColumn(modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(
                items=materias,
                key={it.id ?: it.hashCode()}
            ){materia->
                MateriaEstudianteCard(
                    titulo = materia.nombre,
                    onClick = {},
                    onLongClick = {
                        materiaSeleccionada = materia
                        sheetType = MateriaSheetType.OPCIONES
                    }
                )

            }
        }
    }
    if (sheetType!= MateriaSheetType.NONE){
        ModalBottomSheet(
            onDismissRequest = {sheetType= MateriaSheetType.NONE}
        ) {
            when(sheetType){
                MateriaSheetType.AGREGAR->{
                    EditarSheet(
                        title = "Agregar materias",
                        label = "Nombre de la materia",
                        onSave = {
                            viewModel.insertar(it)
                            sheetType= MateriaSheetType.NONE

                        },
                        onCancel = {sheetType= MateriaSheetType.NONE}
                    )

                }

                MateriaSheetType.OPCIONES->{
                    OpcionesMateriaEtudianteSheet (
                        titulo = "Opciones materia",
                        onEditar = {
                            sheetType= MateriaSheetType.EDITAR
                        },
                        onEliminar = {
                            materiaSeleccionada?.let { viewModel.eliminar(it) }
                            sheetType= MateriaSheetType.NONE
                        },
                        onCancelar = {sheetType= MateriaSheetType.NONE}
                    )
                }

                MateriaSheetType.EDITAR->{
                    EditarSheet(
                        title = "Editar materia",
                        initialValue = materiaSeleccionada?.nombre?:"",
                        label = "nombre",
                        onSave = {nuevoNombre->
                            materiaSeleccionada?.let {
                                viewModel.actualizar(it.copy(nombre = nuevoNombre))
                            }
                            sheetType= MateriaSheetType.NONE
                        },
                        onCancel = {sheetType= MateriaSheetType.NONE}
                    )
                }
                else -> Unit
            }

        }
    }


}