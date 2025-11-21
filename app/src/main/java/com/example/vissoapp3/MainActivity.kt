package com.example.vissoapp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vissoapp3.screens.*
import com.example.vissoapp3.ui.screens.CarritoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Routes.Login
            ) {
                composable(Routes.Login) {
                    Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                        LoginScreen(navController = navController, paddingValues = innerPadding)
                    }
                }
                composable(Routes.Home) {
                    HomeScreen(navController = navController)
                }
                composable(Routes.Categories) {
                    CategoriesScreen(navController = navController)
                }
                composable("${Routes.FormularioCot}/{nombreLente}") { backStackEntry ->
                    val nombreLente =
                        backStackEntry.arguments?.getString("nombreLente") ?: "Lente Óptico"
                    FormularioCotScreen(navController = navController, nombreLente = nombreLente)
                }
                composable(Routes.LentesOpticos) {
                    LentesOpticosScreen(navController = navController)
                }
                composable(Routes.Carrito) {
                    CarritoScreen(navController = navController)
                }
                composable(Routes.Register) {
                    RegisterScreen(navController = navController)
                }
            }
        }
    }
}