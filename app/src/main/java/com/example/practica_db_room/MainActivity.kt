package com.example.practica_db_room

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.practica_db_room.presentacion.components.MainScreen
import com.example.practica_db_room.presentacion.principal.HomeScreen
import com.example.practica_db_room.ui.theme.Practica_Db_RoomTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica_Db_RoomTheme() {
                MainScreen()
            }

        }
    }
}

