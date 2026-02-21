package com.example.practica_db_room.presentacion.principal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.practica_db_room.domain.model.Curso

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CursoCard(
    curso: Curso,
    onClick:()-> Unit,
    isSelect: Boolean,
    onLongClick:()-> Unit
){
    val containerColor by animateColorAsState(
        targetValue = if (isSelect)
            MaterialTheme.colorScheme.surface
        else
            MaterialTheme.colorScheme.secondary,
        label = ""
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelect)
            MaterialTheme.colorScheme.onSecondary
        else
            MaterialTheme.colorScheme.onSecondary,
        label = ""
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelect) 10.dp else 3.dp
    )

    Card(modifier = Modifier
        .width(100.dp)
        .height(60.dp)
        .combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
        .animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = curso.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}