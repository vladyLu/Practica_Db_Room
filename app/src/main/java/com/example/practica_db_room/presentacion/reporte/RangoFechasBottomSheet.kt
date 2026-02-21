package com.example.practica_db_room.presentacion.reporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangoFechasBottomSheet(
    onDismiss: () -> Unit,
    onGenerarReporte: (String, String) -> Unit
) {
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    var showDatePickerInicio by remember { mutableStateOf(false) }
    var showDatePickerFin by remember { mutableStateOf(false) }

    val formato = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    // ------------ DATE PICKER INICIO ------------
    if (showDatePickerInicio) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePickerInicio = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val localDate = Instant
                            .ofEpochMilli(millis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()

                        fechaInicio = localDate.format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        )
                        error = null
                    }
                    showDatePickerInicio = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerInicio = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // ------------ DATE PICKER FIN ------------
    if (showDatePickerFin) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePickerFin = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val localDate = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()

                            fechaFin = localDate.format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            )
                        }
                        error = null
                    }
                    showDatePickerFin = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerFin = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // ---------------- UI ----------------
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                "Seleccionar rango de fechas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            // ----------- Fecha inicio -----------
            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { },
                readOnly = true,
                label = { Text("Fecha inicio") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePickerInicio = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null)
                    }
                }
            )

            Spacer(Modifier.height(12.dp))

            // ----------- Fecha fin -----------
            OutlinedTextField(
                value = fechaFin,
                onValueChange = { },
                readOnly = true,
                label = { Text("Fecha fin") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePickerFin = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null)
                    }
                }
            )

            // ----------- Mensaje de error -----------
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(20.dp))

            // ----------- Botones -----------
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        try {
                            if (fechaInicio.isBlank() || fechaFin.isBlank()) {
                                error = "Debes seleccionar ambas fechas"
                                return@Button
                            }

                            val dateInicio = formato.parse(fechaInicio)
                            val dateFin = formato.parse(fechaFin)

                            if (dateInicio != null && dateFin != null) {
                                if (dateFin.before(dateInicio)) {
                                    error = "La fecha fin no puede ser menor que la fecha inicio"
                                } else {
                                    error = null
                                    onGenerarReporte(fechaInicio, fechaFin)
                                }
                            }
                        } catch (e: Exception) {
                            error = "Formato de fecha inválido"
                        }
                    }
                ) {
                    Text("Generar reporte")
                }
            }
        }
    }
}