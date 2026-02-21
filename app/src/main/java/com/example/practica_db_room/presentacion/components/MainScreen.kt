package com.example.practica_db_room.presentacion.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practica_db_room.navigation.BottomNavItem
import com.example.practica_db_room.presentacion.asistencia.AsistenciaScreen
import com.example.practica_db_room.presentacion.estudiante.EstudianteScreen
import com.example.practica_db_room.presentacion.informacion.InfoScreen
import com.example.practica_db_room.presentacion.materia.MateriasScreen
import com.example.practica_db_room.presentacion.perfil.PerfilScreen
import com.example.practica_db_room.presentacion.principal.HomeScreen
import com.example.practica_db_room.presentacion.reporte.RangoFechasBottomSheet
import com.example.practica_db_room.presentacion.reporte.ReporteAsistenciaScreen
import com.example.practica_db_room.presentacion.reporte.ReporteParaleloMateriaScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var openAddCursoSheet by remember { mutableStateOf(false) }
    var openAddMateriaSheet by remember { mutableStateOf(false) }
    var openRangoFechasSheet by remember { mutableStateOf(false) }
    var openAddEstudiantesSheet by remember { mutableStateOf(false) }
    val rutasSinFab=listOf("asistencia")


    Scaffold(
        bottomBar = { BottomBar(navController) },

        floatingActionButton = {
            val ocultarFab=rutasSinFab.any{currentRoute?.startsWith(it)==true}
            if (!ocultarFab){
                ReusableFab(
                    icon = Icons.Default.Add,
                    onClick = {
                        val current = navController.currentBackStackEntry?.destination?.route
                        when  {
                            current== BottomNavItem.Home.route -> openAddCursoSheet = true
                            current== BottomNavItem.Materias.route -> openAddMateriaSheet = true
                            current?.startsWith("estudiantes/")==true ->openAddEstudiantesSheet=true
                            current?.startsWith("reporte/") == true -> openRangoFechasSheet = true
                            current?.startsWith("reporte_paralelo_materia/") == true -> openRangoFechasSheet = true
                        }
                    }
                )
            }

        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    openAddCursoSheet = openAddCursoSheet,
                    onSheetOpened = { openAddCursoSheet = false },
                    onAregarEstudiante = {paraleloId->
                        navController.navigate("estudiantes/$paraleloId")
                    },
                    onAbrirAsistencia = {materiaId, paraleloId->
                        navController.navigate("asistencia/$materiaId/$paraleloId")
                    }
                )
            }
            composable(BottomNavItem.Materias.route) {
                MateriasScreen(
                    openAddMateriaSheet=openAddMateriaSheet,
                    onSheetOpened={openAddMateriaSheet=false}
                ) }
            composable(BottomNavItem.Perfil.route) { PerfilScreen() }
            composable(BottomNavItem.Info.route) { InfoScreen() }

            composable(
                route = "estudiantes/{paraleloId}",
                arguments = listOf(navArgument("paraleloId") { type = NavType.LongType })
            ) { backStackEntry ->
                val paraleloId = backStackEntry.arguments?.getLong("paraleloId") ?: 0L
                EstudianteScreen(
                    paraleloId=paraleloId,
                    openAddEstudianteSheet=openAddEstudiantesSheet,
                    onSheetOpened={openAddEstudiantesSheet=false}
                )
            }

            composable(
                route="asistencia/{materiaId}/{paraleloId}",
                arguments = listOf(
                    navArgument("materiaId"){type=NavType.LongType},
                    navArgument("paraleloId"){type= NavType.LongType}
                    )
            ){backStackEntry ->
                val materiaId=backStackEntry.arguments?.getLong("materiaId")?:0L
                val paraleloId=backStackEntry.arguments?.getLong("paraleloId")?:0L
                AsistenciaScreen(
                    materiaId=materiaId,
                    paraleloId=paraleloId,
                    navController=navController)

            }

            composable(
                route = "reporte/{materiaId}/{estudianteId}",
                arguments = listOf(
                    navArgument("materiaId"){ type = NavType.LongType },
                    navArgument("estudianteId"){ type = NavType.LongType }
                )
            ) { backStackEntry ->
                val materiaId = backStackEntry.arguments?.getLong("materiaId") ?: 0L
                val estudianteId = backStackEntry.arguments?.getLong("estudianteId") ?: 0L

                ReporteAsistenciaScreen(
                    materiaId = materiaId,
                    estudianteId = estudianteId,
                    navController=navController,
                    openRangoFechasSheet = openRangoFechasSheet,  // <-- estado de MainScreen
                    onSheetOpened = { openRangoFechasSheet = false }
                )
            }

            composable(
                route = "reporte_paralelo_materia/{materiaId}/{paraleloId}",
                arguments = listOf(
                    navArgument("materiaId") { type = NavType.LongType },
                    navArgument("paraleloId") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val materiaId = backStackEntry.arguments?.getLong("materiaId") ?: 0L
                val paraleloId = backStackEntry.arguments?.getLong("paraleloId") ?: 0L

                ReporteParaleloMateriaScreen(
                    materiaId = materiaId,
                    paraleloId = paraleloId,
                    navController = navController,
                    openRangoFechasSheet = openRangoFechasSheet,  // <-- agregado
                    onSheetOpened = { openRangoFechasSheet = false }
                )
            }
        }
    }
}