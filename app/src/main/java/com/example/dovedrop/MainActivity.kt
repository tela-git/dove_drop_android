package com.example.dovedrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dovedrop.chat.presentation.navigation.AppNavigation
import com.example.dovedrop.chat.presentation.theme.DoveDropTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoveDropTheme {
                AppNavigation()
            }
        }
    }
}
