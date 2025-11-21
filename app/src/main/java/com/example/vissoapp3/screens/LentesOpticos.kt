package com.example.vissoapp3.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vissoapp3.R
import com.example.vissoapp3.Routes
import com.example.vissoapp3.ui.components.Navbar
import com.example.vissoapp3.data.viewmodel.LentesOpticosViewModel
import com.example.vissoapp3.data.ProductItem
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.vissoapp3.ui.components.AppBottomBar

@Composable
fun LentesOpticosScreen(
    navController: NavController,
    viewModel: LentesOpticosViewModel = LentesOpticosViewModel()
) {
    val productosState = viewModel.productos.collectAsState()

    // Convertimos a ProductItem sin modificar el diseño
    val products = productosState.value.map {
        ProductItem(
            name = it.nombre,
            brand = it.marca?.nombre ?: "",
            price = it.precio.toInt(),
            imageRes = R.drawable.lente1 // Esto luego será reemplazado por la imagen real
        )
    }

    Scaffold(
        topBar = {
            Navbar(
                logoResId = R.drawable.logo,
                onLogoClick = { navController.navigate(Routes.Home) },
                showBackButton = true,
                menuItems = listOf("Inicio", "Categorías", "Cerrar sesión"),
                onMenuItemClick = { item ->
                    when (item) {
                        "Inicio" -> navController.navigate(Routes.Home)
                        "Categorías" -> navController.navigate(Routes.Categories)
                        "Cerrar sesión" -> navController.navigate(Routes.Login)
                    }
                }
            )
        },
        bottomBar = {
            AppBottomBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF2196F3))
            ) {
                Text(
                    text = "Lentes ópticos",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    ProductBox(product, navController)
                }
            }
        }
    }
}

@Composable
fun ProductBox(product: ProductItem, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp)
            .clickable {},
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.brand,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("${Routes.FormularioCot}/${Uri.encode(product.name)}") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Comprar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$${product.price} CLP",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}