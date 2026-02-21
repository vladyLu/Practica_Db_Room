package com.example.practica_db_room.presentacion.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OpcionesShett(
    onEditar:()-> Unit,
    onEliminar:()-> Unit,
    onCancelar:()-> Unit,
    onAgregarParalelo:()-> Unit
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
    ) {
        Text("Ociones del curso",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        OpcionItem(
            icon = Icons.Default.Edit,
            text = "Editar",
            onClick = onEditar
        )
        Divider()

        OpcionItem(
            icon = Icons.Default.Delete,
            text = "Eliminar",
            onClick = onEliminar
        )
        Divider()

        OpcionItem(
            icon = Icons.Default.Add,
            text = "Agregar paralelos",
            onClick = onAgregarParalelo
        )

        Divider()

        OpcionItem(
            icon = Icons.Default.Close,
            text = "Cancelar",
            onClick = onCancelar
        )


    }
}