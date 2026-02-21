package com.example.practica_db_room.presentacion.asistencia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica_db_room.domain.model.Asistencia
import com.example.practica_db_room.domain.model.Estudiante
import com.example.practica_db_room.domain.model.Materia
import com.example.practica_db_room.ui.theme.GrisMaterial

@Composable
fun EstudianteAsistenciaItem(
    estudiante: Estudiante,
    asistencias: List<Asistencia>,
    materiaId: Long,
    onAsistencia: (String)-> Unit,
    onVerEstadistica:(Long, Long)-> Unit
){
    val asistenciaHoy = asistencias.find { it.estudianteId == estudiante.id }
    val estadoActual = asistenciaHoy?.estado ?: "Sin registro"

    val colorEstado = when (estadoActual) {
        "PRESENTE" -> Color(0xFF4CAF50)
        "FALTA" -> Color(0xFFE53935)
        "RETRAZO" -> Color(0xFFFFA000)
        "LICENCIA" -> Color(0xFF00897B)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GrisMaterial
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "${estudiante.apellido} ${estudiante.nombre}",
                        style = MaterialTheme.typography.titleMedium,
                        //fontSize = 16.sp
                    )
                    Text(
                        text = estadoActual,
                        color = colorEstado,
                        style = MaterialTheme.typography.titleSmall,
                       // fontSize = 10.sp
                    )
                }

                IconButton(
                    onClick = { onVerEstadistica(materiaId, estudiante.id) }
                ) {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Ver estadísticas",
                        tint = colorEstado,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                EstadoIconButton(
                    icon = Icons.Default.CheckCircle,
                    description = "Presente",
                    color = Color(0xFF4CAF50),
                    seleccionado = estadoActual == "PRESENTE",
                ) { onAsistencia("PRESENTE") }

                EstadoIconButton(
                    icon = Icons.Default.Cancel,
                    description = "Falta",
                    color = Color(0xFFE53935),
                    seleccionado = estadoActual == "FALTA",
                ) { onAsistencia("FALTA") }

                EstadoIconButton(
                    icon = Icons.Default.AccessTime,
                    description = "Retrazo",
                    color = Color(0xFFFFA000),
                    seleccionado = estadoActual == "RETRAZO",
                ) { onAsistencia("RETRAZO") }

                EstadoIconButton(
                    icon = Icons.Default.Description,
                    description = "Licencia",
                    color = Color(0xFF00897B),
                    seleccionado = estadoActual == "LICENCIA",
                ) { onAsistencia("LICENCIA") }
            }
        }
    }
}