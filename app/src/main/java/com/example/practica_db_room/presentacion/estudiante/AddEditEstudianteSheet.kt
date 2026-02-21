package com.example.practica_db_room.presentacion.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practica_db_room.domain.model.Estudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditEstudianteSheet(
    estudiante: Estudiante?=null,
    onDismiss:()-> Unit={},
    onSave: (nombre: String, apellido: String)-> Unit,
    onCancel:()->Unit
){
    val sheetState= rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var nombre by remember { mutableStateOf(estudiante?. nombre?:"") }
    var apellido by remember { mutableStateOf(estudiante?. apellido?:"") }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState=sheetState
    ) {
        Column(Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
            Text(
                text = if (estudiante == null) "Agregar Estudiante" else "Editar Estudiante",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { onSave(nombre, apellido)
                              onDismiss()},
                    enabled = nombre.isNotBlank()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}