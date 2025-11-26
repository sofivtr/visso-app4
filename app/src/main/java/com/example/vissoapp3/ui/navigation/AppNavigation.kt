package com.example.vissoapp3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vissoapp3.screens.*
import com.example.vissoapp3.ui.screens.CartScreen // Importa la pantalla del Carrito

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("categories") { CategoriesScreen(navController) }
        composable("lentes_opticos") { LentesOpticosScreen(navController) }
        composable("cart") { CartScreen(navController) } // Asegúrate de tener CartScreen adaptado

        // Ruta con argumentos para la cotización
        composable(
            route = "cotizacion/{id}/{nombre}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("nombre") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            FormularioCotScreen(navController, id, nombre)
        }
    }
}