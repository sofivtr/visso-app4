package com.example.vissoapp3.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vissoapp3.R
import com.example.vissoapp3.Routes

@Composable
fun AppBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.Home,
            onClick = { navController.navigate(Routes.Home) {
                launchSingleTop = true
            } },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Categories,
            onClick = { navController.navigate(Routes.Categories) {
                launchSingleTop = true
            } },
            icon = { Icon(Icons.Default.List, contentDescription = "Categorías") },
            label = { Text("Categorías") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Carrito,
            onClick = { navController.navigate(Routes.Carrito) {
                launchSingleTop = true
            } },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") }
        )
    }
}
