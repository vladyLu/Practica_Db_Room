package com.example.practica_db_room.presentacion.paralelo

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
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.practica_db_room.presentacion.components.OpcionItem

@Composable
fun OpcionesParaleloSheet(
    onEditar:()-> Unit,
    onEliminar:()-> Unit,
    onAgregarMaterias:()-> Unit,
    onAgregarEstudiantes:()-> Unit,
    onCancelar:()-> Unit
    ){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            "Opciones de paralelo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
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
            text = "Agregar materias",
            onClick = onAgregarMaterias
        )

        Divider()

        OpcionItem(
            icon = Icons.Default.People,
            text = "Agregar estudiantes",
            onClick = onAgregarEstudiantes
        )
        Divider()

        OpcionItem(
            icon = Icons.Default.Close,
            text = "Cancelar",
            onClick = onCancelar
        )
    }
}