package com.example.vissoapp3.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vissoapp3.R
import com.example.vissoapp3.Routes
import com.example.vissoapp3.data.model.Producto
import com.example.vissoapp3.data.viewmodel.LentesOpticosViewModel
import com.example.vissoapp3.data.viewmodel.LentesUiState
import com.example.vissoapp3.ui.components.AppBottomBar
import com.example.vissoapp3.ui.components.Navbar

@Composable
fun LentesOpticosScreen(
    navController: NavController,
    viewModel: LentesOpticosViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.uiState.collectAsState()

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
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            // Encabezado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF2196F3)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Lentes ópticos",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            // Contenido según el Estado (Loading, Success, Error)
            when (val uiState = state) {
                is LentesUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF2196F3))
                    }
                }
                is LentesUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Ups! Algo salió mal.", fontWeight = FontWeight.Bold)
                            Text(text = uiState.message, color = Color.Red, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.cargarProductos() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
                is LentesUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.productos) { producto ->
                            ProductBox(producto, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductBox(producto: Producto, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Podrías ir al detalle aquí */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // IMAGEN DINÁMICA CON COIL
            AsyncImage(
                model = producto.imagenUrl ?: R.drawable.lente1, // Fallback si es null
                contentDescription = producto.nombre,
                placeholder = painterResource(R.drawable.lente1), // Mientras carga
                error = painterResource(R.drawable.lente1),       // Si falla la carga
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
                    text = producto.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                producto.marca?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.nombre,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                // Ejemplo de descripción corta
                if (!producto.descripcion.isNullOrBlank()) {
                    Text(
                        text = producto.descripcion,
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        // Pasamos el nombre al formulario
                        navController.navigate("${Routes.FormularioCot}/${Uri.encode(producto.nombre)}")
                    },
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
                    text = "$${producto.precio.toInt()} CLP",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}