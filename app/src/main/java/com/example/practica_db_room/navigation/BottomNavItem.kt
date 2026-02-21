package com.example.practica_db_room.navigation

import android.text.style.IconMarginSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
){
    object Home: BottomNavItem("home", "Home", Icons.Default.Home)
    object Materias: BottomNavItem("materias", "Materias", Icons.Default.List)
    object Perfil: BottomNavItem("perfil", "Perfil", Icons.Default.Person)
    object Info: BottomNavItem("info","Información", Icons.Default.Info)
}
