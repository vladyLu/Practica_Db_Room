package com.example.practica_db_room.presentacion.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AgregraEditarDialog(
    titulo: String,
    texto: String,
    onTextoChange:(String)-> Unit,
    onConfirmar:()->Unit,
    onCancelar:()-> Unit
){
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(text = titulo, style = MaterialTheme.typography.titleLarge) },
        text = {
            OutlinedTextField(
                value = texto,
                onValueChange = onTextoChange,
                label = {Text("Nombre")},
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = onConfirmar) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}