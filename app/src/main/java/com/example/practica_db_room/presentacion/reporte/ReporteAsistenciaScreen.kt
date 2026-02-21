package com.example.practica_db_room.presentacion.reporte

import android.os.Build
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.practica_db_room.presentacion.asistencia.AsistenciaViewModel
import com.example.practica_db_room.ui.theme.GrisMaterial
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReporteAsistenciaScreen(
    materiaId: Long,
    estudianteId: Long,
    navController: NavController,
    openRangoFechasSheet: Boolean,  // Parámetro desde MainScreen
    onSheetOpened: () -> Unit,
    viewModel: AsistenciaViewModel = hiltViewModel()
) {
    var sheetVisible by remember { mutableStateOf(false) }

    val estado by viewModel.estado.collectAsState()

    // 🔹 Simulado por ahora (luego lo sacas de tu ViewModel real)
    val nombreEstudiante = estado.nombreEstudiante

    LaunchedEffect(estudianteId) {
        viewModel.cargarNombreEstudiante(estudianteId)
    }


    LaunchedEffect(openRangoFechasSheet) {
        if (openRangoFechasSheet) {
            sheetVisible = true
            onSheetOpened()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ─────────── HEADER ───────────
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Reporte de asistencia",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // 🔹 Nombre del estudiante
        Text(
            text = nombreEstudiante,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )

        Divider()

        Spacer(Modifier.height(12.dp))

        // ─────────── TOTALES (Cards) ───────────
        // ─────────── TOTALES (Cards) ───────────
        Column(modifier = Modifier.fillMaxWidth()) {

            // Fila 1
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Total clases
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Total presentes",
                            fontWeight = FontWeight.Bold,
                            color = Color.White)
                        Text(text = "${estado.asistencias.size}",
                            color = Color.White)
                    }
                }

                // Faltas
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE53935)
                    )
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Total faltas",
                            fontWeight = FontWeight.Bold,
                            color = Color.White)
                        Text(text = "${estado.asistencias.count { it.estado == "FALTA" }}",
                            color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Fila 2
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Retrasos
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFA000)
                    )
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(
                            "Total retrasos", fontWeight = FontWeight.Bold, color = Color.White
                        )
                        Text(
                            text = "${estado.asistencias.count { it.estado == "RETRAZO" }}",
                            color = Color.White
                        )
                    }
                }

                // Licencias
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF00897B),
                    )
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(
                            "Total licencias", fontWeight = FontWeight.Bold, color = Color.White
                        )
                        Text(
                            text = "${estado.asistencias.count { it.estado == "LICENCIA" }}",
                            color = Color.White
                        )
                    }
                }
            }
        }


        Spacer(Modifier.height(16.dp))
        Divider()

        Spacer(Modifier.height(16.dp))

        // ─────────── HISTORIAL ───────────
        Text(
            text = "Historial de asistencia",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        when {
            estado.cargando -> {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            estado.error != null -> {
                Text(
                    text = estado.error!!, color = MaterialTheme.colorScheme.error
                )
            }

            estado.asistencias.isEmpty() -> {
                Text("Seleccione un rango de fechas para el reporte")
            }

            else -> {
                LazyColumn {
                    items(estado.asistencias) { asistencia ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = GrisMaterial
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = asistencia.fecha.toString())
                                Text(
                                    asistencia.estado, fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // ─────────── BottomSheet ───────────
        if (sheetVisible) {
            RangoFechasBottomSheet(
                onDismiss = { sheetVisible = false },
                onGenerarReporte = { fechaInicio, fechaFin ->
                    sheetVisible = false

                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                    val inicio = LocalDate.parse(fechaInicio, formatter)
                    val fin = LocalDate.parse(fechaFin, formatter)

                    viewModel.cargarReporte(
                        estudianteId = estudianteId,
                        materiaId = materiaId,
                        inicio = inicio,
                        fin = fin
                    )
                })
        }
    }
}