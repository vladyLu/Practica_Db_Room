package com.example.practica_db_room.presentacion.asistencia

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.practica_db_room.presentacion.estudiante.EstudianteViewModel
import com.example.practica_db_room.presentacion.materia.MateriaViewModel
import com.example.practica_db_room.presentacion.paralelo.ParaleloViewModel
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AsistenciaScreen(
    materiaId: Long,
    paraleloId: Long,
    navController: NavController,
    estudianteViewModel: EstudianteViewModel = hiltViewModel(),
    asistenciaViewModel: AsistenciaViewModel = hiltViewModel(),
    paraleloViewModel: ParaleloViewModel=hiltViewModel(),
    materiaViewModel: MateriaViewModel = hiltViewModel()
) {
    val state by estudianteViewModel.state.collectAsState()
    val asistencias by asistenciaViewModel.asistencias.collectAsState()
    val estadisticas by asistenciaViewModel.estadisticas.collectAsState()

    val paralelo by paraleloViewModel.paraleloSeleccionado.collectAsState()
    val curso by paraleloViewModel.cursoSeleccionado.collectAsState()
    val materia by materiaViewModel.materiaSeleccionada.collectAsState()

    LaunchedEffect(materiaId) {
        materiaViewModel.cargarMateriaPorId(materiaId)
    }

    LaunchedEffect(paraleloId) {
        paraleloViewModel.cargarParaleloPorId(paraleloId)
        estudianteViewModel.cargarPorParalelo(paraleloId)
        asistenciaViewModel.setMateriaId(materiaId)
    }

    LaunchedEffect(paralelo) {
        if (paralelo != null) paraleloViewModel.cargarCursoDelParalelo()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            "Control de asistencia",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        // --- Info general ---

        Card(
            modifier = Modifier
                .fillMaxWidth(),
                //.padding(12.dp),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Estadística de hoy",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = {
                            navController.navigate("reporte_paralelo_materia/$materiaId/$paraleloId")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "reporte",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // -------- CURSO/PARALELO + MATERIA -----------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${curso?.nombre ?: ""} ${paralelo?.nombre ?: ""}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        materia?.nombre ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(10.dp))

                // -------- LÍNEA DIVISORIA -----------
                Divider(color = Color.LightGray, thickness = 1.dp)

                Spacer(Modifier.height(6.dp))

                Column(modifier = Modifier.fillMaxWidth()) {

                    // -------- Fila 1: Presente - Falta --------
                    Row(modifier = Modifier.fillMaxWidth()) {

                        // Presente
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                "Presente = ${estadisticas.presentes} (${estadisticas.porcentajePresente.toInt()}%)",
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF4CAF50)
                            )
                        }

                        // Falta
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                "Falta = ${estadisticas.faltas} (${estadisticas.porcentajeFalta.toInt()}%)",
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFE53935)
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // -------- Fila 2: Licencia - Retraso --------
                    Row(modifier = Modifier.fillMaxWidth()) {

                        // Licencia
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                "Licencia = ${estadisticas.licencias} (${estadisticas.porcentajeLicencia.toInt()}%)",
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF00897B)
                            )
                        }

                        // Retraso
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                "Retraso = ${estadisticas.retrasos} (${estadisticas.porcentajeRetraso.toInt()}%)",
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFFA000)
                            )
                        }
                    }
                }

            }
        }

        Spacer(Modifier.height(16.dp))

        // --- Lista de estudiantes ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp) // separa las cards automáticamente
        ) {
            items(state.estudiantes) { estudiante ->
                EstudianteAsistenciaItem(
                    estudiante = estudiante,
                    asistencias = asistencias,
                    materiaId = materiaId,
                    onAsistencia = { estado ->
                        asistenciaViewModel.registrarAsistencia(
                            estudianteId = estudiante.id,
                            materiaId = materiaId,
                            estado = estado
                        )
                    },
                    onVerEstadistica = { materiaId, estudianteId ->
                        navController.navigate("reporte/$materiaId/$estudianteId")
                    }
                )
            }
        }
    }
}