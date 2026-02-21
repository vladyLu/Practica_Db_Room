package com.example.practica_db_room.presentacion.reporte

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practica_db_room.presentacion.asistencia.AsistenciaViewModel
import com.example.practica_db_room.presentacion.components.ReusableFab
import com.example.practica_db_room.presentacion.materia.MateriaViewModel
import com.example.practica_db_room.presentacion.paralelo.ParaleloViewModel
import com.example.practica_db_room.ui.theme.GrisMaterial
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReporteParaleloMateriaScreen(
    materiaId: Long,
    paraleloId: Long,
    navController: NavController,
    openRangoFechasSheet: Boolean,   // ← viene del MainScreen
    onSheetOpened: () -> Unit,
    viewModel: AsistenciaViewModel = hiltViewModel(),
    paraleloViewModel: ParaleloViewModel = hiltViewModel(),
    materiaViewModel: MateriaViewModel = hiltViewModel()
){
    var sheetVisible by remember { mutableStateOf(false) }

    // Cuando el FAB activa el sheet desde MainScreen
    LaunchedEffect(openRangoFechasSheet) {
        if (openRangoFechasSheet) {
            sheetVisible = true
            onSheetOpened()
        }
    }

    val reporte by viewModel.estado.collectAsState() // Observamos el estado completo
    val listaReporte = reporte.asistenciasReporteParalelo
    val estado by viewModel.estado.collectAsState()

    val paralelo by paraleloViewModel.paraleloSeleccionado.collectAsState()
    val curso by paraleloViewModel.cursoSeleccionado.collectAsState()
    val materia by materiaViewModel.materiaSeleccionada.collectAsState()

    LaunchedEffect(materiaId) {
        materiaViewModel.cargarMateriaPorId(materiaId)
    }

    LaunchedEffect(paraleloId) {
        paraleloViewModel.cargarParaleloPorId(paraleloId)
    }

    LaunchedEffect(paralelo) {
        if (paralelo != null) paraleloViewModel.cargarCursoDelParalelo()
    }

    Scaffold(
        topBar = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Reporte",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${curso?.nombre ?: ""} ${paralelo?.nombre ?: ""}",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            materia?.nombre ?: "",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (listaReporte.isEmpty()) {
                Text("No hay datos para mostrar. Selecciona un rango de fechas.")
            } else {
                // 1. Obtener fechas únicas
                val fechasUnicas = listaReporte.map { it.fecha }.distinct()

// 2. Agrupar por estudiante
                val estudiantes = listaReporte.groupBy { "${it.apellido} ${it.nombre}" }

// Scroll horizontal para toda la tabla
                val scrollX = rememberScrollState()

                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollX)
                        .fillMaxWidth()
                ) {
                    Column {

                        // ---------- CABECERA ----------
                        Row(
                            modifier = Modifier
                                .background(color= GrisMaterial)
                                .padding(vertical = 8.dp)
                        ) {

                            // Columna fija: "Estudiante"
                            Text(
                                text = "Estudiante",
                                modifier = Modifier
                                    .width(170.dp)
                                    .padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )

                            // Fechas en vertical
                            fechasUnicas.forEach { fecha ->
                                val formateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM"))

                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(80.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    RotatedText(text = formateada)
                                }
                            }
                        }

                        Divider()

                        // ---------- FILAS ----------
                        estudiantes.forEach { (nombre, asistencias) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // Nombre del estudiante
                                Text(
                                    text = nombre,
                                    modifier = Modifier
                                        .width(170.dp)
                                        .padding(8.dp)
                                )

                                // Celdas por fecha
                                fechasUnicas.forEach { fecha ->
                                    val estadoAsistencia =
                                        asistencias.find { it.fecha == fecha }?.estado ?: ""

                                    val (texto, fondo, colorTexto) = when (estadoAsistencia) {
                                        "PRESENTE" -> Triple("P", Color(0xFF4CAF50), Color.White)
                                        "FALTA" -> Triple("F", Color(0xFFF44336), Color.White)
                                        "LICENCIA" -> Triple("L", Color(0xFF2196F3), Color.White)
                                        "RETRAZO" -> Triple("R", Color(0xFFFF9800), Color.Black)
                                        else -> Triple("-", Color.Transparent, Color.Black)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(32.dp)
                                            .padding(2.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(fondo),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = texto,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colorTexto
                                        )
                                    }
                                }
                            }

                            Divider()
                        }
                    }
                }
            }

        }

        if (sheetVisible) {
            RangoFechasBottomSheet(
                onDismiss = { sheetVisible = false },
                onGenerarReporte = { fechaInicio, fechaFin ->
                    sheetVisible = false

                    // Convertir strings a LocalDate
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val inicioDate = LocalDate.parse(fechaInicio, formatter)
                    val finDate = LocalDate.parse(fechaFin, formatter)

                    // Llamada REAL al ViewModel
                    viewModel.cargarReportePorMateriaYParalelo(
                        paraleloId = paraleloId,
                        materiaId = materiaId,
                        inicio = inicioDate,
                        fin = finDate
                    )
                }
            )
        }
    }
}