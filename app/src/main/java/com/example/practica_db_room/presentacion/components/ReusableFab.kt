package com.example.practica_db_room.presentacion.components

import android.graphics.drawable.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ReusableFab(
    icon: ImageVector,
    onClick:()-> Unit
){
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) {
        Icon(icon, contentDescription = null)
    }
}