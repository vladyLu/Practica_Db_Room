package com.example.practica_db_room.presentacion.asistencia

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun EstadoIconButton(
    icon: ImageVector,
    description: String,
    color: Color,
    seleccionado: Boolean,
    onClick:()-> Unit

){

    // Opacidad del ícono
    val iconAlpha by animateFloatAsState(
        targetValue = if (seleccionado) 1f else 0.35f,  // ← opacidad aquí
        animationSpec = tween(250)
    )

    // Escala cuando está seleccionado
    val scale by animateFloatAsState(
        targetValue = if (seleccionado) 1.15f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    // Fondo
    val fondo by animateColorAsState(
        targetValue = if (seleccionado) color.copy(alpha = 0.30f) else Color.Transparent
    )

    // Borde
    val bordeWidth by animateDpAsState(
        targetValue = if (seleccionado) 2.dp else 0.dp
    )

    Box(
        modifier = Modifier
            .size(38.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .background(fondo, CircleShape)
            .border(
                BorderStroke(bordeWidth, color.copy(alpha = 0.9f)),
                CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = color.copy(alpha = iconAlpha),
            modifier = Modifier.size(20.dp)
        )
    }
}