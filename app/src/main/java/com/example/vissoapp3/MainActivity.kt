package com.example.vissoapp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.vissoapp3.ui.navigation.AppNavigation
import com.example.vissoapp3.ui.theme.VissoApp3Theme // Usa tu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VissoApp3Theme {
                AppNavigation()
            }
        }
    }
}