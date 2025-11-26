package com.example.vissoapp3.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vissoapp3.R
import com.example.vissoapp3.data.api.RetrofitClient
import com.example.vissoapp3.data.model.Producto
import com.example.vissoapp3.ui.components.AppBottomBar
import com.example.vissoapp3.ui.components.Navbar
import kotlinx.coroutines.launch

@Composable
fun LentesOpticosScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Cargar productos al entrar
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.obtenerProductos()
                if (response.isSuccessful) {
                    // Filtramos solo los ópticos ('O')
                    productos = response.body()?.filter { it.tipo == "O" } ?: emptyList()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error cargando productos", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = { Navbar(R.drawable.logo, navController) },
        bottomBar = { AppBottomBar(navController) }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        "Lentes Ópticos",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                items(productos) { producto ->
                    ProductoCard(producto, navController)
                }
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(producto.nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Stock: ${producto.stock}", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "$${producto.precio}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
                Button(
                    onClick = {
                        // Navegamos al formulario pasando el ID y Nombre del producto
                        // Usamos un formato simple: cotizacion/{id}/{nombre}
                        navController.navigate("cotizacion/${producto.id}/${producto.nombre}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Text("Cotizar")
                }
            }
        }
    }
}