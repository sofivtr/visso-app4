package com.example.vissoapp3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vissoapp3.R
import com.example.vissoapp3.ui.components.Navbar
import com.example.vissoapp3.ui.components.AppBottomBar

data class CategoryItem(val name: String, val imageRes: Int, val route: String)

@Composable
fun CategoriesScreen(navController: NavController) {
    // Aquí definimos las rutas de navegación
    val categories = listOf(
        CategoryItem("Lentes Ópticos", R.drawable.icon_frames, "lentes_opticos"), // Esta ruta debe coincidir con AppNavigation
        CategoryItem("Lentes de Sol", R.drawable.icon_sunglasses, "home"), // Por ahora al home o crea otra pantalla
        CategoryItem("Accesorios", R.drawable.icon_accessories, "home")
    )

    Scaffold(
        topBar = { Navbar(R.drawable.logo, navController) },
        bottomBar = { AppBottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Categorías",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { item ->
                    CategoryBox(item, navController)
                }
            }
        }
    }
}

@Composable
fun CategoryBox(item: CategoryItem, navController: NavController) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .clickable { navController.navigate(item.route) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
        }
    }
}